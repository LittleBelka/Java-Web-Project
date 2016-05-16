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
        <c:when test="${requestScope.listQuestionAndAnswer == null}">
            <script language="JavaScript">
                window.location.href = "http://localhost:8080"
            </script>
        </c:when>
    </c:choose>

    <title>Take test</title>
    <link href="css/style_create_test.css" rel="stylesheet">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.takeTest.locale" var="loc" />
    <fmt:message bundle="${loc}" key="local.button.finishTest" var="finishTest" />
    <fmt:message bundle="${loc}" key="local.menu.home" var="home" />
    <fmt:message bundle="${loc}" key="local.alert.home" var="alertHome" />
    <fmt:message bundle="${loc}" key="local.alert.finishTest" var="alertFinishTest" />

    <script>
        function finishTest(){
            if (confirm('<c:out value="${alertFinishTest}" />')) {
                document.getElementById('form').submit();
            }
        }
    </script>

</head>
<body>

<div class="main">

    <div class="menu">
        <ul id="leftMenu">
            <li><form action="tutor_home.jsp" method="post">
                <input type="submit" onclick="return confirm('<c:out value="${alertHome}" />')"
                       value='<c:out value="${home}" />' /><br>
            </form></li>
        </ul>
    </div>

    <div class = "centreTest">
        <form class="formTest" action="TakeTest" onsubmit="finishTest(this.name);return false;"
              method="post" name="create" id="form">
            <label class="nameTakeTest"><c:out value="${requestScope.nameTest}" /></label><br>
            <div class="inputs">
                <c:forEach items="${requestScope.listQuestionAndAnswer}" var="elem" varStatus="varStatus">
                    <label class="paddingForQuestion"><c:out value="${elem.key}" /></label>
                    <c:forEach items="${elem.value}" var="elem2" varStatus="varStatus">
                        <p class="listAnswer">
                            <input class="choiseAnswer" type="checkbox" name="${elem2.key}" value="${elem2.value}">
                            <c:out value="${elem2.value}"/></p>
                    </c:forEach>
                </c:forEach>
            </div>
            <br>
            <input class="finishTestSend" name='take_test' type='submit' value='<c:out value="${finishTest}"/>' />
        </form>
        <span id="check"></span>
    </div>

</div>
</body>
</html>