package br.com.isiflix.appmoments.services;

import java.util.List;

import br.com.isiflix.appmoments.dto.EventoDTO;
import br.com.isiflix.appmoments.model.Evento;

public interface IEventoService {
	public Evento criarEvento(Evento novo, String token);
	public EventoDTO recuperarPeloUuid(String uuid);
	public Evento alterarDados(Evento evento);
	public List<EventoDTO> recuperarPeloCliente(Integer idCliente);
	public List<String> recuperarMolduras();
}