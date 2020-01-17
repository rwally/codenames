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

const ShowSectionJoueur = () => {
	document
	.querySelector('#listeJoueurs')
		.style.display = 'block';
	document
	.querySelector('#connexion')
		.style.display = 'none';
	
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




const ajouterJoueur = () => {
	let row = document.createElement('tr');
	let colId = document.createElement('td');
	let colEquipe = document.createElement('td');
	
	
	colId.innerHTML = document.querySelector('input[name="ID"]').value;
	colEquipe.innerHTML = "sonEquipe";
	
	row.append(colId);
	row.append(colEquipe);
	
	document.querySelector('tbody').append(row);
//	if(document.querySelector('input[name="ID"]').value !== "" && document.querySelector('input[name="PASSWORD"]').value !== ""){
//		ShowSectionJoueur();
//	}
	
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
	
//	document.querySelector('tr:last').remove();
}

document.querySelector('#btnConnexion').addEventListener('click',(event) => {
	event.preventDefault();
	
	if(document.querySelector('input[name="ID"]').value !== "" && document.querySelector('input[name="PASSWORD"]').value !== ""){
		ajouterJoueur();
		ShowSectionJoueur();
		disableConnexion();
		
	}
});

//document.querySelector('#btnConnexion').addEventListener('click',ajouterJoueur);
//document.querySelector('#btnConnexion').addEventListener('click',disableConnexion);
document.querySelector('#navDeconnexion').addEventListener('click',deconnexion);