<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/myTag"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <fmt:setLocale value="${language}"></fmt:setLocale>
  <fmt:setBundle basename="languages"></fmt:setBundle>
  <title><fmt:message key="view_invoices"></fmt:message></title>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/table.css"/>
</head>
<body>
<table>
  <thead>
  <tr>
    <th align="left"><fmt:message key="number"></fmt:message></th>
    <th align="left"><fmt:message key="count_places"></fmt:message></th>
    <th align="left"><fmt:message key="apartment_class"></fmt:message></th>
    <th align="left"><fmt:message key="price"></fmt:message></th>
  </tr>
  </thead>
  <c:forEach var="element" items="${invoices}">
    <tr>
      <td>${element.apartment.number}</td>
      <td>${element.apartment.countPlaces}</td>
      <td><fmt:message key="${element.apartment.apartmentClass}"></fmt:message></td>
      <td>${element.price}</td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
