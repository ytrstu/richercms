<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "com.sfeir.richercms.site.template.template_basic.*" %>
<%@ page import = "com.sfeir.richercms.page.shared.*, java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<% // initialize template class
		String siteSufix = "/site";
		int languageIndex = (Integer)request.getAttribute("language");
		BeanArboPage p = (BeanArboPage)request.getAttribute("page");
		TemplateBasic template = new TemplateBasic(p, languageIndex);
		LinkPage root = template.getRootPage();
	%>
	<meta name = "decription" content = "<%=template.getBrowserTitle()%>" />
	<meta name = "keywords" content = "<%=template.getKeyWord()%>" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><%=template.getBrowserTitle()%></title>
	<link href="/template_basic/style.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<!-- header -->
    <div id="logo"><a href="<%= siteSufix+root.getPath()%>"><%=root.getName()%></a></div>
    <div id="language">
       <div id="language_fr"><a href="/link"><img src="/tab_images/fr.png" class="photo"/></a></div>
       <div id="language_en"><a href="/link"><img src="/tab_images/en.png" class="photo"/></a></div>
       <div id="language_de"><a href="/link"><img src="/tab_images/de.png" class="photo"/></a></div>
	</div>
    <div id="header">
    	<div id="left_header"></div>
      	<div id="right_header"></div>
  </div> 
  <div id="menu">
    <ul>
      <% 
      	List<LinkPage> categs = template.getAllCategory();
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
    	<div id="content_top">
        	<div id="content_top_left"></div>
            <div id="content_top_right"></div>
        </div>
      <div id="content_body">
       	  <div id="sidebar">
            <div id="sidebar_top"></div>
            <div id="sidebar_body">
            <h1>Sub Categories</h1>
              <ul>
                <% 
        			List<LinkPage> subCategs = template.getChildCategory();
	        	    for (LinkPage subCateg : subCategs) {
	        	        %>
	        	        <li><a href="<%= siteSufix+subCateg.getPath()%>"><%= subCateg.getName()%></a></li>
	        	        <%
	        	    }
        		%>
              </ul>
              <h1>Article</h1>
              <ul>
                <% 
        			List<LinkPage> articles = template.getChildArticle();
	        	    for (LinkPage article : articles) {
	        	        %>
	        	        <li><a href="<%= siteSufix+article.getPath()%>"><%= article.getName()%></a></li>
	        	        <%
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
            	<%=template.getContent() %>
              </div>
                <div id="text_bottom">
                	<div id="text_bottom_left"></div>
                    <div id="text_bottom_right"></div>
                </div>
          </div>
      </div>
        <div id="content_bottom">
        	<div id="content_bottom_left"></div>
            <div id="content_bottom_right"></div>
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
</html>