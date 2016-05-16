<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<link href="css/style_home.css" rel="stylesheet">
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
    <title>Register</title>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.registration.locale" var="loc" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
    <fmt:message bundle="${loc}" key="local.menu.home" var="home" />
    <fmt:message bundle="${loc}" key="local.button.register" var="registerBut" />
    <fmt:message bundle="${loc}" key="local.text.register" var="registerText" />
    <fmt:message bundle="${loc}" key="local.text.email" var="email" />
    <fmt:message bundle="${loc}" key="local.text.first_name" var="first_name" />
    <fmt:message bundle="${loc}" key="local.text.middle_name" var="middle_name" />
    <fmt:message bundle="${loc}" key="local.text.last_name" var="last_name" />
    <fmt:message bundle="${loc}" key="local.text.login" var="login" />
    <fmt:message bundle="${loc}" key="local.text.password" var="password" />
    <fmt:message bundle="${loc}" key="local.text.repeat_password" var="repeat_password" />
    <fmt:message bundle="${loc}" key="local.text.user_status" var="user_status" />
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
            <li><form action="home.jsp" method="post">
                <input type="submit" value=<c:out value="${home}" /> /><br />
            </form></li>
        </ul>
    </div>

    <div class = "centre">
        <div class = "registerTitle">
            <p><c:out value="${registerText}" /></p>
        </div>
        <div class = "registr">
            <form class="registerForm" action="Register" method="post">
                <label class="regText"><c:out value="${login}" /></label>
                <input type="text" name="login" size="30" placeholder="${sessionScope.loginTmp}">
                <label class="regText"><c:out value="${first_name}" /></label>
                <input type="text" name="first_name" size="30" placeholder="${sessionScope.firstNameTmp}">
                <label class="regText"><c:out value="${middle_name}" /></label>
                <input type="text" name="middle_name" size="30" placeholder="${sessionScope.middleNameTmp}">
                <label class="regText"><c:out value="${last_name}" /></label>
                <input type="text" name="last_name" size="30" placeholder="${sessionScope.lastNameTmp}">
                <label class="regText"><c:out value="${email}" /></label>
                <input type="text" name="email" size="30" placeholder="${sessionScope.emailTmp}">
                <label class="regText"><c:out value="${password}" /></label>
                <input type="password" name="password" size="30" placeholder="${sessionScope.passwordTmp}">
                <label class="regText"><c:out value="${repeat_password}" /></label>
                <input type="password" name="repeat_password" size="30" placeholder="${sessionScope.passwordRepeatTmp}">
                <label class="regText"><c:out value="${user_status}" /></label>
                <input class="choise" type="radio" name="status" value="student"><label class="text">Student</label>
                <input class="choise" type="radio" name="status" value="tutor"><label class="text">Tutor</label>
                <input class="submit_registr" type="submit" value="${registerBut}" <c:out value="${sessionScope.registerBut}"/> >
                <p class="message"><c:out value="${sessionScope.messageReg}"/></p>
            </form>
        </div>
    </div>

</div>
</body>
</html>
