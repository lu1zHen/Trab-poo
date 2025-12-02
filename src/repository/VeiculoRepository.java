package repository;

import dominio.Veiculo;
import java.util.List;
import java.util.Optional;

public interface VeiculoRepository {
    void salvar(Veiculo veiculo);
    Optional<Veiculo> buscarPorPlaca(String placa);
    List<Veiculo> listarTodos();
}