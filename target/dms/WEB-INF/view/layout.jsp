<!DOCTYPE html>
<html>
<head>
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

    <tiles:insertAttribute name="scripts"/>
</head>
<body>
<div class="flex-container">
    <div class="page-wrapper chiller-theme toggled">
        <sec:authorize access="isAuthenticated()">
            <tiles:insertAttribute name="menu"/>
        </sec:authorize>

        <main class="page-content">
            <div class="container-fluid">
                <sec:authorize access="isAuthenticated()">
                    <h2>
                        <tiles:insertAttribute name="title"/>
                    </h2>
                    <hr>
                </sec:authorize>

                <div id="message_div"></div>
                <tiles:insertAttribute name="content"/>
            </div>
        </main>
    </div>
</div>
</body>
</html>

<script>
    (function ($) {
        $.fn.extend({
            message_error: function (message, title) {
                var cls = 'alert-danger';
                var html = '<div class="alert ' + cls + ' alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>';
                if (typeof title !== 'undefined' && title !== '') {
                    html += '<h4>' + title + '</h4>';
                }
                html += '<span>' + message + '</span></div>';
                $(this).html(html);
            },
            message_warning: function (message, title) {
                var cls = 'alert-warning';
                var html = '<div class="alert ' + cls + ' alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>';
                if (typeof title !== 'undefined' && title !== '') {
                    html += '<h4>' + title + '</h4>';
                }
                html += '<span>' + message + '</span></div>';
                $(this).html(html);
            },
            message_success: function (message, title) {
                var cls = 'alert-success';
                var html = '<div class="alert ' + cls + ' alert-dismissable"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>';
                if (typeof title !== 'undefined' && title !== '') {
                    html += '<h4>' + title + '</h4>';
                }
                html += '<span>' + message + '</span></div>';
                $(this).html(html);
            }
        });
    })(jQuery);

    function confirmDialog(title, message, handler, data) {
        $("<div class=\"modal fade\" id=\"myModal\" role=\"dialog\"> <div class=\"modal-dialog\">" +
            "<div class=\"modal-content\">" +
            "<div class=\"modal-header\">" +
            "<h4 class=\"modal-title\" id=\"myModalLabel\">" + title + "</h4>" +
            "<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>" +
            "</div>" +
            "<div class=\"modal-body\" style=\"padding:10px;\">" +
            "<p>" + message + "</p>" +
            "<div class=\"modal-footer\">" +
            "<a class=\"btn btn-danger btn-yes\">Yes</a>" +
            "<a class=\"btn btn-success btn-no\">No</a>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>").appendTo('body');

        $("#myModal").modal({
            backdrop: 'static',
            keyboard: false
        });

        $(".btn-yes").click(function () {
            handler(true, data);
            $("#myModal").modal("hide");
        });

        $(".btn-no").click(function () {
            handler(false, data);
            $("#myModal").modal("hide");
        });

        $("#myModal").on('hidden.bs.modal', function () {
            $("#myModal").remove();
        });
    }
</script>