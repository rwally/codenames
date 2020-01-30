package fr.formation.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.annotation.JsonView;

import fr.formation.Views.Views;
import fr.formation.dao.IDAOChat;
import fr.formation.model.Chat;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
public class ChatRestController {
	
	@Autowired
	IDAOChat daoChat;

	private List<SseEmitter> emitters = new ArrayList<SseEmitter>();
	
	
	@GetMapping
	@JsonView(Views.Chat.class)
	public List<Chat> listeChats() {
	 return this.daoChat.findAll();
	}
	
	@PostMapping
	@JsonView(Views.Chat.class )
	public Chat save(@RequestBody Chat chat) {
//		System.out.println(chat.getMessage());
		daoChat.save(chat);
		
		for (SseEmitter emitter : this.emitters) {
			try {
				emitter.send("UN NOUVEAU MESSAGE");
			} catch (Exception e) {
				emitter.completeWithError(e);
			}
		}
		
		return chat;
	}
	
	@GetMapping("/sse")
	public SseEmitter sse() {
		SseEmitter emitter = new SseEmitter();
		
		//action a faire quand l'event est complété
		emitter.onCompletion(()-> {
			synchronized (this.emitters) {
				// permet d'être sur que la liste est utilisée par une seule thread (on est seul a l'utiliser)
				this.emitters.remove(emitter);
			}
			
		});
		
		//action a faire quand l'event est timeout
		emitter.onTimeout(() -> {
			this.emitters.remove(emitter);			
		});
		
		
		this.emitters.add(emitter);
		return emitter;
	}
	
}
