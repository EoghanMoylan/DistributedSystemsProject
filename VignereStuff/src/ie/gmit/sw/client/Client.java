package ie.gmit.sw.client;

import java.rmi.Naming;
import ie.gmit.sw.VignereBreaker;

public class Client 
{
	public static void main(String[] args) throws Exception
	{
			VignereBreaker vb = (VignereBreaker) Naming.lookup("cypher-service");
			String result = vb.decrypt("MABLBLTMXLMMHLXXBYBMIBVDLMABLNI", 4);
			System.out.println(result);
	}
}