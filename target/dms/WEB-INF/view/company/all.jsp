<div class="table-responsive">
    <table class="table" id="table">
        <thead>
        <th>Id</th>
        <th>Name</th>
        <th>Company number</th>
        <th>Action</th>
        </thead>
    </table>
</div>
<script>
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
                        "data": "name",
                        orderable: true,
                        render: function (data, type, row) {
                            return '<a href="<%=request.getContextPath()%>/companies/' + row.id + '/preview">' + data + '</a>'
                        }
                    },
                    {
                        "data": "companyNumber",
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
                        "searchable": false, "targets": [3]
                    }
                ],
                "ajax": {
                    "url": "<%=request.getContextPath()%>/companies/search",
                    "contentType": "application/json",
                    type: 'POST',
                    data: function (d) {
                        return JSON.stringify(d);
                    }
                }

            });

        $("#table tbody").on('click', '.datatable-button-edit', function () {
            var data = table.row($(this).parents('tr')).data();
            window.location = "<%=request.getContextPath()%>/companies/edit/" + data.id;
        });

        $("#table tbody").on('click', '.datatable-button-delete', function () {
            var data = table.row($(this).parents('tr')).data();
            confirmDialog("Delete company", "Do you really want to delete company - " + data.name + "?", deleteCompany, data.id);
        });

    });

    function deleteCompany(result, data) {
        if (result) {
            $.ajax({
                type: 'GET',
                url: "<%=request.getContextPath()%>/companies/delete/" + data,
                success: function (result) {
                    $("#message_div").message_success(result, "Delete company");
                    table.draw();
                },
                error: function (error) {
                    $("#message_div").message_error(error, "Delete company");
                }
            });
        }
    }


</script>
