package com.sfeir.richercms.page.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.client.ArboPageService;
import com.sfeir.richercms.page.server.business.ArboPage;
import com.sfeir.richercms.page.server.business.MemoryFileItem;
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
        ObjectifyService.register(MemoryFileItem.class);
    }
	
	private Long keyRoot = null;
	private int nbTranslation = 1;
	
	public ServiceArboPageImpl(){super();}
	
	public Long addArboPage(BeanArboPage newArboPage, Long parentId) {
		
		Objectify ofy = ObjectifyService.begin();
		ArboPage nAP = this.BeanToArboPage(newArboPage);
		ofy.put(nAP);
			
		ArboPage parentPage = ofy.get(ArboPage.class, parentId);
		parentPage.getIdChildArboPage().add(nAP.getId());
		ofy.put(parentPage);
		return nAP.getId();
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
        	TranslationPage tp = new TranslationPage();
        	tp.setPageTitle("index");
        	rootPage.setUrlName("index");
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
	
	public void updateArboPage(BeanArboPage bean, List<Long> recPath) {
		Objectify ofy = ObjectifyService.begin();
		ArboPage page = ofy.get(ArboPage.class, bean.getId());
		
		//update page only if defaultUrlName are modified
		if(!page.getUrlName().equals(bean.getUrlName()))
			updateImagePath(recPath, page.getUrlName(), bean.getUrlName());
		
		page.getTranslation().clear();
		
		for(BeanTranslationPage trans : bean.getTranslation()) {
				TranslationPage tP = this.BeanToTranslationPage(trans);
				ofy.put(tP);
				Key<TranslationPage> kTp = new Key<TranslationPage>(TranslationPage.class, tP.getId());
				page.getTranslation().add(kTp);
		}
		
		//modification on the page
		page.setPublicationStart(bean.getPublicationStart());
		page.setPublicationFinish(bean.getPublicationFinish());
		page.setTemplateId(bean.getTemplateId());
		page.setTagsId(bean.getTagsId());
		page.setUrlName(bean.getUrlName());
		
		ofy.put(page);
	}
	
	/**
	 * Call this method to update image's path
	 * before an update of arboPage
	 * @param recPath : list of id who represent path : selectedId => root
	 * @param altUrlName : alt urlName of arboPage who need modification
	 * @param newUrlName : new urlName of arboPage who need update
	 */
	private void updateImagePath(List<Long> recPath, String altUrlName, String newUrlName) {
		Objectify ofy = ObjectifyService.begin();
		//save alt path
		String altPath = this.getPath(recPath);
		//create new path
		String newPath = new String(altPath);
		newPath = newPath.replaceAll(altUrlName+"/$", "");
		newPath = newPath.concat(newUrlName+"/");
		
		
		ArboPage currentPage = ofy.get(ArboPage.class, recPath.get(0));
		Map<Long,ArboPage> childs = ofy.get(ArboPage.class, currentPage.getIdChildArboPage());
		//for all childs, make the same thinks	
		for(ArboPage child : childs.values()){
			modifyPath(child.getId(), newPath, altPath);
		}
	}
	
	/**
	 * Recusive call for update path in image
	 * don't call this method, call updateImagePath
	 * @param pageId : Page's id
	 * @param newPath : modified Path
	 * @param altPath : alt path
	 */
	private void modifyPath(Long pageId, String newPath, String altPath) {
		Objectify ofy = ObjectifyService.begin();
		//take img with specific path
		Query<MemoryFileItem> files  = ofy.query(MemoryFileItem.class).filter("path =", altPath);
		
		//modify path with new
		for(MemoryFileItem file: files){
			file.setPath(newPath);
			ofy.put(file);
		}

		//add new part in the new path
		ArboPage currentPage = ofy.get(ArboPage.class, pageId);
		newPath = newPath.concat(currentPage.getUrlName()+"/");
		altPath = altPath.concat(currentPage.getUrlName()+"/");
		//take all child of currentPage
		Map<Long,ArboPage> childs = ofy.get(ArboPage.class, currentPage.getIdChildArboPage());
		//for all childs, make the same thinks
		if(childs.size() != 0){
			for(ArboPage child : childs.values()){
				modifyPath(child.getId(), newPath, altPath);
			}
		}else{//useful when recursive code arrive on a leaf. 
			files  = ofy.query(MemoryFileItem.class).filter("path =", altPath);
			//modify path with new
			for(MemoryFileItem file: files){
				file.setPath(newPath);
				ofy.put(file);
			}
		}
		
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
		
		//reverse exploring
		for(int i = ids.size(); i>0 ; i--) {
			//add the URLName in path
			path = path+res.get(ids.get(i-1)).getUrlName()+"/";
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
		if(pageId != null){
			ArboPage page = ofy.get(ArboPage.class, pageId);
			
			if(page != null) {
				page.setIdUserInModif(new Long(-1));
				ofy.put(page);
			}
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
	
	public boolean existSameUrl(Long parentId, Long pageId, List<String> urlNames){
		Objectify ofy = ObjectifyService.begin();
		if(parentId !=null){
			ArboPage parentPage = ofy.get(ArboPage.class, parentId);
			Map<Long,ArboPage> childs = ofy.get(ArboPage.class,parentPage.getIdChildArboPage());
			
			//on enlève la page fils que l'on veut ajouter/modifier
			if(pageId != null)
				childs.remove(pageId);
			
			//pour chaque nom d'url
			for(String urlName : urlNames) {
				//on ne test pas les url vide
				if(!urlName.equals("")){
					//on récupère tout les URL possédant le même nom
					Query<ArboPage> sameUrlNames  = ofy.query(ArboPage.class).filter("urlName =", urlName);
					//si >0 alors il faut tester si ce nom n'est pas présent au même niveau que le nouveau
					if(sameUrlNames.countAll()!= 0){
						//on parcours donc les url identique
						for(ArboPage sameUrlName : sameUrlNames){
							//pour chaque url identique on parcours toutes les page de même niveau que la courante
							for(ArboPage child : childs.values()){
								if(child.getId() == sameUrlName.getId().longValue())
									return false;
							
							}
						}
					}
				}
			}
		}
		return true;
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
	
	
	/**
	 * make an BeanArboPage with an ArboPage
	 * with some datastore call to get all translation
	 * @param bAP
	 * @return corresponding BeanArboPage
	 */
	private BeanArboPage arboPageToBean(ArboPage ap){
		Objectify ofy = ObjectifyService.begin();
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
	
	/**
	 * make a BeanTranslationPage with an TranslationPage
	 * @param bAP
	 * @return corresponding BeanTranslationPage
	 */
	private BeanTranslationPage translationPageToBean(TranslationPage tp) {
		return new BeanTranslationPage(tp.getId(), 
				tp.getBrowserTitle(),
				tp.getPageTitle(), 
				tp.getDescription(), 
				tp.getKeyWord(), 
				tp.getContent().getValue());
	}
	
	/**
	 * make an ArboPage with an BeanArboPage
	 * with some datastore call to save all translation
	 * @param bAP
	 * @return corresponding ArboPage
	 */
	private ArboPage BeanToArboPage(BeanArboPage bAP){
		Objectify ofy = ObjectifyService.begin();
		ArboPage ap = new ArboPage(bAP.getId(),
								   bAP.getUrlName(),
								   bAP.getPublicationStart(),
								   bAP.getPublicationFinish(),
								   bAP.getCreationDate(),
								   bAP.getTemplateId(),
								   bAP.getTagsId(),
								   bAP.getParentId());
		
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
	
	/**
	 * make a TranslationPage with a BeanTranslationPage
	 * @param bTp
	 * @return corresponding TranslationPage
	 */
	private TranslationPage BeanToTranslationPage(BeanTranslationPage bTp){
		return new TranslationPage(bTp.getId(), bTp.getBrowserTitle(),bTp.getPageTitle(),
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

	/**
	 * Return a path list id of the current page
	 * @param path : path of the page
	 * @return the last element of the list is the root
	 */
	public List<Long> loadPathIdFromRealPath(String path) {
		LinkedList<Long> res = new LinkedList<Long>();
		
		Objectify ofy = ObjectifyService.begin();
		BeanArboPage root = getMainArboPage();
		res.add(root.getId());
		
		if (path==null) {
			return res; 
		} else {
			long parent = root.getId();
			boolean isFirst = true;
			for(String pathPart : path.split("/")) {
				if (!(isFirst && pathPart.equals(root.getUrlName())) && !"".equals(pathPart)) {
					Key<ArboPage> childKey = ofy.query(ArboPage.class)
					.filter("parentId = ", parent)
					.filter("urlName = ", pathPart)
					.getKey();
					
					if (childKey!=null) {
						long child = childKey.getId();
						res.addFirst(child);
						parent = child;
					}
				}
				
				isFirst = false;
			}
		
			return res;
		}		
	}
	
}
