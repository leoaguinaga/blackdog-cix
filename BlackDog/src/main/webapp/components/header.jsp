<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .modal-content {
        background-color: rgba(255, 255, 255, 0.2);
        backdrop-filter: blur(10px);
    }

    .modal-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .modal-title {
        font-size: var(--h2-font);
        color: var(--main-color);
    }

    .close {
        color: white;
        font-size: var(--h2-font);
        text-shadow: 0 0 5px red;
        opacity: 1;
        background-color: transparent;
        border: none;
        padding: 0;
    }

    .close:hover {
        color: red;
        text-shadow: 0 0 10px red;
    }

    .signUp {
        color: var(--main-color);
        text-decoration: none;
    }

    .signUp:hover,
    .signUp:focus {
        color: var(--other-color);
        text-decoration: underline;
    }

    /* Estilo adicional para la etiqueta del formulario */
    .form-label {
        font-weight: bold;
    }

    /* Eliminar padding para el form-control */
    .form-control {
        padding: .375rem .75rem;
    }

    .modal-body {
        padding: 2rem;
    }

    header {
        position: fixed;
        width: 100%;
        top: 0;
        right: 0;
        z-index: 100;
        display: flex;
        align-items: center;
        justify-content: space-between;
        background: transparent;
        padding: 30px 14%;
        transition: all 0.5s ease;
    }

    .navbar-nav a {
        color: var(--text-color);
        font-size: var(--p-font);
        font-weight: 600;
        margin: 0 15px;
        transition: all 0.5s ease;
        text-decoration: none;
    }

    .navbar-nav a:hover {
        color: var(--main-color) !important;
    }

    .navbar-toggler-icon {
        color: var(--bg-color);
    }

    .navbar-light .navbar-toggler {
        border-color: var(--bg-color);
    }

    .btn-primary {
        background-color: var(--main-color);
        border-color: var(--main-color);
    }
</style>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
        <a class="navbar-brand" href="index.jsp"><img src="img/blackdog-logo.png" width="35px" height="35px" alt=""><strong>Black Dog</strong></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="index.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" style="cursor: pointer;" href="#" data-toggle="modal" data-target="#Modal_About">Nosotros</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="menu">Carta</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="https://wa.me/+51945851002?text=Hola%20,%20quiero%20hacer%20un%20pedido%20." target="_blank">Contactanos</a>
                </li>
                <%
                    String name = (String) session.getAttribute("name");
                    if (name == null) { %>
                <li class="nav-item">
                    <a class="nav-link" href="#" data-toggle="modal" data-target="#myModal">Log in</a>
                </li>
                <%
                } else { %>
                <li class="nav-item">
                    <a class="nav-link" href="profile">Mi perfil</a>
                </li>
                <li class="nav-item">
                    <form action="logout" method="post" class="d-inline">
                        <button type="submit" class="btn btn-primary">Logout</button>
                    </form>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </nav>

</header>
