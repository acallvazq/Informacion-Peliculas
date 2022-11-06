package p3;

import java.io.*;
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

public class Data{
    //Atributos
    private static String directorio = "http://alberto.gil.webs.uvigo.es/SINT/21-22/";
    private static ArrayList<String> ficherosXML = new ArrayList<String>();
    private static TreeMap<String, Document> documentsXML = new TreeMap<>();
    private static String schema = "";
    private static String ficheroInit = "mml2001.xml";
    private static URL fullDir = null;
    private static boolean booleano;
    private static Document documento;

    //Constructores
    public Data(){

    }

    //Metodos
    protected void iniciar(){
        String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
        String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
        String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
        ArrayList<String> filesInvalid = new ArrayList<String>();
        Set<String> elimDuplicados;
        URL direccion = null;
        ficherosXML.add(ficheroInit);

        //Crea el parser
        DocumentBuilderFactory dbf;
        dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        dbf.setNamespaceAware(true);
        dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
        File fileSchema = new File(schema);
        dbf.setAttribute(JAXP_SCHEMA_SOURCE, fileSchema);
        DocumentBuilder db = null;
        documento = null;

        try {
            direccion = new URL(directorio);
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new MML_ErrorHandler());

            for(int i = 0; i < ficherosXML.size(); i++) {
                fullDir = new URL(direccion,ficherosXML.get(i));
                if(verificaFichero(ficherosXML.get(i), db)){
                    //Agrego fichero erroneo al ArrayList filesInvalid
                    filesInvalid.add(ficherosXML.get(i));
                    ficherosXML.remove(i);
                    //Reordeno el ArrayList que contiene los ficheros XML
                    elimDuplicados = new LinkedHashSet<>(ficherosXML);
                    ficherosXML.clear();
                    ficherosXML.addAll(elimDuplicados);
                    i--;
                    continue;
                }
                leeFichero(ficherosXML.get(i));
                //Elimino ficheros duplicados del ArrayList
                elimDuplicados = new LinkedHashSet<>(ficherosXML);
                ficherosXML.clear();
                ficherosXML.addAll(elimDuplicados);
            }
        }catch(Exception e) {
            System.out.println(e);
        }
        Collections.sort(ficherosXML);
        Collections.sort(filesInvalid);
    }

    private static boolean verificaFichero(String fichero, DocumentBuilder db){
        booleano = false;

        try {
            documento = db.parse(fullDir.openConnection().getInputStream());

        }catch (SAXException | IOException e){
            booleano = true;
        }
        return booleano;
    }

    private static void leeFichero(String fichero){
        NodeList peliculas = documento.getElementsByTagName("Movie");

        //Leo la etiqueta Year y lo guardo en un ArrayList
        NodeList todo = documento.getElementsByTagName("Movies");
        Node all = todo.item(0);
        Element eall = (Element) all;
        NodeList everybody = eall.getChildNodes();
        Node year = everybody.item(1);

        //Agrego al treemap el documento con su anho
        documentsXML.put(year.getTextContent(), documento);

        //Busco todos los ficheros que se mencionan en los ficheros encontrados
        for(int i = 0; i < peliculas.getLength(); i++){
            Node nodo = peliculas.item(i);

            if(nodo.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element) nodo;
                NodeList hijos = e.getChildNodes();

                for(int j = 0; j < hijos.getLength(); j++){
                    Node hijo = hijos.item(j);
                    if(hijo.getNodeType() == Node.ELEMENT_NODE && hijo.getNodeName() == "Cast"){
                        Element eHijo = (Element) hijo;
                        NodeList nietos = eHijo.getChildNodes();

                        for(int k = 0; k < nietos.getLength(); k++){
                            Node nieto = nietos.item(k);
                            if(nieto.getNodeType() == Node.ELEMENT_NODE && nieto.getNodeName() == "MML") ficherosXML.add(nieto.getTextContent());
                        }
                    }
                }
            }
        }
    }


    //Getters y Setters
    public String getDirectorio(){
        return directorio;
    }

    public String getSchema(){
        return schema;
    }

    public ArrayList<String> getFicherosXML(){
        return ficherosXML;
    }

    public TreeMap<String,Document> getDocumentsXML(){
        return documentsXML;
    }

    public String getFicheroInit(){
        return ficheroInit;
    }

    public URL getFullDir(){
        return fullDir;
    }

    public boolean getBooleano(){
        return booleano;
    }

    public Document getDocumento(){
        return documento;
    }

    public void setDirectorio(String directorio){
        this.directorio = directorio;
    }

    public void setSchema(String schema){
        this.schema = schema;
    }

    public void setFicherosXML(ArrayList<String> ficherosXML){
        this.ficherosXML = ficherosXML;
    }

    public void setDocumentsXML(TreeMap<String,Document> documentsXML){
        this.documentsXML = documentsXML;
    }

    public void setFicheroInit(String ficheroInit){
        this.ficheroInit = ficheroInit;
    }

    public void setFullDir(URL fullDir){
        this.fullDir = fullDir;
    }

    public void setBooleano(boolean booleano){
        this.booleano = booleano;
    }

    public void setDocumento(Document documento){
        this.documento = documento;
    }

    //Clase interna
    public static class MML_ErrorHandler extends DefaultHandler {
        public MML_ErrorHandler(){

        }

        public void warning(SAXParseException spe){
            booleano = true;
        }

        public void error(SAXParseException spe){
            booleano = true;
        }

        public void fatalerror(SAXParseException spe){
            booleano = true;
        }
    }
}
