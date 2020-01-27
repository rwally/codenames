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


