<%@page import="com.adityabhosale.crudapp.model.User"%>
<%
    User user = (User)session.getAttribute("user");

%>

<% if(user!=null) { %>
   <div class="d-flex align-items-center justify-content-end mb-3">
    <span class="me-3 text-muted">
        Welcome <strong><%=user.getUsername()%></strong>
    </span>
    <a href="auth?action=logout" class="btn btn-danger btn-sm">Logout</a>
    </div>
<% } %>