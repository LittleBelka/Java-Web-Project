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
        <c:when test="${requestScope.editTest == null}">
            <script language="JavaScript">
                window.location.href = "http://localhost:8080"
            </script>
        </c:when>
    </c:choose>
    <title>Edit test</title>
    <link href="css/style_create_test.css" rel="stylesheet">
    <fmt:setLocale value="${sessionScope.local}" />
    <fmt:setBundle basename="localization.createTest.locale" var="loc" />
    <fmt:message bundle="${loc}" key="local.button.testEdit" var="testEdit" />
    <fmt:message bundle="${loc}" key="local.button.add" var="add" />
    <fmt:message bundle="${loc}" key="local.button.delete" var="delete" />
    <fmt:message bundle="${loc}" key="local.menu.home" var="home" />
    <fmt:message bundle="${loc}" key="local.alert.home" var="alertHome" />
    <fmt:message bundle="${loc}" key="local.alert.fields" var="fields" />
    <fmt:message bundle="${loc}" key="local.alert.separator" var="alertSeparator" />
    <fmt:message bundle="${loc}" key="local.alert.saveChanges" var="alertSaveChanges" />
    <fmt:message bundle="${loc}" key="local.text.nameTest" var="nameTest" />
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

        function add_input(testQuestion, testResponseOptions, separator, testAnswer){

            if (testQuestion == null) {
                testQuestion = "";
            }
            if (separator == null) {
                separator = "";
            }
            if (testAnswer == null) {
                testAnswer = "";
            }
            if (testResponseOptions != null) {
                var str = testResponseOptions;
                var responseOptions = str.split(separator);
                for (var i = 0; i < responseOptions.length; i++) {
                    responseOptions[i] = responseOptions[i] + separator + '\r\n';
                }
                responseOptions.splice(responseOptions.length - 1, 1);
            }
            $('.inputs').append('<p id="' + index + '-' + new_id0 +
                    '" class="indent">- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -</p>');
            $('.inputs').append('<input id="' + index + '-' + new_id1 + '" name="question" value="'+testQuestion+'" />');
            if (responseOptions == null) {
                $('.inputs').append('<textarea id="' + index + '-' + new_id2 + '" name="response_options"></textarea>');
            } else {
                $('.inputs').append('<textarea id="' + index + '-' + new_id2 + '" name="response_options">' +
                        responseOptions.join("") + '</textarea>');
            }
            $('.inputs').append('<input id="' + index + '-' + new_id3 + '" name="answer" value="'+testAnswer+'" />');
            $('.inputs').append('<input class="add" id="' + index + '-' + new_id4 +
                    '" type="button" onclick="add_input(null, null, null, null)" value=<c:out value="${add}"/> />');
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
            var sep = document.getElementsByName("separator");
            for(i = 0; i < element.length; i++) {
                if (element[i] == null || element[i] == undefined || element[i] == 0
                        || (!sep[0].checked && !sep[1].checked && !sep[2].checked)) {
                    bol = false;
                }
            }
            if(bol) {
                bolSeparator = true;
                if(sep[2].checked){
                    var sepYour = document.getElementById('separate');
                    if (sepYour.value == '') {
                        bolSeparator = false;
                        alert('<c:out value="${alertSeparator}" />');
                    }
                }
                if (bolSeparator) {
                    if (confirm('<c:out value="${alertSaveChanges}" />')) {
                        document.getElementById('form').submit();
                    }
                }
            } else {
                alert('<c:out value="${fields}" />');
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
        <form class="formTest" action="CreateTest" onsubmit="checkFilledAllFields(this.name);return false;"
              method="post" name="create" id="form">
            <label><c:out value="${nameTest}" /></label><br>
            <input type="text" name="name_test" size="50" value='<c:out value="${requestScope.nameTest}" />'><br>
            <label><c:out value="${separator}" /></label><br>
            <p class="text"><input class="choise" type="radio" name="separator" value=";" >";"
            <input class="choise" type="radio" name="separator" value="/">"/"
            <input class="choise" type="radio" name="separator" value="your">
            <input id="separate" name="yourSeparator" type="text" placeholder=""></p>
            <div class="inputs">
                <c:forEach items="${requestScope.editTest}" var="elem" varStatus="varStatus">
                    <script>
                        add_input('<c:out value="${elem.key}" />', '<c:out value="${elem.value.key}" />',
                                '<c:out value="${requestScope.separator}" />', '<c:out value="${elem.value.value}" />');
                    </script>
                </c:forEach>
            </div>
            <input class="addTest" name='edit_test' type='submit' value='<c:out value="${testEdit}"/>' />
            <input name='id_test' type='text' hidden="true" value='<c:out value="${requestScope.idTestOpen}"/>' />
        </form>
        <span id="check"></span>
    </div>

</div>
</body>
</html>