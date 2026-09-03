package br.com.isiflix.appmoments.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.isiflix.appmoments.dto.LoginDTO;
import br.com.isiflix.appmoments.model.Cliente;
import br.com.isiflix.appmoments.repo.ClienteRepo;
import br.com.isiflix.appmoments.security.MomentsToken;
import br.com.isiflix.appmoments.security.TokenUtil;

@Service
public class AuthServiceImpl implements IAuthService{

	@Autowired
	private ClienteRepo repo;
	@Override
	public MomentsToken realizarLogin(LoginDTO dados) {
		Cliente cli	= repo.findByEmailOrTelefone(dados.emailTelefone(), dados.emailTelefone());
		if (cli != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(dados.senha(), cli.getSenha())) {
				return TokenUtil.encode(cli);
			}
		}
		return null;
	}
	
	

}