//Модальные формы со страницы контрагентов
const modalAdd = document.getElementById("addModal");
const modalConfirmDel = document.getElementById("delModal");
const modalDelId = document.getElementById("delByIdModal");
const modalDelName = document.getElementById("delByNameModal");
const modalCng = document.getElementById("cngModal");
const modalSearchName = document.getElementById("searchByNameModal");
const modalSearchBikNumber = document.getElementById("searchByBikAndAccNumber");

//Закрытие форм
function closeForm(modal) {
    modal.style.display = "none";
}

//Очистить все поля формы
function clearInputs(modal) {
    let inputs = modal.querySelectorAll('input[type="text"]');
    inputs.forEach(function (item) {
        item.value = "";
    })
}

//Задать значение атрибутам формы редактирования
function changeInputValues(modal, attributes) {
    let inputs = modal.querySelectorAll('input[type="text"]');
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].value = attributes[i];
    }
}

//Открытие формы добавления
function openAddForm(modal) {
    clearInputs(modal);
    modalAdd.style.display = "block";
}

//Открытие формы для подтверждения удаления
let delAgentId = "";

function openConfirmDelForm(agentId) {
    delAgentId = agentId;
    modalConfirmDel.style.display = "block";
}

//Нажатие кнопки подтверждения удаления
function clickDelConfirm() {
    document.location.href = "/counteragents/delete/" + delAgentId;
}

//Открытие формы удаления по ID
function openDelByIDForm() {
    document.querySelector('input').value = "";
    modalDelId.style.display = "block";
}

//Открытие форму удаления по наименованию
function openDelByNameForm(modal) {
    clearInputs(modal);
    modalDelName.style.display = "block";
}

//Открытие формы редактирования
function openCngForm(modal, id, name, inn, kpp, accNumber, bik) {
    changeInputValues(modal, [id, name, inn, kpp, accNumber, bik]);
    modalCng.style.display = "block";
}

//Открытие формы поиска по имени
function openSearchByNameForm(modal) {
    clearInputs(modal);
    modalSearchName.style.display = "block";
}

//Открытие формы поиска по БИК и номеру счёта
function openSearchBikNumberForm(modal) {
    clearInputs(modal);
    modalSearchBikNumber.style.display = "block";
}