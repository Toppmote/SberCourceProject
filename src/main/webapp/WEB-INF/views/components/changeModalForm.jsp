<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="cngModal" class="modal">
    <div class="modal-content">
        <form action="/counteragents/update" method="post">
            <span class="close" onclick="closeForm(modalCng)">&times;</span>
            <h2>Редактирование контрагента</h2>
            <jsp:include page="counteragentForm.jsp"/>
            <div class="green-button">
                <input type="submit" value="Сохранить изменения">
            </div>
        </form>
    </div>
</div>
