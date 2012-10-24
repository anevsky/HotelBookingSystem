<!-- include jsp head -->
<%@ include file="/jsp/fragment/jsp-head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <!-- include head meta -->
        <%@ include file="/jsp/fragment/head-meta.jsp" %>
        <title>
            <fmt:message bundle="${messages}" key="page.login.title" />
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

                    <c:if test="${empty login}">
                        <h2 style="color:red">
                            <fmt:message bundle="${messages}" key="page.login.no.login.message" />
                        </h2>
                    </c:if>

                    <c:choose>
                        <c:when test="${empty login}">
                            <h2><fmt:message bundle="${messages}" key="page.login.title" /></h2>
                            <div class="text">
                                <form name="loginForm" method="post" action="controller">
                                    <input type="hidden" name="command" value="login" />
                                    <fieldset>
                                        <legend>
                                            <fmt:message bundle="${messages}" key="page.login.auth.title" />
                                        </legend>                                
                                        <br /> 
                                        <p>
                                            <label for="login">
                                                <fmt:message bundle="${messages}" key="page.login.field.login" />:
                                            </label>
                                            <input type="text" name="login" maxlength=30 value="" />
                                        </p>  
                                        <br /> 
                                        <p>
                                            <label for="password">
                                                <fmt:message bundle="${messages}" key="page.login.field.password" />:
                                            </label>
                                            <input type="password" name="password" maxlength=30 value="" />
                                        </p>
                                        <br />                                
                                        <p class="submit">
                                            <input type="submit" name="submit" value="<fmt:message bundle="${messages}" key="page.login.field.enter" />" />
                                        </p>
                                    </fieldset>
                                </form>
                            </div>
                            </c:when>
                            <c:otherwise>                                
                                <h2 style="color:red">
                                    <fmt:message bundle="${messages}" key="page.login.already.login.message" />
                                </h2>
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
                                        <c:otherwise />
                                    </c:choose>
                                </h2>
                                <div class="text">
                                    <ul class="link">
                                        <li>
                                            <a href="./"><fmt:message bundle="${messages}" key="navigation.index.page" /></a>
                                        </li>
                                        <li>
                                            <a href="controller?command=logout">
                                                <fmt:message bundle="${messages}" key="navigation.left.sidebar.site.navigation.logout" />
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </c:otherwise>
                        </c:choose>

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