<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% boolean isError = false; %>
<% if(request.getAttribute("isError") != null) {
    isError = (boolean) request.getAttribute("isError");
}%>
<jsp:include page="components/head.jsp" />
<jsp:include page="components/header.jsp" />

<section class="home" id="home">
    <div class="home-text">
        <h1>Conoce,<span> Come y</span> Disfruta una buena burger</h1>
        <a href="${pageContext.request.contextPath}/menu" class="btn-custom">
            Carta
            <i class="bx bxs-right-arrow"></i>
        </a>
    </div>

    <jsp:include page="modal.jsp" />
    <jsp:include page="loginError.jsp" />
    <jsp:include page="modal_about.jsp" />
    <jsp:include page="singin.jsp" />

    <div class="home-img">

        <div id="carouselExampleFade" class="carousel slide carousel-fade">
            <div class="carousel-inner">
                <div class="carousel-item active" data-bs-interval="10000">
                    <img src="img/homenburgerclasica.png" class="img-fluid d-block aspect-carousel" alt="...">
                </div>
                <div class="carousel-item" data-bs-interval="2000">
                    <img src="img/homeburgercheeseburger.png" class="img-fluid d-block aspect-carousel" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="img/homeburgeralopobre.png" class="img-fluid d-block aspect-carousel" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="img/homeburgerdelacasa.png" class="img-fluid d-block aspect-carousel" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="img/homechaufadepollo.png" class="img-fluid d-block aspect-carousel" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="img/homechaufadececina.png" class="img-fluid d-block aspect-carousel" alt="...">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>

    </div>
</section>

<section class="about" id="about">
    <div class="container-boton">
        <a href="${pageContext.request.contextPath}/checkout" class="nav-link">
            <span class="icon-wtsp">
            <i class="fa-solid fa-cart-shopping icon-wtsp"></i>
            </span>
        </a>
    </div>
</section>
<% if(isError == true) { %>
<script>
    $(document).ready(function() {
        $('#myError').modal('show');
    });
</script>
<% } %>
<jsp:include page="components/footer.jsp" />

</body>
</html>