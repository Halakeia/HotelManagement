//package hotel.bao.view;
//
//import hotel.bao.controller.AuthenticationController;
//import hotel.bao.controller.EstadiaController;
//import hotel.bao.dtos.AuthenticationDTO;
//import hotel.bao.dtos.EstadiaDTO;
//import hotel.bao.dtos.LoginResponseDTO;
//import hotel.bao.entities.Estadia;
//import hotel.bao.entities.Quarto;
//import hotel.bao.entities.Usuario;
//import hotel.bao.repository.UsuarioRepository;
//import hotel.bao.security.config.TokenService;
//import hotel.bao.service.QuartoService;
//import hotel.bao.service.UsuarioService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.*;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.time.format.DateTimeParseException;
//import java.util.Scanner;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//import hotel.bao.dtos.UsuarioDTO;
//import hotel.bao.dtos.QuartoDTO;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//
//@Component
//public class Screen {
//    static Scanner scanner = new Scanner(System.in);
//
//
//    @Autowired
//    private AuthenticationController authController;
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @Autowired
//    private EstadiaController estadiaController;
//
//    @Autowired
//    private QuartoService QuartoService;
//
//    @Autowired
//    private UsuarioService UsuarioService;
//
//    private final RestTemplate restTemplate;
//
//
//    private String authority;
//    private String currentToken;
//
//    public Screen(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public static void saudacao() {
//        System.out.println("==============================================");
//        System.out.println("     Bem-vindo ao sistema do HotelBAO      ");
//        System.out.println("==============================================");
//    }
//
//    public boolean login() {
//        System.out.println("\nLogin");
//        System.out.println("------------------------");
//        System.out.print("Usuário: ");
//        String login = scanner.nextLine().trim();
//
//        System.out.print("Senha: ");
//        String senha = scanner.nextLine().trim();
//
//        if (login.isEmpty() || senha.isEmpty()) {
//            System.out.println("\nERRO: Usuário e senha não podem estar vazios!");
//            return false;
//        }
//
//        try {
//            AuthenticationDTO authDTO = new AuthenticationDTO(login, senha);
//            ResponseEntity<?> response = authController.login(authDTO);
//
//            if (response.getStatusCode().is2xxSuccessful() && response.getBody() instanceof LoginResponseDTO) {
//                LoginResponseDTO loginResponse = (LoginResponseDTO) response.getBody();
//                this.currentToken = loginResponse.token();
//                UserDetails usuario = usuarioRepository.findByLogin(login);
//                authority = usuario.getAuthorities().stream().findFirst().get().getAuthority();
//                System.out.println("\nLogin realizado com sucesso!");
//                return true;
//            } else {
//                System.out.println("\nERRO: Credenciais inválidas!");
//                return false;
//            }
//        } catch (Exception e) {
//            System.out.println("\nERRO: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public void start() {
//        boolean running = true;
//        while (running) {
//            saudacao();
//            if (login()) {
//                running = showMainMenu();
//            } else {
//                System.out.println("Deseja tentar novamente? (S/N)");
//                String resposta = scanner.nextLine();
//                if (!resposta.equalsIgnoreCase("S")) {
//                    running = false;
//                }
//            }
//        }
//    }
//
//    private boolean isAuthorized(String... allowedRoles) {
//        for (String role : allowedRoles) {
//            if (authority.equals(role)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean showMainMenu() {
//        while (true) {
//            boolean shouldExit = false; // Variável de controle para sair do loop
//
//            switch (authority) {
//                case "ROLE_ADMIN":
//                    menuPrincipal();
//                    System.out.print("\nEscolha uma opção: ");
//                    String opcaoAdmin = scanner.nextLine();
//
//                    switch (opcaoAdmin) {
//                        case "0":
//                            System.out.println("Saindo do sistema...");
//                            return false; // Retorna para o método start()
//                        case "1":
//                            // lógica
//                            break;
//                        case "2":
//                            // lógica
//                            break;
//                        case "3":
//                            handleReservation();
//                            break;
//                        default:
//                            System.out.println("\nOpção inválida!");
//                    }
//                    break;
//
//                case "ROLE_CLIENT":
//                    menuCliente();
//                    System.out.print("\nEscolha uma opção: ");
//                    String opcaoClient = scanner.nextLine();
//
//                    switch (opcaoClient) {
//                        case "0":
//                            System.out.println("Saindo do sistema...");
//                            return false; // Retorna para o método start()
//                        case "1":
//                            // Ver reservas
//                            break;
//                        case "2":
//                            break;
//                        default:
//                            System.out.println("\nOpção inválida!");
//                    }
//                    break;
//
//                case "ROLE_NAO_AUTENTICADO":
//                    menuNaoAutenticado();
//                    System.out.print("\nEscolha uma opção: ");
//                    String opcao = scanner.nextLine();
//                    switch (opcao) {
//                        case "0":
//                            System.out.println("Saindo do sistema...");
//                            return false; // Retorna para o método start()
//                        case "1":
//                            break;
//                        case "2":
//                            break;
//                    }
//                    break;
//
//                default:
//                    System.out.println("Role inválida!");
//                    return false; // Retorna para o método start()
//            }
//
//            if (shouldExit) {
//                return false;
//            }
//        }
//    }
//
//    private void menuNaoAutenticado() {
//
//    }
//
//
//    public static void menuPrincipal() {
//        System.out.println("======================== Menu de Opções ======================");
//        System.out.println("""
//                1 - Cadastro de Cliente\s
//                2 - Cadastro de Quarto\s
//                3 - Lançamento de Estadias\
//
//                4 - Listar dados dos Clientes\s
//                5 - Listar dados dos Quartos\s
//                6 - Listar Estadias cadastradas\
//
//                7 - Emitir nota Fiscal
//                8 - Limpar banco de dados
//
//                9 - Relatório - Maior valor da estadia do cliente\
//                10 - Relatório - Menor valor da estadia do clie nte
//                11 - Relatório - Totalizar as estadias do cliente\
//
//                Digite '0' para terminar"""
//
//        );
//    }
//
//    public static void menuCliente() {
//        System.out.println("======================== Menu de Opções do Cliente ======================");
//        System.out.println("""
//                1 - Inserir cliente\s
//                2 - Deletar cliente\s
//                3 - Alterar cliente\s
//
//                Digite '0' para terminar""");
//    }
//
//    public static void menuQuarto() {
//        System.out.println("======================== Menu de Opções do Quarto ======================");
//        System.out.println("""
//                1 - Inserir quarto\s
//                2 - Deletar quarto\s
//                3 - Alterar quarto\s
//
//                Digite '0' para terminar""");
//    }
//
//    public static void menuEstadia() {
//        System.out.println("======================== Menu de Opções da Estadia ======================");
//        System.out.println("""
//                1 - Fazer reserva\s
//                2 - Cancelar reserva\s
//                3 - Alterar reserva\s
//                4 - Listar reservas\s
//
//                Digite '0' para terminar""");
//    }
//
//    // Método para configurar o RestTemplate com timeout
//    @Bean
//    public RestTemplate restTemplate() {
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setConnectTimeout(3000);
//        factory.setReadTimeout(3000);
//        return new RestTemplate(factory);
//    }
//
//    private void handleReservation() {
//        if (!isAuthorized("ROLE_ADMIN", "ROLE_CLIENT")) {
//            System.out.println("Acesso não autorizado para fazer reservas.");
//            return;
//        }
//
//        try {
//            System.out.println("\n=== Nova Estadia ===");
//            EstadiaDTO novaEstadia = new EstadiaDTO();
//

