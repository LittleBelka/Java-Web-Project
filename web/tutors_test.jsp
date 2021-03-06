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
    <fmt:message bundle="${loc}" key="local.alert.testWithRegister" var="testWithRegister" />
    <fmt:message bundle="${loc}" key="local.text.subject" var="subject" />
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
        <form name="footerForm"  class="choiseInfoTest" action="" method="post">
            <c:if test="${requestScope.nameTestsByTutors != null}">
                <c:forEach items="${requestScope.nameTestsByTutors}" var="elem" varStatus="varStatus">
                    <input class="footerSubmitTestST" type="button" value="${elem.value.key}"
                           onclick="alert('<c:out value="${testWithRegister}"/>')">
                    <label><c:out value="${subject}" /> <c:out value="${elem.value.value}" /></label><br>
                </c:forEach>
            </c:if>
        </form>
    </div>

</div>
</body>
</html>