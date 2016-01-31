<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/myTag"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <fmt:setLocale value="${language}"></fmt:setLocale>
    <fmt:setBundle basename="languages"></fmt:setBundle>
    <title><fmt:message key="change_password"></fmt:message></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
</head>
<body>
<div id="wrapper" class="login-form">
    <div class="header" id="header">
      <h1><fmt:message key="change_password"></fmt:message></h1>
        <span id="spanError">
          ${incorrect_old_password}
          ${equals_old_new_password}
          ${not_match_new_confirm_password}
          ${password_change_error}
        </span>
         <span id="span">
          ${need_to_change_password}
        </span>

    </div>
    <div class="content" id="content">
      <form action="handler" method="post">
        <input type="text" name="login" class="input username" placeholder="<fmt:message key="login"></fmt:message>" value="${login}"/><br/>
        <input type="password" name="oldPassword" class="input password" placeholder="<fmt:message key="old_password"></fmt:message>"/><br/>
        <input type="password" name="newPassword" class="input password" placeholder="<fmt:message key="new_password"></fmt:message>"/><br/>
        <input type="password" name="confirmPassword" class="input password" placeholder="<fmt:message key="confirm_password"></fmt:message>"/><br/>
        <mytag:commandTag  command="change_password"><fmt:message key="change_password"></fmt:message></mytag:commandTag>
        <input type="button" class="register" onclick="location.href='index.jsp';" value="<fmt:message key="to_index_page"></fmt:message>"/>
      </form>
    </div>
</div>

</body>
</html>
