package DBApp;

//import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringJoiner;


public class Table {
	
	int numPages;
	String name;
	String [] colNames;
	String [] colTypes;
	Page last;
	boolean open;
		
	//First Step: Constructing a table - You should initialize the variables given above -
	public Table(String name, String [] colNames,String [] colTypes) throws IOException
	{
		this.name = name;
		this.colNames = colNames;
		this.colTypes = colTypes;
	}
		
	//Function1: A function that creates a "Tables" folder in which the pages of a table will be created 
	//and adds a page into that folder
	public void addPage(Page p) throws IOException
	{
		this.numPages += 1;
		this.last = p;
		String folders = "tabledata/" + this.name + "/page" + numPages + ".csv";
		File f = new File(folders);
		
		//	This is line it done so it can create directories that don't exist yet
		f.getParentFile().mkdirs();
		
		// This line is to create the CSV file itself
		new FileWriter(f).close();
		
	}
	
	//Function2: A function that inserts a record of strings into the last page of the table if it is not full,
	//otherwise, it should add a new page into the folder and insert the record into it
	public int insert (String []record) throws ClassNotFoundException, IOException
	{        
		if(this.last == null || this.last.isFull()){
			Page x = new Page(record.length);
			addPage(x);
			x.insert(record);
		} else{
			this.last.insert(record);
		}
		String fileName = "tabledata/" + name + "/page" + numPages + ".csv";
		FileWriter pw = new FileWriter(fileName,true);
		
		StringJoiner sj = new StringJoiner(", ", "", "\n");
		
		for(String col : record){
			sj.add(col);
		}
		
		String row = sj.toString();
		pw.append(row);
		pw.flush();
		pw.close();
		System.out.println(row);
		
		return 1;
		// Do not fully understand yet why we are returning an integer
	}
		
	//Testing Your Code
	public static void main(String []args) throws IOException, ClassNotFoundException {
		
		String tName="Student";
		String [] tColNames={"ID","Name","GPA","Age","Year"};
		String [] tColTypes={"int","String","double","int","int"};			
		Table t = new Table(tName,tColNames,tColTypes);
		
		
		for(int i=0;i<300;i++)
		{
			String []st={""+i,"Name"+i,"0."+i,"20","3"};
			t.insert(st);
		}
		for(int i=0;i<300;i++)
		{
			String []st={""+(i+300),"Name"+(i+300),"0."+(i+300),"21","4"};
			t.insert(st);
		}
			
		for(int i=0;i<300;i++)
		{
			String []st={""+(i+600),"Name"+(i+600),"0."+(i+600),"21","3"};
			t.insert(st);
		}
			
		System.out.println(t);
		
	}
}
