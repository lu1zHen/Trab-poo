package dominio;

import java.time.LocalDateTime;

public class Infracao {
    private final TipoInfracao tipo;
    private final double valorMulta;
    private final int pontos;
    private final Veiculo veiculoEnvolvido;
    private final LocalDateTime dataHora;

    public Infracao(TipoInfracao tipo, Veiculo veiculoEnvolvido) {
        this.tipo = tipo;
        this.valorMulta = tipo.getValor();
        this.pontos = tipo.getPontos();
        this.veiculoEnvolvido = veiculoEnvolvido;
        this.dataHora = LocalDateTime.now();
    }

    public TipoInfracao getTipo() {
        return tipo;
    }

    public double getValorMulta() {
        return valorMulta;
    }

    public int getPontos() {
        return pontos;
    }

    public Veiculo getVeiculoEnvolvido() {
        return veiculoEnvolvido;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        return String.format("%s - %s | %d pontos | R$ %.2f | Veículo: %s",
                dataHora.toLocalDate(), tipo, pontos, valorMulta,
                veiculoEnvolvido.getPlaca());
    }
}