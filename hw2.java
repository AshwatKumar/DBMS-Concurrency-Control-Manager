import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class hw2 {
	static HashMap<Flight, ArrayList<Passenger>> data = new HashMap<Flight, ArrayList<Passenger>>();

	public static void main(String[] args) throws IOException, InterruptedException {
		FileReader fr = new FileReader("D:\\flight_database.txt");
		BufferedReader br = new BufferedReader(fr);
		ArrayList<Passenger> totalPass = new ArrayList<Passenger>();
		ArrayList<Flight> totalFlights = new ArrayList<Flight>();
		if (fr != null) {
			String currline;
			while ((currline = br.readLine()) != null) {
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
		for (int i = 0; i < 1; i++) {
			int res = random(lt, rt);
//			System.out.println(res);
			if (res >= 1 && res <= 20) {
				int obj = random(lo, ro);
				boolean checkFlag = false;
				Passenger p1 = null;
				for (Passenger p : totalPass) {
					if (p.id == obj) {
						p1 = p;
//						System.out.println("here");
//						System.out.println(p1.id);
						checkFlag = true;
					}
				}
				if (checkFlag == false) {
					p1 = new Passenger(obj);
//					System.out.println("new");
				}
				int fli = random(111, 121);
				Flight f1 = null;
//				System.out.println(obj);
//				System.out.println(fli);
				for (Flight f : totalFlights) {
					if (f.name == fli) {
						f1 = f;
//						System.out.println("here");
					}
				}
				Transacation t = new Transacation(db, f1, p1, null, 1);
				Thread t1 = new Thread(t);
				t1.start();
			} else if (res >= 21 && res <= 40) {
				int obj = random(lo, ro);
				boolean checkFlag = false;
				Passenger p1 = null;
				for (Passenger p : totalPass) {
					if (p.id == obj) {
						p1 = p;
//						System.out.println("here");
//						System.out.println(p1.id);
						checkFlag = true;
					}
				}
				if (checkFlag == false) {
					p1 = new Passenger(obj);
//					System.out.println("new");
				}
				int fli = random(112, 113);
				Flight f1 = null;
				for (Flight f : totalFlights) {
					if (f.name == fli) {
						f1 = f;
//						System.out.println("here");
					}
				}
//				System.out.println(obj);
//				System.out.println(fli);
				Transacation t = new Transacation(db, f1, p1, null, 2);
				Thread t1 = new Thread(t);
				t1.start();
			} else if (res >= 41 && res <= 60) {
				int obj = random(lo, ro);
				boolean checkFlag = false;
				Passenger p1 = null;
				for (Passenger p : totalPass) {
					if (p.id == obj) {
						p1 = p;
//						System.out.println("here");
//						System.out.println(p1.id);
						checkFlag = true;
					}
				}
				if (checkFlag == false) {
					p1 = new Passenger(obj);
//					System.out.println("new");
				}
//				System.out.println(obj);
				Transacation t = new Transacation(db, null, p1, null, 3);
				Thread t1 = new Thread(t);
				t1.start();
			} else if (res >= 61 && res <= 80) {
				Transacation t = new Transacation(db, null, null, null, 4);
				Thread t1 = new Thread(t);
				t1.start();
			} else {
				int obj = random(lo, ro);
				boolean checkFlag = false;
				Passenger p1 = null;
				for (Passenger p : totalPass) {
					if (p.id == obj) {
						p1 = p;
						checkFlag = true;
//						System.out.println("here");
//						System.out.println(p1.id);
					}
				}
				if (checkFlag == false) {
					p1 = new Passenger(obj);
//					System.out.println("new");
				}
				int fli = random(111, 121);
				Flight f1 = null;
				for (Flight f : totalFlights) {
					if (f.name == fli) {
						f1 = f;
//						System.out.println("here");
					}
				}
//				System.out.println(obj);
//				System.out.println(fli);
				fli = random(111, 121);
				Flight f2 = null;
				for (Flight f : totalFlights) {
					if (f.name == fli) {
						f2 = f;
						System.out.println("here");
					}
				}
				System.out.println(fli);
				Transacation t = new Transacation(db, f1, p1, f2, 5);
				Thread t1 = new Thread(t);
				t1.start();
			}
		}
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
	ReentrantLock Lock1;

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

class Transacation extends Thread {
	Database D1;
	Flight f;
	Passenger ID;
	Flight last;
	boolean reser = false;
	int which;

	public Transacation(Database d, Flight fli, Passenger pass, Flight f2, int w) {
		D1 = d;
		f = fli;
		ID = pass;
		last = f2;
		which = w;
	}

	public void run() {
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
		if (reser == false) {
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
		} else {
			reser = false;
			boolean left = getCurrentSize(last.capacity, D1.data.get(last));
			if (left == true) {
				if (D1.data.containsKey(last)) {
					ArrayList<Passenger> res = D1.data.get(last);
					boolean cFlag = false;
					for (Passenger p : res) {
						if (p.id == ID.id) {
							cFlag = true;
						}
					}
					if (cFlag == false) {
						res.add(ID);
					}
					D1.data.put(last, res);
				}
			}
		}
	}

	public void cancel() {
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
	}

	public ArrayList<Flight> my_flight() {
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
		return fl;
	}

	public int total_reservation() {
		int count = 0;
		for (Flight key : D1.data.keySet()) {
			ArrayList<Passenger> output = D1.data.get(key);
			for (Passenger it : output) {
				if (it.id != 0) {
					count++;
				}
			}
		}
		// System.out.println(count);
		return count;
	}

	public void transfer() {
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
				cancel();
				reser = true;
				reserve();
			}
		}
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
