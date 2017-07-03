package com.sk.netty.httpxml;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/6/16
 * Time: 11:20
 */
public class Order {
	private long orderNumber;

	private Customer customer;

	private Address billTo;

	private Shipping shipping;

	private Address shipTo;

	private Float total;

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getBillTo() {
		return billTo;
	}

	public void setBillTo(Address billTo) {
		this.billTo = billTo;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public Address getShipTo() {
		return shipTo;
	}

	public void setShipTo(Address shipTo) {
		this.shipTo = shipTo;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Order{");
		sb.append("orderNumber=").append(orderNumber);
		sb.append(", customer=").append(customer);
		sb.append(", billTo=").append(billTo);
		sb.append(", shipping=").append(shipping);
		sb.append(", shipTo=").append(shipTo);
		sb.append(", total=").append(total);
		sb.append('}');
		return sb.toString();
	}
}
