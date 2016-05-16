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
        <c:when test="${sessionScope.status == 'student'}">
            <script language="JavaScript">
                window.location.href = "http://localhost:8080/student_home.jsp"
            </script>
        </c:when>
    </c:choose>
    <title>Create test</title>
    <link href="css/style_create_test.css" rel="stylesheet">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.createTest.locale" var="loc" />
    <fmt:message bundle="${loc}" key="local.button.test" var="test" />
    <fmt:message bundle="${loc}" key="local.button.add" var="add" />
    <fmt:message bundle="${loc}" key="local.button.delete" var="delete" />
    <fmt:message bundle="${loc}" key="local.menu.home" var="home" />
    <fmt:message bundle="${loc}" key="local.alert.home" var="alertHome" />
    <fmt:message bundle="${loc}" key="local.alert.fields" var="fields" />
    <fmt:message bundle="${loc}" key="local.alert.separator" var="alertSeparator" />
    <fmt:message bundle="${loc}" key="local.alert.create" var="alertCreate" />
    <fmt:message bundle="${loc}" key="local.text.nameSubject" var="nameSubject" />
    <fmt:message bundle="${loc}" key="local.text.nameTest" var="nameTest" />
    <fmt:message bundle="${loc}" key="local.text.question" var="question" />
    <fmt:message bundle="${loc}" key="local.text.answerOptions" var="answerOptions" />
    <fmt:message bundle="${loc}" key="local.text.answer" var="answer" />
    <fmt:message bundle="${loc}" key="local.text.separator" var="separator" />

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script>
        var index = 1;
        var inputs = $('.inputs input[type="text"]');
        var new_id0 = inputs.length;
        var new_id1 = inputs.length + 1;
        var new_id2 = inputs.length + 2;
        var new_id3 = inputs.length + 3;
        var new_id4 = inputs.length + 4;
        var new_id5 = inputs.length + 5;

        function add_input(){
            $('.inputs').append('<p id="' + index + '-' + new_id0 + '" class="indent">- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -</p>');
            $('.inputs').append('<input id="' + index + '-' + new_id1 + '" name="question" />');
            $('.inputs').append('<textarea id="' + index + '-' + new_id2 + '" name="response_options"></textarea>');
            $('.inputs').append('<input id="' + index + '-' + new_id3 + '" name="answer" />');
            $('.inputs').append('<input class="add" id="' + index + '-' + new_id4 + '" type="button" onclick="add_input()" value=<c:out value="${add}"/> />');
            $('.inputs').append('<input class="delete" id="' + index + '-' + new_id5 +
                    '" type="button" onclick="delete_input('+index+')" value=<c:out value="${delete}"/> />');
            index = index + 1;
        }

        function delete_input(number){
            var elemP = document.getElementById(number+'-'+new_id0);
            elemP.parentNode.removeChild(elemP);
            var elemQ = document.getElementById(number+'-'+new_id1);
            elemQ.parentNode.removeChild(elemQ);
            var elemRO = document.getElementById(number+'-'+new_id2);
            elemRO.parentNode.removeChild(elemRO);
            var elemAN = document.getElementById(number+'-'+new_id3);
            elemAN.parentNode.removeChild(elemAN);
            var elemAD = document.getElementById(number+'-'+new_id4);
            elemAD.parentNode.removeChild(elemAD);
            var elemRE = document.getElementById(number+'-'+new_id5);
            elemRE.parentNode.removeChild(elemRE);
        }

        function checkFilledAllFields(){
            var len = document.create.elements.length - 1;
            var element = [];
            for(var i = 0; i < len; i++){
                if (!document.create.elements[i].hasAttribute('placeholder')) {
                    var val = document.create.elements[i].value;
                    element.push(val);
                }
            }
            bol = true;
            for(i = 0; i < element.length; i++) {
                if (element[i] == null || element[i] == undefined || element[i] == 0) {
                    bol = false;
                }
            }
            if(bol) {
                bolSeparator = true;
                var sep = document.getElementsByName("separator");
                if(sep[2].checked){
                    var sepYour = document.getElementById('separate');
                    if (sepYour.value == '') {
                        bolSeparator = false;
                        alert('<c:out value="${alertSeparator}" />');
                    }
                }
                if (bolSeparator) {
                    if (confirm('<c:out value="${alertCreate}" />')) {
                        document.getElementById('form').submit();
                    }
                }
            } else {
                alert('<c:out value="${fields}" />');
            }
        }
    </script>

    <script type="text/javascript">
        function down()
        {
            var a = document.getElementById('dropdown');
            if(a.style.display == 'none') {
                a.style.display = 'block';
            }
            else if(a.style.display == 'block') {
                a.style.display = 'none';
            }
        }
        function select_subject(subject){
            var b = document.getElementById('subjects');
            b.value = subject;
            down();
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
        <form class="formTest" action="CreateTest" onsubmit="checkFilledAllFields(this.name);return false;"
              method="post" name="create" id="form">
            <label><c:out value="${nameSubject}" /></label>
            <input id="subjects" type="text" name ="name_subject" onclick="down()" ><br>
            <ul id="dropdown" style="display:none;">
                   <c:forEach items="${sessionScope.subject}" var="elem" varStatus="varStatus">
                       <li onclick="select_subject('${elem}')">${elem}</li>
                   </c:forEach>
            </ul>
            <label><c:out value="${nameTest}" /></label><br>
            <input type="text" name="name_test" size="50"><br>
            <label><c:out value="${separator}" /></label><br>
            <p class="text"><input class="choise" type="radio" name="separator" value=";" checked>";"
            <input class="choise" type="radio" name="separator" value="/">"/"
            <input class="choise" type="radio" name="separator" value="your">
                <input id="separate" name="yourSeparator" type="text" placeholder=""></p>
            <div class="inputs">
                <label><c:out value="${question}" /></label>
                <input id="0-1" name="question" /><br>
                <label><c:out value="${answerOptions}" /></label>
                <textarea class="options" id="0-2" name="response_options"></textarea><br>
                <label><c:out value="${answer}" /></label><br>
                <input id="0-3" name="answer" />
                <input id="0-4" class="add" type="button" onclick="add_input()" value='<c:out value="${add}"/>' />
                <input id="0-5" class="delete" type="button" hidden="true" value='<c:out value="${delete}"/>' /><br>
            </div>
            <input class="addTest" name='create_test' type='submit' value='<c:out value="${test}"/>' />
        </form>
        <span id="check"></span>
    </div>

</div>
</body>
</html>