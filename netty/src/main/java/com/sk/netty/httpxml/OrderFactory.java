package com.sk.netty.httpxml;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/7/4
 * Time: 18:10
 */
public class OrderFactory {
	public static Order create(long orderId) {
		Order order = new Order();
		order.setOrderNumber(orderId);
		order.setTotal(9999.999f);
		Address address = new Address();
		address.setCity("上海市");
		address.setCountry("中国");
		address.setPostCode("200000");
		address.setState("上海市");
		address.setStreet1("世纪大道");
		order.setBillTo(address);
		Customer customer = new Customer();
		customer.setCustomerNumer(orderId);
		customer.setFirstName("孙");
		customer.setLastName("锟");
		order.setCustomer(customer);
		order.setShipping(Shipping.INTERNATIONAL_MAIL);
		order.setShipTo(address);
		return order;
	}
}
