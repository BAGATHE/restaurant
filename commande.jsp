<%@ page import="java.io.*,java.util.*, gestion.*, produit.*, utilitaire.*, vivant.*" %>
<%@ page import="org.json.JSONArray, org.json.JSONObject" %>

<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%
String resp ="";
RequestDataBase requette = new RequestDataBase();
String choixParam = request.getParameter("choix");                  // Récupère le paramètre "choix" de la requête
JSONArray tabchoice = new JSONArray(choixParam);                     //Convertit la chaîne JSON en tableau d'objets
JSONObject firstChoice = tabchoice.getJSONObject(0);                  //je traite le premier elemennt qui contient type de choix

try{

requette.insertClient("postgres");                                               //--insertion client  
String idClient = requette.getLastIdINtable("postgres","idClient","Client");     //on recupere l'id recement inserer;

Menu menu = null;
Commande commande= null;
       
   
      if(firstChoice.getInt("choix")==0){                                 //payement sans intermediaire
        double somme = Double.parseDouble(firstChoice.getString("sommetotal"));
        Facture facture = new Facture(idClient,"",firstChoice.getString("date"),somme);      //on creer une facture ratacher au client             
        requette.insert(facture,"postgres");                                             //on insert la facture
        String idfacture = requette.getLastIdINtable("postgres","idfacture","Facture");   //je recupere l'idfacture recement inserer

        //Parcours le tableau d'objets
        for (int i = 1; i < tabchoice.length(); i++) {
        JSONObject choice = tabchoice.getJSONObject(i);
        String idmenu = choice.getString("idmenu");
        menu = requette.getMenuById(idmenu);    //recupere les info du menu a l aide id
        commande = new Commande(menu.getIdMenu(),idfacture,menu.getPrixUnitaire(),menu.getPrixIntermediaire(),menu.getPrixReviens(),Integer.parseInt(choice.getString("quantiter")),choice.getString("date"),idClient,"");
        requette.insert(commande,"postgres");     // on insert dans la base; 
     }
     resp ="success";

       }else if(firstChoice.getInt("choix")==1){

        String cin = firstChoice.getString("cin");
        String mdp =  firstChoice.getString("password");
        Intermediaire intermediaire = requette.getIntermediaire(cin,mdp);

        if(intermediaire!=null){
          resp ="success";
        double somme = Double.parseDouble(firstChoice.getString("sommetotal"));
        Facture facture = new Facture(idClient,intermediaire.getIdIntermediaire(),firstChoice.getString("date"),somme );      //on creer une facture ratacher au client             
        requette.insert(facture,"postgres");                                             //on insert la facture
        String idfacture = requette.getLastIdINtable("postgres","idfacture","Facture");   //je recupere l'idfacture recement inserer



          //Parcours le tableau d'objets
          for (int i = 1; i < tabchoice.length(); i++) {
          JSONObject choice = tabchoice.getJSONObject(i);
          String idmenu = choice.getString("idmenu");
          menu = requette.getMenuById(idmenu);    //recupere les info du menu a l aide id
          commande = new Commande(menu.getIdMenu(),idfacture,menu.getPrixUnitaire(),menu.getPrixIntermediaire(),menu.getPrixReviens(),Integer.parseInt(choice.getString("quantiter")),choice.getString("date"),idClient,intermediaire.getIdIntermediaire());
          requette.insert(commande,"postgres");     // on insert dans la base; 
       }
           }else{
            resp="failed";
           }
       }
   
       response.setContentType("application/json");
       response.setCharacterEncoding("UTF-8");
       response.getWriter().write(resp);
}catch(Exception e){
  e.printStackTrace();
}
    

  


%>
