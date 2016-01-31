<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/myTag"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <fmt:setLocale value="${language}"></fmt:setLocale>
    <fmt:setBundle basename="languages"></fmt:setBundle>
    <title><fmt:message key="edit_user"></fmt:message></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
    <script src="js/jquery-1.12.0.min.js"></script>
    <script src="js/users.js"></script>
</head>
<body>
<div id="wrapper" class="login-form">
    <div class="header" id="header">
        <h1><fmt:message key="edit_user"></fmt:message></h1>
    </div>
    <div class="content" id="content">
        <form action="handler" method="post">
            <label><fmt:message key="user"></fmt:message>: </label>
            <select id="usersSelect" name="usersSelect" class="input password" onchange="loadFields()">
              <c:forEach var="element" items="${users}">
                <option value="${element.login} ${element.password} ${element.phone} ${element.email} ${element.role} ${element.isBlocked} ${element.id}">
                    ${element.login} | ${element.isBlocked}
                </option>
              </c:forEach>
            </select><br/>
            <input hidden type="text" name="id"/>
            <input type="text" name="login" class="input password" placeholder="<fmt:message key="login"></fmt:message>"/> <br/>
            <input type="password" name="password" class="input password" placeholder="<fmt:message key="password"></fmt:message>"/><br/>
            <input type="text" name="phone" class="input password" placeholder="<fmt:message key="phone"></fmt:message>"/><br/>
            <input type="email" name="email" class="input password" placeholder="<fmt:message key="email"></fmt:message>"/><br/>
            <label><fmt:message key="group"></fmt:message>: </label>
            <select name="group" class="input password">
              <option value="admin" name="admin"><fmt:message key="admin"></fmt:message></option>
              <option value="user" name="user"><fmt:message key="user"></fmt:message></option>
            </select><br/>
            <label><fmt:message key="is_blocked"></fmt:message>: </label>
            <input type="checkbox" name="isBlocked"/><br/>
            <mytag:commandTag  command="edit_user"><fmt:message key="edit_user"></fmt:message></mytag:commandTag>
        </form>
        <form action="handler" method="post">
            <input hidden type="text" name="login"/>
            <mytag:commandTag  command="delete_user"><fmt:message key="delete_user"></fmt:message></mytag:commandTag>
            <input type="button" class="register" onclick="location.href='index.jsp';" value="<fmt:message key="to_index_page"></fmt:message>"/>
        </form>
    </div>
</div>
</body>
</html>
