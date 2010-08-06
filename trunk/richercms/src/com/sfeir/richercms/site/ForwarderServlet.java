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
import com.sfeir.richercms.page.server.business.Tag;
import com.sfeir.richercms.server.business.LogInfo;

public class ForwarderServlet extends HttpServlet {

	private static final long serialVersionUID = -6965673584834316768L;
	private ArboPage page;
	private static final String defaultLanguage = "fr";
	private static final String defaultErrorPage = "/site_basic/Denied.jsp"; // default error page
	private String errorTemplatePage = defaultErrorPage; // modify this function in your template function
	
	static {
        ObjectifyService.register(LogInfo.class);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		//log => begin
		Long avant = System.currentTimeMillis();
		Long apres = System.currentTimeMillis();
		Objectify ofy = ObjectifyService.begin();
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
		request.setAttribute("language", TemplateTools.getIndexOfLanguage(languageTag));
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
				this.page = TemplateTools.getRootPage();
			else 
				this.page = TemplateTools.getArboPageWithPath(path);
		
		}else{//if path are null, take root page are take by default
			this.page = TemplateTools.getRootPage();
		}
		
		//no root or no page corresponding to path
		if(this.page == null)
			return defaultErrorPage;
		
		String templateName = TemplateTools.getTemplateName(this.page.getTemplateId());
		
		//no template selected for this page
		if(templateName == null)
			return defaultErrorPage;
		
		// Here you need to make good call for your template
		// and add right value in the jspFodler var
		if(templateName.equals("siteBasic")){
			jspName = templateBasic();
			jspFolder = "site_basic";
		}else if(templateName.equals("blogBasic")){
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
		
		List<Tag> tags = TemplateTools.getTag(this.page.getId());
		//define this var if you would use your one error page with specific style
		this.errorTemplatePage = "/site_basic/Denied.jsp";
		String jspName = null;
		
		if(this.page.getId() == TemplateTools.getRootPage().getId())
			return "MainPage";
		
		for(Tag tag : tags){
			if (tag.getTagName().equals("Category")){
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
		List<Tag> tags = TemplateTools.getTag(this.page.getId());
		//define this var if you would use your one error page with specific style
		this.errorTemplatePage = "/site_basic/Denied.jsp";
		String jspName = null;
		
		for(Tag tag : tags){
			if (tag.getTagName().equals("Blog")){
				jspName = "Blog";
				break;
			}
		}
		
		return jspName;
	}
	
}
