//Модальные формы со страницы контрагентов
const modalAdd = document.getElementById("addModal");
const modalConfirmDel = document.getElementById("delModal");
const modalDelId = document.getElementById("delByIdModal");
const modalDelName = document.getElementById("delByNameModal");
const modalCng = document.getElementById("cngModal");
const modalSearchName = document.getElementById("searchByNameModal");
const modalSearchBikNumber = document.getElementById("searchByBikAndAccNumber");

//Открытие форм
function openForm(modal) {
    clearInputs(modal);
    modal.style.display = "block";
}

//Закрытие форм
function closeForm(modal) {
    modal.style.display = "none";
    document.location.reload();
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
function openDelByIDForm(modal) {
    modal.querySelector('input[type="number"]').value = "";
    modalDelId.style.display = "block";
}

//Открытие формы редактирования
function openCngForm(modal, id, name, inn, kpp, accNumber, bik) {
    changeInputValues(modal, [id, name, inn, kpp, accNumber, bik]);
    modal.style.display = "block";
}