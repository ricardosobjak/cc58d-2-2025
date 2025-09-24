package br.edu.utfpr.lembretes.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.lembretes.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {}