<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Контрагенты</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="css/counteragents.css" rel="stylesheet" type="text/css">
    <link href="css/tableStyle.css" rel="stylesheet" type="text/css">
    <link href="css/navbarStyle.css" rel="stylesheet" type="text/css">
    <link href="css/modalFormStyle.css" rel="stylesheet" type="text/css">
    <link href="css/buttonsStyle.css" rel="stylesheet" type="text/css">
    <link href="css/headingStyle.css" rel="stylesheet" type="text/css">
</head>

<body>

<div class="navbar">
    <ul>
        <li>
            <button id="addButton" onclick="openAddForm(modalAdd)">Добавить</button>
        </li>
        <li>
            <button>Удалить<i class="fa fa-caret-down"></i></button>
            <ul class="dropdown-menu">
                <li>
                    <button id="delId" onclick="openDelByIDForm()">По ID</button>
                </li>
                <li>
                    <button id="delName" onclick="openDelByNameForm()">По наименованию</button>
                </li>
            </ul>
        </li>
        <li>
            <button>Поиск<i class="fa fa-caret-down"></i></button>
            <ul class="dropdown-menu">
                <li>
                    <button id="searchName" onclick="openSearchByNameForm()">По наименованию</button>
                </li>
                <li>
                    <button id="searchAccNumberBik">По БИК-номер счёта</button>
                </li>
            </ul>
        </li>
    </ul>
</div>

<div class="main">
    <div class="header"><h1>Таблица контрагентов</h1></div>
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
                    <button onclick="openConfirmDelForm(${counteragent.id})" class="del-btn"><i
                            class="fa fa-remove"></i></button>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div id="addModal" class="modal">
        <div class="modal-content">
            <form action="/counteragents" method="post">
                <span class="close" onclick="closeForm(modalAdd)">&times;</span>
                <h2>Добавление контрагента</h2>
                <jsp:include page="components/counteragentForm.jsp"/>
                <div class="green-button">
                    <input type="submit" value="Добавить">
                </div>
            </form>
        </div>
    </div>
    <div id="cngModal" class="modal">
        <div class="modal-content">
            <form action="/counteragents/update" method="post">
                <span class="close" onclick="closeForm(modalCng)">&times;</span>
                <h2>Редактирование контрагента</h2>
                <jsp:include page="components/counteragentForm.jsp"/>
                <div class="green-button">
                    <input type="submit" value="Сохранить изменения">
                </div>
            </form>
        </div>
    </div>
    <div id="delModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeForm(modalConfirmDel)">&times;</span>
            <h2>Удаление контрагента</h2>
            <div class="delete-text">
                <p>Вы действительно хотите удалить контрагента?<br>
                    Действие невозможно будет отменить.</p>
            </div>
            <div class="red-button">
                <button onclick="clickDelConfirm()" type="submit">Удалить</button>
            </div>
        </div>
    </div>
    <div id="delByIdModal" class="modal">
        <div class="modal-content">
            <form action="/counteragents/delete" method="post">
                <span class="close" onclick="closeForm(modalDelId)">&times;</span>
                <h2>Удаление по ID</h2>
                <label>
                    <input type="number" name="id" placeholder="ID контрагента" required="required"/>
                </label>
                <jsp:include page="components/delButton.jsp"/>
            </form>
        </div>
    </div>
    <div id="delByNameModal" class="modal">
        <div class="modal-content">
            <form action="/counteragents/delete" method="post">
                <span class="close" onclick="closeForm(modalDelName)">&times;</span>
                <h2>Удаление по наименованию</h2>
                <label>
                    <input type="text" name="name" placeholder="Имя контрагента" required="required" value=""
                           maxlength="${constants.NAME_LENGTH}"/>
                </label>
                <jsp:include page="components/delButton.jsp"/>
            </form>
        </div>
    </div>
    <div id="searchByNameModal" class="modal">
        <div class="modal-content">
            <form action="/counteragents/search_result" method="post">
                <span class="close" onclick="closeForm(modalSearchName)">&times;</span>
                <h2>Поиск по наименованию</h2>
                <label>
                    <input type="text" name="name" placeholder="Имя контрагента" required="required"
                           maxlength="${constants.NAME_LENGTH}"/>
                </label>

            </form>
        </div>
    </div>
    <div id="searchByBikAndAccNumber" class="modal">
        <div class="modal-content">
            <form action="/counteragents/search_result" method="post">
                <span class="close">&times;</span>
                <h2>Поиск по паре номер счёта-БИК</h2>
                <label>
                    <input type="text" name="accountNumber" placeholder="Номер счёта"
                           required="required" maxlength="${constants.ACCOUNT_NUMBER_LENGTH}"/>
                </label>
                <label>
                    <input type="text" name="bik" placeholder="БИК" required="required"
                           maxlength="${constants.BIK_LENGTH}"/>
                </label>
            </form>
        </div>
    </div>
</div>

<script src="<c:url value="/js/modalFormScripts.js"/>"></script>

</body>

</html>
