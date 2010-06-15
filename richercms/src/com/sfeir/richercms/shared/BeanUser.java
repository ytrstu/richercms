package com.sfeir.richercms.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BeanUser implements Serializable{

    private Long id;
	private String emailAddress;
	private String nickname;
	private boolean loggedIn;
	private boolean isAdmin;	
		
	public BeanUser() {
		super();
		this.emailAddress = "";
		this.nickname = "";
		this.loggedIn = false;
		this.isAdmin = false;
	}
	
	public BeanUser(String emailAddress, String nickname,
			boolean isAdmin) {
		super();
		this.emailAddress = emailAddress;
		this.nickname = nickname;
		this.loggedIn = false;
		this.isAdmin = isAdmin;
	}
	
	public BeanUser(String emailAddress, String nickname,
			boolean loggedIn, boolean isAdmin) {
		super();
		this.emailAddress = emailAddress;
		this.nickname = nickname;
		this.loggedIn = loggedIn;
		this.isAdmin = isAdmin;
	}
	
	public BeanUser(Long id, String emailAddress, String nickname,
			boolean loggedIn, boolean isAdmin) {
		super();
		this.id = id;
		this.emailAddress = emailAddress;
		this.nickname = nickname;
		this.loggedIn = loggedIn;
		this.isAdmin = isAdmin;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
