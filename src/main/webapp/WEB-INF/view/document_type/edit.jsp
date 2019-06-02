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
    <fieldset>
        <hr>
        <legend class="text-semibold"><b>Descriptors list</b></legend>
        <div class="form-group row">
            <label for="descriptor_name" class="col-sm-4 col-form-label">Name</label>
            <div class="col-sm-6">
                <input type="text" name="name" id="descriptor_name" class="form-control"  ${disabled}/>
            </div>
        </div>
        <div class="form-group row">
            <label for="descriptor_type" class="col-sm-4 col-form-label">Type</label>
            <div class="col-sm-6">
                <select id="descriptor_type" class="form-control">
                    <c:forEach items="${descriptorClassList}" var="descriptorClass">
                        <option value="${descriptorClass}"> ${descriptorClass}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label">Mandatory</label>
            <div class="col-sm-6 row">
                <div class="col-sm-3">
                    <div class="radio">
                        <label><input type="radio" ${disabled} name="mandatory"
                                      checked value="true"> Yes</label>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="radio">
                        <label><input type="radio" ${disabled} name="mandatory"
                                      value="false"> No</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-10">
                <div class="text-right">
                    <button type="button" class="btn btn-basic" id="add_descriptor_type" ${disabled}>Add descriptor type
                    </button>
                </div>
            </div>
        </div>
        <div class="row col-lg-10 mt-4">
            <div class="table-responsive">
                <table id="descriptor_type_table" class="table table-framed table-hover">

                    <thead class="bg-teal-400">
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Mandatory</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
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
    var documentTypeDto = {
        id: $("#id").val(),
        name: $("#name").val(),
        descriptorTypeDtos: []
    };
    $(document).ready(function () {

        if (mode == 3) {
            fetchDescriptorsType();
        }
        $("#save").on("click", function () {
            save();
        });
        $("#edit").on("click", function () {
            window.location = "<%=request.getContextPath()%>/document/type/edit/" + $("#id").val();
        });

        $("#descriptor_type_table tbody").on('click', '.delete', function () {
            var rowId = jQuery(this).closest('tr').attr('id').split("_")[1];
            deleteDescriptorType(rowId);
        });


        $("#add_descriptor_type").on("click", function () {
            documentTypeDto.id = $("#id").val();
            documentTypeDto.name = $("#name").val();
            descriptorTypeDto = {
                id: null,
                name: $("#descriptor_name").val(),
                type: $("#descriptor_type").val(),
                mandatory: $("input[name=mandatory]:checked").val()
            };
            documentTypeDto.descriptorTypeDtos.push(descriptorTypeDto);
            $.ajax({
                type: "POST",
                url: "<%=request.getContextPath()%>/document/type/save",
                data: JSON.stringify(documentTypeDto),
                contentType: 'application/json',
                success: function (result) {
                    console.log(result);
                    $("#id").val(result.id);
                    documentTypeDto.descriptorTypeDtos = [];
                    $("#descriptor_type_table tbody").empty();
                    result.descriptorTypeDtos.map(function (item) {
                        documentTypeDto.descriptorTypeDtos.push(item);
                        addRow(item);
                    });

                },
                error: function (error) {
                    $('#message_div').message_error(error.responseJSON.errors.join("<br>"), 'Save document type');
                }
            });

        });
    });

    function addRow(data) {
        console.log(data);
        html = '<tr id="descriptor_' + data.id + '"><td>' + data.name + '</td><td>' + data.type + '</td><td>' + (data.mandatory ? 'Yes' : 'No') + '</td><td><button class=\"btn btn-danger delete\" type=\"button\" name=\"action\"><i class=\"fa fa-trash\" aria-hidden=\"true\"></i></button>' + '</td></tr>';
        $("#descriptor_type_table tbody").append(html);
    }

    function deleteDescriptorType(id) {
        $.ajax({
            url: "<%=request.getContextPath()%>/document/type/delete",
            type: 'POST',
            data: {"id": $("#id").val(), "descriptorTypeId": id},
            success: function (result) {
                console.log(result);
                if (result) {
                    deleteRow(id);
                }
            },
            error: function (error) {
            }
        });

    }

    function deleteRow(id) {
        for (var i = 0; i < documentTypeDto.descriptorTypeDtos.length; i++) {
            if (documentTypeDto.descriptorTypeDtos[i].id == id) {
                documentTypeDto.descriptorTypeDtos.splice(i, 1);
                break;
            }
        }
        $("#descriptor_" + id).remove();
    }

    function save() {
        documentTypeDto.id=$("#id").val();
        documentTypeDto.name = $("#name").val();
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/document/type/save",
            data: JSON.stringify(documentTypeDto),
            contentType: 'application/json',
            success: function (result) {
                window.location = "<%=request.getContextPath()%>/document/type";
            },
            error: function (error) {
                $('#message_div').message_error(error.responseJSON.errors.join("<br>"), 'Save document type');
            }
        });

    }

    function fetchDescriptorsType() {
        $.ajax({
            type: "GET",
            url: "<%=request.getContextPath()%>/document/type/descriptors/" + $("#id").val(),
            'dataType': 'json',
            success: function (result) {
                result.map(function (item) {
                    documentTypeDto.descriptorTypeDtos.push(item);
                    addRow(item);
                });
            },
            error: function (error) {
                console.log("Error: ", error);
                $('#message_div').message_error(error.responseJSON.errors.join("<br>"), 'Edit document type');
            }
        });

    }
</script>

