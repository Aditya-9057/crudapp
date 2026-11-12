<%@page language="java" import = "java.util.*,com.adityabhosale.crudapp.model.Student"%>
<!DOCTYPE html>
<html>
    <head>
        <title>MVC CRUD APPLICATION</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@6.0.0-alpha.1/dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.1/css/all.css" integrity="sha512-x9WwyMYBnlXMNQ6kQ/Lyzu1NqIhLQKL5Oq6xByfXuRj7s9CskyCbLv/1IjqzJmXwFXWr0ov6jBV7Qbc0hh9nHg==" crossorigin="anonymous" referrerpolicy="no-referrer">
    </head>

    <body class="container mt-4">
        <h2 class="text-center mb-3">MVC CRUD APPLICATION</h2>
        <h5 class="text-center mb-3">SERVLET + JSP + JDBC</h5>

        <%
            String success = (String)request.getParameter("success");
            if(success != null){
        %>
            <div class="d-flex justify-content-center my-4">
                <div class="alert alert-success text-center w-50 mb-0">
                    <%= success %>
                </div>
            </div>

        <%
            }
        %>

        <a href="students?action=add" class="btn-solid theme-primary d-inline-block my-4">
        <i class="fa-sharp fa-solid fa-user-plus me-1"></i> Add Student
        </a>



        <table class="table table-bordered table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Mobile</th>
                    <th>Action</th>

                </tr>
            </thead>

            <tbody>
            <%
                int cnt = 1;
                List<Student> students = (List<Student>)request.getAttribute("students");
                if(students !=null && !students.isEmpty()){
                    for(Student s : students){
            %>

                     <tr>
                         <td><%= cnt++ %></td>
                         <td><%=s.getName() %></td>
                         <td><%=s.getEmail() %></td>
                         <td><%=s.getMobile() %></td>
                         <td>
                            <a href="students?action=edit&id=<%= s.getId() %>"
                             class="btn btn-warning btn-sm me-1">
                              <i class="fa-sharp fa-solid fa-pen-to-square"></i>
                            </a>
                            <a href="students?action=delete&id=<%= s.getId() %>"
                            class="btn btn-danger btn-sm" >

                            <i class="fa-solid fa-trash"></i>

                            </a>
                         </td>

                     </tr>
            <%
                    }
                 } else {
            %>
                    <tr>
                        <td colspan="5" class="text-center">No Students Found</t>
                    </tr>
            <%

                 }

            %>
            </tbody>
        </table>
        <%
                    int totalPages = (int)request.getAttribute("totalPages");
                    int currentPage = (int)request.getAttribute("currentPage");
                    int pageSize = (int)request.getAttribute("pageSize");
                    int totalRecords =(int)request.getAttribute("totalRecords");

                    int start = ((currentPage - 1) * pageSize) + 1;
                    int end = Math.min(start + pageSize - 1,totalRecords);
        %>

                    <div class="d-flex align-items-center justify-content-between mb-3">

                    <div class="text-muted">
                        Showing
                        <strong><%=start%><i class="fa-sharp fa-solid fa-arrow-right"></i><%=end%></strong>
                         of
                        <strong><%=totalRecords%></strong>
                    </div>

                    <form action="students" class="d-flex" class="d- align-items-center">
                       <span class="text-muted me-2">Show</span>
                        <select name="pageSize" class="form-select form-select-sm me-2" onchange="this.form.submit()" style="width:70px;">
                            <option value="5" <%= (pageSize==5? "selected" : "")%> >5</option>
                            <option value="10" <%= (pageSize==10? "selected" : "")%> >10</option>
                            <option value="20" <%= (pageSize==20? "selected" : "")%> >20</option>
                            <option value="50" <%= (pageSize==50? "selected" : "")%> >50</option>
                            <option value="100" <%= (pageSize==100? "selected" : "")%> >100</option>
                            <option value="200" <%= (pageSize==200? "selected" : "")%> >200</option>
                        </select>
                        <span class="text-muted">entries</span>

                    </form>




                <form action="students" method="get" class="d- align-items-center">
                    <span class="text-muted me-2">Go</span>

                    <input type="hidden" name="pgeSize" value="<%=pageSize%>">

                    <input type="number" name="page"
                    min = 1
                    max = <%=totalPages%>
                     class="form-control form-control-sm me-2"
                     style="width:70px;">
                    <button class="btn btn-sm btn-primary">Go</button
                </form>

                </div>

                 <% if(totalPages > 1) { %>
                 <nav aria-label="Page navigation example">
                          <ul class="pagination justify-content-center">

                          <!-- First -->
                           <li class="page-item <%= (currentPage==1)? "disabled" : "" %>">
                               <a class="page-link" href="students?page=1&pageSize=<%=pageSize%>">
                               <i class="fa-sharp fa-solid fa-backward-fast"></i>
                               </a>
                           </li>

                          <!-- Previous -->
                            <li class="page-item <%= (currentPage==1)? "disabled" : "" %>">
                                <a class="page-link" href="students?page=<%= (currentPage-1)%>&pageSize=<%=pageSize%>">
                                <i class="fa-solid fa-chevron-left"></i>
                                </a>
                            </li>

                            <% for(int i=1;i<=totalPages;i++) { %>
                            <li class="page-item <%= (i==currentPage)? "active" : ""%>">
                                <a class="page-link" href="students?page=<%= i %>&pageSize=<%=pageSize%>">
                                <%= i %>
                                </a>
                            </li>
                            <% } %>


                            <!-- Next -->
                            <li class="page-item <%= (currentPage==totalPages)? "disabled" : "" %>">
                                <a class="page-link" href="students?page=<%= (currentPage+1)%>&pageSize=<%=pageSize%>">
                                <i class="fa-solid fa-chevron-right"></i>
                                </a>
                            </li>

                            <!-- Last -->
                             <li class="page-item <%= (currentPage==totalPages)? "disabled" : "" %>">
                                 <a class="page-link" href="students?page=<%= totalPages %>&pageSize=<%=pageSize%>">
                                 <i class="fa-solid fa-forward-fast"></i>
                                 </a>
                             </li>

                          </ul>
                 </nav>
                 <% }%>



    </body>
</html
