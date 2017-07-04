package com.sk.netty.httpxml;

import io.netty.channel.AddressedEnvelope;
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
		Customer customer = new Customer();
		customer.setCustomerNumer(1);
		customer.setFirstName("孙");
		customer.setLastName("锟");
		Address address = new Address();
		address.setCountry("中国");
		address.setState("上海市");
		address.setCity("上海市");
		address.setPostCode("200000");
		address.setStreet1("浦东新区");
		address.setStreet2("世纪大道");
		Address address1 = new Address();
		address1.setCountry("中国");
		address1.setState("上海市");
		address1.setCity("上海市");
		address1.setPostCode("200000");
		address1.setStreet1("徐汇区");
		address1.setStreet2("凯滨路");
		Order order = new Order();
		order.setBillTo(address);
		order.setCustomer(customer);
		order.setOrderNumber(1);
		order.setShipping(Shipping.INTERNATIONAL_MAIL);
		order.setShipTo(address1);
		order.setTotal(9999f);
		String body = test.encode2Xml(order);
		Order order2 = test.decode2Order(body);
		System.out.println(order2);
	}
}
