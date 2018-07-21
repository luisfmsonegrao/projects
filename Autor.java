import java.io.Serializable;
import java.util.ArrayList;
public class Autor implements Serializable {
	
	String nome;
	String instituicao;
	ArrayList<Publicacao> publicacoes;
	
	public Autor (String n,String i){
		nome=n;
		instituicao = i;
		publicacoes = new ArrayList<Publicacao>();
	}

	public String getNome() {
		return nome;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public ArrayList<Publicacao> getPublicacoes() {
		return publicacoes;
	}
	
	public void adicionar_public(Publicacao p1){
		publicacoes.add(p1);
	}
	public boolean comparar(Publicacao p1){//compara uma publica��o com as publica��es do autor. devolve true se o autor j� tiver uma publica��o com o mesmo nome, false caso contr�rio
		for(Publicacao p:publicacoes){
			if(p.getNome().equals(p1.getNome())){
				return true;//j� h� uma publica��o igual
			}
			
		}
		return false;
	}
	public boolean remover(String p1){// remove uma publica��o da lista de publica��es do autor, devolve true se remo��o tiver sucesso, false caso contr�rio
		for(Publicacao p:publicacoes){
			if(p.getNome().equals(p1)){
				publicacoes.remove(p);
				return true;
			}
			
		}
		return false;
	}
	
	

}
