package com.sk.netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaders.*;
import static io.netty.handler.codec.http.HttpVersion.*;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/6/13
 * Time: 14:19
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

	private WebSocketServerHandshaker handshaker;

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof FullHttpRequest) {
			System.out.println("handShank message:" + msg);
			handleHttpRequest(ctx, (FullHttpRequest)msg);
		} else if(msg instanceof WebSocketFrame) {
			System.out.println("websocket message:" + msg);
			handleWebSocketFrame(ctx, (WebSocketFrame)msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	protected void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
		if(!req.getDecoderResult().isSuccess() || !"websocket".equals(req.headers().get("upgrade"))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
		}
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:9999/websocket", null, false);
		handshaker = wsFactory.newHandshaker(req);
		if(null == handshaker) {
			WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		// 判断是否是关闭按连的指令
		if(frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}
		if(frame instanceof PingWebSocketFrame) {
			ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		//本例程仅支持文本消息，不支持二进制消息
		if(!(frame instanceof  TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format(
				"%s frame types not supported", frame.getClass().getName()));
		}
		//返回应答消息
		String request = ((TextWebSocketFrame)frame).text();
		System.out.println(String.format("%s received %s", ctx.channel(), request));
		ctx.channel().write(
			new TextWebSocketFrame(request + " ， 欢迎使用Netty WebSocket服务，现在时刻： " + new Date().toString()));
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
		if(res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			setContentLength(res, res.content().readableBytes());
		}
		//如果是非Kepp-Alive， 关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if(!isKeepAlive(req) || res.getStatus().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
