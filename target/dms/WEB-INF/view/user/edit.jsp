<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="readonly" value="false"></c:set>
<c:set var="disabled" value=""></c:set>

<c:if test="${mode==2}">
    <c:set var="readonly" value="true"></c:set>
    <c:set var="disabled" value="disabled"></c:set>
</c:if>
<form:form method="POST" modelAttribute="formDto">
    <form:input path="id" type="hidden"/>
    <div class="form-group row">
        <label for="firstName" class="col-sm-4 col-form-label">First name</label>
        <div class="col-sm-6">
            <form:input path="firstName" type="text" class="form-control"
                        placeholder="First name" readonly="${readonly}"/>
        </div>
    </div>
    <div class="form-group row">
        <label for="lastName" class="col-sm-4 col-form-label">Last name</label>
        <div class="col-sm-6">
            <form:input path="lastName" type="text" class="form-control"
                        placeholder="Last name" readonly="${readonly}"/>
        </div>
    </div>
    <div class="form-group row">
        <label for="username" class="col-sm-4 col-form-label">Username</label>
        <div class="col-sm-6">
            <form:input path="username" type="text" class="form-control"
                        placeholder="Username" readonly="${readonly}"/>
        </div>
    </div>
    <div class="form-group row ${mode!=1? 'hidden' : ''}">
        <label for="password" class="col-sm-4 col-form-label">Password</label>
        <div class="col-sm-6">
            <form:input path="password" type="password" class="form-control"
                        placeholder="Password" readonly="${readonly}"/>
        </div>
    </div>
    <div class="form-group row">
        <label for="roles" class="col-sm-4 col-form-label">Company</label>
        <div class="col-sm-6">
            <form:select path="companyId" class="form-control" disabled="${readonly}">
                <form:option value=""> None selected </form:option>
                <form:options items="${companyList}" itemValue="id" itemLabel="name"></form:options>
            </form:select>
        </div>
    </div>
    <div class="form-group row">
        <label for="roles" class="col-sm-4 col-form-label">User roles</label>
        <div class="col-sm-6">
            <form:select path="roleIds" id="roles"
                         class="form-control select-multiple-role">
                <form:options items="${roleList}" itemValue="id"></form:options>
            </form:select>
        </div>
    </div>


    <div class="form-group row float-right">
        <div class="col-sm-10">
            <c:if test="${mode==1 || mode==3}">
                <button type="button" id="save" class="btn btn-primary">Save</button>
            </c:if>
            <c:if test="${mode==2}">
                <button type="button" id="edit" class="btn btn-primary">Edit</button>
            </c:if>
        </div>
    </div>
</form:form>

<script>
    var mode = "${mode}";
    $(document).ready(function () {

        $('.select-multiple-role').multiselect({
            enableClickableOptGroups: true,
            includeSelectAllOption: true,
            buttonWidth: "100%",
            numberDisplayed: 10
        });

        if (mode == 2) {
            $(".select-multiple-role").multiselect("disable");
        }

        $("#save").on("click", function () {
            save();
        });
        $("#edit").on("click", function () {
            window.location = "<%=request.getContextPath()%>/users/edit/" + $("#id").val();
        });


    });

    function save() {
        var userDto = {
            id: $("#id").val(),
            firstName: $("#firstName").val(),
            lastName: $("#lastName").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            companyId: $("#companyId").val(),
            roleIds: $("#roles").val()
        };

        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/users/save",
            data: JSON.stringify(userDto),
            contentType: 'application/json',
            success: function (result) {
                window.location = "<%=request.getContextPath()%>/users/preview/" + result.id;
            },
            error: function (error) {
                $('#message_div').message_error(error.responseJSON.errors.join("<br>"), 'Save user');
            }
        });

    }
</script>

