package dominio;

public class Veiculo {
    private final String placa;
    private final String modelo;
    private final Motorista motoristaResponsavel;

    public Veiculo(String placa, String modelo, Motorista motoristaResponsavel) {
        this.placa = placa;
        this.modelo = modelo;
        this.motoristaResponsavel = motoristaResponsavel;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public Motorista getMotoristaResponsavel() {
        return motoristaResponsavel;
    }

    @Override
    public String toString() {
        return String.format("Placa: %s | Modelo: %s | Motorista: %s",
                placa, modelo, motoristaResponsavel.getNome());
    }
}