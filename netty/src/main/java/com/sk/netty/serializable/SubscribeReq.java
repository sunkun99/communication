package com.sk.netty.serializable;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/5/15
 * Time: 12:45
 */
public class SubscribeReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private int subReqId;

	private String userName;

	private String productName;

	private String phoneNumer;

	private String address;

	public int getSubReqId() {
		return subReqId;
	}

	public void setSubReqId(int subReqId) {
		this.subReqId = subReqId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPhoneNumer() {
		return phoneNumer;
	}

	public void setPhoneNumer(String phoneNumer) {
		this.phoneNumer = phoneNumer;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SubscribeReq{");
		sb.append("subReqId=").append(subReqId);
		sb.append(", userName='").append(userName).append('\'');
		sb.append(", productName='").append(productName).append('\'');
		sb.append(", phoneNumer='").append(phoneNumer).append('\'');
		sb.append(", address='").append(address).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
