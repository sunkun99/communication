package com.sk.netty.filedownload;

import io.netty.channel.*;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/5
 * Time: 18:42
 */
public class FileServerHandler extends SimpleChannelInboundHandler<String> {

	private static final String CR = System.getProperty("line.separator");

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
		File file = new File(msg);

		if(file.exists()) {
			if(!file.isFile()) {
				ctx.writeAndFlush("Not a file : " + file + CR);
				return;
			}
			ctx.write(file + " " + file.length() + CR);
			RandomAccessFile randomAccessFile = new RandomAccessFile(msg, "r");
			FileRegion region = new DefaultFileRegion(randomAccessFile.getChannel(), 0, randomAccessFile.length());
			ctx.write(region);
			ctx.writeAndFlush(CR);
			randomAccessFile.close();
		} else {
			ctx.writeAndFlush("File not found: " + file + CR);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
