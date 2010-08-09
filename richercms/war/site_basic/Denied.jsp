<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!--
Author: Reality Software
Website: http://www.realitysoftware.ca
Note: This is a free template released under the Creative Commons Attribution 3.0 license, 
which means you can use it in any way you want provided you keep the link to the author intact.
-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<% // initialize template class
		String msg = request.getParameter("msg");
	%>
<title></title>
<link href="/site_basic/style.css" rel="stylesheet" type="text/css" />
<body>
	<!-- header -->
    <div id="logo"><a href="#">/!\ -- Access denied -- /!\</a></div>
    <div id="header">
    	<div id="left_header"></div>
        <div id="right_header"></div>
  </div> 
  <div id="menu">
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
				<h1>/!\ -- Warning -- /!\</h1>
            </div>
            <div id="sidebar_bottom"></div>
          </div>
            <div id="text">
            <div id="text_top">
            	<div id="text_top_left"></div>
                <div id="text_top_right"></div>
            </div>
            <div id="text_body">
              <h1><span>RicherCMS</span></h1>
			  The page you requested is unavailable at this time.
			  Check the address entered in the address bar of your  web browser
			  <br/>
              Reason : <%=msg%>
              <div id="foot_text">Contact webMaster if problem perssist</div>
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
