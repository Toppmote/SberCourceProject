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
            <button id="addButton">Добавить</button>
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
                    <button id="searchName">По наименованию</button>
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
                    <button onclick="openCngForm('${counteragent.id}', '${counteragent.name}', '${counteragent.inn}',
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
                <span class="close">&times;</span>
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
                <span class="close">&times;</span>
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
            <span class="close">&times;</span>
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
                <span class="close">&times;</span>
                <h2>Удаление по ID</h2>
                <label>
                    <input type="text" name="id" placeholder="ID контрагента" required="required"/>
                </label>
                <jsp:include page="components/delButton.jsp"/>
            </form>
        </div>
    </div>
    <div id="delByNameModal" class="modal">
        <div class="modal-content">
            <form action="/counteragents/delete" method="post">
                <span class="close">&times;</span>
                <h2>Удаление по наименованию</h2>
                <label>
                    <input type="text" name="name" placeholder="Имя контрагента" required="required"
                           maxlength="${constants.NAME_LENGTH}"/>
                </label>
                <jsp:include page="components/delButton.jsp"/>
            </form>
        </div>
    </div>
</div>

<script>
    //Открытие формы добавления
    const modalAdd = document.getElementById("addModal");
    const btnAdd = document.getElementById("addButton");

    let spanButtons = document.querySelectorAll(".close");

    btnAdd.onclick = function () {
        clearInputValues();
        modalAdd.style.display = "block";
    }

    let inputId = document.getElementsByName("id");
    let inputName = document.getElementsByName("name");
    let inputInn = document.getElementsByName("inn");
    let inputKpp = document.getElementsByName("kpp");
    let inputAccNumber = document.getElementsByName("accountNumber");
    let inputBikNumber = document.getElementsByName("bik");

    //Очистить все поля формы добавления
    function clearInputValues() {
        inputId[0].setAttribute('value', "");
        inputName[0].setAttribute('value', "");
        inputInn[0].setAttribute('value', "");
        inputKpp[0].setAttribute('value', "");
        inputAccNumber[0].setAttribute('value', "");
        inputBikNumber[0].setAttribute('value', "");
    }

    //Задать значение атрибутам формы редактирования
    function changeInputValues(id, name, inn, kpp, accNumber, bik) {
        inputId[1].setAttribute('value', id);
        inputName[1].setAttribute('value', name);
        inputInn[1].setAttribute('value', inn);
        inputKpp[1].setAttribute('value', kpp);
        inputAccNumber[1].setAttribute('value', accNumber);
        inputBikNumber[1].setAttribute('value', bik);
    }

    //Открытие формы для подтверждения удаления
    const modalConfirmDel = document.getElementById("delModal");
    let delAgentId = "";

    function openConfirmDelForm(agentId) {
        inputId[2].setAttribute('value', "");
        modalConfirmDel.style.display = "block";
    }

    //Открытие форму удаления по ID
    const modalDelId = document.getElementById("delByIdModal");

    function openDelByIDForm() {
        modalDelId.style.display = "block";
    }

    //Открытие форму удаления по наименованию
    const modalDelName = document.getElementById("delByNameModal");

    function openDelByNameForm() {
        modalDelName.style.display = "block";
    }

    //Нажатие кнопки подтверждения удаления
    function clickDelConfirm() {
        document.location.href = "/counteragents/delete/" + delAgentId;
    }

    //Открытие формы редактирования
    const modalCng = document.getElementById("cngModal");

    function openCngForm(id, name, inn, kpp, accNumber, bik) {
        changeInputValues(id, name, inn, kpp, accNumber, bik);
        modalCng.style.display = "block";
    }

    function clickCngConfirm() {
        document.location.href = "/counteragents/update";
    }

    //Обработчик закрытия форм
    spanButtons.forEach(function (button) {
        button.addEventListener("click", function (event) {
            modalAdd.style.display = "none";
            modalCng.style.display = "none";
            modalConfirmDel.style.display = "none";
            modalDelId.style.display = "none";
            modalDelName.style.display = "none";
        })
    });
</script>

</body>

</html>
