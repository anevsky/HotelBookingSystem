<!-- include jsp head -->
<%@ include file="/jsp/fragment/jsp-head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <!-- include head meta -->
        <%@ include file="/jsp/fragment/head-meta.jsp" %>
        <title>
            <fmt:message bundle="${messages}" key="welcome.title" />
            -
            <fmt:message bundle="${messages}" key="system.title" />
        </title>
        <!-- include head link -->
        <%@ include file="/jsp/fragment/head-link.jsp" %>
    </head>
    <body>

        <!-- include body header -->
        <%@ include file="/jsp/fragment/header.jsp" %>

        <!-- start content -->
        <div id="content">
            <!-- start content container -->
            <div id="container">

                <!-- include body left-sidebar -->
                <%@ include file="/jsp/fragment/left-sidebar.jsp" %>

                <!-- start right-sidebar -->
                <div id="right-sidebar">
                    <h1><fmt:message bundle="${messages}" key="system.title" /></h1>

                    <h2>
                        <c:choose>
                            <c:when test="${userRole == 'customer'}">
                                <fmt:message bundle="${messages}" key="message.welcome.customer">
                                    <fmt:param value="${login}" />
                                </fmt:message>                
                            </c:when>
                            <c:when test="${userRole == 'admin'}">
                                <fmt:message bundle="${messages}" key="message.welcome.admin">
                                    <fmt:param value="${login}" />
                                </fmt:message>  
                            </c:when>
                            <c:otherwise>
                                <fmt:message bundle="${messages}" key="message.welcome.guest" />
                            </c:otherwise>
                        </c:choose>
                    </h2>
                    
                    <c:if test="${result != null}">
                        <h2 style="color:darkviolet">
                            <fmt:message bundle="${messages}" key="${result}" />
                        </h2>
                    </c:if>

                    <h2><fmt:message bundle="${messages}" key="message.select.action" /></h2>
                    <div class="text">
                        <c:choose>
                            <c:when test="${userRole == 'customer'}">
                                <ul class="link">
                                    <li>
                                        <a href="controller?command=bookingroom">
                                            <fmt:message bundle="${messages}" key="action.booking.room" />
                                        </a>
                                    </li>
                                    <li>
                                        <a href="controller?command=showmyorders">
                                            <fmt:message bundle="${messages}" key="action.show.myorders" />
                                        </a>
                                    </li>
                                </ul>
                            </c:when>
                            <c:when test="${userRole == 'admin'}">
                                <ul class="link">                                    
                                    <li>
                                        <a href="controller?command=vieworders">
                                            <fmt:message bundle="${messages}" key="action.view.orders" />
                                        </a>
                                    </li>
                                    <li>
                                        <a href="controller?command=viewrooms">
                                            <fmt:message bundle="${messages}" key="action.view.rooms" />
                                        </a>
                                    </li>
                                    <li>
                                        <a href="controller?command=viewcustomers">
                                            <fmt:message bundle="${messages}" key="action.view.customers" />
                                        </a>
                                    </li>
                                </ul>
                            </c:when>
                            <c:otherwise />
                        </c:choose>
                    </div>

                </div>
                <!-- end right-sidebar -->

            </div>
            <!-- end container -->
        </div>
        <!-- end content -->

        <!-- include body footer -->
        <%@ include file="/jsp/fragment/footer.jsp" %>

    </body>
</html>