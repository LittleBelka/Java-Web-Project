<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<link href="css/style_home.css" rel="stylesheet">
<html>
<head>
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
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Quizar.org - Home</title>
  <fmt:setLocale value="${sessionScope.local}" />
  <fmt:setBundle basename="localization.home.locale" var="loc" />
  <fmt:message bundle="${loc}" key="local.locbutton.name.ru" var="ru_button" />
  <fmt:message bundle="${loc}" key="local.locbutton.name.en" var="en_button" />
  <fmt:message bundle="${loc}" key="local.button.subjects" var="subjects" />
  <fmt:message bundle="${loc}" key="local.button.tutors" var="tutors" />
  <fmt:message bundle="${loc}" key="local.button.authorization" var="authorization" />
  <fmt:message bundle="${loc}" key="local.link.register" var="register" />
  <fmt:message bundle="${loc}" key="local.text.login" var="login" />
  <fmt:message bundle="${loc}" key="local.text.password" var="password" />
  <fmt:message bundle="${loc}" key="local.text.title" var="title" />
</head>

<body>
  <c:if test="${sessionScope.messageErrorReg != null}">
    <input type="submit" onclick= 'alert(<c:out value="${sessionScope.messageErrorReg}"/>)' value="Go"/></a>
  </c:if>

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
    </div>

    <div class = "centreImage">
      <div class = "font">
        <p id="quizar">Quizar</p>
        <p id="system"><c:out value="${title}" /></p>
      </div>
      <div class = "authoriz">
        <form class="authozForm" action="Authorization" method="post">
          <label><c:out value="${login}" /></label>
          <input type="text" name="login" size="30">
          <label class="pas"><c:out value="${password}" /></label>
          <input type="password" name="password" size="30">
          <input class="submit_authoriz" type="submit" value="${authorization}" <c:out value="${sessionScope.authOK}"/> >
          <c:if test="${sessionScope.login == null}">
            <a href="register.jsp"><c:out value="${register}" /></a>
          </c:if>
          <p class="message"><c:out value="${sessionScope.messageAuth}"/></p>
        </form>
      </div>
    </div>

    <div class = "footer">
      <form action="ChoiceSubject" method="post">
        <input class="subjects" type="submit" value="${subjects}" /><br />
      </form>
      <form action="ChoiceTutors" method="post">
        <input class="tutors" type="submit" value="${tutors}" /><br />
      </form>
    </div>

  </div>
</body>
</html>
