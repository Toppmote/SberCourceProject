<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Результаты поиска</title>
    <link href="css/headingStyle.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="navbar">
    <button><i class="fa fa-caret-left"></i>Вернуться</button>
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
