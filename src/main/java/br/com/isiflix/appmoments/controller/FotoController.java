package br.com.isiflix.appmoments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.isiflix.appmoments.dto.FotoDTO;
import br.com.isiflix.appmoments.dto.FotoResponse;
import br.com.isiflix.appmoments.services.IFotoService;

@RestController
@CrossOrigin("*")
public class FotoController {

	@Autowired
	private IFotoService service;
	
	@PostMapping("/pictures")
	public ResponseEntity<FotoResponse> registrarFoto(@RequestBody FotoDTO fotoDTO) {
		Integer status = service.enviarFoto(fotoDTO);
		FotoResponse response = new FotoResponse(status, "Resultado");
		return ResponseEntity.ok(response);
	}
}
