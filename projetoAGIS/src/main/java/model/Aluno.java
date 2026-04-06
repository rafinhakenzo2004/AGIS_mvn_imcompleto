package model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Aluno {
	private String ra;
	private String cpf;
	private String nome;
	private String nome_social;
	private LocalDate dt_nasc;
	private String telefone;
	private String email_pessoal;
	private String email_corporativo;
	private LocalDate dt_con_2grau;
	private String instituicao_2grau;
	private int pt_vestibular;
	private int pos_vestibular;
	private int ano_ingresso;
	private int sem_ingresso;
	private int sem_lim_grad;
	private int ano_lim_grad;
	private String turno;
	private int curso_codigo;
}
