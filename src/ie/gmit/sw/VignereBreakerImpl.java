package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class VignereBreakerImpl extends UnicastRemoteObject implements VignereBreaker {

	private static final long serialVersionUID = 1L;
	private KeyEnumerator breaker;
	
	public VignereBreakerImpl() throws Exception {
		breaker = new KeyEnumerator();
		//UnicastRemoteObject.exportObject(this);
		
	}
	
	public String decrypt(String cypherText, int maxKeyLength) throws RemoteException {
		
		return breaker.crackCypher(cypherText, maxKeyLength).toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println("starting remote service");
		
		LocateRegistry.createRegistry(1099);
		
		Naming.bind("cypher-service", new VignereBreakerImpl());
		
		System.out.println("service started...");
	}	
}
