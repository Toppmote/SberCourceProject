<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
