<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>
    <link href="/css/errorStyle.css" rel="stylesheet" type="text/css">
</head>

<input type="text" name="id" hidden="hidden" value="${counteragentErrForm.id}"/>
<label>
    <input type="text" name="name" placeholder="Имя контрагента" required="required"
           value="${counteragentErrForm.name}" maxlength="${constants.NAME_LENGTH}"/>
    <c:if test="${errorMessages.containsKey('name')}">
        <p class="myError">${errorMessages.get('name')}</p>
    </c:if>
</label>
<label>
    <input type="text" name="inn" placeholder="ИНН" required="required"
           value="${counteragentErrForm.inn}" maxlength="${constants.INN_MAX_LENGTH}"/>
    <c:if test="${errorMessages.containsKey('inn')}">
        <p class="myError">${errorMessages.get('inn')}</p>
    </c:if>
</label>
<label>
    <input type="text" name="kpp" placeholder="КПП" required="required"
           value="${counteragentErrForm.kpp}" maxlength="${constants.KPP_LENGTH}"/>
    <c:if test="${errorMessages.containsKey('kpp')}">
        <p class="myError">${errorMessages.get('kpp')}</p>
    </c:if>
</label>
<label>
    <input type="text" name="accountNumber" placeholder="Номер счёта"
           value="${counteragentErrForm.accountNumber}" required="required"
           maxlength="${constants.ACCOUNT_NUMBER_LENGTH}"/>
    <c:if test="${errorMessages.containsKey('accountNumber')}">
        <p class="myError">${errorMessages.get('accountNumber')}</p>
    </c:if>
</label>
<label>
    <input type="text" name="bik" placeholder="БИК" required="required"
           value="${counteragentErrForm.bik}" maxlength="${constants.BIK_LENGTH}"/>
    <c:if test="${errorMessages.containsKey('bik')}">
        <p class="myError">${errorMessages.get('bik')}</p>
    </c:if>
    <c:if test="${errorMessages.containsKey('accNumberAndBik')}">
        <p class="myError">${errorMessages.get('accNumberAndBik')}</p>
    </c:if>
</label>