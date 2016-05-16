<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<link href="css/style_home_user.css" rel="stylesheet">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <c:choose>
        <c:when test="${sessionScope.status == 'student'}">
            <script language="JavaScript">
                window.location.href = "http://localhost:8080/student_home.jsp"
            </script>
        </c:when>
        <c:when test="${sessionScope.status == 'tutor'}">
            <script language="JavaScript">
                window.location.href = "http://localhost:8080/tutor_home.jsp"
            </script>
        </c:when>
    </c:choose>
    <title>Tutors</title>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.userHome.locale" var="loc" />
    <fmt:message bundle="${loc}" key="local.menu.home" var="home" />
</head>

<body>
<div class="main">
    <div class="menu">
        <ul id="leftMenuWithRegistration">
            <li><form action="home.jsp" method="post">
                <input type="submit" value=<c:out value="${home}" /> /><br />
            </form></li>
        </ul>
    </div>

    <div class = "centreInfo">
        <form name="footerForm"  class="choiseInfoTest" action="ChoiceTestsByTutors" method="post">
            <c:if test="${sessionScope.tutors != null}">
                <c:forEach items="${sessionScope.tutors}" var="elem" varStatus="varStatus">
                    <input class="footerSubmitTT" type="button" value="${elem.value}"
                           onclick="document.getElementById('command').value='${elem.key}';document.footerForm.submit(); "><br>
                </c:forEach>
            </c:if>
            <input type="hidden" name="subjectFooter" id="command" value="" />
        </form>
    </div>

</div>
</body>
</html>