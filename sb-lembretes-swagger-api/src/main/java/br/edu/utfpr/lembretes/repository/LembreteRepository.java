package br.edu.utfpr.lembretes.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.lembretes.model.Lembrete;

public interface LembreteRepository extends JpaRepository<Lembrete, UUID> {
    Page<Lembrete> findByPessoaId(UUID pessoaId, Pageable pageable);
}
