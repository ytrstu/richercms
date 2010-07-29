package com.sfeir.richercms.site;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanDependentTag;
import com.sfeir.richercms.page.shared.BeanTemplate;


public class ForwarderServlet extends HttpServlet {

	private static final long serialVersionUID = -6965673584834316768L;
	private BeanArboPage page;
	private static final String defaultLanguage = "fr";
	private static final String defaultErrorPage = "/template_basic/Denied.jsp"; // default error page
	private String errorTemplatePage = defaultErrorPage; // modify this function in your template function
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		String languageTag = request.getParameter("lg");
		if(languageTag == null)
			languageTag = defaultLanguage;
		String nextJSP = selectJsp(request.getPathInfo());
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
			this.page = TemplateTools.getRootPage();;
		}
		
		//no root or no page corresponding to path
		if(this.page == null)
			return defaultErrorPage;
		
		BeanTemplate template = TemplateTools.getTemplate(this.page.getTemplateId());
		
		//no template selected for this page
		if(template == null)
			return defaultErrorPage;
		
		// Here you need to make good call for your template
		// and add right value in the jspFodler var
		if(template.getName().equals("basic")){
			jspName = templateBasic();
			jspFolder = "template_basic";
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
	 * Basic template function with all specificity
	 * @return right jspName to call
	 */
	private String templateBasic() {
		
		List<BeanDependentTag> tags = TemplateTools.getTag(this.page.getId());
		//define this var if you would use your one error page with specific style
		this.errorTemplatePage = "/template_basic/Denied.jsp";
		String jspName = null;
		
		for(BeanDependentTag tag : tags){
			if(tag.getDependentTag().getTagName().equals("MainPage")){
				jspName = "MainPage";
				break;
			}else if (tag.getDependentTag().getTagName().equals("Category")){
				jspName = "Category";
				break;
			}else if (tag.getDependentTag().getTagName().equals("Article")){
				jspName = "Article";
				break;
			}
		}
		
		return jspName;
	}
	
}
