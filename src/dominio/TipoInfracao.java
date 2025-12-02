package dominio;

public enum TipoInfracao {
    LEVE(3, 88.30),
    MEDIA(4, 130.16),
    GRAVE(5, 195.23),
    GRAVISSIMA(7, 293.47);

    private final int pontos;
    private final double valor;

    TipoInfracao(int pontos, double valor) {
        this.pontos = pontos;
        this.valor = valor;
    }

    public int getPontos() {
        return pontos;
    }

    public double getValor() {
        return valor;
    }
}