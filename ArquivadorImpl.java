import java.rmi.RemoteException;
import java.util.ArrayList;
import java.rmi.server.UnicastRemoteObject;
public class ArquivadorImpl extends UnicastRemoteObject implements Arquivador {
	
	public ArrayList<Autor> autores=new ArrayList<Autor>();
	
	public ArquivadorImpl () throws RemoteException{
		
	}
	
	public boolean adicionaAutor (Autor a1) throws RemoteException{
		for(Autor a:autores){
			if(a.getNome().equals(a1.getNome())){
				return false;
			}
			
		}
		autores.add(a1);
		return true;
		
	}
	
	public boolean adicionaPublicacao (Publicacao p1,ArrayList<String> nomes) throws RemoteException{
		ArrayList<Autor> aut_publ = new ArrayList<Autor>();
		for (Autor aut: autores){//verificar se já existe uma publicação com o mesmo nome
			if(aut.comparar(p1)){//comparar() devolve true se já houver uma publicação com o mesmo nome
				return false;
			}
		}
		for(String n: nomes){//verificar se os autores associados à publicação já existem na base de dados
			boolean existe = false;
			for (Autor a: autores){
				if (n.equals(a.getNome())){
					aut_publ.add(a);
					existe = true;
					break;
				}
			}
			if(!existe){// basta um autor não existir
				return false;
			}
		}
		p1.setAutores(aut_publ);
		for (Autor a: aut_publ){//adicionar a publicação a cada um dos seus autores
			a.adicionar_public(p1);
		}
		return true;
		
	}

	public boolean removeAutor (String a1) throws RemoteException{
		for (Autor a : autores){
			if(a.getNome().equals(a1)){
				if (a.getPublicacoes().isEmpty()){
					autores.remove(a);
					return true;
				}
				else{//podemos retornar logo porque já encontrámos o autor e já verificámos que ainda tem publicações
					return false;
				}
			}
		}
		return false;
	}
	
	public boolean removePublicacao (String s1) throws RemoteException{
		boolean sucesso=false;
		for(Autor a:autores){
			if(a.remover(s1)){//basta ocorrer uma vez, mas pode occorrer mais que uma
				sucesso=true;
			}
		}
		return sucesso;
	}
	
	public ArrayList<Autor> listaAutores () throws RemoteException{
		return autores;
	}
	
	public ArrayList<Autor> listaAutoresInstituicao (String uni) throws RemoteException{
		ArrayList<Autor> aut = new ArrayList<Autor>();
		for(Autor a: autores){
			if(a.getInstituicao().equals(uni)){
				aut.add(a);
			}
		}
		return aut;
		
	}
	
	public String mostraEstatistica () throws RemoteException{
		int num_aut = autores.size(), num_publ, num_publ_rep=0,num_aut_rep=0;
		ArrayList<Publicacao> publ = new ArrayList<Publicacao>();
		for(Autor a: autores){
			ArrayList<Publicacao> publicacoes = a.getPublicacoes();
			num_publ_rep+=publicacoes.size();
			for(Publicacao p : publicacoes){
				if(publ.contains(p)){
				}
				else{
					publ.add(p);
				}
			}
		}
		for (Publicacao p: publ){
			num_aut_rep += p.getAutores().size();
		}
		num_publ = publ.size();
		String resultado = ""+num_aut+"&"+num_publ+"&"+(float)num_aut_rep/(float)num_publ+"&"+(float)num_publ_rep/(float)num_aut;
		return resultado;
	}
}