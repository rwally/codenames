const compteJoueurs = () => {
	
	var compteurBleu=0, compteurRouge=0;

	var table =  document.querySelector('table');
	for (var r = 1; r < table.rows.length; r++) {
		for (var c = 1; c < table.rows[r].cells.length; c+=2) {
				if(table.rows[r].cells[c].innerHTML.includes("Bleu")){
	        		 compteurBleu++;
	        	}else if(table.rows[r].cells[c].innerHTML.includes("Rouge")){
	        		 compteurRouge++;
	        	}
	        }
	}
	 
	 if(compteurBleu>=2 && compteurRouge>=2){
		 let boutonPlateau=document.createElement("button");
			boutonPlateau.id="boutonPlateau";
			boutonPlateau.className="btn btn-primary";
			boutonPlateau.type="submit";
			boutonPlateau.innerHTML="Jouez !"
			document.querySelector('table').append(boutonPlateau);
			
			boutonPlateau.addEventListener('click', () => {
				 window.location = "plateau.html";	
			});
	 }
}

compteJoueurs();
