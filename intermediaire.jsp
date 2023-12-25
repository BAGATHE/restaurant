<%@ page language="java" contentType="application/json; charset=UTF-8"%>
<%@ page import="java.util.*, java.lang.*" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="utilitaire.*, produit.* , vivant.* , gestion.*" %>

<%
RequestDataBase requete = new RequestDataBase();
String month = request.getParameter("month"); // Récupère le paramètre month de la requête 
String year = request.getParameter("year"); // Récupère le paramètre month de la requête 
try {
   
    JSONArray intermediaireArray = new JSONArray();
    List<String> iddemarcheur = requete.getIntermediairework(month,year); // Obtient la liste des intermédiaires pour le mois

    if (iddemarcheur != null) {
      
        for (String idinter : iddemarcheur) {
              JSONArray commandesArray = new JSONArray();
            List<Commande> commandeRatacher = requete.getCommandeInAllMonthIntermediaire(month,year,idinter);
            for (Commande com : commandeRatacher) {
                JSONObject commandeObj = new JSONObject();
                commandeObj.put("Menu",requete.getMenuById(com.getIdMenu()).getNom());
                commandeObj.put("idFacture",com.getIdFacture());
                commandeObj.put("prixUnitaire",com.getPrixUnitaire());
                commandeObj.put("prixIntermediaire",com.getPrixIntermediaire());
                commandeObj.put("prixReviens",com.getPrixReviens());
                commandeObj.put("quantiter",com.getQuantiter());
                commandeObj.put("dateCommande",com.getDateCommande());
                commandeObj.put("idClient",com.getIdClient());
                commandeObj.put("idIntermediaire",com.getIdIntermediaire());
                commandesArray.add(commandeObj);    
            }
            intermediaireArray.add(commandesArray);
        }
    }


    // Envoi de la réponse JSON
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(intermediaireArray.toJSONString());
} catch (Exception e) {
    e.printStackTrace();
}
%>
