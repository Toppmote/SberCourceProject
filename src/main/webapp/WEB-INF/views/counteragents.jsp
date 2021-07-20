<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Контрагенты</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="/css/counteragents.css" rel="stylesheet" type="text/css">
    <link href="/css/navbarStyle.css" rel="stylesheet" type="text/css">
    <link href="/css/modalFormStyle.css" rel="stylesheet" type="text/css">
    <link href="/css/buttonsStyle.css" rel="stylesheet" type="text/css">
    <link href="/css/headingStyle.css" rel="stylesheet" type="text/css">
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
                    <button id="delId" onclick="openDelByIDForm(modalDelId)">По ID</button>
                </li>
                <li>
                    <button id="delName" onclick="openDelByNameForm(modalDelName)">По наименованию</button>
                </li>
            </ul>
        </li>
        <li>
            <button>Поиск<i class="fa fa-caret-down"></i></button>
            <ul class="dropdown-menu">
                <li>
                    <button id="searchName" onclick="openSearchByNameForm(modalSearchName)">По наименованию</button>
                </li>
                <li>
                    <button id="searchAccNumberBik"
                            onclick="openSearchBikNumberForm(modalSearchBikNumber)">По БИК-номер счёта
                    </button>
                </li>
            </ul>
        </li>
    </ul>
</div>

<div class="main-container">
    <div class="header"><h1>Таблица контрагентов</h1></div>
    <jsp:include page="components/counteragentsTable.jsp"/>
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
    <jsp:include page="components/changeModalForm.jsp"/>
    <jsp:include page="components/delConfirmModalForm.jsp"/>
    <div id="delByIdModal" class="modal">
        <div class="modal-content">
            <form action="/counteragents/delete" method="post">
                <span class="close" onclick="closeForm(modalDelId)">&times;</span>
                <h2>Удаление по ID</h2>
                <label>
                    <input type="number" name="id" placeholder="ID контрагента" required="required" min="1"
                           max="${constants.ID_MAX_VALUE}"/>
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
            <form action="/counteragents/search_by_name" method="post">
                <span class="close" onclick="closeForm(modalSearchName)">&times;</span>
                <h2>Поиск по наименованию</h2>
                <label>
                    <input type="text" name="name" placeholder="Имя контрагента" required="required"
                           maxlength="${constants.NAME_LENGTH}"/>
                </label>
                <jsp:include page="components/searchButton.jsp"/>
            </form>
        </div>
    </div>
    <div id="searchByBikAndAccNumber" class="modal">
        <div class="modal-content">
            <form action="/counteragents/search_by_bik_and_acc_number" method="post">
                <input name="field" type="text" hidden ="hidden">
                <span class="close" onclick="closeForm(modalSearchBikNumber)">&times;</span>
                <h2>Поиск по паре номер счёта-БИК</h2>
                <label>
                    <input type="text" name="accountNumber" placeholder="Номер счёта"
                           required="required" maxlength="${constants.ACCOUNT_NUMBER_LENGTH}"/>
                </label>
                <label>
                    <input type="text" name="bik" placeholder="БИК" required="required"
                           maxlength="${constants.BIK_LENGTH}"/>
                </label>
                <jsp:include page="components/searchButton.jsp"/>
            </form>
        </div>
    </div>
</div>

<script src="<c:url value="/js/modalFormScripts.js"/>"></script>

</body>

</html>
