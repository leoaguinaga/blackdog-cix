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

    .close:hover{
        color: red;
        text-shadow: 0 0 10px red;
    }

    .btn-primary {
        color: var(--other-color);
        background-color: var(--text-color);
        border-color: var(--bg-color);
    }

    .btn-primary:hover {
        color: #fff;
        background-color: var(--main-color);
        border-color: var(--bg-color);
    }

    .signUp {
        color: var(--main-color);
        text-decoration: none;
    }

    .signUp:hover,
    .signUp:focus {
        color: var(--other-color);
        text-decoration:underline;
    }
    .Register{
        color: var(--main-color);
    }
    .Register:hover{
        color: #fff;
    }
</style>

<div class="modal fade" id="myModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title mx-auto">Inicia sesión</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
            </div>

            <div class="modal-body">
                <div class="container-modal">
                    <div class="row d-flex align-items-center justify-content-center">
                        <div class="col-md-8 col-lg-7 col-xl-6">
                            <img src="img/logo letras blancas.png"
                                 class="img-fluid" alt="Phone image">
                        </div>
                        <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
                            <form action="login" method="post">
                                <!-- Email input -->
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="email">Ingresa tu correo</label>
                                    <input type="text" id="email" name="email" class="form-control form-control-lg" required/>
                                </div>

                                <!-- Password input -->
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="password">Ingresa tu contraseña</label>
                                    <input type="password" id="password" name="password"
                                           class="form-control form-control-lg" required/>
                                </div>

                                <button type="submit" data-mdb-button-init data-mdb-ripple-init
                                        class="btn btn-primary btn-lg btn-block d-flex align-items-center my-4">Ingresar</button>

                                <div class="d-flex justify-content-around align-items-center mb-4">
                                    <label class="form-check-label">No tienes una cuenta? <a data-toggle="modal" data-target="#singin" class ="Register">Regístrate!</a></label>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>