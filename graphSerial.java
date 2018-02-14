
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class graphSerial
{
	static HashMap<Flight, ArrayList<Passenger>> data = new HashMap<Flight, ArrayList<Passenger>>();

	public static void main(String[] args) throws IOException, InterruptedException {
		FileReader fr = new FileReader("C:\\Users\\HP\\Desktop\\flight_database.txt");
		BufferedReader br = new BufferedReader(fr);
		ArrayList<Passenger> totalPass = new ArrayList<Passenger>();
		ArrayList<Flight> totalFlights = new ArrayList<Flight>();
		if (fr != null) 
		{
			String currline;
			while ((currline = br.readLine()) != null)
			{
				String[] arey = currline.split("\n");
				String[] commas = arey[0].split(",");
				Flight f = new Flight(Integer.parseInt(commas[0]), Integer.parseInt(commas[1]));
				totalFlights.add(f);
				ArrayList<Passenger> passen = new ArrayList<Passenger>();
				for (int i = 2; i < commas.length; i++) {
					int val = Integer.parseInt(commas[i]);
					boolean checkFlag = false;
					for (Passenger p : totalPass) {
						if (p.id == val) {
							passen.add(p);
							checkFlag = true;
						}
					}
					if (checkFlag == false) {
						Passenger pass = new Passenger(val);
						totalPass.add(pass);
						passen.add(pass);
					}
				}
				data.put(f, passen);
			}
		}
		int lt = 1;
		int rt = 101;
		int lo = 1;
		int ro = 71;
		Database db = new Database(data);
		Thread[] arr = new Thread[15];
		
		int numOfThreads = 0;
		ExecutorService executor = Executors.newFixedThreadPool(10);
		long startTime = System.currentTimeMillis();
		Runnable t;
		while((System.currentTimeMillis()-startTime)<1000)
		//for (int i = 0; i < 20 ; i++) 
		{
			
			int res = random(lt,rt);
			if (res >= 1 && res <= 20) {
				int obj = random(lo,ro);
				boolean checkFlag = false;
				Passenger p1 = null;
				for (Passenger p : totalPass) {
					if (p.id == obj) {
						p1 = p;
						checkFlag = true;
					}
				}
				if (checkFlag == false) {
					p1 = new Passenger(obj);
				}
				int fli = random(111, 121);
				Flight f1 = null;
				for (Flight f : totalFlights) {
					if (f.name == fli) {
						f1 = f;
					}
				}
				//arr[i] = new Thread(new Transacation(db, f1, p1, null, 1));
				//arr[i].start();
				t = new Transacation(db, f1, p1, null, 1);
				executor.execute(t);
				
			}
			if (res >= 21 && res <= 40) {
				int obj = random(lo, ro);
				boolean checkFlag = false;
				Passenger p1 = null;
				for (Passenger p : totalPass) {
					if (p.id == obj) {
						p1 = p;
						checkFlag = true;
					}
				}
				if (checkFlag == false) {
					p1 = new Passenger(obj);
				}
				int fli = random(112, 113);
				Flight f1 = null;
				for (Flight f : totalFlights) {
					if (f.name == fli) {
						f1 = f;
					}
				}
				//arr[i] = new Thread(new Transacation(db, f1, p1, null, 2));
				//arr[i].start();
				//arr[i].join();
				t =  new Transacation(db, f1, p1, null, 2);
				executor.execute(t);
				
			}
			if (res >= 41 && res <= 60) {
				int obj = random(lo, ro);
				boolean checkFlag = false;
				Passenger p1 = null;
				for (Passenger p : totalPass) {
					if (p.id == obj) {
						p1 = p;
						checkFlag = true;
					}
				}
				if (checkFlag == false) {
					p1 = new Passenger(obj);
				}
				//arr[i] = new Thread(new Transacation(db, null, p1, null, 3));
				//arr[i].start();
				t = new Transacation(db, null, p1, null, 3);
				executor.execute(t);
				
			}
			if (res >= 61 && res <= 80) {
				//arr[i] = new Thread(new Transacation(db, null, null, null, 4));
				//arr[i].start();
				t = new Transacation(db, null, null, null, 4);
				executor.execute(t);
			}
			if (res >= 81 && res <= 100) {
				int obj = random(lo, ro);
				boolean checkFlag = false;
				Passenger p1 = null;
				for (Passenger p : totalPass) {
					if (p.id == obj) {
						p1 = p;
						checkFlag = true;
					}
				}
				if (checkFlag == false) {
					p1 = new Passenger(obj);
				}
				int fli = random(111, 121);
				Flight f1 = null;
				for (Flight f : totalFlights) {
					if (f.name == fli) {
						f1 = f;
					}
				}
				fli = random(111, 121);
				Flight f2 = null;
				for (Flight f : totalFlights) {
					if (f.name == fli) {
						f2 = f;
					}
				}
				//arr[i] = new Thread(new Transacation(db, f1, p1, f2, 5));
				//arr[i].start();
				t = new Transacation(db, f1, p1, f2, 5);
				executor.execute(t);
			}
		}
		executor.shutdown();
/*		for (int j = 0; j < 15; j++) {
			arr[j].join();
		}*/
//		for (Flight f : db.data.keySet()) {
//			System.out.print(f.name + " " + f.capacity + " ");
//			ArrayList<Passenger> out = db.data.get(f);
//			for (Passenger p : out) {
//				System.out.print(p.id + " ");
//			}
//			System.out.println();
//		}

	}

	public static int random(int l, int h) {
		Random r = new Random();
		int resu = r.nextInt(h - l) + l;
		return resu;
	}

	public static int getCapacity(int id) {
		int cap = 0;
		for (Flight f : data.keySet()) {
			if (f.name == id) {
				cap = f.capacity;
				break;
			}
		}
		return cap;
	}
}

