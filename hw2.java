
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class hw2
{
	public static void main( String[] args ) throws IOException
	{
	//	Reader.init(System.in);
		HashMap<String,ArrayList<Integer>> data = null;
		FileReader fr = new FileReader("C:\\Users\\HP\\Desktop\\flight_database.txt");
		BufferedReader br = new BufferedReader(fr);
		if ( fr != null )
		{
			String currline;
			data = new HashMap<String,ArrayList<Integer>>();
			while ( ( currline = br.readLine() ) != null )
			{
				String[] arey = currline.split("\n");
				String[] commas = arey[0].split(",");
				ArrayList<Integer> ele = new ArrayList<Integer>();
				for ( int i = 1; i < commas.length ; i++ )
				{
					ele.add(Integer.parseInt(commas[i]));
				}
				data.put(commas[0],ele);
			}
		}
		Transacation t1 = new Transacation(data);
		Transacation t2 = new Transacation(data);
		Transacation t3 = new Transacation(data);
		
	}
}

class Transacation
{
	HashMap<String,ArrayList<Integer>> data;
	public Transacation( HashMap<String,ArrayList<Integer>> d )
	{
		data = d;
	}
	
	public int random( int l , int h )
	{
		Random r = new Random();
		int resu = r.nextInt(h-l)+l;
		return resu;
	}
	
	public void run()
	{
		int result = random(1,6);
		String flight = "Flight "+String.valueOf(result);
		int res2 = random(1,71);
		
		if ( result == 1 )
		{
			reserve(flight,res2);
		}
		else if ( result == 2 )
		{
			cancel(flight,res2);
		}
		else if ( result == 3 )
		{
			my_flight(res2);
		}
		else if ( result == 4 )
		{
			total_reservation();
		}
		else
		{
			int res3 = random(1,6);
			String fli2= "Flight "+String.valueOf(result);
			transfer(flight,fli2,res2);
		}
	}
	
	public void reserve(String f , Integer id )
	{
		if ( data.containsKey(f) )
		{
			ArrayList<Integer> res = data.get(f);
			res.add(id);
			data.put(f,res);
		}
	}
	
	public void cancel( String f , Integer id )
	{
		if ( data.containsKey(f) )
		{
			ArrayList<Integer> res = data.get(f);
			int pos = 0;
			for ( int i = 0 ; i < res.size() ; i++ )
			{
				if ( res.get(i) == id )
				{
					pos = i;
					break;
				}
			}
			res.set(pos,0);
			data.put(f,res);
		}
	}
	
	public int my_flight( Integer id )
	{
		int count = 0;
		for ( String key : data.keySet() )
		{
			ArrayList<Integer> output = data.get(key);
			for ( Integer it : output )
			{
				if ( it == id )
				{
					count++;
				}
			}
		}
		return count;
	}
	
	public int total_reservation()
	{
		int count = 0;
		for ( String key : data.keySet() )
		{
			ArrayList<Integer> output = data.get(key);
			for ( Integer it : output )
			{
				if ( it != 0 )
				{
					count++;
				}
			}
		}
		return count;
	}
	
	public void transfer( String f1 , String f2 , Integer id )
	{
		ArrayList<Integer> res = data.get(f1);
		boolean found = false;
		for ( Integer val : res )
		{
			if ( val == id )
			{
				found = true;
				break;
			}
		}
		if ( found == true )
		{
			cancel(f1,id);
			reserve(f2,id);
		}
	}
	
}