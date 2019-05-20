package com.sk.netty.httpxml;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/4
 * Time: 17:00
 */
public class HttpXmlResponseDecoder extends AbstractHttpXmlDecoder<DefaultFullHttpResponse> {

	protected HttpXmlResponseDecoder(Class<?> clazz) {
		super(clazz, false);
	}

	public HttpXmlResponseDecoder(Class<?> clazz, boolean isPringLog) {
		super(clazz, isPringLog);
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse msg, List<Object> out) throws Exception {
		HttpXmlResponse resHttpXmlResponse = new HttpXmlResponse(msg, decode0(ctx, msg.content()));
		out.add(resHttpXmlResponse);
	}
}
