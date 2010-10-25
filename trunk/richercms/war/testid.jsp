<%@page import="com.google.appengine.api.datastore.KeyRange"%>
<%@page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>
	<% 
	String kind = request.getParameter("kind");
	long start = Long.parseLong(request.getParameter("start"));
	long end = Long.parseLong(request.getParameter("end"));
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	KeyRange keyRange = new KeyRange(null, kind, start, end);
	DatastoreService.KeyRangeState res = ds.allocateIdRange(keyRange);
	out.print("Result for the current range : " + res + ".");
	%>
</body>
</html>