package com.haima.regeneration.model;

import java.io.Serializable;
import java.util.List;

public class VideoTaskResult implements Serializable{

	private static final long serialVersionUID = 5901584438344227930L;
	String msg;
	int total;
	int code;
	List<VideoTask> list;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public List<VideoTask> getList() {
		return list;
	}
	public void setList(List<VideoTask> list) {
		this.list = list;
	}
}
