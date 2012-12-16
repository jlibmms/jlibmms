package com.jlibmms;
/*
 * Copyright (C) 2012 Kyle Turner
 *
 * This file is part of jlibmms, a Java wrapper for libmms.
 *
 * jlibmms is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * jlibmms is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;


public class MmsStream extends InputStream
{
	Pointer connection;
	
	long totalLength;
	long lengthRead;
	
	int position = 0;
	int max = 0;
	
	Memory buffer = new Memory(40960);
	
	public MmsStream(URI uri) throws IOException
	{
		connection = MMSX.NATIVE.mmsx_connect(null, null, uri.toASCIIString(), 1000000);
		if(connection == null) throw new FileNotFoundException(uri.toASCIIString());
		totalLength = MMSX.NATIVE.mmsx_get_length(connection);
	}
	
	public int getLengthInBytes()
	{
		return MMSX.NATIVE.mmsx_get_length(connection);
	}
	
	public double getLengthInSeconds()
	{
		return MMSX.NATIVE.mmsx_get_time_length(connection);
	}
	
	public boolean isSeekable()
	{
		return MMSX.NATIVE.mmsx_get_seekable(connection) > 0;
	}
	
	@Override
	public void close()
	{
		MMSX.NATIVE.mmsx_close(connection);
	}
	
	@Override
	public void finalize()
	{
		close();
	}

	@Override
	public int read() throws IOException
	{
		if(position == 0) { max = MMSX.NATIVE.mmsx_read(null, connection, buffer, (int)buffer.size()); }
		if(max == 0) return -1;
		int value = buffer.getByte(position);
		position = (position+1) % max;
		lengthRead += 1;
		return value & 0xff;
	}

	@Override
	public int read(byte[] b) throws IOException
	{
		return read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int off, int len)  throws IOException
	{
		if(position == 0) max = MMSX.NATIVE.mmsx_read(null, connection, buffer, (int)buffer.size());
		if(max == 0) return -1;
		int lengthOfRead = Math.min(max-position, len);
		System.arraycopy(buffer.getByteArray(position, lengthOfRead), 0, b, off, lengthOfRead);
		position = (position+lengthOfRead)%max;
		lengthRead += lengthOfRead;
		return lengthOfRead;
	}
}

