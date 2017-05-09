package DBApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.StringJoiner;
public class Main {
	ArrayList<Table> tables = new ArrayList<Table>();
	public void init( ){
		String folders = "metadata.csv";
		File f = new File(folders);
		
		//	This is line it done so it can create directories that don't exist yet
		f.getParentFile().mkdirs();
		
		// This line is to create the CSV file itself
		try {
			new FileWriter(f).close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	* 	A method to create a table/relation.
	*	@param	strTableName: name of table to be created
	*	@param	htblColNameType: a hashtable of column names and column types , for example ("ID", "java.lang.Integer") where ID is key and the value
	*							 is java.lang.Integer
	*	@param	strKeyColName: the name of the key column, for example "ID"
	 * @throws IOException 
	*/
	public void createTable(String strTableName, Hashtable<String,String> htblColNameType, String strKeyColName)  throws DBException, IOException {
		Table newTable = null;
		String fileName = "metadata.csv";
		FileWriter pw = new FileWriter(fileName,true);
		
		int size = htblColNameType.size();
		String [] colNames = new String[size];
		String [] colTypes = new String[size];
		Set<String> keys = htblColNameType.keySet();
		int i = 0;
        for(String key: keys){
        	colNames[i] = key;
        	colTypes[i] = htblColNameType.get(key);
            // System.out.println("Type of "+key+" is: "+htblColNameType.get(key));
			            
			// delimiter, prefix, suffix
			StringJoiner sj = new StringJoiner(", ", "", "\n");
			
            // Table Name, Column Name, Column Type, Key, Indexed
			sj.add(strTableName);
			sj.add(key);
			sj.add(htblColNameType.get(key));
			if(key.equals(strKeyColName))
				sj.add("True");
			else
				sj.add("False");
			sj.add("False");
			
			String row = sj.toString();
			pw.append(row);
			// System.out.println(row);
            i++;
        }
        pw.flush();
		pw.close();
        newTable = new Table(strTableName, colNames, colTypes);
        tables.add(newTable);
	}
	
	/** 
	*	A method to insert a tuple into an existing table
	*	@param	strTableName: name of table to insert into
	*	@param	htblColNameValue: a hashtable of column names and values for each column, for example, ("ID", "50011") where ID is 
	*								the name of the column and 50011 is the value to be inserted.
	*/
	public void insertIntoTable(String strTableName, Hashtable<String,String> htblColNameValue)  throws DBException {
		// searching for the table
		for(Table currentTable : tables) {
		     if(currentTable.name.equals(strTableName)) {
		    	 
		    	 // mapping each field to an index, just in case the user doesn't
		    	 // input the fields in the same order as they are in the table
		 		 Hashtable<String, String> htblColNameIndex = new Hashtable<String, String>();
		 		 for (int i = 0; i < currentTable.colNames.length; i++) {
		 			 htblColNameIndex.put(currentTable.colNames[i], i+"");
				 }
		 		 
		 		 String [] record= new String[htblColNameValue.size()];
		 		 
		    	 Set<String> keys = htblColNameValue.keySet();
		         for(String type: keys){
		             System.out.println("Value of "+type+" is: "+htblColNameValue.get(type));
		        	 int indexOfType = Integer.parseInt(htblColNameIndex.get(type));
		             System.out.println(currentTable.colNames[indexOfType]);
		        	 record[indexOfType] = htblColNameValue.get(type);	 
		         }
		         try {
					currentTable.insert(record);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		     }
		}
	}
	
	/** 
	*	A method to delete one or more tuple(s) from an existing table
	*	@param	strTableName: name of table to delete from
	*	@param	htblColNameValue: a hashtable of column names and values for each column, for example, ("ID", "50011") where ID is 
	*								the name of the column and 50011 is the value to be used for identifying row to be deleted.
	*	@param	strOperator: possible values are AND or OR to combine the keys in htblColNameValue
	*/
	public void deleteFromTable(String strTableName, Hashtable<String,String> htblColNameValue, String strOperator) throws DBException {
	
	}
		
	public static void main( String[] strArgs ){
		Main myMain = new Main();
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("ID","java.lang.Integer");
		hashtable.put("Name","java.lang.String");
		hashtable.put("Governorate","java.lang.String");
		hashtable.put("Founding_Date","java.util.Date");
		try {
			myMain.createTable("Cities", hashtable, "ID");
		} catch (DBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		hashtable = new Hashtable<String, String>();
		hashtable.put("ID","34");
		hashtable.put("Name","Mohab");
		hashtable.put("Governorate","Cairo");
		hashtable.put("Founding_Date","12/12/2012");
		try {
			myMain.insertIntoTable("Cities", hashtable);
		} catch (DBException e) {
			e.printStackTrace();
		}
		
	}
}
