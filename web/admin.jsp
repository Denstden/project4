<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/myTag"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${language}"></fmt:setLocale>
    <fmt:setBundle basename="languages"></fmt:setBundle>
    <title><fmt:message key="admin_panel"></fmt:message></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/dashboard.css"/>
</head>
<body>
<h1 align="center" style="color: red"><fmt:message key="welcome"></fmt:message> ${user.login}!</h1>
<font color="BLUE">
    ${bill_success}
    ${add_apartment_success}
    ${delete_user_success}
    ${edit_user_success}
</font>
<font color="RED">
    ${delete_user_error}
    ${edit_user_error}
</font>
<table>
    <tr>
        <td>
            <form action="handler" method="post">
                <mytag:commandTag  command="view_requests" ><fmt:message key="view_requests"></fmt:message></mytag:commandTag>
            </form>
        </td>
    </tr>
    <tr>
        <td>
            <form action="handler" method="post">
                <mytag:commandTag  command="load_users" ><fmt:message key="edit_user"></fmt:message></mytag:commandTag>
            </form>
        </td>
    </tr>
    <tr>
        <td>
            <input type="button" class="register" onclick="location.href='addApartment.jsp';" value="<fmt:message key="add_apartment"></fmt:message>"/>
        </td>
    </tr>
    <tr>
        <td>
            <form action="handler" method="post">
                <mytag:commandTag  command="logout" ><fmt:message key="logout"></fmt:message></mytag:commandTag>
            </form>
        </td>
    </tr>
</table>

</body>
</html>
