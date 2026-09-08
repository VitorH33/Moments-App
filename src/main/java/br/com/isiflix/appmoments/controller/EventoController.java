package br.com.isiflix.appmoments.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.com.isiflix.appmoments.dto.EventoDTO;
import br.com.isiflix.appmoments.model.Evento;
import br.com.isiflix.appmoments.security.TokenUtil;
import br.com.isiflix.appmoments.services.IEventoService;

@RestController
@CrossOrigin("*")
public class EventoController {

	@Autowired
	private IEventoService service;
	
	@PostMapping("/eventos")
	public ResponseEntity<Evento> cadastrarNovo(@RequestBody Evento novo, @RequestHeader("Authorization") String token){
		Evento res = service.criarEvento(novo, token);
		if (res != null) {
			return ResponseEntity.status(201).body(res);
		}
		return ResponseEntity.badRequest().build();
	
	}
	
	@GetMapping("/eventos/{uuid}")
	public ResponseEntity<EventoDTO> recuperarPeloUUID(@PathVariable String uuid){
		EventoDTO res = service.recuperarPeloUuid(uuid);
		if (res != null) {
			return ResponseEntity.ok(res);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/eventos/{uuid}")
	public ResponseEntity<Evento> atualizarDados(@RequestBody Evento evt){
		Evento res = service.alterarDados(evt);
		if (res != null) {
			return ResponseEntity.ok(res);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/eventos")
	public ResponseEntity<List<EventoDTO>> recuperarTodosPorCliente(@RequestHeader(name = "Authorization") String token){
		Integer id = TokenUtil.getClienteByToken(token);
		if (id != null) {
			return ResponseEntity.ok(service.recuperarPeloCliente(id));
		}
		return ResponseEntity.status(403).build();
	}
	
	@GetMapping("/molduras")
	public List<String> recuperarMolduras(){
		return service.recuperarMolduras();
	}
	
}
