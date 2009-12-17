<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>

<%
	UserService userService = UserServiceFactory.getUserService();
	if (!userService.isUserLoggedIn()) {
%>
Example page of authentication. No role is checked.
<br/>
Please 1
<A
	HREF="<%=userService.createLoginURL("/examples/authentication.jsp")%>">log
in</A>
<%
	} else {
%>
Welcome,
<%=userService.getCurrentUser().getNickname()%>! (
<A
	HREF="<%=userService.createLogoutURL("/examples/authentication.jsp")%>">log
out</A>
)
<%
	}
%>

