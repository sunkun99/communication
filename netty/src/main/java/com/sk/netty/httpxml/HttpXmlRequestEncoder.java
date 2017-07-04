package com.sk.netty.httpxml;

import com.sun.net.httpserver.HttpHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.net.InetAddress;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/4
 * Time: 15:44
 */
public class HttpXmlRequestEncoder extends AbstractHttpXmlEncoder<HttpXmlRequest> {
	@Override
	protected  void encode(ChannelHandlerContext ctx, HttpXmlRequest msg, List<Object> out) throws  Exception {
		ByteBuf body = encode0(ctx, msg.getBody());
		FullHttpRequest request = msg.getRequest();
		if(null == request) {
			request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/do", body);
			HttpHeaders headers = request.headers();
			headers.set(HttpHeaders.Names.HOST, InetAddress.getLocalHost().getHostAddress());
			headers.set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.CLOSE);
			headers.set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP.toString() + "," + HttpHeaders.Values.DEFLATE.toString());
			headers.set(HttpHeaders.Names.ACCEPT_CHARSET, "ISO-8859-1,UTF-8;q=0.7,*;q=0.7");
			headers.set(HttpHeaders.Names.ACCEPT_LANGUAGE, "ZH");
			headers.set(HttpHeaders.Names.USER_AGENT, "Netty xml Http Client side");
			headers.set(HttpHeaders.Names.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			HttpHeaders.setContentLength(request, body.readableBytes());
			out.add(request);
		}
	}
}
