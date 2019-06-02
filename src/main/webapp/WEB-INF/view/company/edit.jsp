<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="readonly" value="false"></c:set>

<c:if test="${mode==2}">
    <c:set var="readonly" value="true"></c:set>
</c:if>
<form:form method="POST" modelAttribute="formDto">
    <form:input path="id" type="hidden"/>
    <div class="form-group row">
        <label for="name" class="col-sm-4 col-form-label">Name</label>
        <div class="col-sm-6">
            <form:input path="name" type="text" class="form-control"
                        placeholder="Name" readonly="${readonly}"/>
        </div>
    </div>
    <div class="form-group row">
        <label for="companyNumber" class="col-sm-4 col-form-label">Company number</label>
        <div class="col-sm-6">
            <form:input path="companyNumber" type="text" class="form-control"
                        placeholder="Company number" readonly="${readonly}"/>
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
    $(document).ready(function () {
        $("#save").on("click", function () {
            save();
        });
        $("#edit").on("click", function () {
            window.location = "<%=request.getContextPath()%>/companies/edit/" + $("#id").val();
        });
    });

    function save() {
        companyDto = {
            id: $("#id").val(),
            name: $("#name").val(),
            companyNumber: $("#companyNumber").val()
        };
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/companies/save",
            data: JSON.stringify(companyDto),
            contentType: 'application/json',
            'dataType': 'json',
            success: function (result) {
                window.location = "<%=request.getContextPath()%>/companies/preview/" + result.id;
            },
            error: function (error) {
                $('#message_div').message_error(error.responseJSON.errors.join("<br>"), 'Save company');
            }
        });

    }
</script>

