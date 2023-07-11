<%@page import="java.util.List"%>
<%@page import="shoppingapp.LoginCheck"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
     <%
     Connection con=(Connection)application.getAttribute("MyConnection");
     List<Integer> selectedProducts=(List<Integer>)session.getAttribute("cart");
     if(selectedProducts==null)
     {%>
    	 <h1>No products</h1>
    <% }
     else
     { %>
    	 <ul>
    	 <% for(int a: selectedProducts)
    		 { %>
    		    <li><%= a %></li>
    	<% }%>
    	 </ul>
    	 <% 
     }
      
     %>
</body>
</html>