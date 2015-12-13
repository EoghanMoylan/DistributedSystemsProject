package ie.gmit.sw.queue;

import java.util.*;
import java.util.concurrent.*;

public class VigenereRequestManager
{
	private static final int maxCapacity = 10;
	private BlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(maxCapacity);
	private Map<Long, String> out = new ConcurrentHashMap<Long, String>();
	private VigenereHandler handler;
	private String cypherText;
	
	public VigenereRequestManager()
	{
	}
	public void add(final Request r)
	{	
		try
		{
			//queue.put(r)//blocks if queue full
			Thread t1 = new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						queue.put(r);
						//writes to map with encoded text so it can be passed to VignereHandler
						out.put(r.getJobNumber(),r.getCypherText());				
						handler = new VigenereHandler(queue, out);
						//rewrites map with decoded text
						out.put(r.getJobNumber(), handler.returnResult());
					//	System.out.println(r.getJobNumber() + " " + r.getCypherText() + " " + handler.returnResult());
					}
					catch(Exception e)
					{
						System.out.println(e);
					}
				}
			});
			t1.start();
			//Needs to wait to give thread chance to run.
			t1.join();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public String getResult(long jobNumber) throws Exception
	{
		Thread t2 = new Thread(new Runnable()
		{
			public void run()
			{
				try
				{
					//returns decoded text
					String result =	out.get(jobNumber);
					//allows us to us result outside of thread
					cypherText = result;
					System.out.println(cypherText);
					
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
			}
		});
		t2.start();
		t2.join();
		//removes request from map
		out.remove(jobNumber);
		//removes request from map in vignere handler
		handler.removeRequest(jobNumber);
		return cypherText;
	}
	public static void main(String[] args) throws Exception 
	{
		//just used for testing
		Request req = new Request("MABLBLMAXNEMBFTMXMXLMHYYTMX", 4, 1);
		VigenereRequestManager vrm = new VigenereRequestManager();
		vrm.add(req);
		System.out.println(vrm.getResult(1));
	}
}