const envoyerMessage = () => {
	event.preventDefault();

	let msg = document.createElement('div');
	let date = document.createElement('div');
	let contenu = document.createElement('p');
	var message = document.querySelector('input[name="monMessage"]').value;
	
	let dateActuelle = new Date();
	date.innerHTML = dateActuelle;
	contenu.innerHTML = message;
	
	msg.append(date);
	msg.append(contenu);
	
	document.querySelector('.message').append(msg);
}





function openForm() {
  document.querySelector('#monChat').style.display = "block";
}


function closeForm() {
  document.querySelector('#monChat').style.display = "none";
}

document.querySelector('#openBtn').addEventListener('click',openForm);
document.querySelector('#sendBtn').addEventListener('click',envoyerMessage);
document.querySelector('#closeBtn').addEventListener('click',closeForm);