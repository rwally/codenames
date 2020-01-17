/**
 * 		
 */

function afficherCase(arg){
	
	arg.addEventListener('click', (event) => {
		
		event.target.nextElementSibling.style.display = 'none';
		
		if(arg.className=="bleu"){
			arg.querySelector('img').src = 'https://i.imgur.com/miuPPRe.png';
		}else if(arg.className=="rouge"){
			arg.querySelector('img').src = 'https://i.imgur.com/hTeiagx.png';
		}else if(arg.className=="blanc"){
			arg.querySelector('img').src = 'https://i.imgur.com/7MeDbpE.png';
		}else{
			arg.querySelector('img').src = 'https://i.imgur.com/QWHCuAR.png';
		}
		
	})
	
}



function creationPlateau(){
	
	
	for(let i=0;i<25;i++){
		
	
		
		var nouvelleDiv=document.createElement("div");
		nouvelleDiv.className='col';
		
		var nouvelleCase=document.createElement("div");
		nouvelleCase.style.marginBottom='5px';
		
		var imgMot = document.createElement("img");
		imgMot.src='https://i.imgur.com/LRk4Jee.png';
		imgMot.style.borderRadius='5%';
		
		/*
		 * Assigner couleurs
		 */
		if(i<9){
			nouvelleCase.className="bleu";
		}else if(i<17){
			nouvelleCase.className="rouge";
		}else if(i<24){
			nouvelleCase.className="blanc";
		}else{
			nouvelleCase.className="noir";
		};

		
		/*
		 * Assigner mots
		 */
		var nouveauMot=document.createElement("text");
		nouveauMot.innerHTML="Anniversaire";
		nouveauMot.className="centered";
	
		nouvelleCase.append(imgMot);
		nouvelleCase.append(nouveauMot);
		
		afficherCase(nouvelleCase);
		
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



creationPlateau();
