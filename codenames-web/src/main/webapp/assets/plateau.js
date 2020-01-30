/**
 * 		
 */


var images=document.querySelectorAll("img");

images.forEach((image)=>{
	image.addEventListener('click', () => {	
		var mot = image.nextElementSibling;
		mot.style.visibility='hidden';	
		});
		
});


/*

document.querySelector("image")
.addEventListener('click', (event) => {
	modifierImageAjax(event);
});


const modifierImageAjax = async () => {
	let produit={
			libelle: document.querySelector('input[name="libelle"]').value, 
			prix: document.querySelector('input[name="prix"]').value
	};
	
	let produitRecu = await fetch('http://172.16.44.107:8081/E-musique-web/api/produit', {
		headers : {
		      'Content-Type': 'application/json'
		},
		method: 'GET',
		body : JSON.stringify(produit)
	}).then(resp=>resp.json());
	
	ajouterProduitTableau(produitRecu);

}



function ajouterImagePlateau(maCase){
	let nouvelleImage=document.createElement("img");
	img.src=maCase.image;
	
	let nouveauMot=document.createElement("span");
	nouveauMot.innerHTML=maCase.mot.libelle;
	nouveauMot.className='centered';
	
	nouvelleImage.addEventListener('click', () => {
		fetch(`http://localhost:8080/codenames-web/api/plateau/${maCase.mot.libelle}`,{
			method: 'GET'
		}).then(resp=>resp.json());
	});
	
	nouvelleImage.append(nouveauMot);
	
	document.querySelector('#plateau').append(nouvelleImage);
}

fetch('http://172.16.44.107:8081/codenames-web/api/plateau')
.then(resp => resp.json())
.then(cases => {
	c.forEach( (c) => {
		ajouterImagePlateau(c);
	});
	
})
.catch(err => {
	console.log(err);
});

var evtSource = new EventSource('http://localhost:8080/codenames-web/api/plateau/sse');

evtSource.addEventListener('message', (event) => {

	
	let produit = {
			libelle: event.data,
			prix: 0
	}
	//alert(msg);
	
	//document.querySelector('table').prepend(msg.innerHTML);
	
	//let object = JSON.parse(event.data);
	//console.log(object);
	ajouterProduitTableau(produit);
});
*/

/*
var evtSource = new EventSource('http://localhost:8080/codenames-web/plateau/sse');

evtSource.addEventListener('message', (event) => {
	alert(msg);
});
*/