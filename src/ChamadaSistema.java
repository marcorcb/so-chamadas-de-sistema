/**
 * Sistemas Operacionais - PUC Minas 2017
 * Federico Janses, Marco Braga, Taina Viriato
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ChamadaSistema {

    public static void main(String[] args) {
        Scanner arq = new Scanner(System.in);
        int option;
        String diretorio;
        String arquivo;
        String diretorioAtual = System.getProperty("user.dir");

        do {
            System.out.println("Você está no diretório: " + diretorioAtual + "\n");
            System.out.println("Entre com o número desejado:");
            System.out.println("0 - Sair");
            System.out.println("1 - Mudar diretório");
            System.out.println("2 - Listar conteúdo do diretório");
            System.out.println("3 - Apagar arquivos");
            System.out.println("4 - Ler e mostrar conteúdos de texto");
            System.out.println("5 - Criar arquivos texto temporários");

            option = arq.nextInt();

            while (option > 5 || option < 0) {
                System.out.println("Valor inválido. Digite novamente");
                option = arq.nextInt();
            }

            switch (option) {
                case 0:
                    arq.close();
                    break;
                case 1:
                    System.out.println("Digite o novo diretório");
                    diretorio = arq.next();

                    if (checarDiretorio(diretorio)) {
                        System.out.println("Diretório alterado com sucesso");
                        diretorioAtual = diretorio;
                    } else {
                        System.out.println("Diretório inválido");
                    }

                    break;
                case 2:
                    listarDiretorio(diretorioAtual);
                    break;
                case 3:
                    System.out.println("Digite o nome (com a extensão) do arquivo que deseja apagar");
                    arquivo = arq.next();
                    apagarArquivo(diretorioAtual + "/" + arquivo);
                    break;
                case 4:
                    System.out.println("Digite o nome (com a extensão) do arquivo que deseja ler");
                    arquivo = arq.next();
                    lerArquivo(diretorioAtual + "/" + arquivo);
                    break;
                case 5:
                    System.out.println("Digite o nome do arquivo temporário que deseja criar");
                    String nomeArquivo = arq.next();
                    System.out.println("Digite a extensão do arquivo temporário que deseja criar");
                    String extensaoArquivo = arq.next();
                    criarArquivoTemp(diretorioAtual, nomeArquivo, extensaoArquivo);
                    break;
            }
            System.out.println("");
        } while (option != 0);
    }

    /*
     * Verfica se e um diretorio pra que deseja mudar e
     * um diretorio valido
     */
    private static boolean checarDiretorio(String diretorio) {
        // Inicializa um arquivo
        File file = new File(diretorio);
        return file.isDirectory();
    }

    /*
     * Lista todos os arquivos contidos no diretorio atual
     */
    private static void listarDiretorio(String diretorio) {
        // Inicializa um arquivo
        File curDir = new File(diretorio);

        // Monta uma lista com todos os arquivos
        File[] filesList = curDir.listFiles();
        // Imprime a lista de arquivos
        for (File f : filesList) {
            System.out.println(f.getName());
        }
    }

    /*
     * Apaga o arquivo desejado do diretorio atual
     * Verifica se o arquivo e realmente um arquivo e o deleta
     */
    private static void apagarArquivo(String arquivo) {
        // Inicializa um arquivo
        File file = new File(arquivo);

        // Se o arquivo for valido, delete
        if (file.isFile()) {
            if (file.delete()) {
                System.out.println("Arquivo apagado com sucesso");
            } else {
                System.out.println("Erro: não foi possível apagar");
            }
        } else {
            System.out.println("Erro: nome de arquivo inválido");
        }
    }

    /*
     * Faz a leitura do arquivo inserido pelo usuario
     * Se o arquivo for valido le o arquivo utilizando Buffer
     */
    private static void lerArquivo(String arquivo) {
        // Inicializa um arquivo
        File file = new File(arquivo);

        // Verifica se o arquivo e valido e inicializa o buffer
        if (file.isFile()) {
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(file));

                // Percorre o arquivo e imprime as linhas
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            System.out.println("Erro: nome de arquivo inválido");
        }
    }

    /*
     * Cria arquivos temporarios dentro do diretorio atual
     * os arquivos sao apagados com a finalizacao do programa
     */
    private static void criarArquivoTemp(String diretorio, String nomeArquivo, String extensaoArquivo) {

        // Inicializa um arquivo
        File file;

        try {
            // Cria o arquivo temporario com nome e extensao
            file = File.createTempFile(nomeArquivo, extensaoArquivo, new File(diretorio));
            System.out.println("Arquivo criado com sucesso");
            // Deleta o arquivo ao final da execucao do programa
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
