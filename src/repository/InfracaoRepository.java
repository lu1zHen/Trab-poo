package repository;

import dominio.Infracao;
import java.util.List;

public interface InfracaoRepository {
    void salvar(Infracao infracao);
    List<Infracao> listarPorMotorista(String numeroCnh);
    List<Infracao> listarTodos();
}