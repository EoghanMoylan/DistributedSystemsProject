package ie.gmit.sw;
import java.util.*;
import java.io.*;

public class QuadgramMap 
{
	private Map<String, Integer> map = new HashMap<String, Integer>();	
	
	public QuadgramMap(String filename)throws Exception
	{
		parse(filename);
	}
	public float getScore(String text)
	{
		float score = 0.00f;
		float totalScore = 0.00f;
		for(int i = 0 ; i < text.length() ; i +=4)
		{
			if(i + 4 > text.length())
			{
				break;
			}
			String next = text.substring(i, i + 4);

			//System.out.println(next);
			if(map.get(next) != null)
			{
				float frequency =(float) map.get(next);
				score += Math.log10((frequency));
				totalScore += score;
			}
		}
		return totalScore;
	}
	private void parse(String filename) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		StringBuffer sb = new StringBuffer();
		int j;
		while((j = br.read()) != -1)
		{
			char next = (char)j;
			
			if(next >= 'A' || next <= 'z')
			{
				sb.append(next);
			}
			
			if(sb.length() == 4)
			{
				String qgram = sb.toString().toUpperCase();
				sb = new StringBuffer();
				int frequency = 0;
				if(map.containsKey(qgram))
				{
					frequency = map.get(qgram);
				}
				frequency++;
				map.put(qgram, frequency);
			}
		}
		
		br.close();
		//System.out.println(map);
	}
	public static void main(String[] args) throws Exception 
	{
		new QuadgramMap("./stuff.txt");
	}
}