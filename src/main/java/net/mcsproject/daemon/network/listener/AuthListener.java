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

package net.mcsproject.daemon.network.listener;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import net.mcsproject.daemon.network.packet.PacketHandler;
import net.mcsproject.daemon.network.packet.PacketListener;
import net.mcsproject.daemon.network.packets.PacketAuthResponse;

@Log4j2
public class AuthListener extends PacketListener {

	@PacketHandler
	public void onAuthResponse(ChannelHandlerContext ctx, PacketAuthResponse packet) {
		log.info("Response " + (packet.isOk() ? "ok" : "not ok"));
	}

}
