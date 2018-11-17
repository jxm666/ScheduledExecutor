package com.haima.regeneration.model;

import java.io.Serializable;

import com.haima.regeneration.config.Configurate;

public class Fileinfo implements Serializable {

	private static final long serialVersionUID = 8823809938985574119L;
	// 上传主机名
	String uphostname;
	String filename;
	long filesize;

	public Fileinfo() {
		this.uphostname = Configurate.hostName;
	}
	public Fileinfo(String fileName,long size) {
		this.filename = fileName;
		this.filesize = size;
		this.uphostname = Configurate.hostName;
	}
	
	public String getUphostname() {
		return uphostname;
	}
	public void setUphostname(String uphostname) {
		this.uphostname = uphostname;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
}
