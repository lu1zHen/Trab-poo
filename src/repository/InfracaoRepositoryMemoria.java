package repository;

import dominio.Infracao;
import java.util.ArrayList;
import java.util.List;

public class InfracaoRepositoryMemoria implements InfracaoRepository {
    private final List<Infracao> infracoes = new ArrayList<>();

    @Override
    public void salvar(Infracao infracao) {
        infracoes.add(infracao);
    }

    @Override
    public List<Infracao> listarPorMotorista(String numeroCnh) {
        List<Infracao> resultado = new ArrayList<>();
        for (Infracao infracao : infracoes) {
            if (infracao.getVeiculoEnvolvido().getMotoristaResponsavel()
                    .getNumeroCnh().equals(numeroCnh)) {
                resultado.add(infracao);
            }
        }
        return resultado;
    }

    @Override
    public List<Infracao> listarTodos() {
        return new ArrayList<>(infracoes);
    }
}