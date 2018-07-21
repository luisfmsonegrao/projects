import java.io.Serializable;
import java.util.ArrayList;
public class Publicacao implements Serializable{
	
	String nome;
	ArrayList<Autor> autores;
	
	public Publicacao(String n){
		nome = n;
		autores = new ArrayList<Autor>();
	}
	
	public Publicacao(String n, ArrayList<Autor> a){
		nome = n;
		autores = new ArrayList<Autor>();
		autores = a;
	}

	public String getNome() {
		return nome;
	}

	public ArrayList<Autor> getAutores() {
		return autores;
	}

	public void setAutores(ArrayList<Autor> a) {
		autores = a;
	}
}
