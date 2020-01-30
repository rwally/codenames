const envoyerMessage = (chat) => {
	

	let msg = document.createElement('div');
	msg.className = "message";
	
	let date = document.createElement('p');
//	date.className = "date";
	
	let contenu = document.createElement('p');
//	var message = document.querySelector('input[name="monMessage"]').value;
	
//	let dateActuelle = new Date();
	const options = { hour:'2-digit', minute:'2-digit', second:'2-digit' };
	
	date.innerHTML = new Date(chat.date).toLocaleDateString('en-US' , options);
//	date.innerHTML = chat.date.toLocaleDateString('en-US' , options);
	contenu.innerHTML = chat.message;
	
	msg.append(date);
	msg.append(contenu);
	
//	$('.container').append($('.message'));
	document.querySelector('.messages').prepend(msg);
}

fetch('http://localhost:8081/codenames-web/api/chat')
	.then(resp => resp.json()) 
	.then(chats => {
		for(let c of chats) {
			envoyerMessage(c);
		}
	});

const ajouterMessageAjax = async (event) => {
	event.preventDefault();
	let chat = {
			message: document.querySelector('input[name="monMessage"]').value,
			date: new Date()
	};
	let messageRecu = await 
		fetch('http://localhost:8081/codenames-web/api/chat',{
		method: 'POST',
		headers: {
			'Content-Type':'application/json'
		},
		body: JSON.stringify(chat)
		}).then(resp => resp.json());
	envoyerMessage(messageRecu);
}

document.querySelector('#monChat')
	.addEventListener('submit', ajouterMessageAjax);


let eventSource = new EventSource('http://localhost:8081/codenames-web/api/chat/sse');

eventSource.addEventListener('message', () =>{
//	//Si on a reçu un string
//	let msg = event.data;
//	alert(msg);
//	
//	//SI on a reçu un objet JSON
//	let object = JSON.parse(event.data);
//	console.log(object);
	
	let chat = {
		message : event.data,
		date : new Date()
	}
	envoyerMessage(chat);
	
});



function openForm() {
  document.querySelector('#monChat').style.display = "block";
}


function closeForm() {
  document.querySelector('#monChat').style.display = "none";
}

document.querySelector('#openBtn').addEventListener('click',openForm);
document.querySelector('#sendBtn').addEventListener('click',envoyerMessage);
document.querySelector('#closeBtn').addEventListener('click',closeForm);