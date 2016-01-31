<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/myTag"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <fmt:setLocale value="${language}"></fmt:setLocale>
  <fmt:setBundle basename="languages"></fmt:setBundle>
  <title><fmt:message key="create_request"></fmt:message></title>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
</head>
<body>
<div id="wrapper" class="login-form">
  <div class="header" id="header">
    <h1><fmt:message key="create_request"></fmt:message></h1>
        <span id="spanError">
          ${send_request_error}
          ${incorrect_request_data}
        </span>
  </div>
  <div class="content" id="content">
    <form action="handler" method="post">
      <input type="text" name="countPlaces" class="input password" placeholder="<fmt:message key="count_places"></fmt:message>"/> <br/>
      <label><fmt:message key="apartment_class"></fmt:message>: </label>
      <select name="apartmentClass" class="input password">
        <option value="luxury"><fmt:message key="LUXURY"></fmt:message></option>
        <option value="average"><fmt:message key="AVERAGE"></fmt:message></option>
        <option value="economy"><fmt:message key="ECONOMY"></fmt:message></option>
      </select><br/>
      <input type="text" name="dateFrom" class="input password" placeholder="<fmt:message key="date"></fmt:message> <fmt:message key="from"></fmt:message>"/><br/>
      <input type="text" name="dateTo" class="input password" placeholder="<fmt:message key="date"></fmt:message> <fmt:message key="to"></fmt:message>"/><br/>
      <mytag:commandTag  command="send_request" ><fmt:message key="send_request"></fmt:message></mytag:commandTag>
      <input type="button" class="register" onclick="location.href='index.jsp';" value="<fmt:message key="to_index_page"></fmt:message>"/>
    </form>
  </div>
</div>
</body>
</html>
