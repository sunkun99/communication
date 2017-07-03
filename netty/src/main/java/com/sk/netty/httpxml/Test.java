package com.sk.netty.httpxml;

import org.jibx.runtime.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/3
 * Time: 20:16
 */
public class Test {
	private IBindingFactory factory = null;
	private StringWriter writer = null;
	private StringReader reader = null;
	private final static String CHARSET_NAME = "UTF-8";

	private String encode2Xml(Order order) throws JiBXException, IOException {
		factory = BindingDirectory.getFactory(Order.class);
		writer = new StringWriter();
		IMarshallingContext mctx = factory.createMarshallingContext();
		mctx.setIndent(2);
		mctx.marshalDocument(order, CHARSET_NAME, null, writer);
		String xmlStr = writer.toString();
		writer.close();
		System.out.println(xmlStr.toString());
		return xmlStr;
	}

	private  Order decode2Order(String xmlBody) throws JiBXException {
		reader = new StringReader(xmlBody);
		IUnmarshallingContext uctx = factory.createUnmarshallingContext();
		Order order = (Order)uctx.unmarshalDocument(reader);
		return order;
	}

	public static void main(String[] args) throws JiBXException, IOException {
		Test test = new Test();
		Order order = new Order();
		order.setBillTo(null);
		order.setCustomer(null);
		order.setOrderNumber(1);
		order.setShipping(null);
		order.setShipTo(null);
		order.setTotal(1.5f);
		String body = test.encode2Xml(order);
		Order order2 = test.decode2Order(body);
		System.out.println(order2);
	}
}
