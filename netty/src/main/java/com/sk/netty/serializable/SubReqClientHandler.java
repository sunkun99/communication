package com.sk.netty.serializable;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/5/15
 * Time: 13:52
 */
public class SubReqClientHandler extends ChannelHandlerAdapter {
	public SubReqClientHandler() {

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		for(int i = 0; i < 10; i++) {
			ctx.write(subReq(i));
		}
		ctx.flush();
	}

	private SubscribeReq subReq(int i) {
		SubscribeReq req = new SubscribeReq();
		req.setAddress("南京市江宁区方山国家地质公园");
		req.setPhoneNumer("1360000xxxx");
		req.setProductName("Netty 权威指南");
		req.setSubReqId(i);
		req.setUserName("sk");
		return req;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		System.out.println("Receive server response : [" + msg + "]");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
