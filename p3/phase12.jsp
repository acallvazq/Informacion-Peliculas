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
            <h2>Consulta 1: Fase 2(Año = ${fase12.year})</h2>
            <h3>Selecciona una pelicula: </h3>
            <form method="GET">
                <c:forEach var="movie" items="${fase12.peliculas}">
                    <input type="radio" name="pmovie" value="${movie.title}"> ${movie}<br>
                </c:forEach>
                <input type='hidden' name='p' value='${fase12.password}'>
                <input type='hidden' name='pyear' value='${fase12.year}'>
                <input type='hidden' name='pphase' value='13'>
                <input id='enviar' type="submit" value="Enviar">
            </form>
            <div class="botonera">
                <form method="GET">
                    <input type='hidden' name='p' value='${fase12.password}'>
                    <input type='hidden' name='pphase' value='11'>
                    <input class="atras" type="submit" value="Atras">
                </form>
                <form method="GET">
                    <input type='hidden' name='p' value='${fase12.password}'>
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
