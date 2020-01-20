/**
 * 		
 */
import {choisirMot} from './mots.js';

function shuffle(array) {
	  array.sort(() => Math.random() - 0.5);
	}


function creationPlateau(){
	
	/*
	 * Assigner couleurs
	 */
	var listeCouleurs =[];
	for(let i=0;i<25;i++){	
		if(i<9){
			listeCouleurs.push("bleu");
		}else if(i<17){
			listeCouleurs.push("rouge");
		}else if(i<24){
			listeCouleurs.push("blanc");
		}else{
			listeCouleurs.push("noir");
		}
	}
	shuffle(listeCouleurs);
	
	
	for(let i=0;i<25;i++){

		var nouvelleDiv=document.createElement("div");
		nouvelleDiv.className='col';
		
		
		var nouvelleCase=document.createElement("div");
		nouvelleCase.style.marginBottom='5px';
		
		var imgMot = document.createElement("img");
		imgMot.src='https://i.imgur.com/LRk4Jee.png';
		imgMot.style.borderRadius='5%';

		//assigner couleur
		nouvelleCase.classList.add(listeCouleurs[i]);

		/*
		 * Assigner mots
		 */
		var nouveauMot=document.createElement("text");
		nouveauMot.innerHTML=choisirMot();
		nouveauMot.className="centered";

		nouvelleCase.append(imgMot);
		nouvelleCase.append(nouveauMot);
		
		nouvelleDiv.append(nouvelleCase);
		
		if(i%5==0){
			var nouvelleLigne = document.createElement("div");
			nouvelleLigne.className='row';
	
			document.querySelector('#plateau').append(nouvelleLigne);
			nouvelleLigne.append(nouvelleDiv);
		} 
		
		nouvelleLigne.append(nouvelleDiv);	
	}
	
}



document.querySelector('#boutonMaster')
	.addEventListener('click', () => {
		var images=document.querySelectorAll("div.bleu, div.rouge, div.blanc, div.noir");
		images.forEach((image) => {
			if(!(image.classList.contains("trouver"))){
				if(image.classList.contains("bleu")){image.querySelector('img').src='https://i.imgur.com/LDcUXHC.png';}
				else if(image.classList.contains("rouge")){image.querySelector('img').src='https://i.imgur.com/SAcppjf.png';}
				else if(image.classList.contains("blanc")){image.querySelector('img').src='https://i.imgur.com/LRk4Jee.png';}
				else{image.querySelector('img').src='https://i.imgur.com/AfRrEMz.png';}
				image.querySelector('text').style.visibility='visible';
			}
		
		});
		
		document.querySelector('form').style.visibility='visible';
	});

document.querySelector('#boutonAgent')
.addEventListener('click', () => {
	var images=document.querySelectorAll("div.bleu, div.rouge, div.blanc, div.noir");
	images.forEach((image) => {
		if(!(image.classList.contains("trouver")))
		{
			image.querySelector('img').src='https://i.imgur.com/LRk4Jee.png';
			image.querySelector('text').style.visibility='visible';
		}
	});
	document.querySelector('form').style.visibility='hidden';
});

function revelerCase(){
	
	var images=document.querySelectorAll("div.bleu, div.rouge, div.blanc, div.noir");
	images.forEach((image)=>{
		image.addEventListener('click', () => {	
			if(image.className=="bleu"){
				image.querySelector('img').src = 'https://i.imgur.com/miuPPRe.png';
			}else if(image.className=="rouge"){
				image.querySelector('img').src = 'https://i.imgur.com/hTeiagx.png';
			}else if(image.className=="blanc"){
				image.querySelector('img').src = 'https://i.imgur.com/7MeDbpE.png';
			}else{
				image.querySelector('img').src = 'https://i.imgur.com/QWHCuAR.png';
			}
			image.querySelector('text').style.visibility='hidden';
			image.classList.add("trouver");
			
		});
	});
	
}


creationPlateau();
revelerCase();



