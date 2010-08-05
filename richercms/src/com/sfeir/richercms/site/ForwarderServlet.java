package com.sfeir.richercms.site;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.sfeir.richercms.page.server.business.ArboPage;
import com.sfeir.richercms.page.server.business.DependentTag;
import com.sfeir.richercms.page.server.business.RootArbo;
import com.sfeir.richercms.page.server.business.Tag;
import com.sfeir.richercms.page.server.business.Template;
import com.sfeir.richercms.page.server.business.TranslationPage;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanTag;
import com.sfeir.richercms.page.shared.BeanTemplate;
import com.sfeir.richercms.server.business.LogInfo;
import com.sfeir.richercms.wizard.server.business.Language;


public class ForwarderServlet extends HttpServlet {

	private static final long serialVersionUID = -6965673584834316768L;
	private BeanArboPage page;
	private Objectify ofy;
	private static final String defaultLanguage = "fr";
	private static final String defaultErrorPage = "/site_basic/Denied.jsp"; // default error page
	private String errorTemplatePage = defaultErrorPage; // modify this function in your template function
	
	static {
        ObjectifyService.register(ArboPage.class);
        ObjectifyService.register(TranslationPage.class);
        ObjectifyService.register(RootArbo.class);
        ObjectifyService.register(Template.class);
        ObjectifyService.register(Tag.class);
        ObjectifyService.register(DependentTag.class);
        ObjectifyService.register(Language.class);
        ObjectifyService.register(LogInfo.class);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		//log => begin
		Long avant = System.currentTimeMillis();
		this.ofy = ObjectifyService.begin();
		Long apres = System.currentTimeMillis();
		ofy.put(new LogInfo("ForwarderServlet", "doGet", "exec du begin", apres-avant));
		
		String languageTag = request.getParameter("lg");
		
		if(languageTag == null)
			languageTag = defaultLanguage;
		
		// log => selection de la bonne servlet
		avant = System.currentTimeMillis();
		String nextJSP = selectJsp(request.getPathInfo());
		apres = System.currentTimeMillis();
		ofy.put(new LogInfo("ForwarderServlet", "doGet -> selectJsp", "selection de la bonne servlet", apres-avant));
		
		request.setAttribute("page", this.page);
		request.setAttribute("ofy", this.ofy);
		request.setAttribute("language", TemplateTools.getIndexOfLanguage(this.ofy, languageTag));
		//forward to the right jsp
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(request,response);
	}
	
	/**
	 * Take a page with it's path and return the
	 * right jsp page to display content
	 * @param path : Page's path
	 * @return jsp's name to display content.
	 */
	private String selectJsp(String path) {
		String jspName = "";
		String jspFolder = "";
	
		if(path != null){
			//delete param
			if(path.contains("?"))
				path = path.split("?")[0];
				
			// delete the first "/"
			path = path.substring(1, path.length());
			// replace all "%20" to a space char
			path = path.replaceAll("%20", " ");
			
			//if any page are selected, root page are take by default
			if(path.length() == 0)
				this.page = TemplateTools.getRootPage(this.ofy);
			else 
				this.page = TemplateTools.getArboPageWithPath(this.ofy, path);
		
		}else{//if path are null, take root page are take by default
			this.page = TemplateTools.getRootPage(this.ofy);
		}
		
		//no root or no page corresponding to path
		if(this.page == null)
			return defaultErrorPage;
		
		BeanTemplate template = TemplateTools.getTemplate(this.ofy ,this.page.getTemplateId());
		
		//no template selected for this page
		if(template == null)
			return defaultErrorPage;
		
		// Here you need to make good call for your template
		// and add right value in the jspFodler var
		if(template.getName().equals("siteBasic")){
			jspName = templateBasic();
			jspFolder = "site_basic";
		}else if(template.getName().equals("blogBasic")){
			jspName = blogBasic();
			jspFolder = "blog_basic";
		}else{
			// no template corresponding
			return defaultErrorPage;
		}
		
		//no tag corresponding for this template
		if(jspName == null)
			return this.errorTemplatePage;
		
		return "/"+jspFolder+"/"+jspName+".jsp";
	}
	
	/**
	 * Basic site template function with all specificity
	 * @return right jspName to call
	 */
	private String templateBasic() {
		
		List<BeanTag> tags = TemplateTools.getTag(this.ofy, this.page.getId());
		//define this var if you would use your one error page with specific style
		this.errorTemplatePage = "/site_basic/Denied.jsp";
		String jspName = null;
		
		for(BeanTag tag : tags){
			if(tag.getTagName().equals("MainPage")){
				jspName = "MainPage";
				break;
			}else if (tag.getTagName().equals("Category")){
				jspName = "Category";
				break;
			}else if (tag.getTagName().equals("Article")){
				jspName = "Article";
				break;
			}
		}
		
		return jspName;
	}
	
	/**
	 * Basic blog template function with all specificity
	 * @return right jspName to call
	 */
	private String blogBasic(){
		List<BeanTag> tags = TemplateTools.getTag(this.ofy, this.page.getId());
		//define this var if you would use your one error page with specific style
		this.errorTemplatePage = "/site_basic/Denied.jsp";
		String jspName = null;
		
		for(BeanTag tag : tags){
			if (tag.getTagName().equals("Blog")){
				jspName = "Blog";
				break;
			}
		}
		
		return jspName;
	}
	
}
