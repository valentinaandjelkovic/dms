<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach items="${documentList}" var="document">
    <div class="card text-center mt-4">
        <div class="card-body">
            <h5 class="card-title">${document.name}</h5>
            <a href="<%=request.getContextPath()%>/document/download/${document.id}" class="btn btn-primary"><i
                    class="fa fa-download"></i></a>
        </div>
        <div class="card-footer text-muted">
            Document type: ${document.typeDto.name}
            <br>
            Created date: ${document.date}
        </div>
    </div>
</c:forEach>



