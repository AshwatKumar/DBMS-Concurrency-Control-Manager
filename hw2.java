
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
	static HashMap<Flight,ArrayList<Passenger>> data = new HashMap<Flight,ArrayList<Passenger>>();
	public static void main( String[] args ) throws IOException
	{
		FileReader fr = new FileReader("C:\\Users\\HP\\Desktop\\flight_database.txt");
		BufferedReader br = new BufferedReader(fr);
		if ( fr != null )
		{
			String currline;
			while ( ( currline = br.readLine() ) != null )
			{
				String[] arey = currline.split("\n");
				String[] commas = arey[0].split(",");
				Flight f = new Flight(Integer.parseInt(commas[0]),Integer.parseInt(commas[1]));
				ArrayList<Passenger> passen = new ArrayList<Passenger>();
				for ( int i = 2 ; i < commas.length ; i++ )
				{
					int val = Integer.parseInt(commas[i]);
					Passenger pass = new Passenger(val);
					passen.add(pass);
				}
				data.put(f,passen);
			}
		}
		int lt = 1;int rt = 101;
		int lo = 1;int ro = 71;
		Database db = new Database(data);
		for ( int i = 0 ; i < 15 ; i++ )
		{
			int res = random(lt,rt);
			if ( res >= 1 && res <= 20 )
			{
				int obj = random(lo,ro);
				Passenger p = new Passenger(obj);
				int fli = random(111,121);
				int cap = getCapacity(fli);
				Flight flight = new Flight(fli,cap);
				Transacation t = new Transacation(db,flight,p,null,1);
	
			}
			else if ( res >= 21 && res <= 40 )
			{
				int obj = random(lo,ro);
				Passenger p = new Passenger(obj);
				int fli = random(111,121);
				int cap = getCapacity(fli);
				Flight flight = new Flight(fli,cap);
				Transacation t = new Transacation(db,flight,p,null,2);
			}
			else if ( res >= 41 && res <= 60 )
			{
				int obj = random(lo,ro);
				Passenger p = new Passenger(obj);
				Transacation t = new Transacation(db,null,p,null,3);
			}
			else if ( res >= 61 && res <= 80 )
			{
				Transacation t = new Transacation(db,null,null,null,4);
			}
			else
			{
				int obj = random(lo,ro);
				Passenger p = new Passenger(obj);
				int fli1 = random(111,121);
				int cap1 = getCapacity(fli1);
				Flight flight1 = new Flight(fli1,cap1);
				
				int fli2 = random(111,121);
				int cap2 = getCapacity(fli2);
				Flight flight2 = new Flight(fli2,cap2);
				
				Transacation t = new Transacation(db,flight1,p,flight2,5);
			}
		}
		
	}
	
	public static int random( int l , int h )
	{
		Random r = new Random();
		int resu = r.nextInt(h-l)+l;
		return resu;
	}
	
	public static int getCapacity( int id )
	{
		int cap = 0;
		for ( Flight f : data.keySet() )
		{
			if ( f.name == id )
			{
				cap = f.capacity;
				break;
			}
		}
		return cap;
	}
}

class Database
{
	HashMap<Flight,ArrayList<Passenger>> data;
	public Database( HashMap<Flight,ArrayList<Passenger>> d )
	{
		data =d;
	}
}

class Passenger
{
	int id;
	public Passenger( int i )
	{
		id = i;
	}
}

class Flight
{
	int name;
	int capacity;
	public Flight( int n , int cap )
	{
		name = n;
		capacity = cap;
	}
}


class Transacation extends Thread
{
	HashMap<Flight,ArrayList<Passenger>> data;
	Flight f;
	Passenger id;
	Flight last;
	boolean reser = false;
	int which;
	
	public Transacation( Database d , Flight fli , Passenger pass , Flight f2 , int w )
	{
		data = d.data;
		f = fli;
		pass = id;
		last = f2;
		which = w;
	}
	
	public void run()
	{
		 if ( which == 1 )
		 {
			 reserve();
		 }
		 else if ( which == 2 )
		 {
			 cancel();
		 }
		 else if ( which == 3 )
		 {
			 my_flight();
		 }
		 else if ( which == 4 )
		 {
			 total_reservation();
		 }
		 else
		 {
			 transfer();
		 }
	}
	
	public void reserve()
	{
		if ( reser == false )
		{
			boolean left = getCurrentSize(f.capacity,data.get(f));
			if ( left == true )
			{
				if ( data.containsKey(f) )
				{
					ArrayList<Passenger> res = data.get(f);
					res.add(id);
					//f = new Flight(f.name,f.capacity+1);
					data.put(f,res);
				}
			}
		}
		else
		{
			reser = false;
			boolean left = getCurrentSize(last.capacity,data.get(last));
			if ( left == true )
			{
				if ( data.containsKey(last) )
				{
					ArrayList<Passenger> res = data.get(last);
					res.add(id);
					//f = new Flight(f.name,f.capacity+1);
					data.put(last,res);
				}
			}
		}
	}
	
	public void cancel()
	{
		if ( data.containsKey(f) )
		{
			ArrayList<Passenger> res = data.get(f);
			int pos = 0;
			for ( int i = 0 ; i < res.size() ; i++ )
			{
				if ( res.get(i).id == id.id )
				{
					pos = i;
					break;
				}
			}
			res.set(pos,new Passenger(0));
			//f = new Flight(f.name,f.capacity-1);
			data.put(f,res);
		}
	}
	
	public int my_flight()
	{
		int count = 0;
		for ( Flight key : data.keySet() )
		{
			ArrayList<Passenger> output = data.get(key);
			for ( Passenger it : output )
			{
				if ( it.id == id.id )
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
		for ( Flight key : data.keySet() )
		{
			ArrayList<Passenger> output = data.get(key);
			for ( Passenger it : output )
			{
				if ( it.id != 0 )
				{
					count++;
				}
			}
		}
		return count;
	}
	
	public void transfer()
	{
		ArrayList<Passenger> res = data.get(f);
		boolean found = false;
		for ( Passenger val : res )
		{
			if ( val.id == id.id )
			{
				found = true;
				break;
			}
		}
		
		boolean left = getCurrentSize(last.capacity,data.get(last));
		if ( found == true )
		{
			if ( left == true )
			{
				cancel();
				reser = true;
				reserve();
			}
		}
	}
	
	public boolean getCurrentSize(int limit,ArrayList<Passenger> arey)
	{
		boolean left = false;
		int count = 0;
		for ( Passenger pp : arey )
		{
			if ( pp.id != 0 )
			{
				count++;
			}
		}
		if ( count <= limit )
		{
			left = true;
		}
		return left;
	}
}