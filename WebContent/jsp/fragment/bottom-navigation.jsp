<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<a href="./" class="bottom"><fmt:message bundle="${messages}" key="navigation.index.page" /></a>
&nbsp;&nbsp;
<a href="controller" class="bottom"><fmt:message bundle="${messages}" key="navigation.main.page" /></a>
&nbsp;&nbsp;
<a href="controller?command=login" class="bottom"><fmt:message bundle="${messages}" key="navigation.login.page" /></a>
&nbsp;&nbsp;
<a href="controller?command=bookingroom" class="bottom"><fmt:message bundle="${messages}" key="navigation.booking.room" /></a>