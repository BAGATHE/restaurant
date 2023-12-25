
const mainContainer = document.getElementById("main");
    mainContainer.innerHTML = "";
    let intermediaireStorage = localStorage.getItem('intermediaire');
    let date = JSON.parse(localStorage.getItem("date"));
    getStatistiqueIntermediairebyMonth(date.mois,date.year);
    // Vérifie si des données sont stockées
    if (intermediaireStorage) {
        let intermediaires = JSON.parse(intermediaireStorage);
        intermediaires.forEach(intermediaire => {
            displayInfoIntermediaire(intermediaire);
      });
    }


//fonction qui affiche un modal de l'intermediaire
function displayInfoIntermediaire(intermed) {
    // Création des éléments HTML
    const rowDiv = document.createElement("div");
    rowDiv.classList.add("row");

    const colDiv = document.createElement("div");
    colDiv.classList.add("col-md-6", "mb-4", "offset-md-2");

    const cardDiv = document.createElement("div");
    cardDiv.classList.add("card", "text-white", "bg-info", "text-white", "shadow");

    const cardBodyDiv = document.createElement("div");
    cardBodyDiv.classList.add("card-body");

    const idHeading = document.createElement("h3");
    idHeading.classList.add("text-center");
    idHeading.textContent = intermed.idIntermediaire;

    const maxQuotaHeading = document.createElement("h4");
    maxQuotaHeading.textContent = `Forfait Max: ${intermed.quotaMax} Ar`;

    const minQuotaHeading = document.createElement("h4");
    minQuotaHeading.textContent = `Forfait Min: ${intermed.quotaMin} Ar`;

    const quotaHeading = document.createElement("h4");
    quotaHeading.textContent = `Quota: ${intermed.quota} Ar`;

    const forfait = document.createElement("h4");
    forfait.textContent = `Forfait: ${intermed.forfait} Ar`;
     
    const cumulle = document.createElement("h4");
    cumulle.textContent = `cumulle: ${intermed.cumul} Ar`;

    const gainHeading = document.createElement("h4");
    gainHeading.textContent = `Commission: ${intermed.gain} Ar`;

    // Ajout des éléments au DOM
    cardBodyDiv.appendChild(idHeading);
    cardBodyDiv.appendChild(cumulle);
    cardBodyDiv.appendChild(maxQuotaHeading);
    cardBodyDiv.appendChild(minQuotaHeading);
    cardBodyDiv.appendChild(quotaHeading);
    cardBodyDiv.appendChild(forfait);
    cardBodyDiv.appendChild(gainHeading);

    cardDiv.appendChild(cardBodyDiv);
    colDiv.appendChild(cardDiv);
    rowDiv.appendChild(colDiv);

    // Sélectionnez le conteneur principal (main) dans le DOM
    const mainContainer = document.querySelector("main");

    // Ajoutez le contenu au conteneur principal
    mainContainer.appendChild(rowDiv);
}


function getStatistiqueIntermediairebyMonth(month,year){
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
    xhr.open("GET","intermediaire.jsp?month="+month +"&year="+year);      
    xhr.send(null);
  
    xhr.onreadystatechange = ()=>{
    if (xhr.readyState == 4 && xhr.status == 200) {
       var rep = JSON.parse(xhr.responseText);
// Assuming you have a container element with id "main"
const mainContainer = document.getElementById("main");

rep.forEach(tab => {
    // Create table element
    const table = document.createElement("table");
    table.classList.add("table", "my-0", "table-hover", "dataTable");

    // Create table header
    const thead = document.createElement("thead");
    const headerRow = document.createElement("tr");
    headerRow.innerHTML = `
        <th>Nom Menu</th>
        <th>Prix Unitaire</th>
        <th>Prix Intermediaire</th>
        <th>Quantité</th>
        <th>Date Consommation</th>
        <th>Client</th>
        <th>Intermediaire</th>
    `;
    thead.appendChild(headerRow);
    table.appendChild(thead);

    // Create table body
    const tbody = document.createElement("tbody");

    tab.forEach(intermediaire => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${intermediaire.Menu}</td>
            <td>${intermediaire.prixUnitaire}</td>
            <td>${intermediaire.prixIntermediaire}</td>
            <td>${intermediaire.quantiter}</td>
            <td>${intermediaire.dateCommande}</td>
            <td>${intermediaire.idClient}</td>
            <td>${intermediaire.idIntermediaire}</td>
        `;
        tbody.appendChild(row);
    });

    table.appendChild(tbody);

    // Append the table to the main container
    mainContainer.appendChild(table);
});

    }
  }
  }