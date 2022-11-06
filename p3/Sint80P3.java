package p3;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URLEncoder;
import java.net.URLDecoder;
import p3.Data;


public class Sint80P3 extends HttpServlet{
    private static final String passwd = "aBcDe12345";
    Data data;
    Phase01 fase01;
    Phase11 fase11 = new Phase11();
    Phase12 fase12;
    Phase13 fase13;

    public void init (ServletConfig config) throws ServletException {
        String schema;
        data = new Data();

        super.init(config);
        schema = config.getServletContext().getRealPath("p3/mml.xsd");
        data.setSchema(schema);
        data.iniciar();
    }

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        ServletContext sc = getServletContext();
        RequestDispatcher rd = null;
        String pphase = request.getParameter("pphase");
        String pwd = request.getParameter("p");

        //Comprueba que la password es correcta
        if(pwd == null) doGetFasePerror(request, response, sc, rd);
        else if(!pwd.equals(passwd)) doGetFasePerror(request, response, sc, rd);
        else{
            //Elegir fase de consulta
            if(pphase == null || pphase.equals("01")) doGetFase01(request, response, sc, rd);
            if(pphase.equals("11")) doGetFase11(request, response, sc, rd);
            if(pphase.equals("12")){
                if(request.getParameter("pyear") == null) doGetFaseError(request,response, sc, rd);
                else doGetFase12(request, response, sc, rd);
            }
            if(pphase.equals("13")){
                if(request.getParameter("pyear") == null) doGetFaseError(request,response, sc, rd);
                else if(request.getParameter("pmovie") == null) doGetFaseError(request,response, sc, rd);
                else doGetFase13(request,response, sc, rd);
            }
        }
    }

    //Pantalla de errores
    private void doGetFasePerror(HttpServletRequest request, HttpServletResponse response, ServletContext sc, RequestDispatcher rd) throws IOException{
        String pwd = request.getParameter("p");
        response.setCharacterEncoding("UTF-8");

        try{
            if(pwd == null) rd = sc.getRequestDispatcher("/p3/sinP.jsp");
            else if(!pwd.equals(passwd)) rd = sc.getRequestDispatcher("/p3/errorP.jsp");
            rd.forward(request,response);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void doGetFaseError(HttpServletRequest request, HttpServletResponse response, ServletContext sc, RequestDispatcher rd) throws IOException{
        String pyear = request.getParameter("pyear");
        String pmovie = request.getParameter("pmovie");
        response.setCharacterEncoding("UTF-8");

        try{
            if(pyear == null) rd = sc.getRequestDispatcher("/p3/errorYear.jsp");
            else if(pmovie == null) rd = sc.getRequestDispatcher("/p3/errorMovie.jsp");
            rd.forward(request,response);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    //Pantalla de consultas
    private void doGetFase01(HttpServletRequest request, HttpServletResponse response, ServletContext sc, RequestDispatcher rd) throws IOException{
        fase01 = new Phase01();
        fase01.setPassword(request.getParameter("p"));
        request.setAttribute("fase01", fase01);
        response.setCharacterEncoding("UTF-8");

        try{
            rd = sc.getRequestDispatcher("/p3/phase01.jsp");
            rd.forward(request,response);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void doGetFase11(HttpServletRequest request, HttpServletResponse response, ServletContext sc, RequestDispatcher rd) throws IOException{
        fase11.setDocumentsXML(data.getDocumentsXML());
        fase11.setPassword(request.getParameter("p"));
        request.setAttribute("fase11", fase11);
        response.setCharacterEncoding("UTF-8");
        ServletContext contexto = getServletContext();
        rd = contexto.getRequestDispatcher("/p3/phase11.jsp");
        try{
            rd.forward(request,response);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void doGetFase12(HttpServletRequest request, HttpServletResponse response, ServletContext sc, RequestDispatcher rd) throws IOException{
        String pyear = request.getParameter("pyear");
        fase12 = new Phase12();

        fase12.setDocumentsXML(data.getDocumentsXML());
        fase12.setPassword(request.getParameter("p"));
        fase12.setYear(pyear);
        request.setAttribute("fase12", fase12);
        response.setCharacterEncoding("UTF-8");
        try{
            rd = sc.getRequestDispatcher("/p3/phase12.jsp");
            rd.forward(request,response);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void doGetFase13(HttpServletRequest request, HttpServletResponse response, ServletContext sc, RequestDispatcher rd) throws IOException{
        String pyear = request.getParameter("pyear");
        String pmovie = request.getParameter("pmovie");
        fase13 = new Phase13();

        pmovie = URLDecoder.decode(pmovie, "UTF-8");
        fase13.setPassword(request.getParameter("p"));
        fase13.setDocumentsXML(data.getDocumentsXML());
        fase13.setYear(pyear);
        fase13.setMovie(pmovie);
        request.setAttribute("fase13", fase13);
        response.setCharacterEncoding("UTF-8");
        try{
            rd = sc.getRequestDispatcher("/p3/phase13.jsp");
            rd.forward(request,response);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
