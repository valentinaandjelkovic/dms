<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
    <div class="col-md-6">
        <ul class="treeview"></ul>
    </div>
    <div class="col-md-6">
        <div class="row">
            <div id="add_proccess_button">
                <button type="button" id="add_process" class="btn btn-primary">Add process</button>
            </div>
            <div id="add_activity_button" class="ml-2 hidden">
                <button type="button" id="show_activity" class="btn btn-info">Show activities</button>
                <button type="button" id="add_activity" class="btn btn-success">Add activity</button>
            </div>
        </div>
        <div class="hidden" id="div_add_process">
            <fieldset>
                <hr>
                <div class="form-group row">
                    <label for="name" class="col-sm-4 col-form-label">Name</label>
                    <div class="col-sm-8">
                        <input type="text" name="name" id="name" class="form-control"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-4 col-form-label">Primitive</label>
                    <div class="col-sm-8 row">
                        <div class="col-sm-3">
                            <div class="radio">
                                <label><input type="radio" name="primitive"
                                              checked value="true"> Yes</label>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="radio">
                                <label><input type="radio" name="primitive"
                                              value="false"> No</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="name" class="col-sm-4 col-form-label">Parent</label>
                    <div class="col-sm-8">
                        <select id="parentId" class="form-control">
                            <option value="">---</option>
                            <c:forEach items="${processList}" var="process">
                                <option value="${process.id}">${process.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group row float-right">
                    <button type="button" id="save_process" class="btn btn-primary">Save</button>
                </div>
            </fieldset>
        </div>
        <div class="hidden" id="div_add_activity">
            <fieldset>
                <hr>
                <div class="form-group row">
                    <label for="activity_name" class="col-sm-4 col-form-label">Name</label>
                    <div class="col-sm-8">
                        <input type="text" name="name" id="activity_name" class="form-control"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="name" class="col-sm-4 col-form-label">Process</label>
                    <div class="col-sm-8">
                        <select id="processId" class="form-control">
                            <option value="-1">---</option>
                            <c:forEach items="${primitiveProcessList}" var="process">
                                <option value="${process.id}">${process.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group row float-right">
                    <button type="button" id="save_activity" class="btn btn-primary">Save</button>
                </div>
            </fieldset>
        </div>
        <div class="hidden" id="div_edit_process">
            <fieldset>
                <legend>Edit process</legend>
                <hr>
                <input type="hidden" id="id" value=""/>
                <div class="form-group row">
                    <label for="process_name" class="col-sm-4 col-form-label">Name</label>
                    <div class="col-sm-8">
                        <input type="text" name="name" id="process_name" class="form-control"/>
                    </div>
                </div>
                <div class="form-group row float-right">
                    <button type="button" id="update_process" class="btn btn-primary">Save changes</button>
                </div>
            </fieldset>
        </div>
    </div>
</div>
<div class="col-md-12 row">
    <div class="hidden" id="div_activity_table">
        <table id="activity_table" class="table table-framed table-hover mt-2">

            <thead class="bg-teal-400">
            <tr>
                <th>Name</th>
                <th>Input documents</th>
                <th>Output documents</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<script>

    var artaraxTreeView;
    var treeViewData;
    var selectedId;

    $(document).ready(function () {


        loadTreeData();

        $("#add_process").on('click', function () {
            $("#parentId").val(selectedId);
            $("#div_add_process").show();
        });

        $("#add_activity").on('click', function () {
            $("#processId").val(selectedId);
            $("#div_add_activity").show();
        });

        $("#show_activity").on('click', function () {
            fetchActivities();
            $("#div_activity_table").show();
            $('html, body').animate({
                scrollTop: $("#div_activity_table").offset().top
            }, 2000);
        })


        $("#save_process").on('click', function () {
            saveProcess();
        });
        $("#save_activity").on('click', function () {
            saveActivity();
        });

        $("#update_process").on('click', function () {
            updateProcess();
        });


        $(".treeview").on('click', '.node-update', function () {
            var selectedId = parseInt($(this).attr('data-id'));
            var selectedObj = getTreeViewObjectById(selectedId);
            $("#id").val(selectedObj.Id);
            $("#process_name").val(selectedObj.Title);
            $("#div_edit_process").show();
        });

        $(".treeview").on('click', '.node-delete', function () {
            var selectedId = parseInt($(this).attr('data-id'));
            var selectedObj = getTreeViewObjectById(selectedId);
            confirmDialog("Delete process", "Do you really want to delete process and all podprocess of - " + selectedObj.Title + "?", deleteProcess, selectedObj.Id);
        });


        $(".treeview").on('click', '.chk', function () {
            selectedId = parseInt($(this).attr('data-id'));
            $.ajax({
                type: 'GET',
                url: "<%=request.getContextPath()%>/process/" + selectedId,
                success: function (result) {
                    resetData();
                    if (result.primitive) {
                        $("#add_activity_button").show();
                        $("#add_proccess_button").hide();
                    } else {
                        $("#add_activity_button").hide();
                        $("#add_proccess_button").show();
                    }
                },
                error: function (error) {
                    $("#message_div").message_error(error.responseJSON.errors.join("<br>"), "Fetch process data");
                }
            });

        });
    });


    function loadTreeData() {
        $.ajax({
            type: "GET",
            url: "<%=request.getContextPath()%>/process/fetch",
            'dataType': 'json',
            success: function (result) {
                treeViewData = [{'Id': -1, 'Title': 'My tree', 'ParentId': null}];
                result.map(function (item) {
                    if (item.parentId == null) {
                        treeViewData.push({'Id': item.id, 'Title': item.name, 'ParentId': -1});
                    } else {
                        treeViewData.push({'Id': item.id, 'Title': item.name, 'ParentId': item.parentId});
                    }
                });
                artaraxTreeView = $.artaraxTreeView({
                    jsonData: treeViewData,
                    mode: "updatable"
                });
                artaraxTreeView.loadTreeViewOnUpdate(-1);

            },
            error: function (error) {
                $('#message_div').message_error(error.responseJSON.errors.join("<br>"), 'Process tree');
            }
        });
    }

    function saveProcess() {
        processDto = {
            id: null,
            name: $("#name").val(),
            primitive: $("input[name=primitive]:checked").val(),
            parentId: $("#parentId").val()
        };

        console.log("Process", processDto);
        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/process/save",
            data: JSON.stringify(processDto),
            contentType: 'application/json',
            'dataType': 'json',
            success: function (result) {
                var option = new Option(result.name, result.id);
                $(option).html(result.name);
                if (result.primitive) {
                    $("#processId").append(option);
                } else {
                    $("#parentId").append(option);
                }
                resetData();
                loadTreeData();
            },
            error: function (error) {
                $('#message_div').message_error(error.responseJSON.errors.join("<br>"), 'Save process');
            }
        });
    }


    function saveActivity() {
        activityDto = {
            id: null,
            name: $("#activity_name").val(),
            processId: $("#processId").val()
        };

        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/activity/save",
            data: JSON.stringify(activityDto),
            contentType: 'application/json',
            'dataType': 'json',
            success: function (result) {
                resetData();
            },
            error: function (error) {
                $('#message_div').message_error(error.responseJSON.errors.join("<br>"), 'Save activity');
            }
        });
    }

    function deleteProcess(result, data) {
        if (result) {
            $.ajax({
                type: 'GET',
                url: "<%=request.getContextPath()%>/process/delete/" + data,
                success: function (result) {
                    $("#message_div").message_success(result, "Delete process");
                    loadTreeData();
                },
                error: function (error) {
                    $("#message_div").message_error(error.responseJSON.errors.join("<br>"), "Delete process");
                }
            });
        }
    }

    function updateProcess() {
        processDto = {
            id: $("#id").val(),
            name: $("#process_name").val()
        };

        console.log(processDto);

        $.ajax({
            type: "POST",
            url: "<%=request.getContextPath()%>/process/save",
            data: JSON.stringify(processDto),
            contentType: 'application/json',
            'dataType': 'json',
            success: function (result) {
                resetData();
                loadTreeData();
            },
            error: function (error) {
                $('#message_div').message_error(error.responseJSON.errors.join("<br>"), 'Save process');
            }
        });

    }

    function getTreeViewObjectById(id) {
        var temp;
        $.each(treeViewData, function (index, item) {
            if (item.Id === id) {
                temp = item;
            }
        });
        return temp
    }

    function fetchActivities() {
        $.ajax({
            type: 'GET',
            url: "<%=request.getContextPath()%>/activity/" + selectedId,
            success: function (result) {
                console.log(result);
                result.map(function (item) {
                    addRow(item);
                });

            },
            error: function (error) {
                $("#message_div").message_error(error.responseJSON.errors.join("<br>"), "Fetch activities");
            }
        });

    }

    function addRow(data) {
        console.log(data);
        inputDocuments = "";
        data.inputDocumentDtos.map(function (item) {
            inputDocuments += '<a href="<%=request.getContextPath()%>/document/download/' + item.id + '">' + item.fileName + '</a></br>';
        });
        outputDocuments = "";
        data.outputDocumentDtos.map(function (item) {
            outputDocuments += '<a href="<%=request.getContextPath()%>/document/download/' + item.id + '">' + item.fileName + '</a></br>';
        });
        html = '<tr id="activity' + data.id + '"><td>' + data.name + '</td><td>' + inputDocuments + '</td><td>' + outputDocuments + '</td></tr>';
        $("#activity_table tbody").append(html);
    }

    function resetData() {
        $("#id").val("");
        $("#process_name").val("");
        $("#activity_name").val("");
        $("#name").val("");
        $("#parentId").val(-1);
        $("#processId").val(-1);
        $("#div_add_process").hide();
        $("#div_edit_process").hide();
        $("#div_add_activity").hide();
        $("#activity_table tbody").empty();
        $("#div_activity_table").hide();
    }
</script>