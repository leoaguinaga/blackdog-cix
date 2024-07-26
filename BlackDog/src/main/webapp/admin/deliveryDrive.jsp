<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    body {
        font-family: Arial, sans-serif;
        line-height: 1.5;
        color: #D88B30;
        text-align: center;
    }

    .modal-content {
        color: #333;
    }

    .step-number {
        font-size: 2em;
        border: 2px solid #D88B30;
        border-radius: 50%;
        width: 50px;
        height: 50px;
        display: inline-block;
        line-height: 50px;
    }

    .qr-code img {
        width: 100px;
        height: 100px;
    }

    .input-address input {
        width: 80%;
        padding: 10px;
        font-size: 1em;
    }
    .img-qr {
        max-width: 240px;
        max-height: 240px;
        width: auto;
        height: auto;
    }
    .modal-body .step {
        margin-bottom: 1.5rem;
    }

    .modal-body .form-outline {
        margin-bottom: 1.5rem;
    }

    .modal-body .btn {
        display: block;
        margin: 0 auto;
    }

    .modal-body .step h5 {
        color: white;
        text-align: center;
    }

    .step-container {
        display: flex;
        align-items: center;
        margin-bottom: 1rem;
    }

    .step-number {
        color: white;
        margin-right: 1rem;
    }

    .step-container h5 {
        margin: 0;
        text-align: center;
    }

    p {
        color: white;
    }

    .modal-body hr {
        border: none;
        height: 2px;
        background-color: white;
        margin: 1rem 0;
    }

    .distance-info {
        margin-top: 1rem;
        color: white;
    }
</style>

<div class="modal fade" id="modal_car" tabindex="-1" role="dialog" aria-labelledby="modal_pago_title" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title mx-auto" id="modal_pago_title">Instrucciones de Pago</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="container">
                    <!-- Paso 1 -->
                    <div class="step">
                        <div class="step-container">
                            <div class="step-number">1</div>
                            <h5>Realiza el pago</h5>
                        </div>
                        <div class="container">
                            <div class="row">
                                <div class="col-md-6 d-flex flex-column justify-content-center">
                                    <h5>Número para Yape / Plin</h5><br>
                                    <p>+51 947 502 562</p>
                                    <p>A nombre: Yomira Zegarra Coronel</p>
                                </div>
                                <div class="col-md-6 text-center">
                                    <div class="img-qr">
                                        <img src="img/qr_yape.jpg" class="img-qr" alt="Qr Yape">
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-12">
                                    <h5>Transferencia - Cuenta BCP</h5><br>
                                    <p>Numero de cuenta: 30577070122095.</p>
                                    <p>CCI: 00230517707012209513</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <!-- Paso 2 -->
                    <div class="datos_car">
                        <form action="${pageContext.request.contextPath}/registerOrder" method="post" enctype="multipart/form-data">
                            <div class="step">
                                <div class="step-container">
                                    <div class="step-number">2</div>
                                    <h5>Envia la evidencia de pago</h5>
                                </div>
                                <div class="form-outline mb-4">
                                    <p>Adjunta tu evidencia:</p>
                                    <input type="file" id="evidence" name="evidence" class="form-control form-control-lg" required/>
                                </div>
                            </div>
                            <hr>
                            <!-- Paso 3 -->
                            <div class="step">
                                <div class="step-container">
                                    <div class="step-number">3</div>
                                    <h5>Selecciona tu dirección</h5>
                                </div>
                                <div class="form-outline mb-4">
                                    <input type="text" id="address" name="address" readonly><br>
                                    <div id="map" style="height: 400px;"></div><br>
                                </div>
                                <div class="distance-info" id="output"></div>
                            </div>
                            <button type="submit" data-mdb-button-init data-mdb-ripple-init class="btn btn-primary btn-lg btn-block d-flex align-items-center my-4">Registrar Orden</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://maps.googleapis.com/maps/api/js?key=API_KEY=places"></script>
<script>
    let map;
    let marker;
    let geocoder;

    function initMap() {
        const initialLocation = { lat: -6.781015, lng: -79.847122 };
        const initialZoom = 14;

        geocoder = new google.maps.Geocoder();
        map = new google.maps.Map(document.getElementById('map'), {
            center: initialLocation,
            zoom: initialZoom,
        });

        map.addListener('click', function(event) {
            placeMarkerAndPanTo(event.latLng, map);
            geocodeLatLng(geocoder, event.latLng);
        });
    }

    function placeMarkerAndPanTo(latLng, map) {
        if (marker) {
            marker.setPosition(latLng);
        } else {
            marker = new google.maps.Marker({
                position: latLng,
                map: map,
            });
        }
        map.panTo(latLng);
    }

    function geocodeLatLng(geocoder, latLng) {
        geocoder.geocode({ location: latLng }, function(results, status) {
            if (status === "OK") {
                if (results[0]) {
                    const address = results[0].formatted_address;
                    document.getElementById('address').value = address;
                    calculateDistance(latLng);
                } else {
                    window.alert('No results found');
                }
            } else {
                window.alert('Geocoder failed due to: ' + status);
            }
        });
    }

    function calculateDistance(destinationLatLng) {
        const originLatLng = { lat: -6.781015, lng: -79.847122 }; // Replace with your actual origin coordinates
        const service = new google.maps.DistanceMatrixService();

        service.getDistanceMatrix({
            origins: [originLatLng],
            destinations: [destinationLatLng],
            travelMode: 'DRIVING',
        }, function(response, status) {
            if (status === 'OK') {
                const results = response.rows[0].elements;
                const distance = results[0].distance.text;
                const duration = results[0].duration.text;
                document.getElementById('output').innerHTML = 'Distancia: ' + distance + '<br>Duración: ' + duration;
            } else {
                console.error('Error calculating distance: ' + status);
            }
        });
    }

    google.maps.event.addDomListener(window, 'load', initMap);
</script>
