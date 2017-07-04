package com.sk.netty.httpxml;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/4
 * Time: 16:54
 */
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {


	@Override
	protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {
		ByteBuf body = encode0(ctx, msg.getResult());
		FullHttpResponse response = msg.getHttpResponse();
		if(null == response) {
			response = new DefaultFullHttpResponse(HTTP_1_1, OK, body);
		} else {
			response = new DefaultFullHttpResponse(msg.getHttpResponse().getProtocolVersion(), msg.getHttpResponse().getStatus(), body);
		}
		response.headers().set(CONTENT_TYPE, "text/xml");
		setContentLength(response, body.readableBytes());
		out.add(response);
	}

	public static void setContentLength(HttpMessage message, long length) {
		message.headers().set(HttpHeaders.Names.CONTENT_LENGTH, (Object)Long.valueOf(length));
	}
}
