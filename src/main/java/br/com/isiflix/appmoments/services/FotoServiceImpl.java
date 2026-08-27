package br.com.isiflix.appmoments.services;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.isiflix.appmoments.dto.FotoDTO;
import br.com.isiflix.appmoments.model.Evento;
import br.com.isiflix.appmoments.repo.EventoRepo;
import br.com.isiflix.appmoments.util.EventValues;
import br.com.isiflix.appmoments.util.FotoValues;

@Service
public class FotoServiceImpl implements IFotoService{

	@Autowired
	private EventoRepo repo;
	
	@Value("${appmoments.albumfolder}")
	private String albumFolder;
	@Override
	public Integer enviarFoto(FotoDTO fotoDTO) {
		Evento evt = repo.findByUuid(fotoDTO.uuid());
		if (evt != null) {
			if(evt.getStatusFinanceiro() == EventValues.APROVADO) {
				if(evt.getDataHoraFim().isAfter(LocalDateTime.now())) {
					try {
						String base64 = fotoDTO.fotoBase64().replace("data:image/png;base64,", "");				
						String filename = UUID.randomUUID().toString()+".png";
						FileOutputStream fr = new FileOutputStream(albumFolder+File.separator+fotoDTO.uuid()+File.separator+filename);
						byte[] data = Base64.getDecoder().decode(base64);
						fr.write(data);
						fr.close();
						return FotoValues.SUCCESS;
					}
					catch (Exception ex) {
						return FotoValues.ERROR;
					}
				}
				else {
					evt.setStatusFinanceiro(EventValues.EXPIRADO);
					repo.save(evt);
					return FotoValues.EXPIRED;
				}
			}
			else {
				return FotoValues.FORBIDDEN;
			}
		}
		return FotoValues.NOT_FOUND;
	}

}
