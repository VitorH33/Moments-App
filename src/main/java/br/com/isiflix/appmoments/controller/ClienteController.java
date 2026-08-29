package br.com.isiflix.appmoments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.isiflix.appmoments.dto.ClienteResponseDTO;
import br.com.isiflix.appmoments.model.Cliente;
import br.com.isiflix.appmoments.services.IClienteService;

@RestController
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	private IClienteService service;
	
	@PostMapping("/clientes")
	public ResponseEntity<?> cadastrarNovo(@RequestBody Cliente novo) {
		try {
			Cliente res = service.criarNovo(novo);
			if (res != null) {
				return ResponseEntity.status(201).body(res);
			}
			return ResponseEntity.badRequest().body(new ClienteResponseDTO(400, "Nao consegui cadastrar o cliente"));
		}
		catch (Exception ex) {
			return ResponseEntity.badRequest().body(new ClienteResponseDTO(400, "Cliente existente"));
		}
	}
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> alterarDados(@RequestBody Cliente cliente, @PathVariable Integer id) {
		if (cliente.getId() == null) {
			cliente.setId(id);
		}
		Cliente res = service.alterarDados(cliente);
		if (res != null) {
			return ResponseEntity.ok(res);
		}
		return ResponseEntity.badRequest().body(new ClienteResponseDTO(400, "Erro ao atualizar dados do cliente"));
	}
}
