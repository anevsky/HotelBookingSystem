<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- start left-sidebar -->
<div id="left-sidebar">
    <div id="logo">
        <c:set var="logorand" scope="page"><%= java.lang.Math.random()%></c:set>
        <c:choose>
            <c:when test="${logorand < 0.2}">    
                <img src="images/logo/left-logo-1.jpg" alt="alex hotel" width="240" />
            </c:when>
            <c:when test="${logorand >= 0.2 && logorand < 0.4}">    
                <img src="images/logo/left-logo-2.jpg" alt="alex hotel" width="240" /> 
            </c:when>
            <c:when test="${logorand >= 0.4 && logorand < 0.6}">    
                <img src="images/logo/left-logo-3.jpg" alt="alex hotel" width="240" />
            </c:when>
            <c:when test="${logorand >= 0.6 && logorand < 0.8}">    
                <img src="images/logo/left-logo-4.jpg" alt="alex hotel" width="240" />
            </c:when>
            <c:when test="${logorand >= 0.8}">    
                <img src="images/logo/left-logo-5.jpg" alt="alex hotel" width="240" />
            </c:when>
            <c:otherwise>
                <img src="images/logo/left-logo-1.jpg" alt="alex hotel" width="240" /> 
            </c:otherwise>
        </c:choose>
    </div>

    <h2><fmt:message bundle="${messages}" key="navigation.left.sidebar.user.corner.title" /></h2>
    <div class="text">
        <c:choose>
            <c:when test="${userRole == 'customer'}">
                <fmt:message bundle="${messages}" key="customer.title" /> 
            </c:when>
            <c:when test="${userRole == 'admin'}">
                <fmt:message bundle="${messages}" key="admin.title" /> 
            </c:when>
            <c:otherwise>
                <fmt:message bundle="${messages}" key="guest.title" />
            </c:otherwise>
        </c:choose>
            
        <c:out value="${login}" />
        
        <div id="button">
            <ul>
                <li>
                    <a href="controller?command=login"><fmt:message bundle="${messages}" key="navigation.left.sidebar.site.navigation.login" /></a>
                </li>
                <li>
                    <a href="controller?command=logout"><fmt:message bundle="${messages}" key="navigation.left.sidebar.site.navigation.logout" /></a>
                </li>
            </ul>
        </div> 
    </div>
                
    <br />
    
    <h2><fmt:message bundle="${messages}" key="navigation.left.sidebar.site.navigation.title" /></h2>
    <div id="button">
        <ul>
            <li>
                <a href="./"><fmt:message bundle="${messages}" key="navigation.index.page" /></a>
            </li>
            <li>
                <a href="controller"><fmt:message bundle="${messages}" key="navigation.main.page" /></a>
            </li>
            <li>
                <a href="controller?command=login"><fmt:message bundle="${messages}" key="navigation.login.page" /></a>
            </li>
        </ul>
    </div> 
            
    <br />
    
    <c:if test="${userRole == 'admin'}">
        <h2><fmt:message bundle="${messages}" key="navigation.left.sidebar.menu.admin.title" /></h2>
        <div id="button">
            <ul>                
                <li>
                    <a href="controller?command=vieworders"><fmt:message bundle="${messages}" key="navigation.left.sidebar.menu.admin.orders" /></a>
                </li>
                <li>
                    <a href="controller?command=viewrooms"><fmt:message bundle="${messages}" key="navigation.left.sidebar.menu.admin.rooms" /></a>
                </li>
                <li>
                    <a href="controller?command=viewcustomers"><fmt:message bundle="${messages}" key="navigation.left.sidebar.menu.admin.customers" /></a>
                </li>
            </ul>
        </div> 
        <br />
    </c:if>
                
    <c:if test="${userRole == 'customer'}">
        <h2><fmt:message bundle="${messages}" key="navigation.left.sidebar.menu.customer.title" /></h2>
        <div id="button">
            <ul>                
                <li>
                    <a href="controller?command=bookingroom"><fmt:message bundle="${messages}" key="navigation.left.sidebar.menu.customer.bookingroom" /></a>
                </li>
                <li>
                    <a href="controller?command=showmyorders"><fmt:message bundle="${messages}" key="navigation.left.sidebar.menu.customer.myorders" /></a>
                </li>
            </ul>
        </div> 
        <br />
    </c:if>
                
    <h2><fmt:message bundle="${messages}" key="navigation.left.sidebar.lang.title" /></h2>
    <div id="button">
        <ul>
            <li>
                <a href="controller?command=lang&locale=en">English</a>
            </li>
            <li>
                <a href="controller?command=lang&locale=ru">Русский</a>
            </li>
        </ul>
    </div>
    
    <br />
    
</div>
<!-- end left-sidebar -->