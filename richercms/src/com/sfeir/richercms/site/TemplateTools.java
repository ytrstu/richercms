package com.sfeir.richercms.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.server.business.ArboPage;
import com.sfeir.richercms.page.server.business.DependentTag;
import com.sfeir.richercms.page.server.business.RootArbo;
import com.sfeir.richercms.page.server.business.Tag;
import com.sfeir.richercms.page.server.business.Template;
import com.sfeir.richercms.page.server.business.TranslationPage;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanDependentTag;
import com.sfeir.richercms.page.shared.BeanTag;
import com.sfeir.richercms.page.shared.BeanTemplate;
import com.sfeir.richercms.page.shared.BeanTranslationPage;
import com.sfeir.richercms.site.template.template_basic.LinkPage;
import com.sfeir.richercms.wizard.server.business.Language;

public final class TemplateTools {
	
	private TemplateTools(){}
	
	static {
        ObjectifyService.register(ArboPage.class);
        ObjectifyService.register(TranslationPage.class);
        ObjectifyService.register(RootArbo.class);
        ObjectifyService.register(Template.class);
        ObjectifyService.register(Tag.class);
        ObjectifyService.register(DependentTag.class);
        ObjectifyService.register(Language.class);
	}

	/**
	 * Return a page from it's id
	 * @param path : path of the page
	 * @return Corresponding page
	 */
	public static BeanArboPage getArboPageWithPath(String path){
		String[] urlPart = path.split("/");
		String urlName = urlPart[urlPart.length -1];
		Objectify ofy = ObjectifyService.begin();
		Query<TranslationPage> sameUrlNames  = ofy.query(TranslationPage.class).filter("urlName =", urlName);
		
		if(sameUrlNames.countAll() == 1) {
			Key<TranslationPage> transKey = new Key<TranslationPage>(TranslationPage.class, 
						sameUrlNames.get().getId());
			Query<ArboPage> pages = ofy.query(ArboPage.class).filter("translation =", transKey);
			
			if(pages != null )
				return arboPageToBean(pages.get());

		}else if((sameUrlNames.countAll() > 1)){
			//get the root page
			Query<RootArbo> root = ofy.query(RootArbo.class);
			ArboPage rootPage = ofy.get(ArboPage.class, root.get().getIdOfRootArboPage());
			//run recursive function
			return getByPath(rootPage, path);
		}
		
		return null;
	}
	
