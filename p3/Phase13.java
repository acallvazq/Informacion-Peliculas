package p3;

import java.util.Collections;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.*;

public class Phase13{
    //Atributos
    private static TreeMap<String, Document> documentsXML = new TreeMap<>();
    private static ArrayList<Cast> cast;
    private static ArrayList<Movie> peliculas;
    private static String password;
    private static String year, movie;

    //Constructores
    public Phase13(){

    }

    //Getters y Setters
    public TreeMap<String, Document> getDocumentsXML(){
        return documentsXML;
    }

    public ArrayList<Cast> getCast(){
        cast = new ArrayList<Cast>();
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
            Cast castt = new Cast(casts.item(i));
            cast.add(castt);
        }

        Collections.sort(cast, new Cast.SortCastXId());
        Collections.sort(cast, new Cast.SortCastXRole());
        return cast;
    }

    public String getPassword(){
        return password;
    }

    public String getYear(){
        return year;
    }

    public String getMovie(){
        return movie;
    }

    public void setDocumentsXML(TreeMap<String, Document> documentsXML){
        this.documentsXML = documentsXML;
    }

    public void setCast(ArrayList<Cast> cast){
        this.cast = cast;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setYear(String year){
        this.year = year;
    }

    public void setMovie(String movie){
        this.movie = movie;
    }
}
