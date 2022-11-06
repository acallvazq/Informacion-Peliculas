package p3;

import java.util.Collections;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.*;

public class Phase12{
    //Atributos
    private static TreeMap<String, Document> documentsXML = new TreeMap<>();
    private static ArrayList<Movie> peliculas;
    private static String password;
    private static String year;

    //Constructores
    public Phase12(){

    }

    //Getters y Setters
    public TreeMap<String, Document> getDocumentsXML(){
        return documentsXML;
    }

    public ArrayList<Movie> getPeliculas(){
        peliculas = new ArrayList<Movie>();
        Document documento = documentsXML.get(year);

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
        Collections.sort(peliculas, new Movie.SortMoviesXName());
        Collections.sort(peliculas, new Movie.SortMoviesXGenres());
        return peliculas;
    }

    public String getPassword(){
        return password;
    }

    public String getYear(){
        return year;
    }

    public void setDocumentsXML(TreeMap<String, Document> documentsXML){
        this.documentsXML = documentsXML;
    }

    public void setYear(String year){
        this.year = year;
    }

    public void setPeliculas(ArrayList<Movie> peliculas){
        this.peliculas = peliculas;
    }

    public void setPassword(String password){
        this.password = password;
    }

}
