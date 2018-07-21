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
				System.out.println("Qual a operação que deseja executar?\nCarregue 1 para adicionar um autor\nCarregue 2 para adicionar uma publicação\nCarregue 3 para"
						+ " remover autor\nCarregue 4 para remover publicacao\nCarregue 5 para receber a lista de autores\nCarregue 6 para receber a lista de autores de uma instituição\nCarregue 7 para consultar dados estatísticos");
				int resp = sc.nextInt();
				sc.nextLine();
				switch(resp){
				case 1:{//adicionar autor
					System.out.println("Insira o nome do autor");
					String nome = sc.nextLine();
					System.out.println("Insira a instituição do autor");
					String inst = sc.nextLine();
					boolean resultado = myarq.adicionaAutor(new Autor(nome,inst));//imprimir mensagem de sucesso/insucesso
					if(resultado){
						System.out.println("Autor adicionado com sucesso!\n");
					}
					else{
						System.out.println("O autor não foi adicionado. Cerifique-se que não existem já autores com o mesmo nome.\n");
					}
					break;
				}
				case 2:{//adicionar publicação
					ArrayList<String> autores = new ArrayList<String>();
					System.out.println("Insira o nome da publicação");
					String nome = sc.nextLine();
					System.out.println("Quantos autores quer associar?");
					int num = sc.nextInt();
					sc.nextLine();
					for(int i=0;i<num;i++){
						System.out.println("Insira o nome do autor número " + (i+1));
						String nome_aut = sc.nextLine();
						if(!autores.contains(nome_aut)){//impedir que se passe duas vezes o mesmo autor
							autores.add(nome_aut);
						}
					}
					boolean resultado = myarq.adicionaPublicacao(new Publicacao(nome),autores);
					if(resultado){
						System.out.println("Publicação adicionada com sucesso!\n");
					}
					else{
						System.out.println("A publicação não foi adicionada. Certifique-se de que todos os autores já existem no servidor e que não existem publicações com o mesmo nome.\n ");
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
						System.out.println("Não foi possível remover o autor. Certifique-se que o autor existe e que não tem publicações associadas.\n");
					}
					break;
				}
				case 4:{
					System.out.println("Insira o título da publicação");
					String publ = sc.nextLine();
					boolean resultado = myarq.removePublicacao(publ);
					if(resultado){
						System.out.println("Publicação removida com sucesso!\n");
					}
					else{
						System.out.println("A publicação não existe!\n");
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
					System.out.println("Insira o nome da instituição");
					String inst = sc.nextLine();
					ArrayList<Autor> autores = myarq.listaAutoresInstituicao(inst);
					if(autores.isEmpty()){
						System.out.println("Não há autores desta instituição!\n");
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
					System.out.println("Número de autores: " + partes[0] + "\nNúmero de publicações: " + partes[1] + "\nNúmero de autores por publicação: " + partes[2] +
							"\nNúmero de publicações por autor: " + partes[3] + "\n");
					break;
				}
				default:{
					System.out.println("Operação não existe!\n");
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
