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
        <c:when test="${sessionScope.status == 'student'}">
            <script language="JavaScript">
                window.location.href = "http://localhost:8080/student_home.jsp"
            </script>
        </c:when>
    </c:choose>
    <title>Tutor home</title>
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.userHome.locale" var="loc" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
    <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
    <fmt:message bundle="${loc}" key="local.button.subjects" var="subjects" />
    <fmt:message bundle="${loc}" key="local.button.tutors" var="tutors" />
    <fmt:message bundle="${loc}" key="local.button.test" var="test" />
    <fmt:message bundle="${loc}" key="local.button.edit" var="edit" />
    <fmt:message bundle="${loc}" key="local.button.delete" var="delete" />
    <fmt:message bundle="${loc}" key="local.menu.home" var="home" />
    <fmt:message bundle="${loc}" key="local.menu.logOut" var="menuLogOut" />
    <fmt:message bundle="${loc}" key="local.alert.logOut" var="alertLogOut" />
    <fmt:message bundle="${loc}" key="local.alert.delete" var="alertDelete" />
    <fmt:message bundle="${loc}" key="local.text.tutor" var="tutor" />
    <fmt:message bundle="${loc}" key="local.text.subject" var="subject" />
    <fmt:message bundle="${loc}" key="local.text.myCreatedTest" var="myCreatedTest" />

    <script type="text/javascript">
        function deleteTest(id){
            if (confirm('<c:out value="${alertDelete}" />')) {
                document.getElementById('command').value=id;
                document.getElementById('commandChange').value='-1';
                document.footerForm.submit();
            }
        }
    </script>

</head>

<body>
<div class="main">
    <div class="menu">
        <ul id="rightMenu">
            <li><form action="LocaleSet" method="post">
                <input type="hidden" name="local" value="ru" />
                <input class="locale" type="submit" value="${ru_button}" /><br />
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
            <p><c:out value="${tutor}" /> <c:out value="${sessionScope.firstName}" />
                <c:out value="${sessionScope.middleName}" /></p>
        </div>
        <div class = "mainButtonStudent">
            <form action="tutor_home_subject.jsp" method="post">
                <input class="subjectsTutor" type="submit" value=<c:out value="${subjects}" /> /><br />
            </form>
            <form action="create_test.jsp" method="post">
                <input class="testTutor" type="submit" value='<c:out value="${test}" />' /><br />
            </form>
            <form action="tutor_home_tutors.jsp" method="post">
                <input class="tutorsTutor" type="submit" value=<c:out value="${tutors}" /> /><br />
            </form>
        </div>
    </div>

    <div class = "footer">
        <form name="footerForm"  class="conclusionByButtons" action="OperationsWithTest" method="post">
            <label class="myCreatedTest"><c:out value="${myCreatedTest}" /></label><br>
            <div class="myTest">
                <c:if test="${sessionScope.myCreatedTest != null}">
                    <c:forEach items="${sessionScope.myCreatedTest}" var="elem" varStatus="varStatus">
                        <input class="footerSubmitTest" type="button" value="${elem.value.key}"
                               onclick="document.getElementById('command').value='${elem.key}';
                                       document.footerForm.submit(); ">
                        <label><c:out value="${elem.value.value}" /></label><label class="indent">___</label>
                        <input class="footerSubmitChange" type="button" value="${edit}"
                               onclick="document.getElementById('command').value='${elem.key}'
                                       document.getElementById('commandChange').value='0';
                                       document.footerForm.submit(); ">
                        <input class="footerSubmitChange" type="button" value="${delete}"
                               onclick="deleteTest(${elem.key})"><br>
                    </c:forEach>
                </c:if>
            </div>
            <input type="hidden" name="subjectFooter" id="command" value="" />
            <input type="hidden" name="subjectFooterChange" id="commandChange" value="" />
        </form>

    </div>

</div>
</body>
</html>
