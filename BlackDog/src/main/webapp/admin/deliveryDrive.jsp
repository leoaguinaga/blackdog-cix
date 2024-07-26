<%--
  Created by IntelliJ IDEA.
  User: Leo
  Date: 26/07/2024
  Time: 01:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String destino = request.getAttribute("destination").toString(); %>
<!DOCTYPE html>
<html>

<head>
    <title>Google Maps Directions API</title>
    <style>
        /* Define el tamaño del mapa */

        #map {
            height: 1200px;
            width: 100%;
        }
    </style>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDtWFd_3Gt-l9bHY7Iv5My8uG9vpq-vb58&libraries=places"></script>
    <script>
        function initMap() {
            // Crear un objeto de mapa y especificar el centro y el nivel de zoom.
            var map = new google.maps.Map(document.getElementById('map'), {
                center: {
                    lat: -34.397,
                    lng: 150.644
                },
                zoom: 8
            });

            // Crear un objeto de DirectionsService para usar el servicio de direcciones.
            var directionsService = new google.maps.DirectionsService();

            // Crear un objeto de DirectionsRenderer para mostrar las direcciones en el mapa.
            var directionsRenderer = new google.maps.DirectionsRenderer();

            // Enlazar el DirectionsRenderer al mapa.
            directionsRenderer.setMap(map);

            // Definir los puntos de origen y destino.
            var origin = '-6.781015, -79.847122';
            var destination = '<%= destino %>';

            // Llamar a la función para calcular y mostrar la ruta.
            calculateAndDisplayRoute(directionsService, directionsRenderer, origin, destination);
        }

        function calculateAndDisplayRoute(directionsService, directionsRenderer, origin, destination) {
            directionsService.route({
                    origin: origin,
                    destination: destination,
                    travelMode: google.maps.TravelMode.DRIVING // Puedes cambiar el modo de viaje si es necesario.
                },
                function(response, status) {
                    if (status === google.maps.DirectionsStatus.OK) {
                        // Mostrar la ruta en el mapa.
                        directionsRenderer.setDirections(response);
                    } else {
                        window.alert('Directions request failed due to ' + status);
                    }
                }
            );
        }
    </script>
</head>

<body onload="initMap()">
<div id="map"></div>
</body>

</html>