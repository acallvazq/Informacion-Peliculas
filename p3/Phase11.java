package p3;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.TreeMap;
import org.w3c.dom.*;

public class Phase11{
    //Atributos
    private static TreeMap<String, Document> documentsXML = new TreeMap<>();
    private static ArrayList<String> years;
    private static String password;

    //Constructores
    public Phase11(){

    }

    //Getters y Setters
    public TreeMap<String, Document> getDocumentsXML(){
        return documentsXML;
    }

    public ArrayList<String> getYears(){
        years = new ArrayList<String>();

        for (String clave : documentsXML.keySet()) years.add(clave);
        Collections.sort(years, Collections.reverseOrder());
        return years;
    }

    public String getPassword(){
        return password;
    }

    public void setDocumentsXML(TreeMap<String, Document> documentsXML){
        this.documentsXML = documentsXML;
    }

    public void setYears(ArrayList<String> years){
        this.years = years;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
