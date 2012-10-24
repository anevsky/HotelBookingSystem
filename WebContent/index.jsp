<%@ page pageEncoding="UTF-8" %>
<!-- include jsp head -->
<%@ include file="/jsp/fragment/jsp-head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <!-- include head meta -->
        <%@ include file="/jsp/fragment/head-meta.jsp" %>
        <title>
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

                    <h2><fmt:message bundle="${messages}" key="welcome.title" /></h2>
                    <div class="text">
                        <fmt:message bundle="${messages}" key="page.index.welcome.message" /> <strong>alex hotel</strong>!
                        <br /><br />
                        <a href="controller">
                            <fmt:message bundle="${messages}" key="page.index.link.hotel.controller" />
                        </a>
                    </div>

                    <h2>alex hotel</h2>
                    <div class="text">                        
                        <c:set var="rand" scope="page"><%= java.lang.Math.random()%></c:set>
                        
                        <c:choose>
                            <c:when test="${rand < 0.3}">    
                                <img src="images/olivia/ow-1.jpg" alt="<fmt:message bundle="${messages}" key="alt.image.olivia" />" width="610" />  
                            </c:when>
                            <c:when test="${rand >= 0.3 && rand <= 0.6}">    
                                <img src="images/olivia/ow-2.jpg" alt="<fmt:message bundle="${messages}" key="alt.image.olivia" />" width="610" />  
                            </c:when>
                            <c:when test="${rand > 0.6}">    
                                <img src="images/olivia/ow-3.jpg" alt="<fmt:message bundle="${messages}" key="alt.image.olivia" />" width="610" />  
                            </c:when>
                            <c:otherwise>   
                                <img src="images/olivia/ow-1.jpg" alt="<fmt:message bundle="${messages}" key="alt.image.olivia" />" width="610" />  
                            </c:otherwise>
                        </c:choose>
                                
                        <br /><br />
                        <fmt:message bundle="${messages}" key="page.index.alex.hotel.message.1" />
                        <br /><br /> 
                        <img src="images/hotel/hotel-view-1.jpg" alt="<fmt:message bundle="${messages}" key="alt.image.hotel.view.1" />" width="610" />  
                        <br /><br />
                        <fmt:message bundle="${messages}" key="page.index.alex.hotel.message.2" />
                        <br /><br />
                        <img src="images/hotel/hotel-front-1.jpg" alt="<fmt:message bundle="${messages}" key="alt.image.hotel.front.1" />" width="610" />  
                        <br /><br />
                        <fmt:message bundle="${messages}" key="page.index.alex.hotel.message.3" />
                        <br /><br />
                        <img src="images/hotel/hotel-room-1.jpg" alt="<fmt:message bundle="${messages}" key="alt.image.hotel.room.1" />" width="610" />  
                        <br /><br />
                        <fmt:message bundle="${messages}" key="page.index.alex.hotel.message.4" />
                        <br /><br />
                        <img src="images/hotel/hotel-bar-1.jpg" alt="<fmt:message bundle="${messages}" key="alt.image.hotel.bar.1" />" width="610" />  
                        <br /><br />
                        <fmt:message bundle="${messages}" key="page.index.alex.hotel.message.5" />
                        <br /><br />
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