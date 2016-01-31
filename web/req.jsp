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
    <form action="handler" method="post">
      <td>
        <label for="requestsSelect"><fmt:message key="request"></fmt:message>: </label>
        <select id="requestsSelect" name="requestsSelect">
          <c:forEach var="element" items="${requests}">
            <option value="${element.client.id} ${element.busyFrom} ${element.busyTo} ${element.id}">
                ${element.client.login} | ${element.countPlaces} | ${element.apartmentClass} | ${element.busyFrom} | ${element.busyTo}
            </option>
          </c:forEach>
        </select>
      </td>
      <td>
        <label for="apartmentsSelect"><fmt:message key="apartment"></fmt:message>: </label>
        <select id="apartmentsSelect" name="apartmentsSelect" onchange="onApartmentChange()">
          <c:forEach var="elem" items="${apartments}">
            <option value="${elem.id}">
                ${elem.number} |  ${elem.apartmentClass} | ${elem.countPlaces}
            </option>
          </c:forEach>
        </select>
        <table>
          <c:forEach var="elem" items="${load}">
            <c:forEach var="el" items="${elem.value}">
              <%--<tr hidden><td>--%><p hidden name="${el.apartment.id}"><fmt:message key="busy_from"></fmt:message>: ${el.dateFrom}, <fmt:message key="to"></fmt:message>: ${el.dateTo}</p>
              <%--</td></tr>--%>
            </c:forEach>
          </c:forEach>
        </table>
      </td>
      <mytag:commandTag  command="bill"><fmt:message key="bill"></fmt:message></mytag:commandTag>
    </form>
    ${bill_error}
  </tr>
</table>
</body>
</html>

