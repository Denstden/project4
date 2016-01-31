<%@ taglib prefix="mytag" uri="/WEB-INF/myTag"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <fmt:setLocale value="${language}"></fmt:setLocale>
    <fmt:setBundle basename="languages"></fmt:setBundle>
    <title><fmt:message key="register"></fmt:message></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
</head>
<body>
<div id="wrapper" class="login-form">
  <div class="header" id="header">
    <h1><fmt:message key="register"></fmt:message></h1>
        <span id="spanError">
          ${incorrect_register_data}
        </span>
  </div>
  <div class="content" id="content">
    <form action="handler" method="post">
      <input type="text" name="login" class="input username" placeholder="<fmt:message key="login"></fmt:message>"/> <br/>
      <input type="password" name="password" class="input password" placeholder="<fmt:message key="password"></fmt:message>"/><br/>
      <input type="text" name="phone" class="input password" placeholder="<fmt:message key="phone"></fmt:message>"/><br/>
      <input type="email" name="email" class="input password" placeholder="<fmt:message key="email"></fmt:message>"/><br/>
      <mytag:commandTag  command="register"><fmt:message key="register"></fmt:message></mytag:commandTag>
      <input type="button" class="register" onclick="location.href='index.jsp';" value="<fmt:message key="to_index_page"></fmt:message>"/>
    </form>
  </div>
</div>
</body>
</html>
