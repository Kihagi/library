<%--
  Created by IntelliJ IDEA.
  User: mathenge
  Date: 3/24/2018
  Time: 1:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="partial/header.jsp"/>


<div class="wrapper">
    <div class="container">
        <!-- Page-Title -->
        <div class="row">
            <div class="col-sm-12">
                <h4 class="page-title">Employees</h4>
            </div>
        </div>


        <div class="row">
            <div class="col-xs-12">
                <div class="card-box">

                    <div class="row">
                        <div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
                            <h4 class="header-title m-t-0">All Employees</h4>
                            <p class="text-muted font-13 m-b-10">
                                Edit or Delete Employee
                            </p>

                            <div class="p-20">
                                <table class="table">
                                    <thead class="thead-default">
                                    <tr>
                                        <th>#</th>
                                        <th>Names</th>
                                        <th>Phone Number</th>
                                        <th>Email</th>
                                        <th>Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="employee" items="${employees}">
                                        <tr>

                                            <td>${ employee.names }</td>
                                            <td>${ employee.phone }</td>
                                            <td>${ employee.email }</td>
                                            <td>
                                                <a class="btn btn-primary-outline waves-effect waves-light" href="javascript:void(0);"  onclick="edit_employee('${ employee.id }')">
                                                    <i class="fa fa-edit "></i>&nbsp; EDIT</a>&nbsp;&nbsp;

                                                <button class="btn btn-sm btn-danger" onclick="delete_employee('${ employee.id }', '${ employee.names }');">
                                                    <i class="fa fa-edit "></i>&nbsp; DELETE</button>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>

                                </table>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
        </div>
        <!-- end row -->

    </div> <!-- container -->
</div> <!-- End wrapper -->

<script type="text/javascript">
</script>

<jsp:include page="partial/footer.jsp"/>
