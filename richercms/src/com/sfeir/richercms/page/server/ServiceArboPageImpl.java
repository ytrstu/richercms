package com.sfeir.richercms.page.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.client.ArboPageService;
import com.sfeir.richercms.page.server.business.ArboPage;
import com.sfeir.richercms.page.server.business.RootArbo;
import com.sfeir.richercms.page.server.business.TranslationPage;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanTranslationPage;
import com.sfeir.richercms.wizard.server.business.Language;

@SuppressWarnings("serial")
public class ServiceArboPageImpl  extends RemoteServiceServlet implements ArboPageService {

	static {
        ObjectifyService.register(Language.class);
        ObjectifyService.register(RootArbo.class);
        ObjectifyService.register(ArboPage.class);
        ObjectifyService.register(TranslationPage.class);
    }
	
	private Long keyRoot = null;
	private int nbTranslation = 1;
	
	public ServiceArboPageImpl(){super();}
	
	public void addArboPage(BeanArboPage newArboPage, Long parentId) {
		
		Objectify ofy = ObjectifyService.begin();
		ArboPage nAP = this.BeanToArboPage(newArboPage);
		ofy.put(nAP);
			
		ArboPage parentPage = ofy.get(ArboPage.class, parentId);
		parentPage.getIdChildArboPage().add(nAP.getId());
		ofy.put(parentPage);	
	}

	public boolean deleteArboPage(Long id, Long parentId) {
		// test if this page wasn't lock by another user
		if(this.isDeletable(id)){
			Objectify ofy = ObjectifyService.begin();
	
	    	ArboPage parentPage = ofy.get(ArboPage.class, parentId);
	    	//remove this node in this parent
	    	parentPage.getIdChildArboPage().remove(id);
	    	ofy.put(parentPage);
	    
	    	ArboPage arboPage = ofy.get(ArboPage.class, id);
	    	//delete all child
	    	for(Long childKey : arboPage.getIdChildArboPage()) {
	    		deleteArboPage(childKey, arboPage.getId());
	    	}
			// delete all translation
	    	ofy.delete(arboPage.getTranslation());
	    	// delete the current page
			ofy.delete(arboPage);
			return true;
		}
		return false;
	}
	
	/**
	 * Recursing to see a sub-nodes is being modified
	 * @param id : node to delete
	 * @return : false if one node or sub-,ode are in modification, true if all node are free
	 */
	private boolean isDeletable(Long id) {
		Objectify ofy = ObjectifyService.begin();
		
		ArboPage arboPage = ofy.get(ArboPage.class, id);
		if(arboPage.getIdUserInModif().intValue() != -1)
			return false;
    	//delete all child
    	for(Long childKey : arboPage.getIdChildArboPage()) {
    		// if one is locked, its impossible to delete
    		if(!isDeletable(childKey))
    			return false;
    	}
		return true;
	}

	public BeanArboPage getArboPage(Long id) {
		Objectify ofy = ObjectifyService.begin();
    	BeanArboPage bean = null;

		ArboPage page = ofy.get(ArboPage.class, id);
	    bean = this.arboPageToBean(page);
	    
	    return bean;
	}

	
	public List<BeanArboPage> getChildPages(Long ParentId, boolean isMain) {
		
		Objectify ofy = ObjectifyService.begin();
		List<Long> childIds;
		ArrayList<BeanArboPage> lst = new ArrayList<BeanArboPage>();
		
		childIds = ofy.get(ArboPage.class, ParentId).getIdChildArboPage();
		
		ArboPage childArboPage;
		for (Long id : childIds){
			childArboPage = ofy.get(ArboPage.class, id);
			if(childArboPage != null)
				lst.add(this.arboPageToBean(childArboPage));
		}
		return lst;
	}

