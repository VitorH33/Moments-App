package br.com.isiflix.appmoments.services;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.isiflix.appmoments.dto.EventoDTO;
import br.com.isiflix.appmoments.model.Cliente;
import br.com.isiflix.appmoments.model.Evento;
import br.com.isiflix.appmoments.repo.EventoRepo;
import br.com.isiflix.appmoments.util.EventValues;

@Service
public class EventoServiceImp implements IEventoService{

	@Value("${appmoments.albumfolder}")
	private String albumFolder;
	
	@Autowired
	private EventoRepo repo;
	@Override
	public Evento criarEvento(Evento novo) {
		novo.setUuid(UUID.randomUUID().toString());
		novo.setStatusFinanceiro(EventValues.CRIADO);
		novo.setDataHoraFim(novo.getDataHoraInicio().plusHours(4));
		
		Evento res = repo.save(novo);
		if (res != null) {
			String novaPasta = albumFolder + File.separator + novo.getUuid();
			try {
				if (new File(novaPasta).mkdir()) {
					return res;
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public EventoDTO recuperarPeloUuid(String uuid) {
		Evento evt = repo.findByUuid(uuid);
		if (evt != null && evt.getStatusFinanceiro() == EventValues.APROVADO) {
			if (evt.getDataHoraFim().isBefore(LocalDateTime.now())) {
				evt.setStatusFinanceiro(EventValues.EXPIRADO);
				repo.save(evt);
				return null;
			}
			return new EventoDTO(evt.getUuid(), evt.getTitulo(), evt.getFrase(), evt.getDataHoraInicio(), evt.getDataHoraFim(), evt.getLinkMoldura());
		}
		return null;
	}

	@Override
	public Evento alterarDados(Evento evento) {
		Evento evt = repo.findByUuid(evento.getUuid());
		if (evt == null) {
			return null;
		}
		
		evt.setDataHoraInicio(evento.getDataHoraInicio());
		evt.setDataHoraFim(evento.getDataHoraInicio().plusHours(4));
		evt.setLinkMoldura(evento.getLinkMoldura());
		evt.setFrase(evento.getFrase());
		evt.setTitulo(evento.getTitulo());
		return repo.save(evento);
	}

	@Override
	public List<EventoDTO> recuperarPeloCliente(Integer idCliente) {
		Cliente c = new Cliente();
		c.setId(idCliente);
		return repo.findByCliente(c);
	}

}
