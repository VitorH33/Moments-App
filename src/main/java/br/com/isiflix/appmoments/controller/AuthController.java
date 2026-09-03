package br.com.isiflix.appmoments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.isiflix.appmoments.dto.LoginDTO;
import br.com.isiflix.appmoments.security.MomentsToken;
import br.com.isiflix.appmoments.services.IAuthService;

@RestController
public class AuthController {

	@Autowired
	private IAuthService service;

	@PostMapping("/login")
	public ResponseEntity<MomentsToken> realizarLogin(@RequestBody LoginDTO dados) {
		MomentsToken tk = service.realizarLogin(dados);
		if (tk != null) {
			return ResponseEntity.ok(tk);
		}
		return ResponseEntity.status(403).build();
	}

}