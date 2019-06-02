<div class="form-group row mb-3">
    <div class="col-sm-10">
        <button type="button" id="add_document_type" class="btn btn-primary">Add document type</button>
    </div>
</div>

<div class="table-responsive">
    <table class="table" id="table">
        <thead>
        <th>Id</th>
        <th>Name</th>
        <th>Action</th>
        </thead>
    </table>
</div>

<script>
    var table;
    $(document).ready(function () {

        $("#add_document_type").on('click', function () {
            window.location = "<%=request.getContextPath()%>/document/type/add";
        });
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
                        orderable: true
                    },
                    {
                        "data": "null",
                        "defaultContent": "<div class='list-icons'>" +
                            "<a data-popup='tooltip' title='Edit' data-container='body' data-original-title='Edit' class='list-icons-item datatable-button-edit text-success pointer'>  <i class=\"fa fa-edit\"></i>\n</a>" +
                            "</div>",
                        orderable: false,
                        "width": "8%"
                    }


                ],
                "columnDefs": [
                    {
                        "searchable": false, "targets": [2]
                    }
                ],
                "ajax": {
                    "url": "<%=request.getContextPath()%>/document/type/search",
                    "contentType": "application/json",
                    type: 'POST',
                    data: function (d) {
                        return JSON.stringify(d);
                    }
                }

            });

        $("#table tbody").on('click', '.datatable-button-edit', function () {
            var data = table.row($(this).parents('tr')).data();
            window.location = "<%=request.getContextPath()%>/document/type/edit/" + data.id;
        });

    });

</script>
