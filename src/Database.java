import java.util.ArrayList;

public class Database
{
	//   Instance Variable   //
	private static ArrayList<Student> database = new ArrayList<Student>();	


	//  New Student Method   //
	public static void addStudent(int id, String firstName, String lastName, String phoneNumber)
	{	
		database.add(new Student(id, firstName, lastName, phoneNumber));
		sort();
	}


	//   Sort Method (Insertion)   //
	public static void sort()
	{
		for (int x = 0 ; x < database.size() ; x++)
		{
			int current = database.get(x).getID();
			int y = x - 1;

			while (y >= 0)
			{
				if (!(database.get(y).getID() > current))
				{
					break;
				}
				
				database.add(y + 1, database.get(y));
				database.remove(y + 1);
				
				y = y - 1;

			}
			
			database.add(y + 1, database.get(x));
			database.remove(x + 1);
		}
	}



	//  Edit Student Method   //
	public static void editStudent(int id, int newID, String firstName, String lastName, String phoneNumber)
	{
		int student = searchIndex(id);

		database.get(student).setID(newID);
		database.get(student).setFirstName(firstName);
		database.get(student).setLastName(lastName);
		database.get(student).setPhoneNumber(phoneNumber);

		sort();
	}


	//  Edit Student Method   //
	public static void removeStudent(int id)
	{
		int student = searchIndex(id);

		database.remove(student);

		//sort();
	}



	//   Search Method   //
	public static ArrayList<Student> search(ArrayList<Integer> searches)
	{
		ArrayList<Student> students = new ArrayList<Student>();

		for (int x = 0 ; x < searches.size() ; x++)
		{
			students.add(database.get(searches.get(x)));
		}

		return students;
	}
	public static Student search(int id)
	{
		Student student = database.get(searchIndex(id));

		return student;
	}
	public static ArrayList<Integer> search(int id, String firstName, String lastName, String phoneNumber)
	{
		ArrayList<Integer> students = new ArrayList<Integer>(); 

		for (int x = 0 ; x < database.size() ; x++)
		{
			Student current =  database.get(x);

			if ((current.getFirstName().equals(firstName) || firstName.equals("")) && (current.getLastName().equals(lastName) || lastName.equals("")) && (current.getPhoneNumber().equals(phoneNumber) || phoneNumber.equals("")) && (current.getID() == id || id == -1))
			{
				students.add(x);
			}
		}

		return students;
	}


	public static int searchIndex(int id)
	{	
		int low = 0;
		int high = database.size() - 1;

		while (low <= high)
		{
			int middle = (low + high) / 2;
			int middleID = database.get(middle).getID();

			if (id == middleID)
			{
				return middle;
			}
			else if (id < middleID)
			{
				high = middle - 1;
			}
			else
			{
				low = middle + 1;
			}
		}

		return -1;
	}


	public static ArrayList<Integer> searchFirst(String firstName)
	{
		ArrayList<Integer> studentMatch = new ArrayList<Integer>();

		for (int x = 0 ; x < database.size() ; x++)
		{
			if (database.get(x).getFirstName().equals(firstName))
			{
				studentMatch.add(x);
			}
		}

		return studentMatch;
	}


	public static ArrayList<Integer> searchLast(String lastName)
	{
		ArrayList<Integer> studentMatch = new ArrayList<Integer>();

		for (int x = 0 ; x < database.size() ; x++)
		{
			if (database.get(x).getLastName().equals(lastName))
			{
				studentMatch.add(x);
			}
		}

		return studentMatch;
	}


	public static ArrayList<Integer> searchPhone(String phoneNumber)
	{
		ArrayList<Integer> studentMatch = new ArrayList<Integer>();

		for (int x = 0 ; x < database.size() ; x++)
		{
			if (database.get(x).getPhoneNumber().equals(phoneNumber))
			{
				studentMatch.add(x);
			}
		}

		return studentMatch;
	}

	public static boolean studentExists(int id)
	{
		for (int x = 0 ; x < database.size() ; x++)
		{
			if (database.get(x).getID() == id)
			{
				return true;
			}
		}

		return false;
	}

}
