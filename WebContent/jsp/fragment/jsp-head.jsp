<!-- start jsp head -->
<%@ page 
    errorPage="/jsp/error.jsp" 
    language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/customtags" prefix="ctg" %>

<c:if test="${requestScope['specialUserLocale'] != null}">
    <c:set var="specialUserLocale" scope="session" value="${specialUserLocale}"/>
    <fmt:setLocale value="${specialUserLocale}" scope="session" />
</c:if>

<fmt:setBundle basename="properties.messages"
               var="messages" scope="application" />
<!-- end jsp head -->