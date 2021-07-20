<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <link href="/css/tableStyle.css" rel="stylesheet" type="text/css">
</head>

<table>
    <tr>
        <th scope="col">ID</th>
        <th scope="col">Наименование</th>
        <th scope="col">ИНН</th>
        <th scope="col">КПП</th>
        <th scope="col">Номер счёта</th>
        <th scope="col">БИК</th>
        <th scope="col">Действия</th>
    </tr>
    <c:forEach items="${counteragentsList}" var="counteragent">
        <tr>
            <td>${counteragent.id}</td>
            <td>${counteragent.name}</td>
            <td>${counteragent.inn}</td>
            <td>${counteragent.kpp}</td>
            <td>${counteragent.accountNumber}</td>
            <td>${counteragent.bik}</td>
            <td>
                <button onclick="openCngForm(modalCng, '${counteragent.id}', '${counteragent.name}', '${counteragent.inn}',
                        '${counteragent.kpp}', '${counteragent.accountNumber}', '${counteragent.bik}')"
                        class="edit-btn"><i class="fa fa-pencil"></i>
                </button>
                <button onclick="openConfirmDelForm(${counteragent.id})" class="del-btn">
                    <i class="fa fa-remove"></i></button>
            </td>
        </tr>
    </c:forEach>
</table>
