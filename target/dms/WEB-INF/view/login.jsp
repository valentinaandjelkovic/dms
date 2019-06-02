<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container">
    <div class="row">
        <div class="col-sm-6 col-md-7 ml-md-4">
            <h1 class="text-center login-title">Sign in to continue to  </br><b>Document Management System</b></h1>

            <c:if test="${not empty message}">
                <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                        ${message}
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>
            <div class="account-wall">
                <img class="profile-img" src="/resources/images/default-user.png" alt="">
                <form name='login' class="form-signin" role="form" action="/login" method='POST'>
                    <input type="text" name="username" id="username" class="form-control" placeholder="Username"
                           required autofocus>
                    <input type="password" name="password" id="password" class="form-control" placeholder="Password"
                           required>
                    <button class="btn btn-lg btn-primary btn-block" type="submit">
                        Log in
                    </button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>
</div>