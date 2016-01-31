<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/myTag"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${language}"></fmt:setLocale>
    <fmt:setBundle basename="languages"></fmt:setBundle>
    <title><fmt:message key="personal_cabinet"></fmt:message></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/dashboard.css"/>
</head>
<body>
<h1 align="center" style="color: blue"><fmt:message key="welcome"></fmt:message> ${user.login}!</h1>
<font color="BLUE">
    ${send_request_success}
</font>
<table>
    <tr>
        <td>
            <input type="button" class="register" onclick="location.href='createRequest.jsp';" value="<fmt:message key="create_request"></fmt:message>"/>
        </td>
    </tr>
    <tr>
        <td>
            <form action="handler" method="post">
                <mytag:commandTag  command="view_invoices" ><fmt:message key="view_invoices"></fmt:message></mytag:commandTag>
            </form>
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
