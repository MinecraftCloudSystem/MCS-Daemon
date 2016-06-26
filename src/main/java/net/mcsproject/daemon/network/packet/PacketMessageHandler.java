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

package net.mcsproject.daemon.network.packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.mcsproject.daemon.network.packets.PacketAuth;

import java.io.IOException;

@AllArgsConstructor
@Getter
@Log4j2
public class PacketMessageHandler extends SimpleChannelInboundHandler<Packet> {

	private ListenerRegistry listenerRegistry;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(new PacketAuth("test"));
		log.info("Sent");
	}

	@Override
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
		listenerRegistry.callEvent(channelHandlerContext, packet);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) {
			return;
		}
		cause.printStackTrace();
	}

}

