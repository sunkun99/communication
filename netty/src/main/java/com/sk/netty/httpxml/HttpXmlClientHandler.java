package com.sk.netty.httpxml;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/4
 * Time: 17:55
 */
public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));
		ctx.writeAndFlush(request);
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
		System.out.println("The client receive response of http header is : " + msg.getHttpResponse().headers().names());
		System.out.println("The client receive response of http body is : " + msg.getResult());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
