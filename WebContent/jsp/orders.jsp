<!-- include jsp head -->
<%@ include file="/jsp/fragment/jsp-head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <!-- include head meta -->
        <%@ include file="/jsp/fragment/head-meta.jsp" %>
        <title>
            <fmt:message bundle="${messages}" key="orders.title" />
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

                    <h2><fmt:message bundle="${messages}" key="orders.title" /></h2>
                    <div class="text">
                        <c:if test="${viewOrdersList}">
                            <ctg:ordersList />
                        </c:if>
                        <c:if test="${viewOrdersCustomerList}">
                            <ctg:ordersCustomerList customerId="${customerId}" />
                        </c:if>
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