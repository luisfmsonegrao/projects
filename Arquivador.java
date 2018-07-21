import java.rmi.Remote;

import java.util.ArrayList;
import java.rmi.RemoteException;
public interface Arquivador extends Remote{

	public boolean adicionaAutor(Autor a1) throws RemoteException;
	
	public boolean adicionaPublicacao (Publicacao p1,ArrayList<String> nomes) throws RemoteException;
	
	public boolean removePublicacao (String s1) throws RemoteException;
	
	public boolean removeAutor (String s1) throws RemoteException; 
	
	public ArrayList<Autor> listaAutores () throws RemoteException;
	
	public ArrayList<Autor> listaAutoresInstituicao (String uni) throws RemoteException;
	
	public String mostraEstatistica () throws RemoteException;
	
	
}
