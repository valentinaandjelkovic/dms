<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<a id="show-sidebar" class="btn btn-sm btn-dark" href="#">
    <i class="fas fa-bars"></i>
</a>
<nav id="sidebar" class="sidebar-wrapper">
    <div class="sidebar-content">
        <div class="sidebar-brand">
            <a href="#">DMS</a>
            <div id="close-sidebar">
                <i class="fas fa-times"></i>
            </div>
        </div>
        <div class="sidebar-header">
            <div>
                <img class="img-responsive img-rounded"
                     src="https://raw.githubusercontent.com/azouaoui-med/pro-sidebar-template/gh-pages/src/img/user.jpg"
                     alt="User picture"
                     height="50" width="50">
            </div>
            <div class="user-info">
                        <span class="user-name"><strong><sec:authentication property="principal.username"/></strong>
                    </span>
            </div>
        </div>
        <!-- sidebar-header  -->
        <div class="sidebar-menu">
            <ul>
                <li class="header-menu">
                    <span>General</span>
                </li>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <li class="sidebar-dropdown">
                        <a href="#">
                            <i class="fa fa-building"></i>
                            <span>Company</span>
                        </a>
                        <div class="sidebar-submenu">
                            <ul>
                                <li>
                                    <a href="/companies">List</a>
                                </li>
                                <li>
                                    <a href="/companies/add">New</a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li class="sidebar-dropdown">
                        <a href="#">
                            <i class="fa fa-user"></i>
                            <span>User</span>
                        </a>
                        <div class="sidebar-submenu">
                            <ul>
                                <li>
                                    <a href="/users">List
                                    </a>
                                </li>
                                <li>
                                    <a href="/users/add">Add new</a>
                                </li>
                            </ul>
                        </div>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasAnyRole('ROLE_DOCUMENT_MANAGER', 'ROLE_ADMIN')">

                    <li class="sidebar-dropdown">
                        <a href="#">
                            <i class="fa fa-file"></i>
                            <span>Document template</span>
                        </a>
                        <div class="sidebar-submenu">
                            <ul>
                                <li>
                                    <a href="/document/type">List</a>
                                </li>
                                <li>
                                    <a href="/document/type/add">Add new template</a>
                                </li>
                                <li>
                                    <a href="/document/add">Add new document</a>
                                </li>
                            </ul>
                        </div>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_PROCESS_MANAGER', 'ROLE_ADMIN')">

                    <li class="sidebar-dropdown">
                        <a href="#">
                            <i class="fa fa-history"></i>
                            <span>Process</span>
                        </a>
                        <div class="sidebar-submenu">
                            <ul>
                                <li>
                                    <a href="/process/all">Tree</a>
                                </li>
                            </ul>
                        </div>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_DOCUMENT_CONSUMER', 'ROLE_DOCUMENT_MANAGER', 'ROLE_ADMIN')">
                    <li class="header-menu">
                        <span>Library</span>
                    </li>
                    <li>
                        <a href="/document/all">
                            <i class="fa fa-folder"></i>
                            <span>Documents</span>
                        </a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
        <!-- sidebar-menu  -->
    </div>
    <!-- sidebar-content  -->

    <div class="sidebar-footer">
        <sec:authorize access="isAuthenticated()">
            <a href="/logout">
                <i class="fas fa-sign-out-alt"></i>
                <span>Log out</span>
            </a>
        </sec:authorize>
    </div>
</nav>
<!-- sidebar-wrapper -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>

<script>
    jQuery(function ($) {

        $(".sidebar-dropdown > a").click(function () {
            $(".sidebar-submenu").slideUp(200);
            if (
                $(this)
                    .parent()
                    .hasClass("active")
            ) {
                $(".sidebar-dropdown").removeClass("active");
                $(this)
                    .parent()
                    .removeClass("active");
            } else {
                $(".sidebar-dropdown").removeClass("active");
                $(this)
                    .next(".sidebar-submenu")
                    .slideDown(200);
                $(this)
                    .parent()
                    .addClass("active");
            }
        });

        $("#close-sidebar").click(function () {
            $(".page-wrapper").removeClass("toggled");
        });
        $("#show-sidebar").click(function () {
            $(".page-wrapper").addClass("toggled");
        });
    });
</script>