package com.twinmask.gps.msgservice.entity;

import io.netty.channel.Channel;

public class MyChannel {
	private Channel chanel;
	private boolean is300;
	private boolean isOilTest;
	private String type;
	private String version;
	private String clientKey;

	public MyChannel() {
		super();
	}

	public MyChannel(Channel chanel, boolean is300) {
		super();
		this.chanel = chanel;
		this.is300 = is300;
	}

	public MyChannel(Channel chanel, boolean is300, boolean isOilTest) {
		super();
		this.chanel = chanel;
		this.is300 = is300;
		this.isOilTest = isOilTest;
	}

	public MyChannel(Channel chanel, boolean is300, String type, String version, String clientKey) {
		super();
		this.chanel = chanel;
		this.is300 = is300;
		this.type = type;
		this.version = version;
		this.clientKey = clientKey;
	}

	public Channel getChanel() {
		return chanel;
	}

	public void setChanel(Channel chanel) {
		this.chanel = chanel;
	}

	public boolean isIs300() {
		return is300;
	}

	public void setIs300(boolean is300) {
		this.is300 = is300;
	}

	public boolean isOilTest() {
		return isOilTest;
	}

	public void setOilTest(boolean isOilTest) {
		this.isOilTest = isOilTest;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

}
