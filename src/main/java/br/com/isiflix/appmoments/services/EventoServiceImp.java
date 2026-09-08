package br.com.isiflix.appmoments.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.isiflix.appmoments.dto.EventoDTO;
import br.com.isiflix.appmoments.model.Cliente;
import br.com.isiflix.appmoments.model.Evento;
import br.com.isiflix.appmoments.repo.ClienteRepo;
import br.com.isiflix.appmoments.repo.EventoRepo;
import br.com.isiflix.appmoments.security.TokenUtil;
import br.com.isiflix.appmoments.util.EventValues;

@Service
public class EventoServiceImp implements IEventoService{

	@Value("${appmoments.framefolder}")
	private String frameFolder;
	
	@Value("${appmoments.albumfolder}")
	private String albumFolder;
	
	@Autowired
	private EventoRepo repo;
	
	@Autowired
	private ClienteRepo cliRepo;
	
	@Override
	public Evento criarEvento(Evento novo, String token) {

		Cliente c = cliRepo.findById(TokenUtil.getClienteByToken(token)).orElse(null);
		if (c == null) {
			return null;
		}
		novo.setCliente(c);
		// recebo um evento incompleto
		// gero o UUID
		// crio uma pasta
		// dou continuidade no Gateway
		novo.setUuid(UUID.randomUUID().toString());
		novo.setStatusFinanceiro(EventValues.CRIADO);
		// detalhe: nosso evento sempre tem 4h de duração
		novo.setDataHoraFim(novo.getDataHoraInicio().plusHours(4));

//		System.out.println(novo);
		Evento res = repo.save(novo);

		// System.out.println(res);
		if (res != null) {
			// agora crio uma pasta em algum lugar do meu servidor
			String novaPasta = albumFolder + File.separator + novo.getUuid();
			System.out.println(novaPasta);
			try {
				if (new File(novaPasta).mkdir()) {
					return res;
				} else {
					System.out.println("nao criei a pasta");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return res;
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

	@Override
	public List<String> recuperarMolduras() {
		// TODO Auto-generated method stub
		try {
			return Files.list(Path.of(frameFolder)).map( p -> p.getFileName().toString()).toList();					 			
		}
		catch(Exception ex) {
			
			ex.printStackTrace();
		}
		return null;
	}
}
