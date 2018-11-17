package com.haima.regeneration.model;

import java.io.Serializable;

import com.haima.regeneration.config.Configurate;

public class FileEvent extends Fileinfo implements Serializable {

	private static final long serialVersionUID = 1390898164972382254L;
	
	// 上传主机名
/*	String hostName;
	String fileName;*/
	String event;

	public FileEvent() {
		//this.hostName = Configurate.hostName;
		super();
	}
	
	public FileEvent(String fileName,long size,String event) {
		this.setFilename(fileName); 
		this.setFilesize(size);
		this.event= event;
		this.setUphostname(Configurate.hostName);
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
}
