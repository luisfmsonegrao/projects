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
	public boolean comparar(Publicacao p1){//compara uma publicação com as publicações do autor. devolve true se o autor já tiver uma publicação com o mesmo nome, false caso contrário
		for(Publicacao p:publicacoes){
			if(p.getNome().equals(p1.getNome())){
				return true;//já há uma publicação igual
			}
			
		}
		return false;
	}
	public boolean remover(String p1){// remove uma publicação da lista de publicações do autor, devolve true se remoção tiver sucesso, false caso contrário
		for(Publicacao p:publicacoes){
			if(p.getNome().equals(p1)){
				publicacoes.remove(p);
				return true;
			}
			
		}
		return false;
	}
	
	

}
