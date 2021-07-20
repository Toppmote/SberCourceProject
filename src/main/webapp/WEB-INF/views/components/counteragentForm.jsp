<%@ page contentType="text/html;charset=UTF-8" %>
<input type="text" name="id" hidden="hidden" value=""/>
<label>
    <input type="text" name="name" placeholder="Имя контрагента" required="required"
           maxlength="${constants.NAME_LENGTH}"/>
</label>
<label>
    <input type="text" name="inn" placeholder="ИНН" required="required"
           maxlength="${constants.INN_MAX_LENGTH}"/>
</label>
<label>
    <input type="text" name="kpp" placeholder="КПП" required="required"
           maxlength="${constants.KPP_LENGTH}"/>
</label>
<label>
    <input type="text" name="accountNumber" placeholder="Номер счёта"
           required="required" maxlength="${constants.ACCOUNT_NUMBER_LENGTH}"/>
</label>
<label>
    <input type="text" name="bik" placeholder="БИК" required="required"
           maxlength="${constants.BIK_LENGTH}"/>
</label>