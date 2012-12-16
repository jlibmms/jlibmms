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

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface MMSX extends Library
{
	MMSX NATIVE = (MMSX) Native.loadLibrary("libmms", MMSX.class);

	Pointer mmsx_connect(Pointer p1, Pointer p2, String url, int bitrate);

	int mmsx_get_length(Pointer mmsx_t);

	void mmsx_close(Pointer mmsx_t);

	long mmsx_get_current_pos(Pointer mmsx_t);

	double mmsx_get_time_length(Pointer mmsx_t);

	int mmsx_get_seekable(Pointer mmsx_t);

	long mmsx_seek(Pointer p1, Pointer p2, long position, int bitrate);

	int mmsx_time_seek(Pointer p1, Pointer p2, double d1);

	int mmsx_read(Pointer p1, Pointer p2, Memory str, int i);
}
