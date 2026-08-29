package br.com.isiflix.appmoments.services;

import br.com.isiflix.appmoments.model.Cliente;

public interface IClienteService {
	public Cliente criarNovo (Cliente novo);
	public Cliente alterarDados (Cliente cliente);

}
