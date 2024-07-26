<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% double amount = (double) request.getAttribute("amount"); %>
<jsp:include page="components/head.jsp" />
<jsp:include page="components/header.jsp" />
<br><br><br><br><br><br>
<img src="img/qr_yape.jpg" alt="qr_yape" height="360" width="330">

<h2>Formulario para registrar la orden</h2>
<form action="${pageContext.request.contextPath}/registerOrder" method="post" enctype="multipart/form-data">
    <input type="hidden" name="totalPrice" value="<%= amount %>"><br>
    <label for="address"> Dirección</label><br>
    <input type="text" id="address" name="address" placeholder="Dirección para delivery" readonly><br>
    <div id="map"></div><br>
    <label for="evidence"> Evidencia (Yape o Plin)</label><br>
    <input type="file" id="evidence" name="evidence" required><br>
    <button class="btn btn-success" type="submit">Registrar Orden</button>
</form>


<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDtWFd_3Gt-l9bHY7Iv5My8uG9vpq-vb58&libraries=places"></script>
<script>
    let map;
    let marker;
    let geocoder;

    function initMap() {
        // Inicializa el mapa con una ubicación específica y un nivel de zoom
        const initialLocation = { lat: -6.781015, lng: -79.847122 }; // Coordenadas iniciales
        const initialZoom = 14; // Nivel de zoom

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
                    document.getElementById('address').value = results[0].formatted_address;
                } else {
                    window.alert('No results found');
                }
            } else {
                window.alert('Geocoder failed due to: ' + status);
            }
        });
    }

    google.maps.event.addDomListener(window, 'load', initMap);
</script>

<jsp:include page="components/footer.jsp" />
