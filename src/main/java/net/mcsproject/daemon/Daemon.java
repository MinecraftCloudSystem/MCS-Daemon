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

package net.mcsproject.daemon;

import lombok.extern.log4j.Log4j2;
import net.mcsproject.daemon.libs.log4j.OutErrLogger;
import net.mcsproject.daemon.network.NetClient;

@Log4j2
public class Daemon {

	private static Daemon instance;

	private Daemon() {
		instance = this;
		if (System.console() == null) {
			log.error("Not supported Console, Use -console parameter");
			return;
		}
		OutErrLogger.setOutAndErrToLog();

		log.info("Starting daemon...");

		NetClient netClient = new NetClient("127.0.0.1", 27755);

		System.console().readLine();
	}

	public static void main(String[] args) {
		new Daemon();
	}

}
