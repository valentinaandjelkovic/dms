<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page buffer="8192kb" %>
<c:set var="readonly" value="false"></c:set>

<c:if test="${mode==2}">
    <c:set var="readonly" value="true"></c:set>
</c:if>
<form:form method="POST" modelAttribute="formDto">
    <form:input path="id" type="hidden"/>
    <div class="form-group row">
        <label for="name" class="col-sm-4 col-form-label">Name<span class="text-danger required">*</span></label>
        <div class="col-sm-6">
            <form:input path="name" type="text" class="form-control"
                        placeholder="Name" readonly="${readonly}"/>
        </div>
    </div>
    <div class="form-group row">
        <label for="file" class="control-label col-sm-4">File<span class="text-danger required">*</span></label>
        <div class="col-sm-6">
            <input type="file" id="file" name="file">
        </div>
    </div>
    <div class="form-group row">
        <label for="name" class="col-sm-4 col-form-label">Document type<span
                class="text-danger required">*</span></label>
        <div class="col-sm-6">
            <form:select path="documentTypeId" class="form-control" onchange="setDescriptorTypes()"
                         disabled="${readonly}">
                <form:options items="${documentTypeList}" itemValue="id" itemLabel="name"></form:options>
            </form:select>
        </div>
    </div>
    <fieldset>
        <hr>
        <legend class="text-semibold"><b>Document data</b></legend>
        <c:if test="${not empty documentTypeList}">
            <div id="descriptors_div">
                <c:forEach items="${documentTypeList.get(0).descriptorTypeDtos}" var="descriptorType">
                    <c:choose>
                        <c:when test="${descriptorType.type eq 'Date'}">
                            <div class="form-group">
                                <label for="${descriptorType.id}" class="control-label col-sm-4">${descriptorType.name}
                                    <c:if test="${descriptorType.mandatory}">
                                        <span class="required">*</span>
                                    </c:if>
                                </label>
                                <div class="col-sm-6">
                                    <input type="date" data-date-format="DD.MM.YYYY."
                                           class="form-control descriptors date-picker"
                                           name="${descriptorType.name}"
                                           id="${descriptorType.id}"
                                        ${descriptorType.mandatory? 'required': ''}>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="form-group">
                                <label for="${descriptorType.id}" class="control-label col-sm-4">${descriptorType.name}
                                    <c:if test="${descriptorType.mandatory}">
                                        <span class="required">*</span>
                                    </c:if>
                                </label>
                                <div class="col-sm-6">
                                    <input type="text" class="form-control descriptors" name="${descriptorType.name}"
                                           id="${descriptorType.id}" placeholder="Enter ${descriptorType.name}"
                                        ${descriptorType.mandatory? 'required': ''}>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </c:if>
    </fieldset>

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
        $("#save").on("click", function () {
            save();
        });
    });

    function setDescriptorTypes() {
        $.ajax({
            type: "GET",
            url: "<%=request.getContextPath()%>/document/type/descriptors/" + $("#documentTypeId").val(),
            'dataType': 'json',
            success: function (result) {
                $("#descriptors_div").empty();
                html = "";
                result.map(function (item) {
                    html += '<div class="form-group">' +
                        '<label for="' + item.id + '" class="control-label col-sm-4">' +
                        item.name + (item.mandatory ? '<span class="required">*</span>' : '') +
                        '</label>' +
                        '<div class="col-sm-6">';
                    if (item.type == 'Date') {
                        html += '<input type="date"  data-date-format="DD.MM.YYYY." class="form-control  date-picker descriptors" name="' + item.name + '"' +
                            ' id="' + item.id + '"' + (item.mandatory ? ' required' : '') + '>';
                    } else {
                        html += '<input type="text" class="form-control descriptors" name="' + item.name + '"' +
                            ' id="' + item.id + '" placeholder="Enter ' + item.name + '"' + (item.mandatory ? ' required' : '') + '>';
                    }
                    html += '</div>' +
                        '</div>';
                });
                $("#descriptors_div").append(html);
            },
            error: function (error) {
                $('#message_div').msessage_error(error.responseJSON.errors.join("<br>"), 'Save document');
            }
        });
    }

    function save() {
        if ($("#file")[0].files[0] == null) {
            $('#message_div').message_error("You must upload file!", 'Save document');
            return;
        }
        formData = new FormData();
        var descriptors = [];
        $('.descriptors').each(function () {
            descriptors.push({id: null, value: this.value, typeId: this.id});
        });
        documentDto = {
            id: $("#id").val(),
            name: $("#name").val(),
            documentTypeId: $("#documentTypeId").val(),
            descriptorDtoList: descriptors
        }
        console.log(documentDto);
        formData.append("file", $("#file")[0].files[0]);
        formData.append('documentDto', new Blob([JSON.stringify(documentDto)], {
            type: "application/json"
        }));
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/document/save",
            data: formData,
            processData: false,
            enctype: 'multipart/form-data',
            contentType: false,
            dataType: 'json',
            success: function (result) {
                window.location = "<%=request.getContextPath()%>/document/all";
            },
            error: function (error) {
                console.log(error);
                $('#message_div').message_error(error.responseJSON.errors.join("<br>"), 'Save document');
            }
        });

    }
</script>