import hotel.bao.entities.ItemNotaFiscal;
import hotel.bao.entities.NotaFiscal;

import java.time.format.DateTimeFormatter;

////            // Coleta ID do cliente
////            System.out.print("Digite o ID do cliente: ");
////            Long clienteId = Long.parseLong(scanner.nextLine().trim());
////            UsuarioDTO cliente = UsuarioService.findById(clienteId);
////            novaEstadia.setCliente(cliente);
////
////            // Coleta ID do quarto
////            System.out.print("Digite o ID do quarto: ");
////            Long quartoId = Long.parseLong(scanner.nextLine().trim());
////            QuartoDTO quarto = QuartoService.findById(quartoId);
////            novaEstadia.setQuarto(quarto);
//
//            // Coleta data de entrada
//            System.out.print("Data de entrada (dd/mm/aaaa): ");
//            String dataEntradaStr = scanner.nextLine().trim();
//            LocalDate dataEntrada = LocalDate.parse(parseData(dataEntradaStr));
//            novaEstadia.setDataEntrada(dataEntrada);
//
//            String dataSaidaStr = parseData(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("ddMMyyyy")));
//            LocalDate dataSaida = LocalDate.parse(parseData(dataSaidaStr));
//            novaEstadia.setDataSaida(dataSaida);
//
//            // Configuração da requisição HTTP
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.setBearerAuth(currentToken); // Usando o token de autenticação
//
//            // Cria o objeto da requisição
//            HttpEntity<EstadiaDTO> requestEntity = new HttpEntity<>(novaEstadia, headers);
//
//            // Faz a requisição POST
//            ResponseEntity<EstadiaDTO> response = restTemplate.exchange(
//                    "http://localhost:8080/estadia",
//                    HttpMethod.POST,
//                    requestEntity,
//                    EstadiaDTO.class
//            );
//
//            if (response.getStatusCode().is2xxSuccessful()) {
//                EstadiaDTO estadiaCriada = response.getBody();
//                System.out.println("\nEstadia criada com sucesso!");
//                System.out.println("ID da estadia: " + estadiaCriada.getId());
//                System.out.println("Cliente: " + estadiaCriada.getCliente().getName());
//                System.out.println("Quarto: " + estadiaCriada.getQuarto().getId());
//                System.out.println("Período: " + estadiaCriada.getDataEntrada() + " até " + estadiaCriada.getDataSaida());
//            } else {
//                System.out.println("\nErro ao criar estadia. Status: " + response.getStatusCode());
//            }
//
//        } catch (DateTimeParseException e) {
//            System.out.println("\nErro: Data inválida. Use o formato dd/mm/aaaa");
//        } catch (NumberFormatException e) {
//            System.out.println("\nErro: ID inválido. Digite apenas números");
//        } catch (HttpClientErrorException e) {
//            System.out.println("\nErro na requisição: " + e.getResponseBodyAsString());
//        } catch (Exception e) {
//            System.out.println("\nErro inesperado: " + e.getMessage());
//        }
//    }
//
//
//    private String parseData(String dataStr) {
//        try {
//            // Remove qualquer caractere não numérico
//            String numerosApenas = dataStr.replaceAll("[^0-9]", "");
//
//            // Verifica se a string tem exatamente 8 dígitos
//            if (numerosApenas.length() != 8) {
//                throw new IllegalArgumentException("Data deve conter 8 dígitos no formato ddmmaaaa");
//            }
//
//            // Extrai dia, mês e ano
//            String dia = numerosApenas.substring(0, 2);
//            String mes = numerosApenas.substring(2, 4);
//            String ano = numerosApenas.substring(4, 8);
//
//            // Valida os valores
//            int diaInt = Integer.parseInt(dia);
//            int mesInt = Integer.parseInt(mes);
//            int anoInt = Integer.parseInt(ano);
//
//            // Validações básicas
//            if (mesInt < 1 || mesInt > 12) {
//                throw new IllegalArgumentException("Mês inválido");
//            }
//            if (diaInt < 1 || diaInt > 31) {
//                throw new IllegalArgumentException("Dia inválido");
//            }
//            if (anoInt < 1900 || anoInt > 9999) {
//                throw new IllegalArgumentException("Ano inválido");
//            }
//
//            // Monta a data no formato americano
//            return String.format("%s-%s-%s", ano, mes, dia);
//        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException("Formato de data inválido", e);
//        }
//    }
//private void imprimirNota(NotaFiscal nota) {
//    // Aqui você pode implementar a lógica de impressão real
//    // Por exemplo, gerar um PDF ou enviar para uma impressora
//
//    StringBuilder sb = new StringBuilder();
//    sb.append("================================\n");
//    sb.append("         NOTA FISCAL            \n");
//    sb.append("================================\n");
//    sb.append("Número: ").append(nota.getNumero()).append("\n");
//    sb.append("Data: ").append(nota.getDataEmissao().format(
//            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
//    )).append("\n\n");
//
//    // Dados do cliente
//    sb.append("CLIENTE\n");
//    sb.append("Nome: ").append(nota.getCliente().getName()).append("\n");
//    sb.append("Email: ").append(nota.getCliente().getEmail()).append("\n");
//    sb.append("Celular: ").append(nota.getCliente().getCelular()).append("\n\n");
//
//    // Itens
//    sb.append("ITENS\n");
//    for (ItemNotaFiscal item : nota.getItens()) {
//        sb.append(item.getDescricao())
//                .append(" - ")
//                .append(item.getQuantidade())
//                .append(" x R$ ")
//                .append(String.format("%.2f", item.getValorUnitario()))
//                .append(" = R$ ")
//                .append(String.format("%.2f", item.getValorTotal()))
//                .append("\n");
//    }
//
//    sb.append("\nTOTAL: R$ ")
//            .append(String.format("%.2f", nota.getValorTotal()))
//            .append("\n");
//    sb.append("================================\n");
//
//    // Aqui você pode implementar a impressão real
//    System.out.println(sb.toString());
//}
//
//}
