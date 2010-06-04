package com.sfeir.richercms.page.server;

import java.util.ArrayList;
import java.util.List;

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

	public void deleteArboPage(Long id, Long parentId) {
		
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
	
	public List<Long> getLinkedImage(Long id){
		Objectify ofy = ObjectifyService.begin();
		ArboPage page = ofy.get(ArboPage.class, id);
		return page.getIdLinkedImages();
	}
	
	public void modifyLinkedImage(Long pageID, List<Long> linkedImageIds){
		Objectify ofy = ObjectifyService.begin();
		ArboPage page = ofy.get(ArboPage.class, pageID);
		page.setIdLinkedImages(linkedImageIds);
		ofy.put(page);
	}
	
	public BeanArboPage arboPageToBean(ArboPage ap){
		Objectify ofy = ObjectifyService.begin();
		BeanArboPage bap = new BeanArboPage(ap.getId(),ap.getPublicationStart(), 
				ap.getPublicationFinish(), ap.getCreationDate());
		ArrayList<BeanTranslationPage> lst = new ArrayList<BeanTranslationPage>();
		for(Key<TranslationPage> kTp : ap.getTranslation()){
			TranslationPage tp = ofy.get(kTp);
			lst.add(translationPageToBean(tp));
		}
		bap.setIdLinkedImages(ap.getIdLinkedImages());
		bap.setTranslation(lst);
		return bap;
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
		ap.setIdLinkedImages(bAP.getIdLinkedImages());
		
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
