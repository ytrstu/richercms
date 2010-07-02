package com.sfeir.richercms.page.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.client.TagService;
import com.sfeir.richercms.page.server.business.Tag;
import com.sfeir.richercms.page.shared.BeanTag;

@SuppressWarnings("serial")
public class ServiceTagImpl extends RemoteServiceServlet implements TagService {

	static {
		ObjectifyService.register(Tag.class);
	}
	
	public List<BeanTag> getAllTags() {
		ArrayList<BeanTag> lst = new ArrayList<BeanTag>();
		Objectify ofy = ObjectifyService.begin();
		Query<Tag> tags = ofy.query(Tag.class);
		
		for(Tag tag : tags){
			lst.add(this.tagToBean(tag));
		}
		
		return lst;
	}
	
	public List<BeanTag> getTags(List<Long> tagids){
		ArrayList<BeanTag> beans = new ArrayList<BeanTag>();
		
		Objectify ofy = ObjectifyService.begin();
		Map<Long,Tag> tags = ofy.get(Tag.class,tagids);
		
		for(Tag tag : tags.values()){
			beans.add(this.tagToBean(tag));
		}
		
		return beans;
	}
	
	public BeanTag getTag(Long id) {
		Objectify ofy = ObjectifyService.begin();
		Tag tag = ofy.get(Tag.class, id);
		
		if(tag!= null)
			return this.tagToBean(tag);
		return null;
	}
	
	public void deleteTag(Long id) {
		Objectify ofy = ObjectifyService.begin();
		Tag tag = ofy.get(Tag.class, id);
		
		if(tag!= null)
			ofy.delete(tag);
	}
	
	public void addTag(BeanTag bean){
		Tag tag = this.beanToTag(bean);
		Objectify ofy = ObjectifyService.begin();
		ofy.put(tag);
	}
	
	public void updateTag(BeanTag bean){
		Tag tag = this.beanToTag(bean);
		Objectify ofy = ObjectifyService.begin();
		ofy.put(tag);
	}
	
	private Tag beanToTag(BeanTag bean){
		return new Tag(bean.getId(),
				bean.getTagName(),
				bean.getShortLib(),
				bean.getDescription(),
				bean.isTextual());
	}
	
	private BeanTag tagToBean(Tag tag){
		return new BeanTag(tag.getId(),
				tag.getTagName(),
				tag.getShortLib(),
				tag.getDescription(),
				tag.isTextual());
	}
}
