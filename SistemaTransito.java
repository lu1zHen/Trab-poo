import java.time.LocalDateTime;
import java.util.*;

enum TipoInfracao {
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
enum SituacaoCNH {
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
class Motorista {
    private final String nome;
    private final String numeroCnh;
    private int pontosAcumulados;

    public Motorista(String nome, String numeroCnh) {
        this.nome = nome;
        this.numeroCnh = numeroCnh;
        this.pontosAcumulados = 0;
    }
    public String getNome() { return nome; }
    public String getNumeroCnh() { return numeroCnh; }
    public int getPontosAcumulados() { return pontosAcumulados; }

    public void adicionarPontos(int pontos) {
        this.pontosAcumulados += pontos;
    }
    public void zerarPontos() {
        this.pontosAcumulados = 0;
    }
    @Override
    public String toString() {
        return String.format("CNH: %s | Nome: %s | Pontos: %d", numeroCnh, nome, pontosAcumulados);
    }
}
class Veiculo {
    private final String placa;
    private final String modelo;
    private final Motorista motoristaResponsavel;

    public Veiculo(String placa, String modelo, Motorista motoristaResponsavel) {
        this.placa = placa;
        this.modelo = modelo;
        this.motoristaResponsavel = motoristaResponsavel;
    }
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public Motorista getMotoristaResponsavel() { return motoristaResponsavel; }

    @Override
    public String toString() {
        return String.format("Placa: %s | Modelo: %s | Motorista: %s",
                placa, modelo, motoristaResponsavel.getNome());
    }
}
class Infracao {
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
    public TipoInfracao getTipo() { return tipo; }
    public double getValorMulta() { return valorMulta; }
    public int getPontos() { return pontos; }
    public Veiculo getVeiculoEnvolvido() { return veiculoEnvolvido; }
    public LocalDateTime getDataHora() { return dataHora; }

