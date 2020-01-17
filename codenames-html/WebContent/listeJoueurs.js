document
.querySelector('#navDeconnexion')
	.style.display = 'none';



const hideSections = () => {
	document
	.querySelectorAll('section')
	.forEach((section) => {
		section.style.display = 'none';
	});
}
hideSections();
document
	.querySelector('#presentation')
	.style.display = 'block'

const ShowSectionJoueur = () => {
	hideSections();
	document
	.querySelector('#listeJoueurs')
		.style.display = 'block';
	document
	.querySelector('#connexion')
		.style.display = 'none';
	document
	.querySelector('#inscription')
		.style.display = 'none';
	document
	.querySelector('#choixEquipe')
		.style.display = 'none'
	
}



document
	.querySelectorAll('nav a')
	.forEach(lien => {
		lien.addEventListener('click', (event) => {
			event.preventDefault();
			hideSections();
			let sectionId = event.target.getAttribute('href');
			document.querySelector(sectionId)
				.style.display = 'block';
		});
	});

document
.querySelectorAll('div.link > a')
.forEach(lien => {
	lien.addEventListener('click', (event) => {
		event.preventDefault();
		hideSections();
		let sectionId = event.target.getAttribute('href');
		document.querySelector(sectionId)
			.style.display = 'block';
	});
});

const showSectionChoixEquipe = () => {
	hideSections();
	document
	.querySelector('#choixEquipe')
		.style.display = 'block';
}


const ajouterJoueur = (button) => {
	let row = document.createElement('tr');
	let colId = document.createElement('td');
	let colEquipe = document.createElement('td');
	
	if (button.id === 'btnConnexion'){
		colId.innerHTML = document.querySelector('input[name="ID"]').value;
	}
	else if (button.id === 'btnInscription') {
		colId.innerHTML = document.querySelector('input[name="username"]').value;
	}
	colEquipe.innerHTML = document.querySelector('select[name="choixEquipe"]').value;
	
	row.append(colId);
	row.append(colEquipe);
	
	document.querySelector('tbody').append(row);
	
	document.querySelector('#navDeconnexion').addEventListener('click',() => {
		row.remove();
	});
	
}

const disableConnexion = () => {
	document
	.querySelector('#navConnection')
		.style.display = 'none';
	document
	.querySelector('#navInscription')
		.style.display = 'none';
	document
	.querySelector('#navDeconnexion')
		.style.display = 'block';
}

const deconnexion = () => {
	document
	.querySelector('#navConnection')
		.style.display = 'block';
	document
	.querySelector('#navInscription')
		.style.display = 'block';
	
	document
	.querySelector('#listeJoueurs')
		.style.display = 'none';
	document
	.querySelector('#connexion')
		.style.display = 'block';
	document
	.querySelector('#navDeconnexion')
		.style.display = 'none';
	document
	.querySelector('#inscription')
		.style.display = 'none';
	
//	document.querySelector('tr:last').remove();
}

function validation() {
	'use strict';
	window.addEventListener('load', function() {
	    // Fetch all the forms we want to apply custom Bootstrap validation styles to
	    var forms = document.getElementsByClassName('needs-validation');
	    // Loop over them and prevent submission
	    var validation = Array.prototype.filter.call(forms, function(form) {
	    	let buttons = document.querySelectorAll('input[type="submit"]');
	    	for (let button of buttons) {
				button.addEventListener('click', function(event) {
				    if (form.checkValidity() === false) {
				      	event.preventDefault();
				       	event.stopPropagation();
				    }
				    else {
				       	event.preventDefault();				       	
				    	showSectionChoixEquipe();
				    	
				    	document.
				    		querySelector('#btnChoix')
				    		.addEventListener('click', function (event) {
				    			ajouterJoueur(button);
				    			ShowSectionJoueur();
				    			disableConnexion();
				    		})
				    }
				    form.classList.add('was-validated');
				}, false);
	    	}
	    });
	}, false);
}

validation();


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
			boutonPlateau.innerHTML="Jouez !"
			document.querySelector('table').append(boutonPlateau);
			
			boutonPlateau.addEventListener('click', () => {
				 window.location = "plateau.html";	
			});
	 }
}

compteJoueurs();




//document.querySelector('#btnConnexion').addEventListener('click',ajouterJoueur);
//document.querySelector('#btnConnexion').addEventListener('click',disableConnexion);
document.querySelector('#navDeconnexion').addEventListener('click',deconnexion);