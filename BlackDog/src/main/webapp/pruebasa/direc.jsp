<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Calculadora de Distancias</title>
</head>
<body>
<h1>Calculadora de Distancias</h1>
<form action="CalculateDistance" method="post">
    <label for="origin">Dirección de Origen:</label>
    <input type="text" id="origin" name="origin" required><br><br>
    <label for="destination">Dirección de Destino:</label>
    <input type="text" id="destination" name="destination" required><br><br>
    <input type="submit" value="Calcular Distancia">
</form>
</body>
</html>
