<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mytag" uri="/WEB-INF/myTag"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
  <script src="js/jquery-1.12.0.min.js"></script>
  <script src="js/requests.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/table.css"/>
</head>
<body>
<fmt:setLocale value="${language}"></fmt:setLocale>
<fmt:setBundle basename="languages"></fmt:setBundle>
<table>
  <tr>
    <th colspan="5">
      <fmt:message key="request"></fmt:message>
    </th>
    <th rowspan="2" colspan="2">
      <fmt:message key="number"></fmt:message>
    </th>
  </tr>
  <tr>
    <th>
      <fmt:message key="user"></fmt:message>
    </th>
    <th>
      <fmt:message key="count_places"></fmt:message>
    </th>
    <th>
      <fmt:message key="apartment_class"></fmt:message>
    </th>
    <th>
      <fmt:message key="book_from"></fmt:message>
    </th>
    <th>
      <fmt:message key="book_to"></fmt:message>
    </th>
  </tr>
  <c:forEach var="element" items="${requests}">
    <tr id="${element.id}">
      <form action="handler" method="post">
        <input hidden name="requestId" value="${element.id}">
        <td><input name="login" value="${element.client.login}" readonly/></td>
        <td><input name="countPlaces" value="${element.countPlaces}" readonly/></td>
        <td><input name="apartmentClass" value="${element.apartmentClass}" readonly/></td>
        <td><input name="busyFrom" value="<fmt:formatDate value="${element.busyFrom}" type="date" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>" readonly></td>
        <td><input name="busyTo" value="<fmt:formatDate value="${element.busyTo}" type="date" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>" readonly/></td>
        <td>
          <select id="apartmentsSelect${element.id}" name="apartmentsSelect" onchange="onApartmentChange(this)">
            <option value="" selected></option>
            <c:forEach var="elem" items="${apartments}">
              <option value="${elem.id}">
                  ${elem.number} |  ${elem.apartmentClass} | ${elem.countPlaces}
              </option>
            </c:forEach>
          </select>
          <table>
            <c:forEach var="elem" items="${load}">
              <c:forEach var="el" items="${elem.value}">
                <p hidden name="${el.apartment.id}${element.id}">
                  <fmt:message key="busy_from"></fmt:message>:
                  <fmt:formatDate value="${el.dateFrom}" type="date" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>,
                  <fmt:message key="to"></fmt:message>:
                  <fmt:formatDate value="${el.dateTo}" type="date" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                </p>
              </c:forEach>
            </c:forEach>
          </table>
        </td>
        <td><mytag:commandTag  command="bill"><fmt:message key="bill"></fmt:message></mytag:commandTag></td>
      </form>
    </tr>
  </c:forEach>
</table>
</body>
</html>
