package p3;

import java.util.Comparator;
import org.w3c.dom.*;

public class Cast {
    //Atributos
    private String name;
    private String role;
    private String phone;
    private String email;
    private String id;

    //Metodos
    public Cast() {
        //Constructor vacio
    }

    public Cast(Node casting){
        Element cast = (Element) casting;
        NodeList info = cast.getChildNodes();

        this.id = cast.getAttribute("id");

        for(int i = 0; i < info.getLength(); i++){
            Node hijo = info.item(i);
            if(hijo.getNodeType() == Node.ELEMENT_NODE && hijo.getNodeName() == "Name") this.name = hijo.getTextContent();
            if(hijo.getNodeType() == Node.ELEMENT_NODE && hijo.getNodeName() == "Role") this.role = hijo.getTextContent();
            if(hijo.getNodeType() == Node.ELEMENT_NODE && hijo.getNodeName() == "Phone") this.phone = hijo.getTextContent();
            if(hijo.getNodeType() == Node.ELEMENT_NODE && hijo.getNodeName() == "Email") this.email = hijo.getTextContent();
         }
    }

    public String toString(){
        String retorno;
        retorno = "<b>Nombre: </b>"+name+" --- <b>ID: </b>"+id+" --- <b>Papel: </b>"+role;
        if(phone != null) retorno += " --- <b>Contacto: </b>"+phone;
        if(email != null) retorno += " --- <b>Contacto: </b>"+email;
        return retorno;
    }

    //Getters y Setters
    public String getName(){
        return name;
    }

    public String getRole(){
        return role;
    }

    public String getPhone(){
        return phone;
    }

    public String getEmail(){
        return email;
    }

    public String getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setRole(String role){
        this.role = role;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setId(String id){
        this.id = id;
    }

    //Clase interna para ordenar por tipo de rol
    public static class SortCastXRole implements Comparator<Cast> {
        public int compare(Cast una, Cast otra) {
            int resultado = 0;

            if(una.getRole().equals("Extra")) resultado = 1;
            if(otra.getRole().equals("Extra")) resultado = -1;

            if(una.getRole().equals("Supporting")) resultado = -1;
            if(otra.getRole().equals("Supporting")) resultado = 1;

            if(una.getRole().equals("Supporting") && otra.getRole().equals("Supporting")) resultado = 0;
            if(una.getRole().equals("Extra") && otra.getRole().equals("Extra")) resultado = 0;
            if(una.getRole().equals("Main") && otra.getRole().equals("Main")) resultado = 0;

            return resultado;
        }
    }

    //Clase interna para ordenar por id
    public static class SortCastXId implements Comparator<Cast> {
        public int compare(Cast una, Cast otra) {
            return una.getId().compareTo(otra.getId());
        }
    }

}
