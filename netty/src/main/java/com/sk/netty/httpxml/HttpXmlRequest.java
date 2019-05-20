package com.sk.netty.httpxml;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/4
 * Time: 15:45
 */
public class HttpXmlRequest {
	private FullHttpRequest request;
	private Object body;

	public HttpXmlRequest(FullHttpRequest request, Object body) {
		this.request = request;
		this.body = body;
	}

	public final FullHttpRequest getRequest() {
		return request;
	}

	public final void setRequest(FullHttpRequest request) {
		this.request = request;
	}

	public final Object getBody() {
		return body;
	}

	public final void setBody(Object body) {
		this.body = body;
	}
}
