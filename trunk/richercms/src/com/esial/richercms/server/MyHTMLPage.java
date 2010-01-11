package com.esial.richercms.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MyHTMLPage {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String qualified_name;
	@Persistent
	private String title;
	@Persistent
	private User user;
	@Persistent
	private Date createDate;
	@Persistent
	private String html_content;
	
	public MyHTMLPage(){
		this.createDate=new Date();
	}
	
	public MyHTMLPage(User user,String qn, String t, String content){
		this();
		this.user=user;
		this.qualified_name=qn;
		this.title=t;
		this.html_content=content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQualified_name() {
		return qualified_name;
	}

	public void setQualified_name(String qualifiedName) {
		qualified_name = qualifiedName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getHtml_content() {
		return html_content;
	}

	public void setHtml_content(String htmlContent) {
		html_content = htmlContent;
	}

}
