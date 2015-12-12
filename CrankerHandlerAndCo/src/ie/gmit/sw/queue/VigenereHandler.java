package ie.gmit.sw.queue;

import java.rmi.Naming;
import java.util.*;
import java.util.concurrent.*;

import ie.gmit.sw.VignereBreaker;

public class VigenereHandler implements Runnable
{
	private BlockingQueue<Request> queue;
	private Map<Long, String> out = new ConcurrentHashMap<Long, String>();	
	private String result;
	private Request req = null;
	
	public VigenereHandler(BlockingQueue<Request> q, Map<Long, String> out)
	{
		this.out = out;
		this.queue = q;
		run();
	}
	
	public void run()
	{
		try 
		{
			req = queue.take();
		} 
		
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		try
		{
			VignereBreaker vb = (VignereBreaker) Naming.lookup("cypher-service");
			result = vb.decrypt(req.getCypherText(),  req.getMaxKeySize());
			out.put(req.getJobNumber(), result);

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}	
	public String returnResult()
	{
		return out.get(req.getJobNumber());
	}
	public void removeRequest(long jobNumber)
	{
		out.remove(jobNumber);
	}
}