	public static BeanArboPage getRootPage(){
		Objectify ofy = ObjectifyService.begin();
		Query<RootArbo> root = ofy.query(RootArbo.class);
		ArboPage rootPage = ofy.get(ArboPage.class, root.get().getIdOfRootArboPage());
		if(rootPage != null){
			return arboPageToBean(rootPage);
		}
		return null;
	}
	public static BeanArboPage arboPageToBean(ArboPage ap){
		Objectify ofy = ObjectifyService.begin();
		BeanArboPage bap = new BeanArboPage(ap.getId(),
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
	
	public static BeanTemplate getTemplate(Long TemplateId){
		Objectify ofy = ObjectifyService.begin();
    	BeanTemplate bean = null;

		Template template = ofy.get(Template.class, TemplateId);
		
		if(template != null)
			bean = TemplateToBean(template);
	    
	    return bean;
	}
	
	public static BeanTemplate getTemplatebyName(String  templateName){
		Objectify ofy = ObjectifyService.begin();
    	Query<Template> template = ofy.query(Template.class).filter("name ", templateName);
		
		return TemplateToBean(template.get());
	}
	
	public static String getPagePath(Long pageId, int translationIndex){
		String path = "";
		Objectify ofy = ObjectifyService.begin();
		Long parentId = pageId;

		while(parentId != null){
			ArboPage parent = ofy.get(ArboPage.class, parentId);
			parentId = parent.getParentId();
			TranslationPage translation = ofy.get(
					parent.getTranslation().get(translationIndex));
			path = "/"+translation.getUrlName()+path;
		}
		
		return path;
	}
	
	public static List<LinkPage> getLinkPagePath(Long pageId, int translationIndex){
		ArrayList<LinkPage> path = new ArrayList<LinkPage>();
		ArrayList<ArboPage> inversePath = new ArrayList<ArboPage>();
		Objectify ofy = ObjectifyService.begin();
		Long parentId = pageId;
		String strPath = "";

		while(parentId != null){
			ArboPage parent = ofy.get(ArboPage.class, parentId);
			parentId = parent.getParentId();
			inversePath.add(parent);
		}
		
		for(int i = inversePath.size()-1 ; i>-1  ; i--){
			ArboPage parent = inversePath.get(i);
			TranslationPage translation = ofy.get(
					parent.getTranslation().get(translationIndex));
			strPath = strPath + "/" +translation.getUrlName();
			path.add(new LinkPage(translation.getPageTitle(), strPath));
		}
		
		return path;
	}
	
	public static List<BeanDependentTag> getTag(Long pageId){
		ArrayList<BeanDependentTag> beans = new ArrayList<BeanDependentTag>();
		Objectify ofy = ObjectifyService.begin();
		
		ArboPage page = ofy.get(ArboPage.class, pageId);
		
		if(page != null){
			Map<Long, DependentTag> dTags = ofy.get(DependentTag.class, page.getTagsId());
			for(DependentTag dTag : dTags.values()){
				beans.add(DependentTagToBean(dTag));
			}
		}
		
		return beans;
	}
	
	public static List<BeanArboPage> getChildPage(Long pageId){
		ArrayList<BeanArboPage> beans = new ArrayList<BeanArboPage>();
		Objectify ofy = ObjectifyService.begin();
		ArboPage page = ofy.get(ArboPage.class, pageId);
		Map<Long, ArboPage> childs = ofy.get(ArboPage.class, page.getIdChildArboPage());
		
		for(ArboPage child : childs.values()){
			beans.add(arboPageToBean(child));
		}
		return beans;
	}
	
	public static List<LinkPage> getLinkSistersPage(Long pageId, int translationIndex){
		Objectify ofy = ObjectifyService.begin();
		ArrayList<LinkPage> linkPage = new ArrayList<LinkPage>();
		ArboPage page = ofy.get(ArboPage.class, pageId);
		ArboPage parent = ofy.get(ArboPage.class, page.getParentId());
		Map<Long,ArboPage> sisters = ofy.get(ArboPage.class, parent.getIdChildArboPage());
		for(ArboPage sister : sisters.values()){
			TranslationPage translation = ofy.get(
					sister.getTranslation().get(translationIndex));
			linkPage.add(new LinkPage(translation.getPageTitle(), 
					getPagePath(sister.getId(), translationIndex)));
					
		}
		
		return linkPage;
	}
	
	/**
	 * Return the language index to retrieve right translation
	 * into a specific page
	 * @param tag : language's tag
	 * @return languageIndex, -1 either
	 */
	public static int getIndexOfLanguage(String tag) {
		Objectify ofy = ObjectifyService.begin();
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
				tp.getUrlName(),
				tp.getDescription(), 
				tp.getKeyWord(), 
				tp.getContent().getValue());
	}
	
	private static BeanTemplate TemplateToBean(Template tp){
		Objectify ofy = ObjectifyService.begin();
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
	
	private static BeanDependentTag DependentTagToBean(DependentTag tag){
		Objectify ofy = ObjectifyService.begin();
		
		return new BeanDependentTag(tag.getId(),
				tagToBean(ofy.get(tag.getDependentTag())),
				tag.getCustomName());
	}
	
	/**
	 * call by getArboPageWithPath to browse path and
	 * select good page.
	 * @param page : current page during the recusive recall, the root Page either
	 * @param path : the full path including root
	 * @return specific page, null if any are find
	 */
	private static BeanArboPage getByPath(ArboPage page, String path) {
		Objectify ofy = ObjectifyService.begin();
		String newPath = "";
		String[] urlPart = path.split("/");
		if(urlPart.length !=0)
			newPath = path.replace(urlPart[0]+"/", "");
		
		if(urlPart.length >=2){
			Map<Long, ArboPage> childs = ofy.get(ArboPage.class, page.getIdChildArboPage());
			for(ArboPage child : childs.values()){
				Map<Key<TranslationPage>, TranslationPage> trans = ofy.get(child.getTranslation());
				for(TranslationPage t : trans.values()){
					if(t.getUrlName().equals(urlPart[1]) && urlPart.length != 2)
						return getByPath(child,newPath);
					else if(urlPart.length == 2)
						return arboPageToBean(child);
				}
			}
		}
		return null;
	}
}
