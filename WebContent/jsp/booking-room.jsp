<!-- include jsp head -->
<%@ include file="/jsp/fragment/jsp-head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <!-- include head meta -->
        <%@ include file="/jsp/fragment/head-meta.jsp" %>
        <title>
            <fmt:message bundle="${messages}" key="booking.room.title" />
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
                        <fmt:message bundle="${messages}" key="booking.room.title" />
                    </h2>
                    <div class="text">
                        <fmt:message bundle="${messages}" key="page.booking.room.complete.form.message" />
                        <br /><br />
                        <form name="bookingForm" action="controller" method="post">
                            <input type="hidden" name="command" value="processform" />
                            <fieldset>
                                <legend>
                                    <fmt:message bundle="${messages}" key="page.booking.room.form.title" />
                                </legend>                                
                                <br />
                                <p>
                                    <label for="adult">
                                        <fmt:message bundle="${messages}" key="page.booking.room.form.adult" />:
                                    </label>
                                    <select name="adult" size="1">
                                        <c:forEach var="number" begin="1" end="3" step="1">
                                            <option><c:out value="${number}" /></option>
                                        </c:forEach>
                                    </select>
                                </p>
                                <p>
                                    <label for="child">
                                        <fmt:message bundle="${messages}" key="page.booking.room.form.child" />:
                                    </label>
                                    <select name="child" size="1">
                                        <c:forEach var="number" begin="0" end="3" step="1">
                                            <option><c:out value="${number}" /></option>
                                        </c:forEach>
                                    </select>
                                </p>   
                                <br /> 
                                <p>
                                    <label for="room-class">
                                        <fmt:message bundle="${messages}" key="page.booking.room.form.room.class" />:
                                    </label>
                                    <select name="room-class" size="1">
                                        <option value="Standart">
                                            <fmt:message bundle="${messages}" key="page.booking.room.form.room.class.option.standart" />
                                        </option>
                                        <option value="Luxury">
                                            <fmt:message bundle="${messages}" key="page.booking.room.form.room.class.option.luxury" />
                                        </option>
                                        <option value="Grand">
                                            <fmt:message bundle="${messages}" key="page.booking.room.form.room.class.option.grand" />
                                        </option>
                                    </select>
                                </p>
                                <br />                                
                                <p>
                                    <label for="arrival-year">
                                        <fmt:message bundle="${messages}" key="page.booking.room.form.arrival" />:
                                    </label>                                    
                                    <select name="arrival-year" size="1">
                                            <option><c:out value="${year}" /></option>
                                            <option><c:out value="${year + 1}" /></option>
                                    </select>
                                </p>
                                <p>
                                    <label for="arrival-month">
                                        <fmt:message bundle="${messages}" key="page.booking.room.form.arrival.month" />:
                                    </label>
                                    <select name="arrival-month" size="1">
                                        <c:forEach var="month" begin="1" end="12" step="1">
                                            <option><c:out value="${month}" /></option>
                                        </c:forEach>
                                    </select>
                                </p>
                                <p>
                                    <label for="arrival-day">
                                        <fmt:message bundle="${messages}" key="page.booking.room.form.arrival.day" />:
                                    </label>
                                    <select name="arrival-day" size="1">
                                        <c:forEach var="day" begin="1" end="31" step="1">
                                            <option><c:out value="${day}" /></option>
                                        </c:forEach>
                                    </select>
                                </p>    
                                <br />    
                                <p>
                                    <label for="nights">
                                        <fmt:message bundle="${messages}" key="page.booking.room.form.nights" />:
                                    </label>
                                    <select name="nights" size="1">
                                        <c:forEach var="number" begin="1" end="20" step="1">
                                            <option><c:out value="${number}" /></option>
                                        </c:forEach>
                                    </select>
                                </p>                                
                                <br />                                
                                <p>
                                    <label for="commentary">
                                        <fmt:message bundle="${messages}" key="page.booking.room.form.commentary" />:
                                    </label>
                                    <textarea name="commentary" rows="5" cols="35"></textarea>
                                </p>                                 
                                <br />                                
                                <p class="submit">
                                    <input type="submit" name="submit" value="<fmt:message bundle="${messages}" key="page.booking.room.form.submit" />" />
                                    <input type="reset" name="reset" value="<fmt:message bundle="${messages}" key="page.booking.room.form.reset" />" />
                                </p>
                            </fieldset>
                        </form>
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