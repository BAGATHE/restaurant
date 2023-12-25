<%@ page language="java" contentType="application/json; charset=UTF-8"%>
<%@ page import="java.util.*, java.lang.*" %>
<%@ page import="org.json.simple.JSONArray" %>
<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="utilitaire.*, produit.* , vivant.* , gestion.*" %>

<%
try {
    RequestDataBase requestObj = new RequestDataBase();
    // Appel des fonctions  je recuperer les liste categorie de menu 
    List<Categorie> categories = requestObj.getAllCategorie();
    // Création de l'objet JSON
    JSONObject jsonResponse = new JSONObject();
    
    // Ajout des catégories à l'objet JSON 
    JSONArray categoriesArray = new JSONArray();
    for (Categorie category : categories) {
        JSONObject catObj = new JSONObject();

        catObj.put("id", category.getIdCategorie());
        catObj.put("nom", category.getNomCategorie());
     
    // je recupère la liste des menus par categorie    
        List<Menu> menus = requestObj.getMenuByIdCategorie(category.getIdCategorie());


    JSONArray menusArray = new JSONArray();  //qui va stocker les liste de menu au forma json;
        
    //je bouce la liste de menu je l'insert dans jsonObjet avant de le stocker dans un tableau Json    
        for (Menu menu : menus) {
            JSONObject menuObj = new JSONObject();
            menuObj.put("idMenu", menu.getIdMenu());
            menuObj.put("nom", menu.getNom());
            menuObj.put("prixUnitaire", menu.getPrixUnitaire());
            menuObj.put("prixIntermediaire", menu.getPrixIntermediaire());
            menuObj.put("prixReviens", menu.getPrixReviens());
            menuObj.put("idCategorie", menu.getIdCategorie());
            menusArray.add(menuObj);
        }
        catObj.put("menu", menusArray);  
        categoriesArray.add(catObj);
    }
    jsonResponse.put("categories", categoriesArray);

    // Envoi de la réponse JSON
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonResponse.toJSONString());
} catch (Exception e) {
    e.printStackTrace();
}
%>
