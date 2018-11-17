package com.haima.regeneration.model;

import java.io.Serializable;

public class VideoTask implements  Serializable {
	
	private static final long serialVersionUID = 8545399225774436011L;

	private Long id;

	private String filename;

	private Integer tstatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getTstatus() {
		return tstatus;
	}

	public void setTstatus(Integer tstatus) {
		this.tstatus = tstatus;
	}
}
