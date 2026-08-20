package br.com.isiflix.appmoments.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.isiflix.appmoments.dto.EventoDTO;
import br.com.isiflix.appmoments.model.Cliente;
import br.com.isiflix.appmoments.model.Evento;

public interface EventoRepo extends CrudRepository<Evento, Integer>{
	
	@Query("SELECT new br.com.isiflix.appmoments.dto.EventoDTO(e.uuid, e.titulo, e.frase, e.dataHoraInicio, e.dataHoraFim, e.linkMoldura)"
			+ "FROM Evento e WHERE e.cliente = :c")
	public List<EventoDTO> findByCliente(Cliente c);
	public Evento findByUuid(String uuid);

}
