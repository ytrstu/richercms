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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		String languageTag = getLanguage(request.getPathInfo(),"fr","&");
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
		//delete param
		if(path.contains("?"))
			path = path.split("?")[0];
			
		// delete the first "/"
		path = path.substring(1, path.length());
		// replace all "%20" to a space char
		path = path.replaceAll("%20", " ");
		
		this.page = TemplateTools.getArboPageWithPath(path);
		
		BeanTemplate template = TemplateTools.getTemplate(this.page.getTemplateId());
		
		if(template.getName().equals("basic"))
			jspFolder = "template_basic";
		
		List<BeanDependentTag> tags = TemplateTools.getTag(this.page.getId());
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
		
		return "/"+jspFolder+"/"+jspName+".jsp";
	}
	
	private String getLanguage(String path, String defaultLg, String separator){
		
		//contain param to get
		if(path.contains("?")){
			//split
			String[] pathAndParam = path.split("?");
			//Get all param in one string
			String allParam = pathAndParam[pathAndParam.length -1];
			
			//split different param separated by specific separator
			String[] params = allParam.split(separator);
			// for all param
			for(String param : params){
				// paramName '=' value
				String[] oneParam = param.split("=");
				//if language
				if(oneParam[0].equals("lg"))
					return oneParam[1];//return
			}
		}
		//default value
		return defaultLg;
	}
	
}
