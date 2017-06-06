package com.sk.netty.serializable;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/5/15
 * Time: 12:45
 */
public class SubscribeResp implements Serializable {

	private static final long serialVersionUID = 1L;

	private int subReqId;

	private int respCode;

	private String desc;

	public int getSubReqId() {
		return subReqId;
	}

	public void setSubReqId(int subReqId) {
		this.subReqId = subReqId;
	}

	public int getRespCode() {
		return respCode;
	}

	public void setRespCode(int respCode) {
		this.respCode = respCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("SubscribeResp{");
		sb.append("subReqId=").append(subReqId);
		sb.append(", respCode='").append(respCode).append('\'');
		sb.append(", desc='").append(desc).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
