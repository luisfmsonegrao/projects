import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
public class Client {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int numero_tentativas=5;
		int tempo_espera=2000;
		int count=0;
		while(true){
			try{
				Registry registry = LocateRegistry.getRegistry("localhost",8000);
				Arquivador myarq = (Arquivador) registry.lookup("arquivador");
				count=0;
				System.out.println("Qual a opera��o que deseja executar?\nCarregue 1 para adicionar um autor\nCarregue 2 para adicionar uma publica��o\nCarregue 3 para"
						+ " remover autor\nCarregue 4 para remover publicacao\nCarregue 5 para receber a lista de autores\nCarregue 6 para receber a lista de autores de uma institui��o\nCarregue 7 para consultar dados estat�sticos");
				int resp = sc.nextInt();
				sc.nextLine();
				switch(resp){
				case 1:{//adicionar autor
					System.out.println("Insira o nome do autor");
					String nome = sc.nextLine();
					System.out.println("Insira a institui��o do autor");
					String inst = sc.nextLine();
					boolean resultado = myarq.adicionaAutor(new Autor(nome,inst));//imprimir mensagem de sucesso/insucesso
					if(resultado){
						System.out.println("Autor adicionado com sucesso!\n");
					}
					else{
						System.out.println("O autor n�o foi adicionado. Cerifique-se que n�o existem j� autores com o mesmo nome.\n");
					}
					break;
				}
				case 2:{//adicionar publica��o
					ArrayList<String> autores = new ArrayList<String>();
					System.out.println("Insira o nome da publica��o");
					String nome = sc.nextLine();
					System.out.println("Quantos autores quer associar?");
					int num = sc.nextInt();
					sc.nextLine();
					for(int i=0;i<num;i++){
						System.out.println("Insira o nome do autor n�mero " + (i+1));
						String nome_aut = sc.nextLine();
						if(!autores.contains(nome_aut)){//impedir que se passe duas vezes o mesmo autor
							autores.add(nome_aut);
						}
					}
					boolean resultado = myarq.adicionaPublicacao(new Publicacao(nome),autores);
					if(resultado){
						System.out.println("Publica��o adicionada com sucesso!\n");
					}
					else{
						System.out.println("A publica��o n�o foi adicionada. Certifique-se de que todos os autores j� existem no servidor e que n�o existem publica��es com o mesmo nome.\n ");
					}
					break;
					
				}
				case 3:{
					System.out.println("Insira o nome do autor");
					String nome = sc.nextLine();
					boolean resultado = myarq.removeAutor(nome);
					if(resultado){
						System.out.println("Autor removido com sucesso!\n");
					}
					else{
						System.out.println("N�o foi poss�vel remover o autor. Certifique-se que o autor existe e que n�o tem publica��es associadas.\n");
					}
					break;
				}
				case 4:{
					System.out.println("Insira o t�tulo da publica��o");
					String publ = sc.nextLine();
					boolean resultado = myarq.removePublicacao(publ);
					if(resultado){
						System.out.println("Publica��o removida com sucesso!\n");
					}
					else{
						System.out.println("A publica��o n�o existe!\n");
					}
					break;
				}
				case 5:{
					ArrayList<Autor> autores = myarq.listaAutores();
					for(Autor a: autores){
						System.out.println(a.getNome());
						for (Publicacao p : a.getPublicacoes()){
							System.out.println("   " + p.getNome());
						}
					}
					System.out.println();
					break;
				}
				case 6:{
					System.out.println("Insira o nome da institui��o");
					String inst = sc.nextLine();
					ArrayList<Autor> autores = myarq.listaAutoresInstituicao(inst);
					if(autores.isEmpty()){
						System.out.println("N�o h� autores desta institui��o!\n");
					}
					else{
						for(Autor a: autores){
							System.out.println(a.getNome());
						}
						System.out.println();
					}
					break;
				}
				case 7:{
					String resultado = myarq.mostraEstatistica();
					String[] partes = resultado.split("&");
					System.out.println("N�mero de autores: " + partes[0] + "\nN�mero de publica��es: " + partes[1] + "\nN�mero de autores por publica��o: " + partes[2] +
							"\nN�mero de publica��es por autor: " + partes[3] + "\n");
					break;
				}
				default:{
					System.out.println("Opera��o n�o existe!\n");
					break;
				}
				}	
		}catch(Exception e){
					
					if(count<numero_tentativas){
						try{
							Thread.sleep(tempo_espera);
						}
						catch(InterruptedException e1){
							e1.printStackTrace();
						}
						System.err.println("Ocorreu um erro: ");
						e.printStackTrace();
						count++;
					}
					else{
						sc.close();
						break;
					}
					
					
			}
		}
	}
}
