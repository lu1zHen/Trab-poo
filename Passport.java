class Pessoa {
    private String nome;
    private DocumentoPassaporte passaporte;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public DocumentoPassaporte getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(DocumentoPassaporte passaporte) {
        this.passaporte = passaporte;
    }
}

class DocumentoPassaporte {
    private String numero;
    private Pessoa dono;

    public DocumentoPassaporte(String numero, Pessoa dono) {
        this.numero = numero;
        this.dono = dono;
    }

    public String getNumero() {
        return numero;
    }

    public Pessoa getDono() {
        return dono;
    }
}

public class Passport {
    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa("Luiz");
        DocumentoPassaporte passport = new DocumentoPassaporte("AB123456", pessoa);
        pessoa.setPassaporte(passport);

        System.out.println("Nome: " + pessoa.getNome());
        System.out.println("Passaporte: " + pessoa.getPassaporte().getNumero());
        System.out.println("Dono do passaporte: " + passport.getDono().getNome());
    }
}