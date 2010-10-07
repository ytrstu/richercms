package com.sfeir.richercms.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.server.business.ArboPage;
import com.sfeir.richercms.page.server.business.RootArbo;
import com.sfeir.richercms.page.server.business.Tag;
import com.sfeir.richercms.page.server.business.Template;
import com.sfeir.richercms.page.server.business.TranslationPage;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanTag;
import com.sfeir.richercms.page.shared.BeanTemplate;
import com.sfeir.richercms.page.shared.BeanTranslationPage;
import com.sfeir.richercms.site.template.template_basic.LinkPage;
import com.sfeir.richercms.wizard.server.business.Language;

public final class TemplateTools {
	
	private TemplateTools(){}

	/**
	 * Return a page from it's id
	 * @param path : path of the page
	 * @return Corresponding page
	 */
	public static ArboPage getArboPageWithPath(Objectify ofy,String path){
		String[] urlPart = path.split("/");
		String urlName = urlPart[urlPart.length -1];
		
		Query<ArboPage> sameUrlNames  = ofy.query(ArboPage.class).filter("urlName =", urlName);
		
		if(sameUrlNames.countAll() == 1) 
			return sameUrlNames.get();

		else if((sameUrlNames.countAll() > 1)){
			//get the root page
			Query<RootArbo> root = ofy.query(RootArbo.class);
			ArboPage rootPage = ofy.get(ArboPage.class, root.get().getIdOfRootArboPage());
			//run recursive function
			return getByPath(ofy, rootPage, path);
		}
		
		return null;
	}
	
	public static ArboPage getRootPage(Objectify ofy){
		Query<RootArbo> root = ofy.query(RootArbo.class);
		ArboPage rootPage = ofy.get(ArboPage.class, root.get().getIdOfRootArboPage());
		if(rootPage != null){
			return rootPage;
		}
		return null;
	}
	
	public static BeanArboPage arboPageToBean(Objectify ofy, ArboPage ap){
		BeanArboPage bap = new BeanArboPage(ap.getId(),
											ap.getUrlName(),
											ap.getPublicationStart(), 
											ap.getPublicationFinish(),
											ap.getCreationDate(),
											ap.getTemplateId(),
											ap.getTagsId(),
											ap.getParentId());
		
		//if IdUserInModif == -1 we return null
		if(ap.getIdUserInModif().intValue() != -1)
			bap.setIdUserInModif(ap.getIdUserInModif());
		
		ArrayList<BeanTranslationPage> lst = new ArrayList<BeanTranslationPage>();
		for(Key<TranslationPage> kTp : ap.getTranslation()){
			TranslationPage tp = ofy.get(kTp);
			lst.add(translationPageToBean(tp));
		}
		bap.setTranslation(lst);
		return bap;
	}
	
	public static Template getTemplate(Objectify ofy, Long TemplateId){
    	Template template = null;

		template = ofy.get(Template.class, TemplateId);
	    
	    return template;
	}
	
	public static Template getTemplatebyName(Objectify ofy, String  templateName){
    	Query<Template> templates = ofy.query(Template.class).filter("name ", templateName);
		if(templates.countAll() > 0)
			return templates.get();
		return null;
	}
	
	public static String getTemplateName(Objectify ofy, Long TemplateId){
    	String  name = null;

		Template template = ofy.get(Template.class, TemplateId);
		
		if(template != null)
			name = template.getName();
	    
	    return name;
	}
	
	public static String getPagePath(Objectify ofy, Long pageId, int translationIndex){
		String path = "";
		Long parentId = pageId;

		while(parentId != null){
			ArboPage parent = ofy.get(ArboPage.class, parentId);
			parentId = parent.getParentId();
			if (parentId!=null) {
				path = "/"+parent.getUrlName()+path;
			}
		}
		
		return path;
	}
	
	public static List<LinkPage> getLinkPagePath(Objectify ofy, Long pageId, int translationIndex){
		ArrayList<LinkPage> path = new ArrayList<LinkPage>();
		ArrayList<ArboPage> inversePath = new ArrayList<ArboPage>();
		Long parentId = pageId;
		String strPath = "";

		while(parentId != null){
			ArboPage parent = ofy.get(ArboPage.class, parentId);
			parentId = parent.getParentId();
			inversePath.add(parent);
		}
		
		boolean isFirst = true;
		for(int i = inversePath.size()-1 ; i>-1  ; i--){
			ArboPage parent = inversePath.get(i);
			TranslationPage translation = ofy.get(
					parent.getTranslation().get(translationIndex));
			if (isFirst) {
				isFirst = false;
			} else {
				strPath = strPath + "/" +parent.getUrlName();
			}
			path.add(new LinkPage(translation.getPageTitle(), strPath));
		}
		
		return path;
	}
	
