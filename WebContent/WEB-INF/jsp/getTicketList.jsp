<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Optional"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Optional<List<String[]>> optList = Optional.ofNullable((List<String[]>) request.getAttribute("list"));
List<String[]> list = new ArrayList<>();
if (optList.isPresent()) {
	list = optList.get();
}
%>

<%
for (String[] s : list) {
	String ticket_id = (String) request.getAttribute("ticket_id");
	String ticket_name = (String) request.getAttribute("ticket_name");
	String point = (String) request.getAttribute("point");
%>
{"ID":<%= ticket_id %>,"OptName":<%=ticket_name %>","POINT":<%=point %>},
<%
}
%>

