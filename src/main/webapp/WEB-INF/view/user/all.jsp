<div class="table-responsive">
    <table class="table" id="table">
        <thead>
        <th>Id</th>
        <th>First name</th>
        <th>Last name</th>
        <th>Username</th>
        <th>User roles</th>
        <th>Action</th>
        </thead>
    </table>
</div>
<script>
    console.log("Usao")
    var table;
    $(document).ready(function () {
        table = $("#table").DataTable(
            {
                "processing": false,
                "serverSide": true,
                orderable: false,
                "columns": [
                    {
                        "data": "id",
                        "orderable": true
                    },
                    {
                        "data": "firstName",
                        orderable: true
                    },
                    {
                        "data": "lastName",
                        orderable: true
                    },
                    {
                        "data": "username",
                        orderable: true
                    },
                    {
                        "data": "roleDtoList",
                        "mRender": "[, ].name",
                        orderable: false
                    },
                    {
                        "data": "null",
                        "defaultContent": "<div class='list-icons'>" +
                            "<a data-popup='tooltip' title='Edit' data-container='body' data-original-title='Edit' class='list-icons-item datatable-button-edit text-success pointer'>  <i class=\"fa fa-edit\"></i>\n</a>" +
                            "<a data-popup='tooltip' title='Remove' data-container='body' data-original-title='Remove' class='list-icons-item datatable-button-delete text-danger pointer'><i class=\"fa fa-trash\"></i></a>" +
                            "</div>",
                        orderable: false,
                        "width": "8%"
                    }


                ],
                "columnDefs": [
                    {
                        "searchable": false, "targets": [4, 5]
                    }
                ],
                "ajax": {
                    "url": "<%=request.getContextPath()%>/users/search",
                    "contentType": "application/json",
                    type: 'POST',
                    data: function (d) {
                        return JSON.stringify(d);
                    }
                }

            });

        $("#table tbody").on('click', '.datatable-button-edit', function () {
            var data = table.row($(this).parents('tr')).data();
            window.location = "<%=request.getContextPath()%>/users/edit/" + data.id;
        });

        $("#table tbody").on('click', '.datatable-button-delete', function () {
            var data = table.row($(this).parents('tr')).data();
            confirmDialog("Delete user", "Do you really want to delete user - " + data.firstName + " " + data.lastName + "?", deleteUser, data.id);
        });

    });

    function deleteUser(result, data) {
        if (result) {
            $.ajax({
                type: 'GET',
                url: "<%=request.getContextPath()%>/users/delete/" + data,
                success: function (result) {
                    $("#message_div").message_success(result, "Delete user");
                    table.draw();
                },
                error: function (error) {
                    $("#message_div").message_error(error, "Delete user");
                }
            });
        }
    }


</script>
