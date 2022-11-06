<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
            <h2>Bienvenido a este servicio</h2>
            <p>La pandemia por el COVID-19 que surgió a finales del año 2019 ha afectado mundialmente a todo tipo de sectores, incluído la industria del cine. En consecuencia, muchas salas de cine acabaron cerrando, se cancelaron festivales y los estrenos se han retrasado.</p>
            <p>Aun así, en esta sección recopilamos las mejores peliculas hasta la fecha.</p>
            <p>Si quiere ver todo el contenido, por favor inicie una nueva consulta.</p>

            <form method="GET">
                <input type='hidden' name='p' value='${fase01.password}'>
                <input type='hidden' name='pphase' value='11'>
                <input class="button" type="submit" value="Iniciar Consulta">
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
