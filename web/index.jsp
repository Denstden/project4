<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/myTag"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${language}"></fmt:setLocale>
    <fmt:setBundle basename="languages"></fmt:setBundle>
    <title><fmt:message key="login_to_system"></fmt:message></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
</head>
<body>

<div id="wrapper" class="login-form">
      <div class="header" id="header">
        <h1><fmt:message key="login_to_system"></fmt:message></h1>
        <span id="spanError">
          ${no_user}
          ${incorrect_login_password}
          ${blocked}
        </span>
        <span id="span">
          ${success_registration}
          ${password_changed}
        </span>
      </div>
      <div class="content" id="content">
          <form action="handler" method="post">
             <%-- <label><fmt:message key="login"></fmt:message>: </label>--%>
            <input type="text" name="login" class="input username" placeholder="<fmt:message key="login"></fmt:message>"/> <br/>
              <%--<label><fmt:message key="password"></fmt:message>: </label>--%>
            <input type="password" name="password" class="input password" placeholder="<fmt:message key="password"></fmt:message>" /><br/>
            <mytag:commandTag  command="login"><fmt:message key="trylogin"></fmt:message></mytag:commandTag>
               <input type="button" class="register" onclick="location.href='register.jsp';" value="<fmt:message key="register"></fmt:message>"/>
          </form>

      </div>

      <div class="footer" id="footer">
          <form action="handler" method="post">
            <input type="text" hidden name="lang" value="ru_RU"/>
            <mytag:commandTag command="change_language"><fmt:message key="russian"></fmt:message></mytag:commandTag>
          </form>
          <form action="handler" method="post">
            <input type="text" hidden name="lang" value="en_US"/>
            <mytag:commandTag command="change_language"><fmt:message key="english"></fmt:message></mytag:commandTag>
          </form>
          <form action="handler" method="post">
            <input type="text" hidden name="lang" value="uk_UA"/>
            <mytag:commandTag command="change_language"><fmt:message key="ukrainian"></fmt:message></mytag:commandTag>
          </form>
      </div>
</div>
</body>
</html>
