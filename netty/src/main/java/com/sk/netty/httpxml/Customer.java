package com.sk.netty.httpxml;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/6/29
 * Time: 18:08
 */
public class Customer {
	private long customerNumer;

	private String firstName;

	private String lastName;

	private List<String> middleNames;

	public long getCustomerNumer() {
		return customerNumer;
	}

	public void setCustomerNumer(long customerNumer) {
		this.customerNumer = customerNumer;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(List<String> middleNames) {
		this.middleNames = middleNames;
	}


}
