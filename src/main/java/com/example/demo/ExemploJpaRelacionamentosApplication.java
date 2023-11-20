package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExemploJpaRelacionamentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExemploJpaRelacionamentosApplication.class, args);
	}
	@Bean
	public CommandLineRunner inserindoAlunoCurso(AlunoRepositorio alunoRepositorio, CursoRepositorio cursoRepositorio) {
		return (args) -> {

			for (int i = 0; i < 10; i++) {
				Aluno aluno = new Aluno();
				aluno.nome = ("Melo" + i);

				aluno = alunoRepositorio.save(aluno);
				System.out.println(aluno.alunoId);
			}

			for (int i = 0; i < 10; i++) {
				Curso curso = new Curso();
				curso.nome = "Curso" + i;
				curso = cursoRepositorio.save(curso);
				System.out.println(curso.cursoId + " " + curso.nome);
			}
			

		};
	}
//	@Primary
	@Bean
	public CommandLineRunner inserindoCursosDoAluno(AlunoRepositorio alunoRepositorio, CursoRepositorio cursoRepositorio) {
		return (args) -> {
			List<Curso> cursos = cursoRepositorio.findAll();
			Optional<Aluno> aluno = alunoRepositorio.findById(2l);
			if (aluno.isPresent()) {
				aluno.get().cursos = cursos;
				alunoRepositorio.save(aluno.get());
			}
		};
	}

	@Bean
	public CommandLineRunner buscandoAluno(AlunoRepositorio alunoRepositorio, CursoRepositorio cursoRepositorio) {
		return (args) -> {
			
			Optional<Aluno> a = alunoRepositorio.findById(1l);
			if (a.isPresent()) {
				for (Curso obj : a.get().cursos) {
					System.out.println(obj.cursoId + " " + obj.nome);
				}
				for (DisciplinaAluno obj : a.get().disciplinaAluno) {
					System.out.println(obj.disciplina.nome + " " + obj.notaAv1);
				}

			}
		};
	}
	
//	@Bean
	public CommandLineRunner buscandoCurso(AlunoRepositorio alunoRepositorio, CursoRepositorio cursoRepositorio) {
		return (args) -> {
			Optional<Curso> a = cursoRepositorio.findById(1l);
			if (a.isPresent()) {
				for (Aluno obj : a.get().alunos) {
					System.out.println(obj.alunoId + " " + obj.nome);
				}

			}
		};
	}
	
//	@Bean
	public CommandLineRunner inserindoDisciplinas(DisciplinaRepositorio disciplinaRepositorio) {
		return (args) -> {

			for (int i = 0; i < 10; i++) {
				Disciplina disciplina = new Disciplina();
				disciplina.nome = ("Melo" + i);

				disciplina = disciplinaRepositorio.save(disciplina);
				System.out.println(disciplina.disciplinaId);
			}
		};
	}
	
//	@Bean
	public CommandLineRunner inserindoNotaAluno(AlunoRepositorio alunoRepositorio, DisciplinaRepositorio disciplinaRepositorio, DisciplinaAlunoRepositorio disciplinaAlunoRepositorio) {
		return (args) -> {
			Optional<Aluno> a = alunoRepositorio.findById(1l);
			if (a.isPresent()) {
				Optional<Disciplina> d = disciplinaRepositorio.findById(2l);
				if (d.isPresent()) {
					DisciplinaAluno da = new DisciplinaAluno();
					da.disciplina = d.get();
					da.aluno = a.get();
					da.notaAv1 = 9;
					da = disciplinaAlunoRepositorio.save(da);
					System.out.println("Salvo");
				}

			}
		};
	}
}
