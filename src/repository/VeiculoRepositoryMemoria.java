package repository;

import dominio.Veiculo;
import java.util.*;

public class VeiculoRepositoryMemoria implements VeiculoRepository {
    private final Map<String, Veiculo> veiculos = new HashMap<>();

    @Override
    public void salvar(Veiculo veiculo) {
        veiculos.put(veiculo.getPlaca(), veiculo);
    }

    @Override
    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return Optional.ofNullable(veiculos.get(placa));
    }

    @Override
    public List<Veiculo> listarTodos() {
        return new ArrayList<>(veiculos.values());
    }
}