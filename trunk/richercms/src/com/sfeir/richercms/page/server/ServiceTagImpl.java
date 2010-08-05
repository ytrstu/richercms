package com.sfeir.richercms.page.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.client.TagService;
import com.sfeir.richercms.page.server.business.ArboPage;
import com.sfeir.richercms.page.server.business.DependentTag;
import com.sfeir.richercms.page.server.business.Tag;
import com.sfeir.richercms.page.server.business.Template;
import com.sfeir.richercms.page.shared.BeanDependentTag;
import com.sfeir.richercms.page.shared.BeanTag;

@SuppressWarnings("serial")
public class ServiceTagImpl extends RemoteServiceServlet implements TagService {

	static {
		ObjectifyService.register(Tag.class);
		ObjectifyService.register(DependentTag.class);
		ObjectifyService.register(Template.class);
		ObjectifyService.register(ArboPage.class);
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
	
	public List<BeanDependentTag> getAllDependentTag(Long pageId){
		ArrayList<BeanDependentTag> beans = new ArrayList<BeanDependentTag>();
		Objectify ofy = ObjectifyService.begin();
		
		Query<DependentTag> depTags = ofy.query(DependentTag.class).filter("pageId ", pageId);
		
		if(depTags.countAll() != 0){
			for(DependentTag dTag : depTags){
				beans.add(DependentTagToBean(dTag));
			}
		}
		
		return beans;
	}
	
	public List<Long> getAssociatedTag(Long pageId){
		Objectify ofy = ObjectifyService.begin();
		
		ArboPage page = ofy.get(ArboPage.class, pageId);
		
		if(page != null)
			return page.getTagsId();
		return null;
	}
	
	public void upDateDependentTag(List<BeanDependentTag> customTags) {
		Objectify ofy = ObjectifyService.begin();
		if(customTags.size() != 0){
			
			Query<DependentTag> depTags = ofy.query(DependentTag.class)
											.filter("pageId ", customTags.get(0).getPageId());
			// delete all dependentTags for a specific page
			ofy.delete(depTags);
			// add new dependentTag
			for(BeanDependentTag bean: customTags){
				ofy.put(beanToDependentTag(bean));
			}
		}
	}
	
	/*
	public List<Long> upDateDependentTag(List<BeanDependentTag> updateDTags, List<Long> addedTagsId,
			List<Long> deletedTags, HashMap<Long,String> customTag) {
		
		ArrayList<Long> lst = new ArrayList<Long>();
		Objectify ofy = ObjectifyService.begin();
		
		//modify all updated tag
		for(BeanDependentTag updtateDTag : updateDTags){
			ofy.put(this.beanToDependentTag(updtateDTag));
			//add updated DependentTag's id in list
			lst.add(updtateDTag.getId());
		}
		
		// delete all not necessary dependentTag 
		Map<Long,DependentTag> deletedDTags = ofy.get(DependentTag.class, deletedTags);
		ofy.delete(deletedDTags.values());
		
		//add new DependentTag
		DependentTag newDTag;
		for(Long addedTagId : addedTagsId) {
			String customValue = customTag.get(addedTagId);
			if(customValue == null)
				newDTag = new DependentTag(new Key<Tag>(Tag.class, addedTagId), "");
			else
				newDTag = new DependentTag(new Key<Tag>(Tag.class, addedTagId), customValue);
			
			ofy.put(newDTag);
			lst.add(newDTag.getId());
		}
		
		return lst;
	}*/
	
	public void deleteTag(Long id) {
		Objectify ofy = ObjectifyService.begin();
		Tag tag = ofy.get(Tag.class, id);
				
		if(tag!= null) {
			//delete tag in all template who associate this tag
			Query<Template> templates = ofy.query(Template.class).filter("associatedTags ", tag);
			for(Template template : templates){
				template.getAssociatedTags().remove(new Key<Tag>(Tag.class, id));
				ofy.put(template);
			}
			
			Query<ArboPage> pages = ofy.query(ArboPage.class).filter("tagsId", id);
			for(ArboPage page : pages){
				page.getTagsId().remove(id);
				ofy.put(page);
			}
			//delete tag in all arboPage who associate this tag
			Query<DependentTag> dTags = ofy.query(DependentTag.class).filter("correspTagId ", id);
			ofy.delete(dTags);
			//finally delete the tag
			ofy.delete(tag);
		}
	}
	
	public Long addTag(BeanTag bean){
		Tag tag = this.beanToTag(bean);
		Objectify ofy = ObjectifyService.begin();
		
		Query<Tag> sameNameTags = ofy.query(Tag.class).filter("tagName ", tag.getTagName());
		Query<Tag> sameShortLibTags = ofy.query(Tag.class).filter("shortLib ", tag.getShortLib());
		
		if(sameNameTags.countAll()==0 && sameShortLibTags.countAll()==0)
			ofy.put(tag);
			
		return tag.getId();
	}
	
	public Boolean updateTag(BeanTag bean){
		Tag tag = this.beanToTag(bean);
		Objectify ofy = ObjectifyService.begin();
		boolean sameName = false;
		boolean sameShortLib = false;
		
		Query<Tag> sameNameTags = ofy.query(Tag.class).filter("tagName ", tag.getTagName());
		Query<Tag> sameShortLibTags = ofy.query(Tag.class).filter("shortLib ", tag.getShortLib());
		
		// tagName and shortLib are modified but no other tag are the same value for both field
		if(sameNameTags.countAll()==0 && sameShortLibTags.countAll()==0){
			ofy.put(tag);
			return true;
		}
		
		//same tag  = no changement for the tagName
		if(sameNameTags.countAll()==1 && sameNameTags.get().getId().equals(bean.getId()))
			sameName = true;
		
		//same tag  = no changement for the shortLib
		if(sameShortLibTags.countAll()==1 && sameShortLibTags.get().getId().equals(bean.getId()))
			sameShortLib = true;
			
		if((sameShortLib && sameName) ||  // no changement in the tag
				(sameName && sameShortLibTags.countAll()==0) || // just tagName are modified
				(sameShortLib && sameNameTags.countAll()==0))   // just shortLib are modified
		{
			ofy.put(tag);
			return true;
		}
		return false;
	}
	
	/**
	 * Make a Tag with a BeanTag
	 * @param bean
	 * @return corresponding Tag
	 */
	private Tag beanToTag(BeanTag bean){
		return new Tag(bean.getId(),
				bean.getTagName(),
				bean.getShortLib(),
				bean.getDescription(),
				bean.isTextual());
	}
	
	/**
	 * Make a BeanTag with a Tag
	 * @param tag
	 * @return corresponding BeanTag
	 */
	private BeanTag tagToBean(Tag tag){
		return new BeanTag(tag.getId(),
				tag.getTagName(),
				tag.getShortLib(),
				tag.getDescription(),
				tag.isTextual());
	}
	
	/**
	 * Make a DependentTag with a BeanDependentTag
	 * @param bean
	 * @return corresponding DependentTag
	 */
	private DependentTag beanToDependentTag(BeanDependentTag bean){
		return new DependentTag(bean.getId(),
				bean.getPageId(),
				bean.getDependentTagId(),
				bean.getCustomName());
	}
	
	/**
	 * Make a BeanDependentTag with a DependentTag
	 * @param tag
	 * @return corresponding BeanDependentTag
	 */
	private BeanDependentTag DependentTagToBean(DependentTag tag){
		return new BeanDependentTag(tag.getId(),
				tag.getCorrespTagId(),
				tag.getCustomName(),
				tag.getPageId());
	}
}
