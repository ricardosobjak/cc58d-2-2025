package br.edu.utfpr.lembretes.service;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.edu.utfpr.lembretes.dto.PessoaDTO;
import br.edu.utfpr.lembretes.exception.NotFoundException;
import br.edu.utfpr.lembretes.model.Pessoa;
import br.edu.utfpr.lembretes.repository.PessoaRepository;

/**
 * Serviço para gerenciar operações relacionadas a pessoas.
 * Ele fornece métodos CRUD para pessoas.
 * 
 * Utiliza o repositório PessoaRepository para interagir com o banco de dados.
 */
@Service
public class PessoaService {
    Logger logger = Logger.getLogger(PessoaService.class.getName());

    // Injeção do repositório de pessoa
    private final PessoaRepository repository;

    /**
     * Construtor da classe PessoaService.
     * Injeta a dependência do repositório de pessoa.
     * 
     * @param repository
     */
    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    /**
     * Salva uma nova pessoa no banco de dados.
     * Converte um objeto PessoaDTO para a entidade Pessoa e a salva.
     * 
     * @param pessoaDTO
     * @return
     */
    public Pessoa salvar(PessoaDTO pessoaDTO) {
        var pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa, "id");

        logger.info("Criando pessoa: " + pessoa);

        return repository.save(pessoa);
    }

    /**
     * Lista todas as pessoas com paginação.
     * 
     * @param pagina
     * @param tamanho
     * @return
     */
    public Page<Pessoa> listarTodos(int pagina, int tamanho) {
        return repository.findAll(PageRequest.of(pagina, tamanho));
    }

    /**
     * Busca uma pessoa pelo ID.
     * 
     * @param id
     * @return
     */
    public Pessoa buscarPorId(String id) {
        return repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("Pessoa não encontrada com ID: " + id));
    }

    /**
     * Deleta uma pessoa pelo ID.
     * 
     * @param id
     */
    public void deletar(String id) {
        logger.info("Deletando pessoa com ID: " + id);

        repository.delete(buscarPorId(id));
    }

    /**
     * Atualiza uma pessoa existente.
     * Converte um objeto PessoaDTO para a entidade Pessoa e a atualiza.
     * 
     * @param id
     * @param pessoaDTO
     * @return
     */
    public Pessoa atualizar(String id, PessoaDTO pessoaDTO) {
        var pessoaExistente = buscarPorId(id);
        BeanUtils.copyProperties(pessoaDTO, pessoaExistente, "id");

        logger.info("Atualizando pessoa: " + pessoaExistente);

        return repository.save(pessoaExistente);
    }
}