	public BeanArboPage getMainArboPage() {
		Objectify ofy = ObjectifyService.begin();
    	BeanArboPage bean = null;

    	Query<RootArbo> roots = ofy.query(RootArbo.class);
    	
        if(roots.countAll() == 0){
        	countLanguage();
        	RootArbo root = new RootArbo();
        	ArboPage rootPage = new ArboPage();
        	TranslationPage tp = new TranslationPage(); tp.setPageTitle("main");
        	ofy.put(tp);
        	Key<TranslationPage> kTp = new Key<TranslationPage>(TranslationPage.class,tp.getId());
        	rootPage.getTranslation().add(kTp);
        	
        	//add empty Translation into the page
        	for(int i = 1 ; i<this.nbTranslation ; i++) {
	        	TranslationPage emptyTP = new TranslationPage();
	        	ofy.put(emptyTP);
	        	Key<TranslationPage> kEmptyTP = new Key<TranslationPage>(TranslationPage.class,emptyTP.getId());
	        	rootPage.getTranslation().add(kEmptyTP);
        	}
  
        	ofy.put(rootPage);
        	root.setIdOfRootArboPage(rootPage.getId());
        	ofy.put(root);
       
        	this.keyRoot = rootPage.getId();
        	bean = this.arboPageToBean(rootPage);
        }else {
        	this.keyRoot = roots.get().getIdOfRootArboPage();
        	bean = this.arboPageToBean(ofy.get(ArboPage.class, this.keyRoot));
        }
	        
		return bean;
	}
	
	public void updateArboPage(BeanArboPage bean) {
		Objectify ofy = ObjectifyService.begin();
		ArboPage parentPage = ofy.get(ArboPage.class, bean.getId());
		parentPage.getTranslation().clear();
		
		for(BeanTranslationPage trans : bean.getTranslation()) {
				TranslationPage tP = this.BeanToTranslationPage(trans);
				ofy.put(tP);
				Key<TranslationPage> kTp = new Key<TranslationPage>(TranslationPage.class, tP.getId());
				parentPage.getTranslation().add(kTp);
		}
		ofy.put(parentPage);
	}
	
	public BeanArboPage getLastChildAdded(Long parentId){
		Objectify ofy = ObjectifyService.begin();
		BeanArboPage bean = null;
		ArboPage parentPage = ofy.get(ArboPage.class, parentId);
			 
		if(parentPage.getIdChildArboPage().size() != 0) {
			ArboPage LastPageAdded = ofy.get(ArboPage.class, 
					parentPage.getIdChildArboPage().get(parentPage.getIdChildArboPage().size() -1));
			bean = this.arboPageToBean(LastPageAdded);
		}
		return bean;
	}
	
	public void moveChildPage(Long parentId, Long childId, int index) {
		Objectify ofy = ObjectifyService.begin();
		ArboPage parentPage = ofy.get(ArboPage.class, parentId);
			 	
		if(parentPage!=null && parentPage.getIdChildArboPage().contains(childId)){
			parentPage.getIdChildArboPage().remove(childId);
			parentPage.getIdChildArboPage().add(index, childId);
			ofy.put(parentPage);
		}
	}
	
	public void updateChildOrder(Long id, List<Integer> newPositionOrder){
		Objectify ofy = ObjectifyService.begin();
		ArboPage page = ofy.get(ArboPage.class, id);
			 	
		if(page!=null) {
			ArrayList<Long> newOrder = new ArrayList<Long>();
			for(Integer i : newPositionOrder) {
				// 1 to x  DataStore : 0 to x-1
			 	newOrder.add(page.getIdChildArboPage().get(i.intValue()-1));
			}
			page.setIdChildArboPage(newOrder);
			ofy.put(page);
		}
	}
	
	
	public String getPath(List<Long> ids) {
		Objectify ofy = ObjectifyService.begin();
		Map<Long,ArboPage> res = ofy.get(ArboPage.class, ids);
		String path = "";
		Long translationId;
		//reverse exploring
		for(int i = ids.size(); i>0 ; i--) {
			//get Default Translation
			translationId = res.get(ids.get(i-1)).getTranslation().get(0).getId();
			//add the URLName in path
			path = path+ofy.get(TranslationPage.class, translationId).getUrlName()+"/";
		}
		return path;
	}
	
	public Long lockThisPage(Long pageId,Long userId) {
		Objectify ofy = ObjectifyService.begin();

		ArboPage page = ofy.get(ArboPage.class, pageId);
		if(page != null){
			if(page.getIdUserInModif().intValue() == -1) {
				page.setIdUserInModif(userId);
				ofy.put(page);
			} else {
				return page.getIdUserInModif();
			}
		}
		return null;
	}
	
