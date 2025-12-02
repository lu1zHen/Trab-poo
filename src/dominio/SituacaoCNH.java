package dominio;

public enum SituacaoCNH {
    REGULAR("Regular"),
    EM_RISCO("Em risco de suspensão"),
    SUSPENSA("CNH Suspensa");

    private final String descricao;

    SituacaoCNH(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}