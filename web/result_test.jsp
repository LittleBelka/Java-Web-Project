<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script language="JavaScript">
        window.onload = function () {
            if (typeof history.pushState === "function") {
                history.pushState("jibberish", null, null);
                window.onpopstate = function () {
                    history.pushState('newjibberish', null, null);
                };
            }
            else {
                var ignoreHashChange = true;
                window.onhashchange = function () {
                    if (!ignoreHashChange) {
                        ignoreHashChange = true;
                        window.location.hash = Math.random();
                    }
                    else {
                        ignoreHashChange = false;
                    }
                };
            }
        }
    </script>

    <c:choose>
        <c:when test="${sessionScope.status == null}">
            <script language="JavaScript">
                window.location.href = "http://localhost:8080"
            </script>
        </c:when>
        <c:when test="${requestScope.result == null}">
            <script language="JavaScript">
                window.location.href = "http://localhost:8080"
            </script>
        </c:when>
    </c:choose>

    <title>Test results</title>
    <link href="css/style_create_test.css" rel="stylesheet">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.takeTest.locale" var="loc" />
    <fmt:message bundle="${loc}" key="local.text.result" var="result" />
    <fmt:message bundle="${loc}" key="local.menu.home" var="home" />
    <fmt:message bundle="${loc}" key="local.text.question" var="question" />
    <fmt:message bundle="${loc}" key="local.text.yourAnswer" var="yourAnswer" />
    <fmt:message bundle="${loc}" key="local.text.correctAnswer" var="correctAnswer" />
    <fmt:message bundle="${loc}" key="local.text.from" var="from" />

</head>
<body>

<div class="main">

    <div class="menu">
        <ul id="leftMenu">
            <li><form action="home.jsp" method="post">
                <input type="submit" value='<c:out value="${home}" />' /><br>
            </form></li>
        </ul>
    </div>

    <div class = "centreTest">
        <div class="formTest">
            <label class="nameTakeTest"><c:out value="${result}" /> <c:out value="${requestScope.result}" />
                <c:out value="${from}" /> <c:out value="${sessionScope.countQuestionTest}" /></label><br>
            <div class="inputs">
                <c:forEach items="${requestScope.questYourAnswerAndAnswer}" var="elem" varStatus="varStatus">
                    <label class="padQuestionOne"><c:out value="${question}" /> <c:out value="${elem.key}" /></label>
                    <label class="padQuestion"><c:out value="${yourAnswer}" /> </label>
                    <c:forEach items="${elem.value.key}" var="elem2" varStatus="varStatus">
                        <p class="listAnswer">
                            <c:out value="${elem2}"/><c:out value="${requestScope.separator}" /></p>
                    </c:forEach>
                    <label class="padQuestion"><c:out value="${correctAnswer}" /> </label>
                    <c:forEach items="${elem.value.value}" var="elem3" varStatus="varStatus">
                        <p class="listAnswer">
                            <c:out value="${elem3}"/><c:out value="${requestScope.separator}" /></p>
                    </c:forEach>
                </c:forEach>
            </div>
            <br>
        </div>
    </div>

</div>
</body>
</html>