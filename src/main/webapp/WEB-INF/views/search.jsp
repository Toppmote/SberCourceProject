<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Результаты поиска</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="/css/counteragents.css" rel="stylesheet" type="text/css">
    <link href="/css/navbarStyle.css" rel="stylesheet" type="text/css">
    <link href="/css/modalFormStyle.css" rel="stylesheet" type="text/css">
    <link href="/css/buttonsStyle.css" rel="stylesheet" type="text/css">
    <link href="/css/headingStyle.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="navbar">
    <button onclick="document.location.href = '/counteragents'"><i class="fa fa-caret-left"></i>Вернуться</button>
</div>

<div class="main-container">
    <div class="header"><h1>Результат поиска</h1></div>
    <jsp:include page="components/counteragentsTable.jsp"/>
</div>

<jsp:include page="components/changeModalForm.jsp"/>
<jsp:include page="components/delConfirmModalForm.jsp"/>

<script src="<c:url value="/js/modalFormScripts.js"/>"></script>

</body>
</html>
