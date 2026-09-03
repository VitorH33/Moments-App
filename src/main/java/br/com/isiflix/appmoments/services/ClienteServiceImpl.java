package br.com.isiflix.appmoments.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.isiflix.appmoments.model.Cliente;
import br.com.isiflix.appmoments.repo.ClienteRepo;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private ClienteRepo repo;
	
	@Override
	public Cliente criarNovo(Cliente novo) {
		// TODO Auto-generated method stub
		Cliente r = repo.findByEmailOrTelefone(novo.getEmail(), novo.getTelefone());
		if (r != null) {
			throw new RuntimeException("Cliente ja existe");
			
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String novaSenha = encoder.encode(novo.getSenha());
		novo.setSenha(novaSenha);
		return repo.save(novo);
	}

	@Override
	public Cliente alterarDados(Cliente cliente) {
		// TODO Auto-generated method stub
		Cliente res = repo.findById(cliente.getId()).orElse(null);
		if (res != null) {
			return repo.save(cliente);
		}
		return null;
	}

}
