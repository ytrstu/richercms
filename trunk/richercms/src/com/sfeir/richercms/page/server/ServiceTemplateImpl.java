package com.sfeir.richercms.page.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.client.TemplateService;
import com.sfeir.richercms.page.server.business.ArboPage;
import com.sfeir.richercms.page.server.business.Tag;
import com.sfeir.richercms.page.server.business.Template;
import com.sfeir.richercms.page.shared.BeanTag;
import com.sfeir.richercms.page.shared.BeanTemplate;

@SuppressWarnings("serial")
public class ServiceTemplateImpl extends RemoteServiceServlet implements TemplateService {
	
	static {
        ObjectifyService.register(Template.class);
        ObjectifyService.register(Tag.class);
        ObjectifyService.register(ArboPage.class);
	}

	public Long addTemplate(BeanTemplate bean) {
		Objectify ofy = ObjectifyService.begin();
		Template template = this.BeanToTemplate(bean);
		
		Query<Template> sameNames = ofy.query(Template.class).filter("name ", bean.getName());
		Query<Template> sameShortLibs = ofy.query(Template.class).filter("shortLib ", bean.getShortLib());
		
		if(sameNames.countAll()==0 && sameShortLibs.countAll()==0)
			ofy.put(template);
		
		return template.getId();
	}
	
	public BeanTemplate getTemplate(Long id) {
		Objectify ofy = ObjectifyService.begin();
    	BeanTemplate bean = null;

		Template template = ofy.get(Template.class, id);
		
		if(template != null)
			bean = this.TemplateToBean(template);
	    
	    return bean;
	}
	
	public List<BeanTemplate> getAllTemplate() {
		ArrayList<BeanTemplate> lst = new ArrayList<BeanTemplate>();
		Objectify ofy = ObjectifyService.begin();
		
		Query<Template> templates  = ofy.query(Template.class);
		
		for(Template template :templates){
			lst.add(this.TemplateToBean(template));
		}
		
		return lst;
	}
	
	public void upDateTagsTemplate(Long templateId, List<Long> tagIds) {
		ArrayList<Key<Tag>> lst = new ArrayList<Key<Tag>>();
		Objectify ofy = ObjectifyService.begin();

		Template template = ofy.get(Template.class, templateId);
		
		if(template != null)
			for(Long tagId : tagIds) {
				Key<Tag> kTag = new Key<Tag>(Tag.class, tagId);
				lst.add(kTag);
			}
		
		template.setAssociatedTags(lst);
		
		ofy.put(template);
	}
	
	public boolean updateTemplate(Long id, String name, String shortLib, String description) {
		Objectify ofy = ObjectifyService.begin();
		
		Query<Template> sameNames = ofy.query(Template.class).filter("name ", name);
		Query<Template> sameShortLibs = ofy.query(Template.class).filter("shortLib ", shortLib);
		
		int nbSameShortLib = sameShortLibs.countAll();
		int nbSameName = sameNames.countAll();
		
		if(nbSameShortLib==0 && nbSameName==0) {
			Template template = ofy.get(Template.class, id);
			if(template != null) {
				template.setName(name);
				template.setDescription(description);
				template.setShortLib(shortLib);
				ofy.put(template);
				return true;
			}
			
		}//same template with same name and shortLib 
		else if(nbSameName==1 && nbSameShortLib==1 && sameNames.get().getId().equals(id)
				&& sameShortLibs.get().getId().equals(id)){
			Template template = ofy.get(Template.class, id);
			if(template != null) {
				template.setName(name);
				template.setDescription(description);
				template.setShortLib(shortLib);
				ofy.put(template);
				return true;
			}
		}
		return false;
	}
	
	public void deleteTemplate(Long id) {
		Objectify ofy = ObjectifyService.begin();
		Template template = ofy.get(Template.class, id);
		if(template != null){
			
			//delete template in all arboPage who associate this template
			Query<ArboPage> pages = ofy.query(ArboPage.class).filter("templateId ", id);
			for(ArboPage page : pages){
				page.setTemplateId(null);
				page.getTagsId().clear();
				ofy.put(page);
			}
			ofy.delete(template);
		}
	}
	
	/**
	 * Make a Template with a BeanTemplate
	 * @param bTP
	 * @return corresponding Template
	 */
	private Template BeanToTemplate(BeanTemplate bTP){
		Objectify ofy = ObjectifyService.begin();
		Template TP = new Template();
		TP.setName(bTP.getName());
		TP.setShortLib(bTP.getShortLib());
		TP.setDescription(bTP.getDescription());
		ArrayList<Key<Tag>> lst = new ArrayList<Key<Tag>>();
		
		for(BeanTag bTag : bTP.getAssociatedTags()){
			Tag tag = this.beanToTag(bTag);
			
			if(tag.getId()== null) {
				ofy.put(tag);
			}	
			
			Key<Tag> kTag = new Key<Tag>(Tag.class, tag.getId());
			lst.add(kTag);
		}
		
		TP.setAssociatedTags(lst);
		
		return TP;
	}
	
	/**
	 * Make a BeanTemplate with a Template
	 * @param tp
	 * @return Corresponding BeanTemplate
	 */
	private BeanTemplate TemplateToBean(Template tp){
		Objectify ofy = ObjectifyService.begin();
		BeanTemplate btp = new BeanTemplate(tp.getId(),tp.getName(), tp.getShortLib(), tp.getDescription());
		
		ArrayList<BeanTag> lst = new ArrayList<BeanTag>();
		for(Key<Tag> kTag : tp.getAssociatedTags()){
			Tag tag = ofy.get(kTag);
			lst.add(this.tagToBean(tag));
		}
		btp.setAssociatedTags(lst);
		return btp;
	}
	
	/**
	 * Make a Tag with a BeanTag
	 * @param bean
	 * @return Corresponding Tag
	 */
	private Tag beanToTag(BeanTag bean){
		return new Tag(bean.getId(),
				bean.getTagName(),
				bean.getShortLib(),
				bean.getDescription(),
				bean.isTextual());
	}
	
	/**
	 * Make a BeanTag with  a Tag
	 * @param tag
	 * @return Corresponding BeanTag
	 */
	private BeanTag tagToBean(Tag tag){
		return new BeanTag(tag.getId(),
				tag.getTagName(),
				tag.getShortLib(),
				tag.getDescription(),
				tag.isTextual());
	}
}
