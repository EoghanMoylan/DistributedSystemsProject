package ie.gmit.sw.client;

import java.rmi.Naming;
import ie.gmit.sw.VignereBreaker;

public class Client 
{
	public static void main(String[] args) throws Exception
	{
		VignereBreaker vb = (VignereBreaker) Naming.lookup("cypher-service");
		String result = vb.decrypt("fdsafdsafdsa", 7);
		
		//this stuff needs to be in your tomcat app
		System.out.println(result);
	}
}