	public Long lockPageInfo(Long pageId){
		Objectify ofy = ObjectifyService.begin();
		ArboPage page = ofy.get(ArboPage.class, pageId);
		
		if(page != null) {
			if(page.getIdUserInModif().intValue() == -1) 
				return null;
			else 
				return page.getIdUserInModif();
		}
		return null;
	}
	
	public void unlockThisPage(Long pageId) {
		Objectify ofy = ObjectifyService.begin();
		ArboPage page = ofy.get(ArboPage.class, pageId);
		
		if(page != null) {
			page.setIdUserInModif(new Long(-1));
			ofy.put(page);
		}
	}
	
	public void unlockAllUserPage(Long idUser) {
		Objectify ofy = ObjectifyService.begin();
		Query<ArboPage> lockedPages  = ofy.query(ArboPage.class).filter("idUserInModif !=", idUser);
		
		for(ArboPage lockedPage :lockedPages){
			lockedPage.setIdUserInModif(new Long(-1));
			ofy.put(lockedPage);
		}
	}
	
	public List<BeanArboPage> getAllLockedPages() {
		ArrayList<BeanArboPage> lst = new ArrayList<BeanArboPage>();
		Objectify ofy = ObjectifyService.begin();
		
		// -1 ==> page unlocked
		Query<ArboPage> lockedPages  = ofy.query(ArboPage.class).filter("idUserInModif !=", -1);
		
		for(ArboPage lockedPage :lockedPages){
			lst.add(this.arboPageToBean(lockedPage));
		}
		
		return lst;
	}
	
	public BeanArboPage arboPageToBean(ArboPage ap){
		Objectify ofy = ObjectifyService.begin();
		BeanArboPage bap = new BeanArboPage(ap.getId(),ap.getPublicationStart(), 
				ap.getPublicationFinish(), ap.getCreationDate());
		
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
	
	public BeanTranslationPage getDefaultTranslation(Long pageId){
		
		Objectify ofy = ObjectifyService.begin();
		ArboPage page = ofy.get(ArboPage.class, pageId);
		
		if(page != null) {
			TranslationPage tp = ofy.get(page.getTranslation().get(0));
			return this.translationPageToBean(tp);
		}
		return null;
	}
	
	public BeanTranslationPage translationPageToBean(TranslationPage tp) {
		return new BeanTranslationPage(tp.getId(), tp.getBrowserTitle(),tp.getPageTitle(), tp.getUrlName(),
				 tp.getDescription(), tp.getKeyWord(), tp.getContent().getValue());
	}
	

	public ArboPage BeanToArboPage(BeanArboPage bAP){
		Objectify ofy = ObjectifyService.begin();
		ArboPage ap = new ArboPage();
		ap.setPublicationStart(bAP.getPublicationStart());
		ap.setPublicationFinish(bAP.getPublicationFinish());
		ArrayList<Key<TranslationPage>> lst = new ArrayList<Key<TranslationPage>>();
		
		for(BeanTranslationPage bTp : bAP.getTranslation()){
			TranslationPage TP = BeanToTranslationPage(bTp);
			
			if(TP.getId()== null) {
				ofy.put(TP);
			}	
			
			Key<TranslationPage> kTp = new Key<TranslationPage>(TranslationPage.class, TP.getId());
			lst.add(kTp);
		}
		
		ap.setTranslation(lst);
		
		return ap;
	}
	
	public TranslationPage BeanToTranslationPage(BeanTranslationPage bTp){
		return new TranslationPage(bTp.getId(), bTp.getBrowserTitle(),bTp.getPageTitle(), bTp.getUrlName(),
				bTp.getDescription(), bTp.getKeyWord(), bTp.getContent());
	}
	
	/**
	 * Store the number of language in the class variable nbTranslation.
	 * With that, the application know how many translation are needed.
	 */
	private void countLanguage() {
		Objectify ofy = ObjectifyService.begin();

		com.googlecode.objectify.Query<Language> lgs = ofy.query(Language.class);
		this.nbTranslation = lgs.countAll();

	}

}
