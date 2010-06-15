package com.sfeir.richercms.server.business;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

/**
 * Represent a user
 * @author homberg.g
 */
@Entity(name="User")
public class CmsUser {

	@Id
    private Long id;
	private String emailAddress;
	private String nickname;
	private boolean loggedIn;
	private boolean isAdmin;
	
	
	public CmsUser() {
		super();
		this.emailAddress = "";
		this.nickname = "";
		this.loggedIn = false;
		this.isAdmin = false;
	}
	
	public CmsUser(String emailAddress, String nickname,
			boolean isAdmin) {
		super();
		this.emailAddress = emailAddress;
		this.nickname = nickname;
		this.loggedIn = false;
		this.isAdmin = isAdmin;
	}
	
	public CmsUser(String emailAddress, String nickname,
			boolean loggedIn, boolean isAdmin) {
		super();
		this.emailAddress = emailAddress;
		this.nickname = nickname;
		this.loggedIn = loggedIn;
		this.isAdmin = isAdmin;
	}
	
	public CmsUser(Long id, String emailAddress, String nickname,
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
