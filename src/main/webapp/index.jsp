<%@ page import="todo.models.Category" %>
<%@ page import="todo.store.HbmStore" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="scripts.js"></script>
    <title>TODO</title>
</head>

<body>

<div class="container">
    <c:if test="${user == null}">
        <a style="float: left" class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> Login</a>
    </c:if>
    <c:if test="${user != null}">
        <a style="float: right" class="nav-link" href="<%=request.getContextPath()%>/logout.do"> <c:out value="${user.name}"/>  | Log out</a>
    </c:if>

    <form>
        <div class="form-group">
            <input type="text" class="form-control" id="description" placeholder="Enter description">
        </div>
        <select class="form-control" name="cIds" id="cIds" multiple>

            <% for (Category category : HbmStore.instOf().findAllCategories()) { %>
                <option value="<%=category.getId()%>"><%=category.getName()%></option>
            <% } %>

        </select>
        <button type="button" class="btn btn-primary" onclick="return validate()">Submit</button>
        <input type="checkbox" id="check" onclick="showAll()">Show all</input>
    </form>

    <br>
    Tasks:
    <ul id="descriptionList">
        <form>
            <table class="table table-bordered table-hover table-sm">
                <tr>
                    <th scope="col">Description</th>
                    <th scope="col">Category</th>
                    <th scope="col">Created</th>
                    <th scope="col">Creator</th>
                    <th scope="col">Done</th>
                </tr>
            </table>
        </form>
    </ul>
</div>

</body>
</html>