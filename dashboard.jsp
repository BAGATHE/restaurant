<%@ page import="java.io.*,java.util.*,gestion.*,produit.*,utilitaire.*,vivant.*" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%
double somme = 0;
RequestDataBase requete = new RequestDataBase();
String month = request.getParameter("month"); // Récupère le paramètre month de la requête 
String year = request.getParameter("year"); // Récupère le paramètre year de la requête 
try {  
    // je recupère list commande qui est fait pendant tout le mois 
    List<Commande> commandes = requete.getCommandeInAllMonth(month,year);
    List<Facture> facture = requete.getFactureCumul(month,year);
    for (int i = 0;  i < facture.size(); i++) {
            somme+= facture.get(i).getMontantTotal();
    }
        
        
     //creation JsonArray objet pour stocker les liste de commande
     JSONArray commandesArray = new JSONArray();
     for (Commande commande : commandes) {
         JSONObject commandeObj = new JSONObject();
         commandeObj.put("Menu",requete.getMenuById(commande.getIdMenu()).getNom());
         commandeObj.put("idFacture",commande.getIdFacture());
         commandeObj.put("prixUnitaire",commande.getPrixUnitaire());
         commandeObj.put("prixIntermediaire",commande.getPrixIntermediaire());
         commandeObj.put("prixReviens",commande.getPrixReviens());
         commandeObj.put("quantiter",commande.getQuantiter());
         commandeObj.put("dateCommande",commande.getDateCommande());
         commandeObj.put("idClient",commande.getIdClient());
         commandeObj.put("idIntermediaire",commande.getIdIntermediaire());
         commandesArray.add(commandeObj);
     }
     JSONArray intermediaireArray = new JSONArray();
     List<String> iddemarcheur = requete.getIntermediairework(month,year); // Obtient la liste des intermédiaires pour le mois  
    if (iddemarcheur != null) {
        for (String idinter : iddemarcheur) {
            String id = "";
            double quotaMin = 0;
            double quotaMax = 0;
            double quota = 0;
            double gain = 0;
            double forfait = 0;
            //je recupère les commande qui sont rattaché au intermediaire
            List<List<Object>> resultatcommande = requete.get_commandeby_Month_With_Intermediaire(month,year,idinter);
            
                int compteur = 0;
                id = resultatcommande.get(0).get(0).toString();
                quotaMin = Double.parseDouble(resultatcommande.get(0).get(5).toString());
                quotaMax = Double.parseDouble(resultatcommande.get(0).get(4).toString());

         //je boucle la liste de commande de l'intermediaire
            for (List<Object> row : resultatcommande) { 

                /////row.get(1) prixnormal * quantit get(2) prixinter * quantiter
                
               //tant que le quota de l'utilisateur est inferieur a sont quotaMax il encaisse
                if(quota < quotaMax && resultatcommande.size()+1 != compteur) {
                    quota += Double.parseDouble(row.get(1).toString());
                    forfait +=Double.parseDouble(row.get(1).toString());
                    //calcul de gain prixnormal*quantite - prixintermediaire * quantiter par produit
                    gain +=   Double.parseDouble(row.get(1).toString()) - Double.parseDouble(row.get(2).toString());
                    if(quota > quotaMax){
                        gain -=   (Double.parseDouble(row.get(1).toString())/2) - (Double.parseDouble(row.get(2).toString())/2);
                        forfait -= Double.parseDouble(row.get(1).toString())/2;
                        break;
                    }
                   
                }
             
            }
           if (quota < quotaMin) {  //si quota inferieur a quotamin  il ne recois pas de commission
                gain=0  ;
              }
             
            double cs = (double)requete.getCumulleCommandeIntermediaire(month,year,id);
            //on insert le valeur de chaque info dans un objet 
            JSONObject intermediaireObject = new JSONObject();
            intermediaireObject.put("idIntermediaire", id);
            intermediaireObject.put("quotaMin", quotaMin);
            intermediaireObject.put("quotaMax", quotaMax);
            intermediaireObject.put("quota", quota);
            intermediaireObject.put("forfait", forfait);
            intermediaireObject.put("gain", gain);
            intermediaireObject.put("cumul",cs);
            //on insert l'objet dans un tableau 
            intermediaireArray.add(intermediaireObject);
        }
        
    }
    
    JSONObject result = new JSONObject();
    result.put("commande",commandesArray);
    result.put("intermediaire",intermediaireArray);
    result.put("chiffreresto",somme);

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(result.toJSONString());
} catch (Exception e) {
    e.printStackTrace();
}
%>

