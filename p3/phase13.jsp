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
            <h2>Consulta 1: Fase 3(Año = ${fase13.year}, Pelicula = ${fase13.movie})</h2>
            <h3>Este es el resultado de la consulta: </h3>
            <c:forEach var="cast" items="${fase13.cast}">
                <p>${cast}<p>
            </c:forEach>
            <div class="botonera">
                <form method="GET">
                    <input type='hidden' name='p' value='${fase13.password}'>
                    <input type='hidden' name='pphase' value='12'>
                    <input type='hidden' name='pyear' value="${fase13.year}">
                    <input class="atras" type="submit" value="Atras">
                </form>
                <form method="GET">
                    <input type='hidden' name='p' value='${fase13.password}'>
                    <input type='hidden' name='pphase' value='01'>
                    <input class="inicio" type="submit" value="Inicio">
                </form>
            </div>
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
