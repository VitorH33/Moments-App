package br.com.isiflix.appmoments.dto;

import java.time.LocalDateTime;

public record EventoDTO(String uuid, String titulo, String frase, LocalDateTime inicio, LocalDateTime fim, String moldura) {

	
}
