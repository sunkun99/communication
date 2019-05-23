package com.sk.netty.simple;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/5/5
 * Time: 14:02
 */
public class EchoClientHandler extends ChannelHandlerAdapter {
	private int counter;

	static final String ECHO_REQ = "Hi, sk. Welcome to Netty.$_";

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		for(int i = 0; i < 10; i++) {
			ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws  Exception {
		System.out.println("This is " +  ++counter + " times receive server : [" + msg + "]");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws  Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
