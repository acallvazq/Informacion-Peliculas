package p3;

import java.util.ArrayList;
import java.util.Comparator;
import java.lang.StringBuilder;
import org.w3c.dom.*;

public class Movie {
    //Atributos
    private String title;
    private String langs;
    private String sinopsis;
    private String duration;
    private ArrayList<String> genres = new ArrayList<String>();

    //Constructores
    public Movie(){
        //Contructor vacio
    }

    public Movie(Node films){
        Element film = (Element) films;
        NodeList info = film.getChildNodes();
        this.langs = film.getAttribute("langs");
        sinopsis = "";

        for(int i = 0; i < info.getLength(); i++){
            Node hijo = info.item(i);
            if(hijo.getNodeType() == Node.ELEMENT_NODE && hijo.getNodeName() == "Title") this.title = hijo.getTextContent();
            if(hijo.getNodeType() == Node.ELEMENT_NODE && hijo.getNodeName() == "Duration") this.duration = hijo.getTextContent();
            if(hijo.getNodeType() == Node.TEXT_NODE) sinopsis += hijo.getNodeValue().trim();
         }

         //Obtener todos los generos de la pelicula
         NodeList generos = film.getElementsByTagName("Genre");
         for (int i = 0; i < generos.getLength(); i++) {
				     Node nodo = generos.item(i);
				     if (nodo.getNodeType() == Node.ELEMENT_NODE) genres.add(((Element) nodo).getTextContent());
			   }
    }

    //toString
    public String toString(){
        String retorno;
        String generos = "";

        for (String elemento: genres) generos += elemento + ",";
        //Borrar la ultima ", "
        generos = generos.substring(0, generos.length() - 1);

        retorno = "<b>Titulo: </b>"+title+" --- <b>Idiomas: </b>"+langs+" --- <b>Generos: </b>"+generos;
        if(duration != null) retorno += " --- <b>Duracion: </b>"+duration;
        if(!sinopsis.equals(""))retorno += " --- <b>Sinopsis: </b>"+sinopsis;
        return retorno;
    }

    //Getters y Setters
    public String getTitle(){
        return title;
    }

    public String getLangs(){
       return langs;
    }

    public String getSinopsis(){
        return sinopsis;
    }

    public String getDuration(){
        return duration;
    }

    public ArrayList<String> getArrayGenre(){
        return genres;
    }

    public String getGenres(){
        String generos = "";

        for (String elemento: genres) generos += elemento + ",";
        //Borrar la ultima ", "
        generos = generos.substring(0, generos.length() - 1);
        return generos;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setLangs(String langs){
        this.langs = langs;
    }

    public void setSinopsis(String sinopsis){
        this.sinopsis = sinopsis;
    }

    public void setDuration(String duration){
        this.duration = duration;
    }

    public void setGenres(ArrayList<String> genres){
        this.genres = genres;
    }

    //Clase interna para ordenar por cantidad de generos
    public static class SortMoviesXGenres implements Comparator<Movie> {
        public int compare(Movie una, Movie otra) {
            int resultado = 0;
            if(una.getArrayGenre().size() < otra.getArrayGenre().size()) resultado = 1;
            if(una.getArrayGenre().size() > otra.getArrayGenre().size()) resultado = -1;
            return resultado;
        }
    }

    //Clase interna para ordenar por nombre
    public static class SortMoviesXName implements Comparator<Movie> {
        public int compare(Movie una, Movie otra) {
            return una.getTitle().compareTo(otra.getTitle());
        }
    }
}
