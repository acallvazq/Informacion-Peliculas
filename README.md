# Servicio de recomendación de peliculas
Practica 2 de la asignatura Servicios de Internet del grado de Teleco (Uvigo). Consiste en un servicio web que permite obtener información de un conjunto de peliculas ordenados por año. 
Gracias a este servicio, el usuario puede seleccionar un año y obtener información detallada de las películas que se estrenaron en dicha temporada.

# Funcionamiento
Se ha desarrollado un servlet para Apache Tomcat, donde se almacena la información en ficheros MML, un lneguaje derivado del XML definido en el archivo 'mml.xsd'. Una vez iniciado el servlet, la aplicación irá descubriendo recursivamente nuevos archivos mml, de los cuales obtendrá toda la información que mostrará en pantalla.
Una vez obtenida toda la información, la aplicación podrá resolver todas las consultas de los usuarios a través de una interfaz web o a través de respuestas XML.

# Compilación 
javac -classpath ~/apache-tomcat/lib/servlet-api.jar Cast.java DataModel.java FrontEnd.java Movie.java Sint80P2.java

Si nos situamos en el directorio ~/public_html/webapps/WEB-INF/classes/p2: javac -classpath ~/apache-tomcat/lib/servlet-api.jar *.java

# Utilización
Una vez compilado, debemos acceder al directorio de Tomcat y ejecutar el servidor:
 cd ~/apache_tomcat/bin
 ./startup.sh   -- Ejecutar el servidor Tomcat
 ./shutdown.sh  -- Apagar el servidor

A partir de aqui, abrimos nuestro navegador web (Firefox) y accederemos al siguiente enlace (definido previamente en los ficheros fuente de Tomcat): localhost:7080/alba/P2M
Si queremos acceder a la pagina deberemos usar una contraseña "pwd": '/P2M?p=pwd'. 
Si deseamos obtener las respuestas en XML, añadiremos el parámetro '?auto=si' al final del enlace URL. 
Para desplegar el servicio en Tomcat, se puede utilizar el archivo 'web.xml'.

