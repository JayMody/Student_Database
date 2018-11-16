
public class Student
{
	//   Instance Variable   //
	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	
	
	//   Constructor   //
	public Student()
	{
		
	}
	public Student(int id)
	{
		this.id = id;
	}
	public Student(int id, String firstName, String lastName, String phoneNumber)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}
	
	
	//   Accessor Methods   //
	public int getID()
	{
		return id;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	
	
	//   Mutators Methods   //
	public void setID(int id)
	{
		this.id = id;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	public void set(Student student)
	{
		this.id = student.getID();
		this.firstName = student.getFirstName();
		this.lastName = student.getLastName();
		this.phoneNumber = student.getPhoneNumber();
	}
}
