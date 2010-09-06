<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.sfeir.richercms.site.template.template_basic.*" %>
<%@ page import = "com.sfeir.richercms.page.server.business.*, java.util.List" %>
<%@ page import = "com.googlecode.objectify.Objectify" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<% // initialize template class
		String siteSufix = "/site";
		int languageIndex = (Integer)request.getAttribute("language");
		String ParentPageName = "";
		ArboPage p = (ArboPage)request.getAttribute("page");
		Objectify ofy = (Objectify)request.getAttribute("ofy");
		TemplateBasic template = new TemplateBasic(ofy, p, languageIndex);
		LinkPage root = template.getRootPage();
	%>
	<meta name = "decription" content = "<%=template.getBrowserTitle()%>" />
	<meta name = "keywords" content = "<%=template.getKeyWord()%>" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><%=template.getBrowserTitle()%></title>
	<link href="/site_basic/style.css" rel="stylesheet" type="text/css" />
	<!--[if lte IE 8]> 
	<script type="text/javascript" src="/site_basic/DD_roundies_0.0.2a-min.js"> 
	</script>
	<script>
		 DD_roundies.addRule('#content', '20px');
	</script>
	<![endif]-->
</head>
<body>
	<!-- header -->
    <div id="logo"><a href="<%= siteSufix+root.getPath()%>"><%=root.getName()%></a></div>
    <div id="type">Article : <%=template.getPageTitle()%></div>
    <div id="language">
       <div id="language_fr"><a href="<%=template.getRegularPath(request.getPathInfo()+"?lg=fr") %>"><img src="/tab_images/fr.png" class="photo"/></a></div>
       <div id="language_en"><a href="<%=template.getRegularPath(request.getPathInfo()+"?lg=en") %>"><img src="/tab_images/en.png" class="photo"/></a></div>
	</div>
    <div id="header">
    	<div id="left_header"></div>
      	<div id="right_header"></div>
  </div> 
  <div id="menu">
    <ul>
      <% 
      	List<LinkPage> categs = template.getRootChildByTag("Category");
    	for (LinkPage categ : categs) {
    	    %>
    	     <li><a href="<%= siteSufix+categ.getPath()%>"><%= categ.getName()%></a></li>
    	    <%
    	}
      %>
    </ul>
  </div>
  <div id="path">
      <% 
      	List<LinkPage> linkPathes = template.getLinkPagePath();
      
      	//take parent Page name for left liste Menu
      	if(linkPathes.size()>=2){
    	  	ParentPageName = linkPathes.get(linkPathes.size()-2).getName();
    	  	//reduce name size if >25
    	  	if (ParentPageName.length() > 25)
    		 	 ParentPageName = ParentPageName.substring(0, 25)+" ...";
      	}
      	
    	for (LinkPage linkPath : linkPathes) {
    	    %>
    	     <a href="<%= siteSufix+linkPath.getPath()%>"><%= linkPath.getName()%></a> <span style="color: #ffffff;"> > </span> 
    	    <%
    	}
      %>
  </div>
    <!--end header -->
    <!-- main -->
    <div id="content">
      <div id="content_body">
       	  <div id="sidebar">
            <div id="sidebar_top"></div>
            <div id="sidebar_body">
            <h1><%= ParentPageName %></h1>
              <ul>
                <% 
        			List<LinkPage> sisters = template.getLinkSistersPage();
                
	        	    for (LinkPage sister : sisters) {
	        	    	if(sister.getName().equals(template.getPageTitle())){
	        	    		 %>
			        	        <li><%= sister.getName()%></li>
			        	        <%
	        	    	}else{
		        	        %>
		        	        <li><a href="<%= siteSufix+sister.getPath()%>"><%= sister.getName()%></a></li>
		        	        <%
	        	    	}
	        	    }
        		%>
              </ul>
              </div>
                <div id="sidebar_bottom"></div>
          </div>
            <div id="text">
            <div id="text_top">
            	<div id="text_top_left"></div>
                <div id="text_top_right"></div>
            </div>
            <div id="text_body">
            	<% if(template.isVisible()) {%>
            		<%= template.getContent() %>
            	<%}else{ 
            		switch(template.getTranslation()) {
            		case 0 :
            			%> Contenu non accessible pour l'instant <%
            			break;
            		case 1 :
            			%> DE : Contenu non accessible pour l'instant <%
            			
            			break;
            		case 2 :
            			%>content not accessible for the momment <%
            			break;
            		}
            	  } %>
              </div>
                <div id="text_bottom">
                	<div id="text_bottom_left"></div>
                    <div id="text_bottom_right"></div>
                </div>
          </div>
      </div>
    </div>
    <!-- end main -->
    <!-- footer -->
    <div id="footer">
    <div id="left_footer"><b>Richer CMS</b></div>
    <div id="right_footer">

Sponsored by <a href="http://www.sfeir.com/" title="Sfeir"> SFEIR Benelux</a>

    </div>
    </div>
    <!-- end footer -->
</body>
<%//template.storeLog(); %>
</html>