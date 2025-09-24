package br.edu.utfpr.lembretes.service;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.utfpr.lembretes.dto.LembreteDTO;
import br.edu.utfpr.lembretes.exception.NotFoundException;
import br.edu.utfpr.lembretes.model.Lembrete;
import br.edu.utfpr.lembretes.model.Pessoa;
import br.edu.utfpr.lembretes.repository.LembreteRepository;
import br.edu.utfpr.lembretes.repository.PessoaRepository;

@Service
public class LembreteService {

    private final LembreteRepository lembreteRepo;
    private final PessoaRepository pessoaRepo;

    public LembreteService(LembreteRepository lembreteRepo, PessoaRepository pessoaRepo) {
        this.lembreteRepo = lembreteRepo;
        this.pessoaRepo = pessoaRepo;
    }

    public LembreteDTO salvar(String pessoaId, LembreteDTO lembreteDTO) {
        Pessoa pessoa = pessoaRepo.findById(UUID.fromString(pessoaId))
                .orElseThrow(() -> new NotFoundException("Pessoa " + pessoaId + " não encontrada."));

        Lembrete lembrete = new Lembrete();
        BeanUtils.copyProperties(lembreteDTO, lembrete, "id");
        lembrete.setPessoa(pessoa);

        Lembrete salvo = lembreteRepo.save(lembrete);
        return new LembreteDTO(salvo.getId(), salvo.getMensagem(), salvo.getDataHora());
    }

    public Page<LembreteDTO> listarPorPessoa(String pessoaId, int pagina, int tamanho) {
        return lembreteRepo.findByPessoaId(UUID.fromString(pessoaId), PageRequest.of(pagina, tamanho))
                .map(l -> new LembreteDTO(l.getId(), l.getMensagem(), l.getDataHora()));
    }
}