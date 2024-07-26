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

</style>

<div class="modal fade" id="Modal_About">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title mx-auto">¿Quienes somos?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">&times;</button>
            </div>

            <div class="modal-body" style="color: #fff3cd;">
                Black Dog - Night Food es una dark kitchen especializada en la venta de comida rápida
                durante las noches y madrugadas. Una dark kitchen es un establecimiento dedicado exclusivamente
                a la preparación de alimentos para entrega a domicilio, sin servicio de comedor físico.
                <br>
                Operamos desde las 8:00 PM hasta las 3:30 AM, enfocados en satisfacer los antojos nocturnos de
                nuestros clientes en Chiclayo. Nos destacamos por ofrecer productos de alta calidad y un servicio
                de delivery rápido y económico.
                <br>
                A pesar de ser nuevos en el mercado, aspiramos a convertirnos en el referente líder de la comida
                rápida delivery en Chiclayo, consolidándonos como la elección preferida por nuestra calidad,
                confiabilidad y dedicación al cliente.
            </div>
        </div>
    </div>
</div>