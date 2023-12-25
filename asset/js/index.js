//fonction qui va afficher les choix dans le modalapres avoir fait une facture
function displaychoice(){
  var checkboxElements = form.querySelectorAll('input[type="checkbox"]');
  var tbody = document.querySelector("#corptab");
  tbody.innerHTML="";
  var total = 0;
//je boucle le tableau de checkbox et je prend 
  checkboxElements.forEach(checkbox => {
        if (checkbox.checked) {
          var tr = document.createElement("tr");
          var menu = document.createElement("td");
          var quantiter = document.createElement("td");
          var somme = document.createElement("td");
          menu.textContent=document.getElementById(checkbox.value+"-nom").textContent; 
          quantiter.textContent = document.querySelector(`input[name="${checkbox.value}"]`).value;
          somme = parseFloat(document.getElementById(checkbox.value+"-prix").textContent) * parseFloat(document.querySelector(`input[name="${checkbox.value}"]`).value);
          total +=somme;
          tr.appendChild(menu);
          tr.appendChild(quantiter); 
          tr.append(somme);
          tbody.append(tr);    
        }
    });

    var tr = document.createElement("tr");
    var th = document.createElement("th");  th.scope="row";th.colSpan="2";th.className="table-info"; th.textContent="somme total a payer";
    var td = document.createElement("td");   td.colSpan="2"; td.className="table-info"; td.textContent=total; td.id="sumtotal";
    tr.append(th); tr.append(td);
    tbody.append(tr);   
}

//evenement qui ecoute le choix de payement si avec intermediaire ou pas
var categorie = document.querySelector('#payement');
categorie.addEventListener("change", () => {
  var select = document.querySelector("#log");
    var val = categorie.value;
    if (val==1) {
        select.innerHTML="";
        displayLoginForm();
    }else{
    select.innerHTML="";
    }
  });

//fonction qui affiche une formulaire de login(cin,mdp)
function displayLoginForm(){
    var row = document.createElement("div");
    row.classList.add("row");
    // Champ de texte
    var colText = document.createElement("div");
    colText.classList.add("col-md-6");
    var textDiv = document.createElement("div");
    textDiv.classList.add("mb-3");
    var labelText = document.createElement("label");
    labelText.setAttribute("for", "exampleText");
    labelText.classList.add("form-label");
    labelText.textContent = "Numeros Cin De L'intermediaire:";

    var inputText = document.createElement("input");
    inputText.setAttribute("type", "text");
    inputText.classList.add("form-control");
    inputText.classList.add("info-intermediaire");
    inputText.setAttribute("id", "exampleText");
    inputText.name="cin";
  

    textDiv.appendChild(labelText);
    textDiv.appendChild(inputText);
    colText.appendChild(textDiv);

    // Champ de mot de passe
    var colPassword = document.createElement("div");
    colPassword.classList.add("col-md-6");
    var passwordDiv = document.createElement("div");
    passwordDiv.classList.add("mb-3")
    var labelPassword = document.createElement("label");
    labelPassword.setAttribute("for", "examplePassword");
    labelPassword.classList.add("form-label");
    labelPassword.textContent = "Mot de passe :";

    var inputPassword = document.createElement("input");
    inputPassword.setAttribute("type", "password");
    inputPassword.classList.add("form-control");
    inputPassword.classList.add("info-intermediaire");
    inputPassword.setAttribute("id", "examplePassword");
    inputPassword.name="password";

    passwordDiv.appendChild(labelPassword);
    passwordDiv.appendChild(inputPassword);
    colPassword.appendChild(passwordDiv);

    row.appendChild(colText);
    row.appendChild(colPassword);
    
    var select = document.querySelector("#log");
    select.append(row);
};

