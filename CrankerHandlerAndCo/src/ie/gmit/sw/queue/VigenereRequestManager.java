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
	
	public VigenereRequestManager(Request r)
	{
		add(r);
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
						out.put(r.getJobNumber(),r.getCypherText());				
						handler = new VigenereHandler(queue, out);
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
			//Needs to wait three seconds to give thread chance to run.
			t1.join(3000);
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
					String result =	out.get(jobNumber);
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
		t2.join(1000);
		return cypherText;
	}
//	public static void main(String[] args) throws Exception 
//	{
//		Request req = new Request("MABLBLMAXNEMBFTMXMXLMHYYTMX", 4, 1);
//		VigenereRequestManager vrm = new VigenereRequestManager(req);
//		vrm.getResult(1);
//	}
}