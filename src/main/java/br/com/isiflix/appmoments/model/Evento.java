package br.com.isiflix.appmoments.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_evento")
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_evento")
	private Integer id;
	
	@Column(name = "uuid_evento", nullable = false, length = 45)
	private String uuid;
	
	@Column(name = "titulo_evento", nullable = false, length = 100)
	private String titulo;
	
	@Column(name = "data_hora_inicio", nullable = false)
	private LocalDateTime dataHoraInicio;
	
	@Column(name = "data_hora_fim", nullable = false)
	private LocalDateTime dataHoraFim;
	
	@Column(name = "frase_evento", length = 100, nullable = false)
	private String frase;
	
	@Column(name = "status_financeiro")
	private Integer statusFinanceiro;
	
	@Column(name = "comprovante_transacao", columnDefinition = "TEXT")
	private String comprovamentePagamento;
	
	@Column(name = "link_moldura")
	private String linkMoldura;
	
	@ManyToOne
	@JoinColumn(name = "tbl_cliente_id_cliente")
	private Cliente cliente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public LocalDateTime getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(LocalDateTime dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public String getFrase() {
		return frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}

	public Integer getStatusFinanceiro() {
		return statusFinanceiro;
	}

	public void setStatusFinanceiro(Integer statusFinanceiro) {
		this.statusFinanceiro = statusFinanceiro;
	}

	public String getComprovamentePagamento() {
		return comprovamentePagamento;
	}

	public void setComprovamentePagamento(String comprovamentePagamento) {
		this.comprovamentePagamento = comprovamentePagamento;
	}

	public String getLinkMoldura() {
		return linkMoldura;
	}

	public void setLinkMoldura(String linkMoldura) {
		this.linkMoldura = linkMoldura;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
