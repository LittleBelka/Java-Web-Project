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
        <c:when test="${sessionScope.status == null}">
            <script language="JavaScript">
                window.location.href = "http://localhost:8080"
            </script>
        </c:when>
        <c:when test="${sessionScope.status == 'tutor'}">
            <script language="JavaScript">
                window.location.href = "tutor_home.jsp"
            </script>
        </c:when>
    </c:choose>
    <title>Student home</title>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.userHome.locale" var="loc" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
    <fmt:message bundle="${loc}" key="local.button.subjects" var="subjects" />
    <fmt:message bundle="${loc}" key="local.button.tutors" var="tutors" />
    <fmt:message bundle="${loc}" key="local.menu.home" var="home" />
    <fmt:message bundle="${loc}" key="local.menu.logOut" var="menuLogOut" />
    <fmt:message bundle="${loc}" key="local.alert.logOut" var="alertLogOut" />
    <fmt:message bundle="${loc}" key="local.text.student" var="student" />
    <fmt:message bundle="${loc}" key="local.text.tutor" var="tutor" />
    <fmt:message bundle="${loc}" key="local.text.subject" var="subject" />
</head>

<body>
<div class="main">
    <div class="menu">
        <ul id="rightMenu">
            <li><form action="LocaleSet" method="post">
                <input type="hidden" name="local" value="ru" /> <input class="locale" type="submit" value="${ru_button}" /><br />
            </form></li>
            <li><form action="LocaleSet" method="post">
                <input type="hidden" name="local" value="en" /> <input type="submit" value="${en_button}" /><br />
            </form></li>
        </ul>
        <ul id="leftMenu">
            <li><form action="student_home.jsp" method="post">
                <input type="submit" value=<c:out value="${home}" /> /><br />
            </form></li>
            <li><form action="LogOut" method="post">
                <input type="submit"  onclick="return confirm('<c:out value="${alertLogOut}" />')"
                       value='<c:out value="${menuLogOut}"/>' /><br />
            </form></li>
        </ul>
    </div>

    <div class = "centreImage">
        <div class = "name_user">
            <p><c:out value="${student}" /> <c:out value="${sessionScope.firstName}" /> <c:out value="${sessionScope.middleName}" /></p>
        </div>
        <div class = "mainButtonStudent">
            <form action="student_home_subject.jsp" method="post">
                <input class="subjects" type="submit" value=<c:out value="${subjects}" /> /><br />
            </form>
            <form action="student_home_tutors.jsp" method="post">
                <input class="tutors" type="submit" value=<c:out value="${tutors}" /> /><br />
            </form>
        </div>
    </div>

    <div class = "footer">
        <form name="footerForm"  class="conclusionByButtons" action="MyCreatedTest" method="post">
            <c:if test="${requestScope.nameTestsBySubject != null}">
                <c:forEach items="${requestScope.nameTestsBySubject}" var="elem" varStatus="varStatus">
                    <input class="footerSubmitTestST" type="button" value="${elem.value.key}"
                           onclick="document.getElementById('command').value='${elem.key}';document.footerForm.submit(); ">
                    <label><c:out value="${tutor}" /> <c:out value="${elem.value.value}" /></label><br>
                </c:forEach>
            </c:if>
            <input type="hidden" name="subjectFooter" id="command" value="" />
        </form>
    </div>

</div>
</body>
</html>