	public static List<Tag> getTag(Objectify ofy, Long pageId){
		ArrayList<Tag> lst = new ArrayList<Tag>();
		
		ArboPage page = ofy.get(ArboPage.class, pageId);
		
		if(page != null){
			Map<Long, Tag> dTags = ofy.get(Tag.class, page.getTagsId());
			for(Tag tag : dTags.values()){
				lst.add(tag);
			}
		}
		
		return lst;
	}
	
	public static List<BeanArboPage> getChildPage(Objectify ofy, Long pageId){
		ArrayList<BeanArboPage> beans = new ArrayList<BeanArboPage>();
		ArboPage page = ofy.get(ArboPage.class, pageId);
		Map<Long, ArboPage> childs = ofy.get(ArboPage.class, page.getIdChildArboPage());
		
		for(ArboPage child : childs.values()){
			beans.add(arboPageToBean(ofy, child));
		}
		return beans;
	}
	
	public static List<LinkPage> getLinkSistersPage(Objectify ofy, Long pageId, int translationIndex){
		ArrayList<LinkPage> linkPage = new ArrayList<LinkPage>();
		ArboPage page = ofy.get(ArboPage.class, pageId);
		ArboPage parent = ofy.get(ArboPage.class, page.getParentId());
		Map<Long,ArboPage> sisters = ofy.get(ArboPage.class, parent.getIdChildArboPage());
		for(ArboPage sister : sisters.values()){
			TranslationPage translation = ofy.get(
					sister.getTranslation().get(translationIndex));
			linkPage.add(new LinkPage(translation.getPageTitle(), 
					getPagePath(ofy, sister.getId(), translationIndex)));
					
		}
		
		return linkPage;
	}
	
	/**
	 * Return the language index to retrieve right translation
	 * into a specific page
	 * @param tag : language's tag
	 * @return languageIndex, -1 either
	 */
	public static int getIndexOfLanguage(Objectify ofy, String tag) {
		Query<Language> req = ofy.query(Language.class);
		for(Language lg : req){
			if(lg.getTag().equals(tag))
				return lg.getTranslationID();
		}
		return -1;
	}
	
	private static BeanTranslationPage translationPageToBean(TranslationPage tp) {
		return new BeanTranslationPage(tp.getId(), 
				tp.getBrowserTitle(),
				tp.getPageTitle(), 
				tp.getDescription(), 
				tp.getKeyWord(), 
				tp.getContent().getValue());
	}
	
	public static BeanTemplate TemplateToBean(Objectify ofy, Template tp){
		BeanTemplate btp = new BeanTemplate(tp.getId(),tp.getName(), tp.getShortLib(), tp.getDescription());
		
		ArrayList<BeanTag> lst = new ArrayList<BeanTag>();
		for(Key<Tag> kTag : tp.getAssociatedTags()){
			Tag tag = ofy.get(kTag);
			lst.add(tagToBean(tag));
		}
		btp.setAssociatedTags(lst);
		return btp;
	}
	
	private static BeanTag tagToBean(Tag tag){
		return new BeanTag(tag.getId(),
				tag.getTagName(),
				tag.getShortLib(),
				tag.getDescription(),
				tag.isTextual());
	}
	
	/**
	 * call by getArboPageWithPath to browse path and
	 * select good page.
	 * @param page : current page during the recusive recall, the root Page either
	 * @param path : the full path including root
	 * @return specific page, null if any are find
	 */
	private static ArboPage getByPath(Objectify ofy, ArboPage page, String path) {
		String newPath = "";
		String[] urlPart = path.split("/");
		if(urlPart.length !=0)
			newPath = path.replace(urlPart[0]+"/", "");
		
		if(urlPart.length >=2){
			Map<Long, ArboPage> childs = ofy.get(ArboPage.class, page.getIdChildArboPage());
			for(ArboPage child : childs.values()){
				if(child.getUrlName().equals(urlPart[1]) && urlPart.length != 2)
					return getByPath(ofy, child,newPath);
				else if(child.getUrlName().equals(urlPart[1]) && urlPart.length == 2)
					return child;
			}
		}
		return null;
	}
	
	
	/**
	 * Test if today are into publication Date start and finish 
	 * @return true if its right, false either
	 */
	public static boolean isVisible(Date publicationStart,Date publicationFinish) {
		Date now = new Date();
		return (publicationFinish==null || now.before(publicationFinish)) &&
				publicationStart!= null && now.after(publicationStart);
	}
}
