package com.sk.netty.httpxml;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/4
 * Time: 15:45
 */
public class HttpXmlResponse {
	private FullHttpResponse httpResponse;
	private Object result;

	public HttpXmlResponse(FullHttpResponse httpResponse, Object result) {
		this.httpResponse = httpResponse;
		this.result = result;
	}

	public final FullHttpResponse getHttpResponse() {
		return httpResponse;
	}

	public final void setHttpResponse(FullHttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public final Object getResult() {
		return result;
	}

	public final void setResult(Object body) {
		this.result = body;
	}
}
