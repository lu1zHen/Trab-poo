package repository;

import dominio.Motorista;
import java.util.List;
import java.util.Optional;

public interface MotoristaRepository {
    void salvar(Motorista motorista);
    Optional<Motorista> buscarPorCnh(String numeroCnh);
    List<Motorista> listarTodos();
}