class Database {
	HashMap<Flight, ArrayList<Passenger>> data;
	ReentrantLock Lock1 = new ReentrantLock();

	public Database(HashMap<Flight, ArrayList<Passenger>> d) {
		data = d;
	}
}

class Passenger {
	int id;

	public Passenger(int i) {
		id = i;
	}
}

class Flight {
	int name;
	int capacity;

	public Flight(int n, int cap) {
		name = n;
		capacity = cap;
	}
}

class Transacation extends Thread 
{
	Database D1;
	Flight f;
	Passenger ID;
	Flight last;
	boolean reser = false;
	int which;
	volatile static int count = 0;
	
	public Transacation(Database d, Flight fli, Passenger pass, Flight f2, int w) {
		D1 = d;
		f = fli;
		ID = pass;
		last = f2;
		which = w;
	}

	public void run() 
	{
/*		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if (which == 1) {
			reserve();
		} else if (which == 2) {
			cancel();
		} else if (which == 3) {
			my_flight();
		} else if (which == 4) {
			total_reservation();
		} else {
			transfer();
		}

	}

	public void reserve() {
		ConcurrencyControlManager.setLock(D1);
			boolean left = getCurrentSize(f.capacity, D1.data.get(f));
			if (left == true) {
				if (D1.data.containsKey(f)) {
					ArrayList<Passenger> res = D1.data.get(f);
					boolean cFlag = false;
					for (Passenger p : res) {
						if (p.id == ID.id) {
							cFlag = true;
						}
					}
					if (cFlag == false) {
						res.add(ID);
					}
					D1.data.put(f, res);
				}
			}
		ConcurrencyControlManager.unlock(D1);
		count++;
		System.out.println(count);
	}

	public void cancel() {
		ConcurrencyControlManager.setLock(D1);
		if (D1.data.containsKey(f)) {
			ArrayList<Passenger> res = D1.data.get(f);
			for (int i = 0; i < res.size(); i++) {
				if (res.get(i).id == ID.id) {
					res.remove(i);
					break;
				}
			}
			D1.data.put(f, res);
		}
		ConcurrencyControlManager.unlock(D1);
		count++;
		System.out.println(count);
	}

	public ArrayList<Flight> my_flight() {
		ConcurrencyControlManager.setLock(D1);
		ArrayList<Flight> fl = new ArrayList<Flight>();
		for (Flight key : D1.data.keySet()) {
			ArrayList<Passenger> output = D1.data.get(key);
			for (Passenger it : output) {
				// System.out.println(it.id);
				if (it.id == ID.id) {
					fl.add(key);
				}
			}
		}
		// for(Flight fll:fl) {
		// System.out.print(fll.name+" ");
		// }
		// System.out.println();
		ConcurrencyControlManager.unlock(D1);
		count++;
		System.out.println(count);
		return fl;
	}

	public int total_reservation() {
		ConcurrencyControlManager.setLock(D1);
		int count = 0;
		for (Flight key : D1.data.keySet()) {
			ArrayList<Passenger> output = D1.data.get(key);
			for (Passenger it : output) {
				if (it.id != 0) {
					count++;
				}
			}
		}
		ConcurrencyControlManager.unlock(D1);
		// System.out.println(count);
		count++;
		System.out.println(count);
		return count;
	}

	public void transfer() {
		ConcurrencyControlManager.setLock(D1);
		ArrayList<Passenger> res = D1.data.get(f);
		boolean found = false;
		for (Passenger val : res) {
			if (val.id == ID.id) {
				found = true;
				break;
			}
		}

		boolean left = getCurrentSize(last.capacity, D1.data.get(last));
		if (found == true) {
			if (left == true) {
				if (D1.data.containsKey(f)) {
					ArrayList<Passenger> res1 = D1.data.get(f);
					for (int i = 0; i < res1.size(); i++) {
						if (res1.get(i).id == ID.id) {
							res1.remove(i);
							break;
						}
					}
					D1.data.put(f, res1);
					if (D1.data.containsKey(last)) {
						ArrayList<Passenger> res2 = D1.data.get(last);
						boolean cFlag = false;
						for (Passenger p : res2) {
							if (p.id == ID.id) {
								cFlag = true;
							}
						}
						if (cFlag == false) {
							res2.add(ID);
						}
						D1.data.put(last, res2);
					}
				}
			}
		}
		ConcurrencyControlManager.unlock(D1);
		count++;
		System.out.println(count);
	}

	public boolean getCurrentSize(int limit, ArrayList<Passenger> arey) {
		boolean left = false;
		int count = 0;
		for (Passenger pp : arey) {
			if (pp.id != 0) {
				count++;
			}
		}
		if (count <= limit) {
			left = true;
		}
		return left;
	}

}

class ConcurrencyControlManager {
	public static void setLock(Database db) {
		boolean finish = false;
		while (!finish) {
			boolean result = db.Lock1.tryLock();
			if (result == true) {
				db.Lock1.lock();
				finish = true;
			}
		}
	}

	public static void unlock(Database db) {
		db.Lock1.unlock();
		db.Lock1.unlock();
	}
}