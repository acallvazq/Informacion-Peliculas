package p2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import java.net.URL;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpressionException;

public class DataModel {
    //Atributos
    private static TreeMap<String, Document> documentsXML = new TreeMap<>();

    //Metodo para obtener un ArrayList con los anhos
    public ArrayList<String> getQ1Years(){
        ArrayList<String> Years = new ArrayList<String>();
        for (String clave : documentsXML.keySet()) Years.add(clave);
        Collections.sort(Years, Collections.reverseOrder());
        return Years;
    }

    //Metodo que obtienes los cast de una pelicula
    public ArrayList<Cast> getQ1Cast(String year, String movie){
        ArrayList<Cast> listCast = new ArrayList<Cast>();
        Document documento = documentsXML.get(year);

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        NodeList casts = null;

        try {
            casts = (NodeList) xpath.evaluate("/Movies/Movie[Title='"+movie+"']/Cast", documento, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < casts.getLength(); i++) {
            Cast cast = new Cast(casts.item(i));
            listCast.add(cast);
        }

        return listCast;
    }

    //Metodo que devuelve las peliculas de un determinado anho
    public ArrayList<Movie> getQ1Movies(String Year){
        ArrayList<Movie> peliculas = new ArrayList<Movie>();
        Document documento = documentsXML.get(Year);

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        NodeList movies = null;

        try {
            movies = (NodeList) xpath.evaluate("/Movies/Movie", documento, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < movies.getLength(); i++) {
            Movie movie = new Movie(movies.item(i));
            peliculas.add(movie);
        }
        //Collections.sort(peliculas);
        return peliculas;
    }

    //Getters y Setters
    public void setDocumentsXML(TreeMap<String, Document> documentsXML){
        this.documentsXML = documentsXML;
    }
}
