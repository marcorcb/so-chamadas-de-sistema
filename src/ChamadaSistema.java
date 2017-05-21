import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ChamadaSistema {

    public static void main (String [] args) {
        Scanner arq = new Scanner(System.in);
        int option;
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

            String diretorio;
            String arquivo;

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
                    System.out.println("Digite o nome (com a extensão) do arquivo que deseja apagar");
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

    private static boolean checarDiretorio(String diretorio) {
        File file = new File(diretorio);
        return file.isDirectory();
    }

    private static void listarDiretorio(String diretorio) {

        File curDir = new File(diretorio);

        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            System.out.println(f.getName());
        }
    }

    private static void apagarArquivo(String arquivo) {

        File file = new File(arquivo);

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

    private static void lerArquivo(String arquivo) {
        File file = new File(arquivo);

        if (file.isFile()) {
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(file));

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

    private static void criarArquivoTemp(String diretorio, String nomeArquivo, String extensaoArquivo) {

        File file;

        try {
            file = File.createTempFile(nomeArquivo, extensaoArquivo, new File(diretorio));
            System.out.println("Arquivo criado com sucesso");
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
