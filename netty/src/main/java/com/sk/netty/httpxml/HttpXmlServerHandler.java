package com.sk.netty.httpxml;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/4
 * Time: 18:25
 */
public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {

	@Override
	protected void messageReceived(final ChannelHandlerContext ctx, HttpXmlRequest xmlRequest) throws Exception {
		HttpRequest request = xmlRequest.getRequest();
		Order order = (Order)xmlRequest.getBody();
		System.out.println("Http server receive request : " + order);
		doBusiness(order);
		ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null, order));
		if(!isKeepAlive(request)) {
			future.addListener(new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete(Future future) throws Exception {
					ctx.close();
				}
			});
		}
	}

	private void doBusiness(Order order) {
		order.getCustomer().setFirstName("张");
		order.getCustomer().setLastName("三");
		List<String> midNames = new ArrayList<>();
		midNames.add("李四");
		order.getCustomer().setMiddleNames(midNames);
		Address address = order.getBillTo();
		address.setCity("洛阳");
		address.setCountry("大唐");
		address.setState("河南道");
		address.setPostCode("123456");
		order.setBillTo(address);
		order.setShipTo(address);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		if(ctx.channel().isActive()) {
			sendError(ctx, INTERNAL_SERVER_ERROR);
		}
	}

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, status, Unpooled.copiedBuffer("失败：" + status.toString() + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/plain;charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}
