package wizardConfig.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import wizardConfig.client.ServiceLangue;
import wizardConfig.server.businessObj.Langue;
import wizardConfig.shared.DetailsLangue;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServiceLangueImpl extends RemoteServiceServlet implements ServiceLangue{

	private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	private List<Langue> langues = null;

	/**
	 * Renvoi les langues disponibles
	 */
	@SuppressWarnings("unchecked")
	public List<DetailsLangue> getLangues() {
		
		PersistenceManager pm = getPersistenceManager();
		ArrayList<DetailsLangue> lst = new ArrayList<DetailsLangue>();
	    try {
	        Query q = pm.newQuery(Langue.class);
	        langues = (List<Langue>) q.execute();
	        
	        for (Langue langue : langues) {
	            lst.add(new DetailsLangue(langue.getLangue(),langue.isSelected()));
	          }
        } finally {
        	pm.close();
        }
		return lst;
	}
	
	public void ajoutLangueDeBase()
	{
	    PersistenceManager pm = getPersistenceManager();
	    try {
	      pm.makePersistent(new Langue("Français"));
	      pm.makePersistent(new Langue("Anglais"));
	      pm.makePersistent(new Langue("Allemand"));
	      pm.makePersistent(new Langue("Italien"));
	    } finally {
	      pm.close();
	    }
	}
	
	public void addLanguage(String language)
	{
		Langue lg = new Langue(language);
		PersistenceManager pm = getPersistenceManager();
	    try {
	    	// on ajoute dans le dataStore uniquement s'il n'existe pas déjà
	    	if(!this.existingLanguage(language))
	    	{
	    		pm.makePersistent(lg);
	    	}
	    } finally {
	      pm.close();
	    }
	}
	
	public void SelectionneLangue(List<Integer> lstID)
	{
		PersistenceManager pm = getPersistenceManager();
		
		 try
		 {
			//On dé-selectionne toute les langues
			 for (Langue objLangue : this.langues)
			 {
				Langue lg = pm.getObjectById(Langue.class, objLangue.getId());
				lg.setSelected(false);
			 }
	
			 
			 // On selectionne uniquement les bonnes langues
			 for (Integer IDlangue : lstID)
			 {	
				 Langue lg = pm.getObjectById(Langue.class, this.langues.get(IDlangue.intValue()).getId());
				 lg.setSelected(true);
			 }
			 
		 }finally{
			 pm.close();
		 }
	}
	
	public void DeleteLanguage(List<Integer> lstID)
	{
		PersistenceManager pm = getPersistenceManager();
		 try
		 {
			 
			 for (Integer id : lstID)
			 {
				 Langue lg = pm.getObjectById(Langue.class, this.langues.get(id).getId());
				 pm.deletePersistent(lg);
			 }

		 }finally{
			 pm.close();
		 }
	}
	
	private boolean existingLanguage(String lg)
	{
		//ArrayList<String> lst = getLangues();
		 for (Langue langue : this.langues)
		 {
			 if(lg.equals(langue.getLangue()))
				 return true;
		 }
		 return false;
	}
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
	
}
