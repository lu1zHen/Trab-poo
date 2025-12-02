package repository;

import dominio.Motorista;
import java.util.*;

public class MotoristaRepositoryMemoria implements MotoristaRepository {
    private final Map<String, Motorista> motoristas = new HashMap<>();

    @Override
    public void salvar(Motorista motorista) {
        motoristas.put(motorista.getNumeroCnh(), motorista);
    }

    @Override
    public Optional<Motorista> buscarPorCnh(String numeroCnh) {
        return Optional.ofNullable(motoristas.get(numeroCnh));
    }

    @Override
    public List<Motorista> listarTodos() {
        return new ArrayList<>(motoristas.values());
    }
}