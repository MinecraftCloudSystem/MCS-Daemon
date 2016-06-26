/*
 *     MCS - Minecraft Cloud System
 *     Copyright (C) 2016
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.mcsproject.daemon.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.mcsproject.daemon.network.listener.AuthListener;
import net.mcsproject.daemon.network.packet.ListenerRegistry;
import net.mcsproject.daemon.network.packet.PacketMessageHandler;
import net.mcsproject.daemon.network.packet.PacketRegistry;
import net.mcsproject.daemon.network.packets.*;

@Log4j2
public class NetClient {

	@Getter
	private PacketRegistry packetRegistry;
	@Getter
	private ListenerRegistry listenerRegistry;

	public NetClient(String ip, int port) {
		this.packetRegistry = new PacketRegistry();
		this.listenerRegistry = new ListenerRegistry();

		this.registerPackets();
		this.registerListeners();

		new Thread(() -> {
			EventLoopGroup bossGroup = new NioEventLoopGroup();

			try {
				Bootstrap bootstrap = new Bootstrap();
				bootstrap.group(bossGroup);
				bootstrap.channel(NioSocketChannel.class);
				bootstrap.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline()
								.addLast(new PacketDecoder(packetRegistry))
								.addLast(new PacketEncoder(packetRegistry))
								.addLast(new PacketMessageHandler(listenerRegistry));
					}
				});
				bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

				ChannelFuture future = bootstrap.connect(ip, port).sync();
				log.info("Connection established!");
				future.channel().closeFuture().sync();
				log.info("Connection closed!");
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				bossGroup.shutdownGracefully();
			}
		}).start();
	}

	private void registerPackets() {
		PacketRegistry reg = this.packetRegistry;

		reg.addPacket((byte) 0x00, PacketAuth.class);
		reg.addPacket((byte) 0x01, PacketAuthResponse.class);
		reg.addPacket((byte) 0x02, PacketReady.class);
		reg.addPacket((byte) 0x03, PacketRequest.class);
		reg.addPacket((byte) 0x04, PacketServerStart.class);
		reg.addPacket((byte) 0x05, PacketServerStarted.class);
		reg.addPacket((byte) 0x06, PacketServerStatus.class);
	}

	private void registerListeners() {
		ListenerRegistry reg = this.listenerRegistry;

		reg.register(new AuthListener());
	}

}
