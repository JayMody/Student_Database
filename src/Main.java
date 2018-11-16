// Jay Mody
// Friday, November 3rd, 2017
// ISC 4U
// Mr. Jones
// Assignment 4 - Sort and Search (Student Database)
/* Description:
 * This program provides a java GUI student database.
 * The database can hold the information of many students.
 * When added to the database, students have a first name, last name, phone number, and a unique student ID.
 * Users can search the database using any of the 4 fields, then edit/delete anyone that has been searched up.
 * Once the user has done making their changes, the database will be saved into a textfile where 
 * it will be accessed and restored for the next session.
 */

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main
{
	private static FileReader fr;
	private static FileWriter fw;

	private static BufferedReader in;
	private static BufferedWriter out;

	private static File textFile = new File("data.txt");

	private static int confirmSelect;

	private static JList<String> list;

	private JFrame frame;
	private JPanel mainMenu;
	private JLabel lblTitle;
	private static JTextField textFieldPN;
	private static JTextField textFieldLN;
	private static JTextField textFieldFN;
	private static JTextField textFieldSID;
	private JButton btnNewStudent;
	private JButton btnEditStudent;
	private JButton btnRemoveStudents;
	private JButton btnCancel;
	private JButton btnConfirm;
	private JLabel lblSID;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JLabel lblPhoneNumber;
	private JScrollPane scrollPane;
	private JButton btnSearch;
	private JButton btnViewAll;
	private JLabel lblMsg;
	private JButton btnSaveExit;
	private JButton btnClear;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		try
		{
			fr = new FileReader(textFile);
			in = new BufferedReader(fr);

			readFile();

			fw = new FileWriter(textFile);
			out = new BufferedWriter(fw);
		}
		catch(IOException e)
		{
			System.out.println("Error, file was not found");
		}
		catch(NullPointerException e)
		{
			System.out.println("Error, file was unreadable");
		}

		//   Main Pane   //
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) 
			{
				try
				{
					writeFile();
					out.close();
				} 
				catch (IOException e1)
				{
					System.out.println("Failed to write to file");
				}
				catch (NullPointerException e1)
				{
					System.out.println("Failed to write to file");
				}
				
				System.exit(0);
			}
		});
		frame.setMinimumSize(new Dimension(1000, 600));
		frame.setMaximumSize(new Dimension(1600, 900));
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));


		//   Main Panel   //
		mainMenu = new JPanel();
		mainMenu.setBackground(SystemColor.activeCaption);
		frame.getContentPane().add(mainMenu, "name_7008297482334");
		mainMenu.setLayout(null);


		//   Static Labels   //
		lblTitle = new JLabel("Student Database");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblTitle.setBounds(75, 25, 262, 49);
		mainMenu.add(lblTitle);

		lblSID = new JLabel("Student ID");
		lblSID.setHorizontalAlignment(SwingConstants.CENTER);
		lblSID.setBounds(75, 253, 262, 20);
		mainMenu.add(lblSID);

		lblFirstName = new JLabel("First Name");
		lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
		lblFirstName.setBounds(75, 305, 262, 20);
		mainMenu.add(lblFirstName);

		lblLastName = new JLabel("Last Name");
		lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
		lblLastName.setBounds(75, 356, 262, 20);
		mainMenu.add(lblLastName);

		lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhoneNumber.setBounds(75, 405, 262, 20);
		mainMenu.add(lblPhoneNumber);


		//   Text Fields   //
		textFieldPN = new JTextField();
		textFieldPN.setBounds(75, 423, 262, 20);
		mainMenu.add(textFieldPN);
		textFieldPN.setColumns(10);

		textFieldLN = new JTextField();
		textFieldLN.setColumns(10);
		textFieldLN.setBounds(75, 374, 262, 20);
		mainMenu.add(textFieldLN);

		textFieldFN = new JTextField();
		textFieldFN.setColumns(10);
		textFieldFN.setBounds(75, 325, 262, 20);
		mainMenu.add(textFieldFN);

		textFieldSID = new JTextField();
		textFieldSID.setColumns(10);
		textFieldSID.setBounds(75, 274, 262, 20);
		mainMenu.add(textFieldSID);


		//   Buttons   //
		btnNewStudent = new JButton("New Student");
		btnNewStudent.setBounds(15, 127, 186, 23);
		mainMenu.add(btnNewStudent);

		btnEditStudent = new JButton("Edit Student");
		btnEditStudent.setBounds(211, 127, 186, 23);
		mainMenu.add(btnEditStudent);

		btnRemoveStudents = new JButton("Remove Student(s)");
		btnRemoveStudents.setBounds(211, 161, 186, 23);
		mainMenu.add(btnRemoveStudents);

		btnSearch = new JButton("Search");
		btnSearch.setBounds(110, 219, 186, 23);
		mainMenu.add(btnSearch);

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(75, 467, 102, 23);
		mainMenu.add(btnCancel);
		btnCancel.setEnabled(false);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(235, 467, 102, 23);
		mainMenu.add(btnConfirm);
		btnConfirm.setEnabled(false);

		btnViewAll = new JButton("View All");
		btnViewAll.setBounds(15, 161, 186, 23);
		mainMenu.add(btnViewAll);

		btnSaveExit = new JButton("Save & Exit");
		btnSaveExit.setBounds(601, 488, 162, 23);
		mainMenu.add(btnSaveExit);

		btnClear = new JButton("");
		btnClear.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				clearFields();
			}
		});
		btnClear.setToolTipText("Clears all data fields");
		btnClear.setOpaque(false);
		btnClear.setIcon(new ImageIcon(Main.class.getResource("/com/sun/javafx/scene/control/skin/caspian/dialog-error.png")));
		btnClear.setBounds(44, 274, 21, 23);
		mainMenu.add(btnClear);


		//   List   //
		scrollPane = new JScrollPane();
		scrollPane.setBounds(407, 75, 537, 392);
		mainMenu.add(scrollPane);

		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFont(new Font("Calibri", Font.PLAIN, 16));
		scrollPane.setViewportView(list);

		lblMsg = new JLabel("");
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsg.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblMsg.setBounds(375, 25, 599, 39);
		mainMenu.add(lblMsg);


		viewAll();


		//   Action Listners   //
		//   New Student Button Clicked   //
		btnNewStudent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				btnNewStudent.setEnabled(true);
				btnEditStudent.setEnabled(false);
				btnRemoveStudents.setEnabled(false);
				btnViewAll.setEnabled(true);

				btnConfirm.setEnabled(true);
				btnCancel.setEnabled(true);

				list.setEnabled(true);

				lblMsg.setText("| NEW STUDENT |  Fill the fields and click confirm");
				lblMsg.setForeground(Color.BLACK);

				confirmSelect = 0;
			}
		});
		//   Edit Student Button Clicked   //
		btnEditStudent.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				btnNewStudent.setEnabled(false);
				btnEditStudent.setEnabled(true);
				btnRemoveStudents.setEnabled(false);
				btnViewAll.setEnabled(true);

				btnConfirm.setEnabled(true);
				btnCancel.setEnabled(true);

				list.setEnabled(true);
				list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				lblMsg.setText("| EDIT | Pick one student from the list, modify the fields, then click confirm to save changes");
				lblMsg.setForeground(Color.BLACK);

				confirmSelect = 1;
			}
		});
		//   Remove Student(s) Button Clicked   //
		btnRemoveStudents.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				btnNewStudent.setEnabled(false);
				btnEditStudent.setEnabled(false);
				btnRemoveStudents.setEnabled(true);
				btnViewAll.setEnabled(true);

				btnConfirm.setEnabled(true);
				btnCancel.setEnabled(true);

				list.setEnabled(true);
				list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

				lblMsg.setText("| REMOVE | Hold control and click to choose multiple students, then click confirm to remove");
				lblMsg.setForeground(Color.BLACK);

				confirmSelect = 2;
			}
		});
		//   Search Button Clicked   //
		btnSearch.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (textFieldFN.getText().equals("") && textFieldLN.getText().equals("") && textFieldPN.getText().equals("") && textFieldSID.getText().equals(""))
				{
					lblMsg.setText("Please make sure at least one of the text fields are filled before searching");
					lblMsg.setForeground(Color.RED);
				}
				else if (!(isPositiveNumber(textFieldSID.getText())) && !textFieldSID.getText().equals(""))
				{
					lblMsg.setText("Student ID must be a positve integer");
					lblMsg.setForeground(Color.RED);
				}
				else
				{
					int id;

					if (textFieldSID.getText().equals(""))
					{
						id = -1;
					}
					else
					{
						id = Integer.parseInt(textFieldSID.getText());
					}

					String firstName = textFieldFN.getText();
					String lastName = textFieldLN.getText();
					String phoneNumber = textFieldPN.getText();

					ArrayList<Student> searches = Database.search(Database.search(id, firstName, lastName, phoneNumber));


					if (searches.size() == 0)
					{
						lblMsg.setText("No students were found with the given information");
						lblMsg.setForeground(Color.RED);

						clearList(list);
					}
					else
					{
						Student current;
						String text;
						String[] students = new String[searches.size()];

						for (int x = 0 ; x < searches.size() ; x++)
						{
							current = searches.get(x);
							text = "ID: " + current.getID() + "     |     " +  current.getLastName() + ", " + current.getFirstName()  +  "     |      Phone Number: " + current.getPhoneNumber();
							students[x] = text;
						}

						updateList(list, students);

						lblMsg.setText("The following students were found in the database");
						lblMsg.setForeground(Color.GREEN);
					}
				}
			}
		});
		//   View All Button Clicked   //
		btnViewAll.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{	
				viewAll();

				lblMsg.setText("");
				lblMsg.setForeground(Color.BLACK);
			}
		});

		//   Cancel Button Clicked   //
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				btnNewStudent.setEnabled(true);
				btnEditStudent.setEnabled(true);
				btnRemoveStudents.setEnabled(true);
				btnSearch.setEnabled(true);
				btnViewAll.setEnabled(true);

				btnConfirm.setEnabled(false);
				btnCancel.setEnabled(false);

				list.setEnabled(true);
				list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				clearFields();

				lblMsg.setText("action canceled");
				lblMsg.setForeground(Color.BLACK);

				viewAll();
			}
		});

		//   Confirm Button Clicked   //
		btnConfirm.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (confirmSelect == 0)
				{
					if (textFieldFN.getText().equals("") || textFieldLN.getText().equals("") || textFieldPN.getText().equals("") || textFieldSID.getText().equals(""))
					{
						lblMsg.setText("Please fill in all the textfields, if you don't have the required info, put N/A as a placeholder");
						lblMsg.setForeground(Color.RED);
					}
					else if (!(isPositiveNumber(textFieldSID.getText())))
					{
						lblMsg.setText("Student ID must be a positve integer");
						lblMsg.setForeground(Color.RED);
					}
					else
					{
						int id = Integer.parseInt(textFieldSID.getText());
						String firstName = textFieldFN.getText().trim();
						String lastName = textFieldLN.getText().trim();
						String phoneNumber = textFieldPN.getText().trim();

						if (!Database.studentExists(id))
						{
							Database.addStudent(id, firstName, lastName, phoneNumber);
							
							btnNewStudent.setEnabled(true);
							btnEditStudent.setEnabled(true);
							btnRemoveStudents.setEnabled(true);
							btnViewAll.setEnabled(true);						

							btnConfirm.setEnabled(false);
							btnCancel.setEnabled(false);

							list.setEnabled(true);

							clearFields();

							lblMsg.setText(firstName + " was succesfully added to the database");
							lblMsg.setForeground(Color.GREEN);

							viewAll();
						}
						else
						{
							lblMsg.setText("A student with that ID already exists in the system, pick another ID");
							lblMsg.setForeground(Color.RED);
							
							viewAll();
						}
					}
				}
				else if (confirmSelect == 1)
				{
					if (list.getSelectedIndex() == -1)
					{
						lblMsg.setText("Please pick a student to edit before clicking confirm");
						lblMsg.setForeground(Color.RED);		
					}
					else if (textFieldFN.getText().equals("") || textFieldLN.getText().equals("") || textFieldPN.getText().equals("") || textFieldSID.getText().equals(""))
					{
						lblMsg.setText("Please fill in all the textfields, if you don't have the required info, put N/A as a placeholder");
						lblMsg.setForeground(Color.RED);
					}
					else if (!(isPositiveNumber(textFieldSID.getText())))
					{
						lblMsg.setText("Student ID must be a positve integer");
						lblMsg.setForeground(Color.RED);
					}
					else
					{
						String selected = list.getSelectedValue();

						int z = 0;
						String character;

						character = selected.substring(z, z + 1);

						while (!character.equals("|"))
						{
							character = selected.substring(z, z + 1);
							z++;
						}

						int id = Integer.parseInt(selected.substring(4, z - 6));

						int newID = Integer.parseInt(textFieldSID.getText());
						String firstName = textFieldFN.getText();
						String lastName = textFieldLN.getText();
						String phoneNumber = textFieldPN.getText();

						if (!Database.studentExists(newID) || id == newID)
						{
							Database.editStudent(id, newID, firstName, lastName, phoneNumber);
							
							btnNewStudent.setEnabled(true);
							btnEditStudent.setEnabled(true);
							btnRemoveStudents.setEnabled(true);
							btnViewAll.setEnabled(true);						

							btnConfirm.setEnabled(false);
							btnCancel.setEnabled(false);

							list.setEnabled(true);

							clearFields();

							lblMsg.setText("Student was succesfully edited");
							lblMsg.setForeground(Color.GREEN);

							viewAll();
						}
						else
						{
							lblMsg.setText("A student with that ID already exists in the system, pick another ID");
							lblMsg.setForeground(Color.RED);
							
							viewAll();
						}
					}
				}
				else if (confirmSelect == 2)
				{
					if (list.getSelectedIndex() == -1)
					{
						lblMsg.setText("Please pick a student to remove before clicking confirm");
						lblMsg.setForeground(Color.RED);
					}
					else
					{
						ArrayList<String> selected = (ArrayList<String>) list.getSelectedValuesList();

						int z;
						String character;

						int id;

						for (int x = 0 ;  x < selected.size() ; x++)
						{
							z = 0;

							character = selected.get(x).substring(z, z + 1);

							while (!character.equals("|"))
							{
								character = selected.get(x).substring(z, z + 1);
								z++;
							}

							id = Integer.parseInt(selected.get(x).substring(4, z - 6));
							Database.removeStudent(id);

							btnNewStudent.setEnabled(true);
							btnEditStudent.setEnabled(true);
							btnRemoveStudents.setEnabled(true);
							btnViewAll.setEnabled(true);						

							btnConfirm.setEnabled(false);
							btnCancel.setEnabled(false);

							list.setEnabled(true);
							list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							
							clearFields();

							lblMsg.setText("The selected students were succesfully removed");
							lblMsg.setForeground(Color.GREEN);

							viewAll();
						}
					}
				}
			}

		});
		//   List Value Changed   //
		list.addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent arg0) 
			{
				try
				{
					String selected = list.getSelectedValue();

					int z = 0;
					String character;

					int id;

					character = selected.substring(z, z + 1);

					while (!character.equals("|"))
					{
						character = selected.substring(z, z + 1);
						z++;
					}

					id = Integer.parseInt(selected.substring(4, z - 6));

					Student student = Database.search(id);

					textFieldSID.setText(student.getID() + "");
					textFieldFN.setText(student.getFirstName());
					textFieldLN.setText(student.getLastName());
					textFieldPN.setText(student.getPhoneNumber());
				}
				catch(NullPointerException e)
				{

				}
			}
		});
		//   Save and Exit Button Clicked   //
		btnSaveExit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					writeFile();
					out.close();
					System.exit(0);
				} 
				catch (IOException e1)
				{
					System.out.println("Failed to write to file");
				}
				catch (NullPointerException e1)
				{
					System.out.println("Failed to write to file");
				}
			}
		});
	}



	//   Methods   //
	public static void clearFields()
	{
		textFieldSID.setText("");
		textFieldFN.setText("");
		textFieldLN.setText("");
		textFieldPN.setText("");
	}

	public static void updateList(JList<String> charTypeList, String[] students)
	{
		charTypeList.setModel(new AbstractListModel<String>() 
		{
			/**
			 *  Serializes AbstractListModel to remove error warning
			 */
			private static final long serialVersionUID = 1L;

			String[] values = students;

			public int getSize() 
			{
				return values.length;
			}
			public String getElementAt(int index) 
			{
				return values[index];
			}
		});
	}

	public static void clearList(JList<String> charTypeList)
	{
		charTypeList.setModel(new AbstractListModel<String>() 
		{
			/**
			 *  Serializes AbstractListModel to remove error warning
			 */
			private static final long serialVersionUID = 1L;

			String[] values = new String[] {};

			public int getSize() 
			{
				return values.length;
			}
			public String getElementAt(int index) 
			{
				return values[index];
			}
		});
	}

	public static boolean isPositiveNumber(String str)  
	{  
		int n = -1;

		try  
		{  
			n = Integer.parseInt(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  

		if (n >= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static void viewAll()
	{
		ArrayList<Student> searches = Database.search(Database.search(-1, "", "", ""));

		Student current;
		String text;
		String[] students = new String[searches.size()];

		for (int x = 0 ; x < searches.size() ; x++)
		{
			current = searches.get(x);
			text = "ID: " + current.getID() + "     |     " +  current.getLastName() + ", " + current.getFirstName()  +  "     |      Phone Number: " + current.getPhoneNumber();
			students[x] = text;
		}

		updateList(list, students);
	}

	public static void writeFile() throws IOException, NullPointerException
	{
		ArrayList<Student> students = Database.search(Database.search(-1, "", "", ""));
		Student current;

		out.write(students.size() + "");
		out.newLine();

		for (int x = 0 ; x < students.size() ; x++)
		{
			current = students.get(x);

			out.write(current.getID() + " " + current.getFirstName() + " " + current.getLastName() + " " + current.getPhoneNumber() + " ");

			out.newLine();
		}

	}
	public static void readFile() throws NumberFormatException, IOException, NullPointerException
	{
		String line = in.readLine();
		if (line == null || !isPositiveNumber(line))
		{

		}
		else
		{
			int numStudents = Integer.parseInt(line);

			int id = 0;
			String firstName = "";
			String lastName = "";
			String phoneNumber = "";

			int start;

			for (int x = 0 ; x < numStudents ; x++)
			{
				start = 0;
				line = in.readLine();

				for (int y = 0 ; y < line.length() ; y++)
				{
					if (line.substring(y, y + 1).equals(" "))
					{
						id = Integer.parseInt(line.substring(start, y));
						start = y + 1;
						break;
					}
				}
				for (int y = start ; y < line.length() ; y++)
				{
					if (line.substring(y, y + 1).equals(" "))
					{
						firstName = line.substring(start, y);
						start = y + 1;
						break;
					}
				}
				for (int y = start ; y < line.length() ; y++)
				{
					if (line.substring(y, y + 1).equals(" "))
					{
						lastName = line.substring(start, y);
						start = y + 1;
						break;
					}
				}
				for (int y = start ; y < line.length() ; y++)
				{
					if (line.substring(y, y + 1).equals(" "))
					{
						phoneNumber = line.substring(start, y);
						start = y + 1;
						break;
					}
				}

				Database.addStudent(id, firstName, lastName, phoneNumber);
			}
		}
	}
}
