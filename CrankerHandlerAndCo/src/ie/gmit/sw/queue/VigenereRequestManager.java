package ie.gmit.sw.queue;

import java.util.*;
import java.util.concurrent.*;

public class VigenereRequestManager
{
	private static final int maxCapacity = 10;
	private BlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(maxCapacity);
	private Map<Long, String> out = new ConcurrentHashMap<Long, String>();
	
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
		if(out.containsKey(jobNumber))
		{
			return out.get(jobNumber);
		}

		else
		{
			return null; //no result
		}
	}
}
