<%@ page import = "p3.*, javax.servlet.http.*" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="./p3/mml.css" type="text/css">
        <meta name='author' content ='Alba Calleja Vazquez'>
        <title>Servicio de consultas de peliculas</title>
    </head>
    <body>
        <div id="cuerpo">
            <h1>SERVICIO DE CONSULTA DE PELICULAS</h1>
            <h2>Consulta 1: Fase 1</h2>
            <h3>Selecciona un año: </h3>
            <form method="GET">
                <c:forEach var="year" items="${fase11.years}">
                    <input type="radio" name="pyear" value="${year}"> ${year}<br>
                </c:forEach>
                <input type='hidden' name='p' value='${fase11.password}'>
                <input type='hidden' name='pphase' value='12'>
                <input id='enviar' type="submit" value="Enviar">
            </form>
            <form method="GET">
                <input type='hidden' name='p' value='${fase11.password}'>
                <input type='hidden' name='pphase' value='01'>
                <input class="inicio ini" type="submit" value="Inicio">
            </form>
        </div>
    </body>
    <footer>
        <div id="footer">
            <hr/><br>
            <p> Alba Calleja Vazquez</p>
            <p> Copyright &copy; 2021</p>
        </div>
    </footer>
</html>
