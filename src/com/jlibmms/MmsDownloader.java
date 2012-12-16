package com.jlibmms;

/*
 * Copyright (c) 2012 Kyle Turner
 * All rights reserved.
 * 
 * This file demonstrates usage of jlibmms (a Java wrapper for libmms).
 * This file is released under the FreeBSD license.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following condition is met: 
 * 
 * 1.  Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 * 2.  Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;

public class MmsDownloader
{
	public static final String source = "mms://lang.stanford.edu/courses/aa240a/121207ps/121207-aa240aps-5OvCXyLBmnnaro788lz5-500.wmv";
	
	public static void main(String[] args) throws IOException, URISyntaxException
	{
		// Open an MMS stream
		MmsStream stream = new MmsStream(new URI(source));
		
		// Get metadata about the stream
		System.out.println("Stream length is: "+stream.getLengthInSeconds()+" seconds");
		System.out.println("Stream length is: "+stream.getLengthInBytes()+" bytes");
		System.out.println("Stream is seekable: "+stream.isSeekable());

		// Download the stream to a file
		File destination = new File(source.substring(source.lastIndexOf('/')+1));
		System.out.println("Starting download to: "+destination.getAbsolutePath());
		FileUtils.copyInputStreamToFile(stream, destination);
		System.out.println("Done downloading: "+destination.getName());
		
		// Close the connection to free up resources
		stream.close();
	}
}
