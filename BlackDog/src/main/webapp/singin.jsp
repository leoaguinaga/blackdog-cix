
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
</style>
<div class="modal fade" id="singin">
        <div class="modal-dialog modal-lg">
                <div class="modal-content">

                        <div class="modal-header">
                                <h5 class="modal-title mx-auto">Regístrate</h5>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>

                        <div class="modal-body">
                                <form action="singin" method="post">
                                        <div class="form-outline mb-4">
                                                <label class="form-label" for="email">Ingrese su correo</label>
                                                <input type="email" id="email" name="email" class="form-control form-control-lg" required/>
                                        </div>
                                        <div class="form-outline mb-4">
                                                <label class="form-label" for="email">Ingrese su número de teléfono</label>
                                                <input type="text" id="phone" name="phone" class="form-control form-control-lg" required/>
                                        </div>
                                        <div class="form-outline mb-4">
                                                <label class="form-label" for="pwd">Ingrese su contraseña</label>
                                                <input type="password" id="pwd" name="pwd" class="form-control form-control-lg" required/>
                                        </div>
                                        <button type="submit" class="btn btn-primary btn-lg btn-block">Registrar</button>
                                </form>
                        </div>
                </div>
        </div>
</div>