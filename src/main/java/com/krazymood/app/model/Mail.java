package com.krazymood.app.model;

import java.util.Map;

public class Mail {

    private String from;
    private String to;
    private String subject;
    public String scheme;
    public String serverPort;
    public String serverName;
	/* private List<Object> attachments; */
    private Map<String, Object> model;

    public Mail() {

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    

	/*
	 * public List<Object> getAttachments() { return attachments; }
	 * 
	 * public void setAttachments(List<Object> attachments) { this.attachments =
	 * attachments; }
	 */

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
    
    
}
