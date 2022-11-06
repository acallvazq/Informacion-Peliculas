package p2;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.net.URL;
import org.xml.sax.helpers.DefaultHandler;
import java.net.URLEncoder;
import java.net.URLDecoder;

public class FrontEnd {
    //Atributos
    private static final String footer = "</body><footer><hr/><br><p> Alba Calleja Vazquez</p><p> Copyright &copy; 2021</p></footer></html>";
    private static final String headerHTML = "<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"p2/mml.css\"><meta name='author' content ='Alba Calleja Vazquez'><meta charset ='UTF-8'><title>Servicio de consultas de peliculas</title></head>";
    private static final String headerXML = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    //Pantalla de las consultas HTML
    public static void sendHTMLF01(HttpServletResponse response, String p) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(headerHTML);

        out.println("<h1>SERVICIO DE CONSULTA DE PELICULAS</h1>"+
                    "<h2>Bienvenido a este servicio</h2>"+
                    "<p>La pandemia por el COVID-19 que surgió a finales del año 2019 ha afectado mundialmente a todo tipo de sectores, incluído la industria del cine. En consecuencia, muchas salas de cine acabaron cerrando, se cancelaron festivales y los estrenos se han retrasado.</p>"+
                    "<p>Aun así, en esta sección recopilamos las mejores peliculas hasta la fecha.</p>"+
                    "<p>Si quiere ver todo el contenido, por favor inicie una nueva consulta.</p>");
        out.println("<form method='GET' action='?pphase=11'>"+
                    "<input type='hidden' name='pphase' value='11'>"+
                    "<input type='hidden' name='p' value='"+p+"'>"+
                    "<input class=\"button\" type=\"submit\" value=\"Iniciar Consulta\">"+
                    "</form>");
        out.println(footer);
    }

    public static void sendHTMLF11(HttpServletResponse response, ArrayList<String> years, String p) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(headerHTML);

        out.println("<h1>SERVICIO DE CONSULTA DE PELICULAS</h1>"+
                    "<h2>Consulta 1: Fase 1</h2>"+
                    "<div id=\"contain\"><h3>Selecciona un año: </h3>");
        out.println("<form method='GET' id='idd' action='?pphase=12'>"+"<input type='hidden' name='pphase' value='12'>"+"<input type='hidden' name='p' value='"+p+"'>");

        for(int i = 0; i < years.size(); i++) out.println("<input type=\"radio\" name=\"pyear\" value="+years.get(i)+"> "+years.get(i)+"<br>");

        out.println("<input id='enviar' type=\"submit\" value=\"Enviar\">");
        out.println("</form>");
        out.println("</div><form method='GET' action='?pphase=01'>"+
                    "<input type='hidden' name='pphase' value='01'>"+
                    "<input type='hidden' name='p' value='"+p+"'>"+
                    "<input class=\"inicio ini\" type=\"submit\" value=\"Inicio\">"+
                    "</form>");
        out.println(footer);
    }

    public static void sendHTMLF12(HttpServletResponse response, String year, ArrayList<Movie> movies, String p) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String urlTitulo;
        out.println(headerHTML);

        out.println("<h1>SERVICIO DE CONSULTA DE PELICULAS</h1>"+
                    "<h2>Consulta 1: Fase 2 (Año = "+year+") </h2>"+
                    "<div id='informacion'>"+
                    "<h3>Selecciona una pelicula</h3><br>");
        out.println("<form method='GET' action='?pphase=13'>"+"<input type='hidden' name='pphase' value='13'>"+"<input type='hidden' name='pyear' value="+year+">"+"<input type='hidden' name='p' value='"+p+"'>");

        for(int i = 0; i < movies.size(); i++) {
            urlTitulo = (movies.get(i)).getTitle();
            urlTitulo = URLEncoder.encode(urlTitulo, "UTF-8");

            out.println("<input type=\"radio\" name=\"pmovie\" value="+urlTitulo+"> "+movies.get(i)+"<br>");
        }
        out.println("<input id='enviar' type=\"submit\" value=\"Enviar\">");
        out.println("</form></div>"+"<div class=\"botonera\">");
        //Boton atras
        out.println("<form method='GET' action='?pphase=11'>"+
                    "<input type='hidden' name='pphase' value='11'>"+
                    "<input type='hidden' name='p' value='"+p+"'>"+
                    "<input class=\"atras\" type=\"submit\" value=\"Atras\">"+
                    "</form>");
        //Boton inicio
        out.println("<form method='GET' action='?pphase=01'>"+
                    "<input type='hidden' name='pphase' value='01'>"+
                    "<input type='hidden' name='p' value='"+p+"'>"+
                    "<input class=\"inicio\" type=\"submit\" value=\"Inicio\">"+
                    "</form></div>");
        out.println(footer);
    }

    public static void sendHTMLF13(HttpServletResponse response, String year, String movie, ArrayList<Cast> casts, String p) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(headerHTML);

        out.println("<h1>SERVICIO DE CONSULTA DE PELICULAS</h1>"+
                    "<h2>Consulta 1: Fase 3 (Año = "+year+", Pelicula = "+movie+")</h2>"+
                    "<div id='informacion'>"+
                    "<h3>Este es el resultado de la consulta: </h3><br>");

        //Muestro en pantalla el resultado
        for(int i = 0; i < casts.size(); i++) out.println("<p>"+(i+1)+". "+casts.get(i)+"</p>");

        out.println("</form></div>"+"<div class=\"botonera\">");
        //Boton atras
        out.println("<form method='GET' action='?pphase=12'>"+
                    "<input type='hidden' name='pphase' value='12'>"+
                    "<input type='hidden' name='pyear' value='"+year+"'>"+
                    "<input type='hidden' name='p' value='"+p+"'>"+
                    "<input class=\"atras\" type=\"submit\" value=\"Atras\">"+
                    "</form>");
        //Boton inicio
        out.println("<form method='GET' action='?pphase=01'>"+
                    "<input type='hidden' name='pphase' value='01'>"+
                    "<input type='hidden' name='p' value='"+p+"'>"+
                    "<input class=\"inicio\" type=\"submit\" value=\"Inicio\">"+
                    "</form></div>");
        out.println(footer);
    }

    //Pantalla de las consultas XML
    public static void sendXMLF01(HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        out.println(headerXML);

        out.println("<service>");
        out.println("<status>OK</status>");
        out.println("</service>");
    }

    public static void sendXMLF11(HttpServletResponse response, ArrayList<String> Years) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        out.println(headerXML);

        out.println("<years>");
        for(int i = 0; i < Years.size(); i++) out.println("<year>"+Years.get(i)+"</year>");
        out.println("</years>");
    }

    public static void sendXMLF12(HttpServletResponse response, ArrayList<Movie> movies) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        String retorno;
        PrintWriter out = response.getWriter();
        out.println(headerXML);

        out.println("<movies>");
        for(int i = 0; i < movies.size(); i++) {
            retorno = "<movie langs='"+(movies.get(i)).getLangs()+"' genres='"+(movies.get(i)).getGenres();
            if((movies.get(i)).getDuration() != null) retorno += "' duration='"+(movies.get(i)).getDuration();
            if(!(movies.get(i)).getSinopsis().equals("")) retorno += "' synopsis='"+(movies.get(i)).getSinopsis();
            retorno += "'>"+(movies.get(i)).getTitle()+"</movie>";

            out.println(retorno);
        }
        out.println("</movies>");
    }

    public static void sendXMLF13(HttpServletResponse response, ArrayList<Cast> casts) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        String retorno;
        PrintWriter out = response.getWriter();
        out.println(headerXML);

        out.println("<thecast>");
        for(int i = 0; i < casts.size(); i++) {
          retorno = "<cast id='"+(casts.get(i)).getId()+"' role='"+(casts.get(i)).getRole();
          if((casts.get(i)).getPhone() != null) retorno += "' contact='"+(casts.get(i)).getPhone();
          if((casts.get(i)).getEmail() != null) retorno += "' contact='"+(casts.get(i)).getEmail();
          retorno += "'>"+(casts.get(i)).getName()+"</cast>";

          out.println(retorno);
        }
        out.println("</thecast>");
    }

    //Pantalla de los errores HTML
    public static void sendHTMLPerror(HttpServletResponse response, String p, String passwd) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(headerHTML);

        if(p == null) out.println("<body><h2>No se ha introducido una contraseña</h2>"+footer);
        if(!p.equals(passwd)) out.println("<body><h2>La contraseña no es correcta</h2>"+footer);
    }

    public static void sendHTMLYerror(HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(headerHTML);

        out.println("<body><h2>No se ha introducido el parámetro: pyear</h2>"+footer);
    }

    public static void sendHTMLMerror(HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(headerHTML);

        out.println("<body><h2>No se ha introducido el parámetro: pmovie</h2>"+footer);
    }

    //Pantalla de los errores XML
    public static void sendXMLPerror(HttpServletResponse response, String p, String passwd) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        out.println(headerXML);

        if(p == null) out.println("<wrongRequest>no passwd</wrongRequest>");
        if(!p.equals(passwd)) out.println("<wrongRequest>bad passwd</wrongRequest>");

    }

    public static void sendXMLYerror(HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        out.println(headerXML);

        out.println("<wrongRequest>no param:pyear</wrongRequest>");
    }

    public static void sendXMLMerror(HttpServletResponse response) throws IOException{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        out.println(headerXML);

        out.println("<wrongRequest>no param:pmovie</wrongRequest>");
    }
}
