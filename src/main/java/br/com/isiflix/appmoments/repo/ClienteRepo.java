package br.com.isiflix.appmoments.repo;

import org.springframework.data.repository.CrudRepository;

import br.com.isiflix.appmoments.model.Cliente;

public interface ClienteRepo extends CrudRepository<Cliente, Integer>{
	public Cliente findByEmailOrTelefone(String email, String telefone);

}
