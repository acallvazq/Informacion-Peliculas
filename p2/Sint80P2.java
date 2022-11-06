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

public class Sint80P2 extends HttpServlet {
    //Atributos
    private static String directorio = "http://alberto.gil.webs.uvigo.es/SINT/21-22/";
    private static URL direccion, fullDir = null;
    private static ArrayList<String> ficherosXML = new ArrayList<String>();
    private static ArrayList<String> filesInvalid = new ArrayList<String>();
    private static TreeMap<String, Document> documentsXML = new TreeMap<>();
    private static final String passwd = "aBcDe12345";
    private static Set<String> elimDuplicados;
    private static String schema = "";
    private static boolean booleano;
    private static DataModel data = new DataModel();
    private static Document documento;

    //Metodos
    public void init (ServletConfig config) throws ServletException {
        schema = config.getServletContext().getRealPath("p2/mml.xsd");
        String fichero_init = "mml2001.xml";
        ficherosXML.add(fichero_init);

        try {
            direccion = new URL(directorio);

            for(int i = 0; i < ficherosXML.size(); i++) {
                fullDir = new URL(direccion,ficherosXML.get(i));
                if(verificaFichero(ficherosXML.get(i))){
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
        
        data.setDocumentsXML(documentsXML);

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        try {
            String pphase = req.getParameter("pphase");
            String pwd = req.getParameter("p");

            //Comprueba que la password es correcta
            if(pwd == null) doGetFasePerror(req,res);
            else if(!pwd.equals(passwd)) doGetFasePerror(req,res);
            else {
                //Elegir fase de consulta
                if(pphase == null || pphase.equals("01")) doGetFase01(req,res);
                if(pphase.equals("11")) doGetFase11(req,res);
                if(pphase.equals("12")) {
                    if(req.getParameter("pyear") == null) doGetFaseError(req,res);
                    else doGetFase12(req,res);
                }
                if(pphase.equals("13")) {
                    if(req.getParameter("pyear") == null) doGetFaseError(req,res);
                    else if(req.getParameter("pmovie") == null) doGetFaseError(req,res);
                    else doGetFase13(req,res);
                }
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }

    //Pantalla de errores
    private void doGetFasePerror(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String p = request.getParameter("p");
        String auto = request.getParameter("auto");

        if(auto == null || (!auto.equals("true"))) FrontEnd.sendHTMLPerror(response, p, passwd);
        else FrontEnd.sendXMLPerror(response, p, passwd);
    }

    private void doGetFaseError(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String pyear = request.getParameter("pyear");
        String pmovie = request.getParameter("pmovie");
        String auto = request.getParameter("auto");

        if(auto == null || (!auto.equals("true"))){
            if(pyear == null) FrontEnd.sendHTMLYerror(response);
            else if(pmovie == null) FrontEnd.sendHTMLMerror(response);

        }else{
            if(pyear == null) FrontEnd.sendXMLYerror(response);
            else if(pmovie == null) FrontEnd.sendXMLMerror(response);
        }
    }

    //Pantalla de las consultas
    private void doGetFase01(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String p = request.getParameter("p");
        String auto = request.getParameter("auto");

        if(auto == null || (!auto.equals("true"))) FrontEnd.sendHTMLF01(response, p);
        else FrontEnd.sendXMLF01(response);
    }

    private void doGetFase11(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList<String> Years = new ArrayList<String>();
        String auto = request.getParameter("auto");
        String p = request.getParameter("p");
        Years = data.getQ1Years();
        Collections.sort(Years,Collections.reverseOrder());

        if(auto == null || (!auto.equals("true"))) FrontEnd.sendHTMLF11(response, Years, p);
        else FrontEnd.sendXMLF11(response, Years);
    }

    private void doGetFase12(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList<Movie> peliculas = new ArrayList<Movie>();
        String p = request.getParameter("p");
        String auto = request.getParameter("auto");
        String year = request.getParameter("pyear");

        peliculas = data.getQ1Movies(year);
        Collections.sort(peliculas, new Movie.SortMoviesXName());
        Collections.sort(peliculas, new Movie.SortMoviesXGenres());

        if(auto == null || (!auto.equals("true"))) FrontEnd.sendHTMLF12(response,year, peliculas, p);
        else FrontEnd.sendXMLF12(response, peliculas);
    }

    private void doGetFase13(HttpServletRequest request, HttpServletResponse response) throws IOException{
        ArrayList<Cast> casting = new ArrayList<Cast>();
        String p = request.getParameter("p");
        String auto = request.getParameter("auto");
        String year = request.getParameter("pyear");
        String movie = request.getParameter("pmovie");

        movie = URLDecoder.decode(movie, "UTF-8");
        casting = data.getQ1Cast(year, movie);

        Collections.sort(casting, new Cast.SortCastXId());
        Collections.sort(casting, new Cast.SortCastXRole());

        if(auto == null || (!auto.equals("true"))) FrontEnd.sendHTMLF13(response, year, movie, casting, p);
        else FrontEnd.sendXMLF13(response, casting);
    }

    //Metodos de verificacion
    private static boolean verificaFichero(String fichero){
        String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
        String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
        String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
        booleano = false;

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
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new MML_ErrorHandler());
            documento = db.parse(fullDir.openConnection().getInputStream());

        }catch (ParserConfigurationException | SAXException | IOException e){
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