    @Override
    public String toString() {
        return String.format("%s - %s | %d pontos | R$ %.2f | Veículo: %s",
                dataHora.toLocalDate(), tipo, pontos, valorMulta, veiculoEnvolvido.getPlaca());
    }
}
interface CalculadoraInfracao {
    double calcularValorMulta(Infracao infracao);
    int calcularPontos(Infracao infracao);
}
interface AvaliadorSituacaoCNH {
    SituacaoCNH avaliarSituacao(Motorista motorista);
}
interface MotoristaRepository {
    void salvar(Motorista motorista);
    Optional<Motorista> buscarPorCnh(String numeroCnh);
    List<Motorista> listarTodos();
}
interface VeiculoRepository {
    void salvar(Veiculo veiculo);
    Optional<Veiculo> buscarPorPlaca(String placa);
    List<Veiculo> listarTodos();
}
interface InfracaoRepository {
    void salvar(Infracao infracao);
    List<Infracao> listarPorMotorista(String numeroCnh);
    List<Infracao> listarTodos();
}
class MotoristaRepositoryMemoria implements MotoristaRepository {
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
class VeiculoRepositoryMemoria implements VeiculoRepository {
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

class InfracaoRepositoryMemoria implements InfracaoRepository {
    private final List<Infracao> infracoes = new ArrayList<>();
    @Override
    public void salvar(Infracao infracao) {
        infracoes.add(infracao);
    }
    @Override
    public List<Infracao> listarPorMotorista(String numeroCnh) {
        List<Infracao> resultado = new ArrayList<>();
        for (Infracao infracao : infracoes) {
            if (infracao.getVeiculoEnvolvido().getMotoristaResponsavel().getNumeroCnh().equals(numeroCnh)) {
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

class CalculadoraInfracaoPadrao implements CalculadoraInfracao {

    @Override
    public double calcularValorMulta(Infracao infracao) {
        return infracao.getValorMulta();
    }

    @Override
    public int calcularPontos(Infracao infracao) {
        return infracao.getPontos();
    }
}

class AvaliadorSituacaoCNHPadrao implements AvaliadorSituacaoCNH {

    private static final int PONTOS_REGULAR = 10;
    private static final int PONTOS_SUSPENSA = 20;

    @Override
    public SituacaoCNH avaliarSituacao(Motorista motorista) {
        int pontos = motorista.getPontosAcumulados();

        if (pontos < PONTOS_REGULAR) {
            return SituacaoCNH.REGULAR;
        } else if (pontos < PONTOS_SUSPENSA) {
            return SituacaoCNH.EM_RISCO;
        } else {
            return SituacaoCNH.SUSPENSA;
        }
    }
}

class RegistroInfracaoService {

    private final InfracaoRepository infracaoRepository;
    private final CalculadoraInfracao calculadoraInfracao;
    private final AvaliadorSituacaoCNH avaliadorSituacaoCNH;

    public RegistroInfracaoService(
            InfracaoRepository infracaoRepository,
            CalculadoraInfracao calculadoraInfracao,
            AvaliadorSituacaoCNH avaliadorSituacaoCNH) {
        this.infracaoRepository = infracaoRepository;
        this.calculadoraInfracao = calculadoraInfracao;
        this.avaliadorSituacaoCNH = avaliadorSituacaoCNH;
    }

    public Infracao registrarInfracao(TipoInfracao tipo, Veiculo veiculo) {
        Motorista motorista = veiculo.getMotoristaResponsavel();

        Infracao infracao = new Infracao(tipo, veiculo);

        int pontos = calculadoraInfracao.calcularPontos(infracao);
        motorista.adicionarPontos(pontos);

        infracaoRepository.salvar(infracao);

        return infracao;
    }

    public SituacaoCNH consultarSituacaoMotorista(Motorista motorista) {
        return avaliadorSituacaoCNH.avaliarSituacao(motorista);
    }

    public double consultarValorMulta(Infracao infracao) {
        return calculadoraInfracao.calcularValorMulta(infracao);
    }
}
class RelatorioService {

    private final MotoristaRepository motoristaRepository;
    private final InfracaoRepository infracaoRepository;
    private final AvaliadorSituacaoCNH avaliadorSituacaoCNH;

    public RelatorioService(
            MotoristaRepository motoristaRepository,
            InfracaoRepository infracaoRepository,
            AvaliadorSituacaoCNH avaliadorSituacaoCNH) {
        this.motoristaRepository = motoristaRepository;
        this.infracaoRepository = infracaoRepository;
        this.avaliadorSituacaoCNH = avaliadorSituacaoCNH;
    }
    public void emitirRelatorioMotorista(String numeroCnh) {
        Optional<Motorista> motoristaOpt = motoristaRepository.buscarPorCnh(numeroCnh);
        if (motoristaOpt.isPresent()) {
            Motorista motorista = motoristaOpt.get();
            List<Infracao> infracoes = infracaoRepository.listarPorMotorista(numeroCnh);
            SituacaoCNH situacao = avaliadorSituacaoCNH.avaliarSituacao(motorista);

            System.out.println("\n" + "=".repeat(50));
            System.out.println("RELATÓRIO DO MOTORISTA");
            System.out.println("=".repeat(50));
            System.out.println("Nome: " + motorista.getNome());
            System.out.println("CNH: " + motorista.getNumeroCnh());
            System.out.println("Pontos acumulados: " + motorista.getPontosAcumulados());
            System.out.println("Situação: " + situacao.getDescricao());
            System.out.println("Infrações registradas: " + infracoes.size());

            if (!infracoes.isEmpty()) {
                System.out.println("\nDETALHES DAS INFRAÇÕES:");
                for (Infracao infracao : infracoes) {
                    System.out.printf(" - %s%n", infracao);
                }
            }
            System.out.println("=".repeat(50) + "\n");
        } else {
            System.out.println("❌ Motorista não encontrado!");
        }
    }
    public void emitirRelatorioGeral() {
        List<Motorista> motoristas = motoristaRepository.listarTodos();
        List<Infracao> todasInfracoes = infracaoRepository.listarTodos();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("RELATÓRIO GERAL DO SISTEMA");
        System.out.println("=".repeat(50));
        System.out.println("Total de motoristas cadastrados: " + motoristas.size());
        System.out.println("Total de infrações registradas: " + todasInfracoes.size());

        for (Motorista motorista : motoristas) {
            SituacaoCNH situacao = avaliadorSituacaoCNH.avaliarSituacao(motorista);
            List<Infracao> infracoesMotorista = infracaoRepository.listarPorMotorista(motorista.getNumeroCnh());

            System.out.printf("- %s: %d pontos (%s) - %d infrações%n",
                    motorista.getNome(),
                    motorista.getPontosAcumulados(),
                    situacao.getDescricao(),
                    infracoesMotorista.size());
        }
        System.out.println("=".repeat(50) + "\n");
    }
    public void listarTodosMotoristas() {
        List<Motorista> motoristas = motoristaRepository.listarTodos();
        if (motoristas.isEmpty()) {
            System.out.println("❌ Nenhum motorista cadastrado!");
            return;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("MOTORISTAS CADASTRADOS");
        System.out.println("=".repeat(50));
        for (Motorista motorista : motoristas) {
            SituacaoCNH situacao = avaliadorSituacaoCNH.avaliarSituacao(motorista);
            System.out.printf("- %s | Situação: %s%n", motorista, situacao.getDescricao());
        }
        System.out.println("=".repeat(50) + "\n");
    }
    public void listarTodosVeiculos() {
        List<Veiculo> veiculos = ((VeiculoRepositoryMemoria) motoristaRepository).listarTodos();
        if (veiculos.isEmpty()) {
            System.out.println("❌ Nenhum veículo cadastrado!");
            return;
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("VEÍCULOS CADASTRADOS");
        System.out.println("=".repeat(50));
        for (Veiculo veiculo : veiculos) {
            System.out.printf("- %s%n", veiculo);
        }
        System.out.println("=".repeat(50) + "\n");
    }
}
class InterfaceUsuario {
    private final Scanner scanner;
    private final MotoristaRepository motoristaRepository;
    private final VeiculoRepository veiculoRepository;
    private final RegistroInfracaoService registroService;
    private final RelatorioService relatorioService;

    public InterfaceUsuario() {
        this.scanner = new Scanner(System.in);
        this.motoristaRepository = new MotoristaRepositoryMemoria();
        this.veiculoRepository = new VeiculoRepositoryMemoria();
        this.registroService = new RegistroInfracaoService(
                new InfracaoRepositoryMemoria(),
                new CalculadoraInfracaoPadrao(),
                new AvaliadorSituacaoCNHPadrao()
        );
        this.relatorioService = new RelatorioService(
                motoristaRepository,
                new InfracaoRepositoryMemoria(),
                new AvaliadorSituacaoCNHPadrao()
        );
    }
    public void exibirMenu() {
        while (true) {
            System.out.println("\n🚗 SISTEMA DE CONTROLE DE TRÂNSITO 🚗");
            System.out.println("1. Cadastrar Motorista");
            System.out.println("2. Cadastrar Veículo");
            System.out.println("3. Registrar Infração");
            System.out.println("4. Consultar Situação do Motorista");
            System.out.println("5. Listar Motoristas");
            System.out.println("6. Listar Veículos");
            System.out.println("7. Relatório Geral");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarMotorista();
                    break;
                case 2:
                    cadastrarVeiculo();
                    break;
                case 3:
                    registrarInfracao();
                    break;
                case 4:
                    consultarSituacaoMotorista();
                    break;
                case 5:
                    relatorioService.listarTodosMotoristas();
                    break;
                case 6:
                    relatorioService.listarTodosVeiculos();
                    break;
                case 7:
                    relatorioService.emitirRelatorioGeral();
                    break;
                case 8:
                    System.out.println("Saindo do sistema...");
                    return;
                default:
                    System.out.println("❌ Opção inválida!");
            }
        }
    }
    private void cadastrarMotorista() {
        System.out.println("\n--- CADASTRAR MOTORISTA ---");

        System.out.print("Nome do motorista: ");
        String nome = scanner.nextLine();

        System.out.print("Número da CNH: ");
        String numeroCnh = scanner.nextLine();

        if (motoristaRepository.buscarPorCnh(numeroCnh).isPresent()) {
            System.out.println("❌ Já existe um motorista com esta CNH!");
            return;
        }

        Motorista motorista = new Motorista(nome, numeroCnh);
        motoristaRepository.salvar(motorista);

        System.out.println("✅ Motorista cadastrado com sucesso!");
    }
    private void cadastrarVeiculo() {
        System.out.println("\n--- CADASTRAR VEÍCULO ---");
        List<Motorista> motoristas = motoristaRepository.listarTodos();
        if (motoristas.isEmpty()) {
            System.out.println("❌ Não há motoristas cadastrados. Cadastre um motorista primeiro!");
            return;
        }
        System.out.println("Motoristas disponíveis:");
        for (int i = 0; i < motoristas.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, motoristas.get(i));
        }
        System.out.print("Selecione o número do motorista: ");
        int indiceMotorista = lerInteiro() - 1;

        if (indiceMotorista < 0 || indiceMotorista >= motoristas.size()) {
            System.out.println("❌ Índice inválido!");
            return;
        }
        Motorista motorista = motoristas.get(indiceMotorista);
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine();
        System.out.print("Modelo do veículo: ");
        String modelo = scanner.nextLine();

        if (veiculoRepository.buscarPorPlaca(placa).isPresent()) {
            System.out.println("❌ Já existe um veículo com esta placa!");
            return;
        }
        Veiculo veiculo = new Veiculo(placa, modelo, motorista);
        veiculoRepository.salvar(veiculo);

        System.out.println("✅ Veículo cadastrado com sucesso!");
    }
    private void registrarInfracao() {
        System.out.println("\n--- REGISTRAR INFRAÇÃO ---");
        List<Veiculo> veiculos = veiculoRepository.listarTodos();
        if (veiculos.isEmpty()) {
            System.out.println("❌ Não há veículos cadastrados. Cadastre um veículo primeiro!");
            return;
        }
        System.out.println("Veículos disponíveis:");
        for (int i = 0; i < veiculos.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, veiculos.get(i));
        }
        System.out.print("Selecione o número do veículo: ");
        int indiceVeiculo = lerInteiro() - 1;
        if (indiceVeiculo < 0 || indiceVeiculo >= veiculos.size()) {
            System.out.println("❌ Índice inválido!");
            return;
        }
        Veiculo veiculo = veiculos.get(indiceVeiculo);
        System.out.println("\nTipos de infração:");
        TipoInfracao[] tipos = TipoInfracao.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.printf("%d. %s (%d pontos - R$ %.2f)%n",
                    i + 1, tipos[i], tipos[i].getPontos(), tipos[i].getValor());
        }
        System.out.print("Selecione o tipo de infração: ");
        int indiceTipo = lerInteiro() - 1;
        if (indiceTipo < 0 || indiceTipo >= tipos.length) {
            System.out.println("❌ Índice inválido!");
            return;
        }
        TipoInfracao tipo = tipos[indiceTipo];
        Infracao infracao = registroService.registrarInfracao(tipo, veiculo);
        System.out.println("✅ Infração registrada com sucesso!");
        System.out.printf("📝 Detalhes: %s - %d pontos - R$ %.2f%n",
                tipo, tipo.getPontos(), tipo.getValor());
        Motorista motorista = veiculo.getMotoristaResponsavel();
        SituacaoCNH situacao = registroService.consultarSituacaoMotorista(motorista);
        System.out.printf("📊 Situação do motorista: %s (%d pontos)%n",
                situacao.getDescricao(), motorista.getPontosAcumulados());
    }

    private void consultarSituacaoMotorista() {
        System.out.println("\n--- CONSULTAR SITUAÇÃO DO MOTORISTA ---");
        List<Motorista> motoristas = motoristaRepository.listarTodos();
        if (motoristas.isEmpty()) {
            System.out.println("❌ Não há motoristas cadastrados!");
            return;
        }
        System.out.println("Motoristas disponíveis:");
        for (int i = 0; i < motoristas.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, motoristas.get(i));
        }

        System.out.print("Selecione o número do motorista: ");
        int indiceMotorista = lerInteiro() - 1;
        if (indiceMotorista < 0 || indiceMotorista >= motoristas.size()) {
            System.out.println("❌ Índice inválido!");
            return;
        }
        Motorista motorista = motoristas.get(indiceMotorista);
        relatorioService.emitirRelatorioMotorista(motorista.getNumeroCnh());
    }
    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("❌ Por favor, digite um número válido: ");
            }
        }
    }
}
public class SistemaTransito {
    public static void main(String[] args) {
        System.out.println("🚗 BEM-VINDO AO Sistema de Transito 🚗");
        System.out.println("Sistema de Controle de Trânsito Municipal");

        InterfaceUsuario interfaceUsuario = new InterfaceUsuario();
        interfaceUsuario.exibirMenu();
    }
}