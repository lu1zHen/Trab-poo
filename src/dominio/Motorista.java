package dominio;

public class Motorista {
    private final String nome;
    private final String numeroCnh;
    private int pontosAcumulados;

    public Motorista(String nome, String numeroCnh) {
        this.nome = nome;
        this.numeroCnh = numeroCnh;
        this.pontosAcumulados = 0;
    }

    public String getNome() {
        return nome;
    }

    public String getNumeroCnh() {
        return numeroCnh;
    }

    public int getPontosAcumulados() {
        return pontosAcumulados;
    }

    public void adicionarPontos(int pontos) {
        this.pontosAcumulados += pontos;
    }

    public void zerarPontos() {
        this.pontosAcumulados = 0;
    }

    @Override
    public String toString() {
        return String.format("CNH: %s | Nome: %s | Pontos: %d",
                numeroCnh, nome, pontosAcumulados);
    }
}