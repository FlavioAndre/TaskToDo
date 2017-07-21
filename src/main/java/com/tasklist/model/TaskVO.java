package com.tasklist.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.NONE)
public class TaskVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlAttribute
	public Long id;
	@XmlElement
	public String titulo;

	@XmlElement
	public String status;

	@XmlElement
	public String descricao;

	@XmlElement
	private Date criacao;

	@XmlElement
	private Date edicao;

	@XmlElement
	private Date remocao;

	@XmlElement
	private Date conclusao;

	public Date getCriacao() {
		return criacao;
	}

	public void setCriacao(Date criacao) {
		this.criacao = criacao;
	}

	public Date getEdicao() {
		return edicao;
	}

	public void setEdicao(Date edicao) {
		this.edicao = edicao;
	}

	public Date getRemocao() {
		return remocao;
	}

	public void setRemocao(Date remocao) {
		this.remocao = remocao;
	}

	public Date getConclusao() {
		return conclusao;
	}

	public void setConclusao(Date conclusao) {
		this.conclusao = conclusao;
	}

	public TaskVO(Long id, String titulo, String status, String descricao, Date criacao, Date edicao, Date remocao,
			Date conclusao) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.status = status;
		this.descricao = descricao;
		this.criacao = criacao;
		this.edicao = edicao;
		this.remocao = remocao;
		this.conclusao = conclusao;
	}
	
	public TaskVO(){
		
	}

	@Override
	public String toString() {
		return "TaskVO [id=" + id + ", titulo=" + titulo + ", status=" + status + ", descricao=" + descricao
				+ ", criacao=" + criacao + ", edicao=" + edicao + ", remocao=" + remocao + ", conclusao=" + conclusao
				+ "]";
	}
	
	

}
