package ie.gmit.sw.queue;

import java.util.*;
import java.util.concurrent.*;

public class VigenereRequestManager
{
	private static final int maxCapacity = 10;
	private BlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(maxCapacity);
	private Map<Long, String> out = new ConcurrentHashMap<Long, String>();
	private String cypherText;
	VigenereHandler handler;
	
	public VigenereRequestManager(Request r)
	{
		add(r);
	}
	public void add(final Request r)
	{
		try
		{
			//queue.put(r)//blocks if queue full
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						queue.put(r);
						out.put(r.getJobNumber(),r.getCypherText());
						
						handler = new VigenereHandler(queue, out);
						out.put(r.getJobNumber(), handler.returnResult());

					}
					catch(Exception e)
					{
						System.out.println(e);
					}
				}
			}).start();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public String getResult(long jobNumber)
	{
	    
		new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					String result =	out.get(jobNumber);
					cypherText = result;
					System.out.println(result);
					
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		}).start();
		
		return cypherText;
		
	}
	public static void main(String[] args) 
	{
		Request req = new Request("BBACBISBIACIBSBKLACSB", 4, 1);
		VigenereRequestManager vrm = new VigenereRequestManager(req);
		vrm.getResult(1);
	}
}