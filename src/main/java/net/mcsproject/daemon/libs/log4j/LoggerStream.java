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

package net.mcsproject.daemon.libs.log4j;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.io.OutputStream;

@Log4j2
public class LoggerStream extends OutputStream {
	private final Level logLevel;

	public LoggerStream(Level logLevel) {
		super();
		this.logLevel = logLevel;
	}

	@Override
	public void write(byte[] b) throws IOException {
		log(new String(b));
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		log(new String(b, off, len));
	}

	@Override
	public void write(int b) throws IOException {
		log(String.valueOf((char) b));
	}

	private void log(String message) {
		if (!message.trim().isEmpty()) {
			log.log(logLevel, message);
		}
	}
}
