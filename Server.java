import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
public class Server {
	
	public static void main(String[] args){
		try{
			ArquivadorImpl arq= new ArquivadorImpl();
			Registry registry = LocateRegistry.createRegistry(8000);
			registry.bind("arquivador", arq);			
		}
		catch(Exception e)
		{
			System.err.println("Ocorreu um erro:");
			e.printStackTrace();
		}
	}

}
