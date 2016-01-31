<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/myTag"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <fmt:setLocale value="${language}"></fmt:setLocale>
    <fmt:setBundle basename="languages"></fmt:setBundle>
    <title><fmt:message key="add_apartment"></fmt:message></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/styles.css"/>
</head>
<body>
<div id="wrapper" class="login-form">
  <div class="header" id="header">
    <h1><fmt:message key="add_apartment"></fmt:message></h1>
        <span id="spanError">
          ${add_apartment_error}
        </span>
  </div>
  <div class="content" id="content">
    <form action="handler" method="post">
      <input type="text" name="number" class="input password" placeholder="<fmt:message key="number"></fmt:message>"/> <br/>
      <input type="text" name="countPlaces" class="input password" placeholder="<fmt:message key="count_places"></fmt:message>"/> <br/>
      <label><fmt:message key="apartment_class"></fmt:message>: </label>
      <select name="apartmentClass"  class="input password">
        <option value="luxury"><fmt:message key="LUXURY"></fmt:message></option>
        <option value="average"><fmt:message key="AVERAGE"></fmt:message></option>
        <option value="economy"><fmt:message key="ECONOMY"></fmt:message></option>
      </select><br/>
      <mytag:commandTag  command="add_apartment" ><fmt:message key="add_apartment"></fmt:message></mytag:commandTag>
      <input type="button" class="register" onclick="location.href='index.jsp';" value="<fmt:message key="to_index_page"></fmt:message>"/>
    </form>
  </div>
</div>

</body>
</html>
