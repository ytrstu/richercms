package com.sfeir.richercms.server.business;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity(name="LogInfo")
public class LogInfo {
	
	@Id
    private Long id;
	private String className;
	private String methodName;
	private String desc;
	private long duration;
	
	public LogInfo() {
		super();
		this.className = "";
		this.methodName = "";
		this.desc = "";
		this.duration = 0;
	}
	
	public LogInfo(String className, String methodName, String desc) {
		super();
		this.className = className;
		this.methodName = methodName;
		this.desc = desc;
		this.duration = 0;
	}
	
	public LogInfo(String className, String methodName, String desc,
			long duration) {
		super();
		this.className = className;
		this.methodName = methodName;
		this.desc = desc;
		this.duration = duration;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClassName() {
		return this.className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return this.methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getDesc() {
		return this.desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public long getDuration() {
		return this.duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
}
