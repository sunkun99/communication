package com.sk.netty.serializable;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/5/15
 * Time: 13:37
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		SubscribeReq req = (SubscribeReq)msg;
		if("sk".equalsIgnoreCase(req.getUserName())) {
			System.out.println("Service accept client subscribe req : [" + req.toString() +  "]");
			ctx.writeAndFlush(resp(req.getSubReqId()));
		}
	}

	private SubscribeResp resp(int subReqId) {
		SubscribeResp resp = new SubscribeResp();
		resp.setSubReqId(subReqId);
		resp.setRespCode(0);
		resp.setDesc("Netty book order succeed, 3 days later, sent to the designate address");
		return resp;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();;
		ctx.close();
	}
}