//fonction qui affiche les menu par categorie il prend en argument une caregorie et un tableau de menu
function displayMenu(category, menu) {
    // Création du code HTML
    let htmlCode = `
      <div class="card" style="margin-bottom: 5vh;">
        <div class="card-header" style="  background-color: rgba(0, 0, 0,0.55);" >
          <h3 class="card-title text-center" >${category}</h3>
        </div>
        <div class="card-body">
          <table class="table">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Designation</th>
                <th scope="col">Prix Unitaire en Ariary</th>
                <th scope="col">Quantiter</th>
              </tr>
            </thead> 
            <tbody class="table-bordered  border-primary">
    `;
  
    // Ajout des lignes du tableau en fonction de l'objet menu
    for (let i = 0; i < menu.length; i++) {
      htmlCode += `
              <tr>
                <th scope="row"><input type="checkbox"  name="choix[]" value="${menu[i].idMenu}"></th>
                <td  id="${menu[i].idMenu}-nom" >${menu[i].nom}</td>
                <td id="${menu[i].idMenu}-prix">${menu[i].prixUnitaire}</td>
                <td><input type="number"  class="form-control is-valid" name="${menu[i].idMenu}" min="1"></td>
                <td><input type="hidden"  class="form-control" name="${menu[i].idMenu}-cat" value="${menu[i].idCategorie}"></td>
              </tr>
      `;
    }
  
    // Fermeture du code HTML
    htmlCode += `
            </tbody>
          </table>
        </div>
      </div>
    `;
     // Création d'un élément div temporaire pour contenir le code HTML
  const tempDiv = document.createElement("div");
  tempDiv.innerHTML = htmlCode;
  // Sélectionnez le conteneur principal (main) dans le DOM
  const mainContainer = document.getElementById("menu");
  // Ajoutez chaque enfant du div temporaire au conteneur principal
  while (tempDiv.firstChild) {
    mainContainer.appendChild(tempDiv.firstChild);
  }
  }

/*fonction qui recupère les lite de produit de la base par categorie */
function getListproduitByCategorie(){
    var xhr; 
    try {  xhr = new ActiveXObject('Msxml2.XMLHTTP');   }
    catch (e) 
    {
        try {   xhr = new ActiveXObject('Microsoft.XMLHTTP'); }
        catch (e2) 
        {
           try {  xhr = new XMLHttpRequest();  }
           catch (e3) {  xhr = false;   }
         }
    }
    xhr.open("GET","produit.jsp",true);
    xhr.send(null);

    xhr.onreadystatechange = () =>{
        if (xhr.readyState == 4 && xhr.status == 200 ) {
            var response = JSON.parse(xhr.responseText);
            var categories  = response.categories;
             categories.forEach(categorie => {
                displayMenu(categorie.nom,categorie.menu);
             });
        }
    }

}


getListproduitByCategorie();

/****fonction pour envoyer les donner du formulaire au server */
function sendData(form){
  var xhr; 
  var tabchoice = [];
  try {  xhr = new ActiveXObject('Msxml2.XMLHTTP');   }
  catch (e) 
  {
      try {   xhr = new ActiveXObject('Microsoft.XMLHTTP'); }
      catch (e2) 
      {
         try {  xhr = new XMLHttpRequest();  }
         catch (e3) {  xhr = false;   }
       }
  }
//je verifie si il paye avec intermediaire ou pas
  var selection = form.querySelector("#payement");
  if (selection.value==0) {
    var intermediaire = {
           choix: 0,
           cin :"",
           password:"",
           date:form.querySelector("#date").value,
           sommetotal: document.getElementById("sumtotal").textContent
    }
    tabchoice.push(intermediaire);
  }else{
     //je recuperer le login et mot de passe entrée dans le formulaire si le facture est rataché a un intermediaire
     var infos = form.querySelectorAll(".info-intermediaire")
     var intermediaire = {
      choix: 1,
      cin :infos[0].value,
      password:infos[1].value,
      date:form.querySelector("#date").value,
      sommetotal: document.getElementById("sumtotal").textContent
}
tabchoice.push(intermediaire);
  }
  //je recyupere les checkbox qui sont chequé et le input concerné je le stock dans un objet et je le met dans un tableau  et je renvoie le tableau avec la requette
     var checkboxElements = form.querySelectorAll('input[type="checkbox"]');     
     checkboxElements.forEach(checkbox => {
           if (checkbox.checked) {
            var choice = {
              idmenu: checkbox.value,
              quantiter:0,
              date:form.querySelector("#date").value,
              categorie: document.querySelector(`input[name="${checkbox.value}-cat"]`).value
            };
                var input = document.querySelector(`input[name="${checkbox.value}"]`);
                choice.quantiter = input.value; 
                tabchoice.push(choice);
           }
       });
       var url = "commande.jsp";
       url += "?choix=" +  encodeURIComponent(JSON.stringify(tabchoice));
       xhr.open("GET",url,true);
       xhr.send(null);
       xhr.onreadystatechange = ()=>{
    if (xhr.readyState==4 && xhr.status==200) {
        var rep = xhr.responseText;
        //affiche un message de sucess
        if (rep.trim()=="success") {
          alert("facture enregistrer");
        }
        location.reload();
    }
}


}


/*ecouteur evenement lorsque on vallide le formulaire*/
var form = document.querySelector("#menu");
form.addEventListener("submit",(event)=>{
event.preventDefault();
sendData(form);
//location.reload();
});



