<%--
  Created by IntelliJ IDEA.
  User: Pavel
  Date: 19.07.2021
  Time: 1:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test</title>
    <link href="css/test.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="navbar">
    <ul>
        <li>
            <button id="addButton">Добавить</button>
        </li>
        <li>
            <button>Удалить<i class="fa fa-caret-down"></i></button>
            <ul class="dropdown-menu">
                <li>
                    <button id="delId">По ID</button>
                </li>
                <li>
                    <button id="delName">По наименованию</button>
                </li>
            </ul>
        </li>
        <li>
            <button>Поиск<i class="fa fa-caret-down"></i></button>
            <ul class="dropdown-menu">
                <li>
                    <button id="searchName">По ID</button>
                </li>
                <li>
                    <button id="searchAccNumberBik">По наименованию</button>
                </li>
            </ul>
        </li>
    </ul>
</div>
</body>
</html>
