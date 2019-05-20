package com.sk.netty.httpxml;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/6/29
 * Time: 18:09
 */
public class Address {
	private  String street1;

	private String street2;

	private String city;

	private String state;

	private String postCode;

	private String country;

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Address{");
		sb.append("street1='").append(street1).append('\'');
		sb.append(", street2='").append(street2).append('\'');
		sb.append(", city='").append(city).append('\'');
		sb.append(", state='").append(state).append('\'');
		sb.append(", postCode='").append(postCode).append('\'');
		sb.append(", country='").append(country).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
