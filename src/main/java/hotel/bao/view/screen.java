package hotel.bao.view;

public class screen {
    public static void saudacao(){
        System.out.println("==============================================");
        System.out.println("     Bem-vindo ao sistema do HotelBAO      ");
        System.out.println("==============================================");
    }
    public static void menuPrincipal(){
        System.out.println("======================== Menu de Opções ======================");
        System.out.println("""
                        1 - Cadastro de Cliente\s
                        2 - Cadastro de Quarto\s
                        3 - Lançamento de Estadias\
                        
                        4 - Listar dados dos Clientes\s
                        5 - Listar dados dos Quartos\s
                        6 - Listar Estadias cadastradas\
                        
                        7 - Emitir nota Fiscal
                        8 - Limpar banco de dados
                        
                        9 - Relatório - Maior valor da estadia do cliente\
                        10 - Relatório - Menor valor da estadia do cliente
                        11 - Relatório - Totalizar as estadias do cliente\
                        
                        Digite '0' para terminar"""

                );
    }
    public static void menuCliente(){
        System.out.println("======================== Menu de Opções do Cliente ======================");
        System.out.println("""
                1 - Inserir cliente\s
                2 - Deletar cliente\s
                3 - Alterar cliente\s
                
                Digite '0' para terminar""");
    }
    public static void menuQuarto(){
        System.out.println("======================== Menu de Opções do Quarto ======================");
        System.out.println("""
                1 - Inserir quarto\s
                2 - Deletar quarto\s
                3 - Alterar quarto\s
                
                Digite '0' para terminar""");
    }
}
