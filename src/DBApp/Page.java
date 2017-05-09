package DBApp;


import DBApp.Page;
import pagingConfiguration.*;



public class Page {
	
	String [][] data;
	boolean [] deleted;
	int current;
	int max_records;
	
	//First Step: Constructing a page - You should initialize the variables given above -
	public Page(int noCol)
	{	
		configuration c = new configuration();
		this.max_records = c.pageSize;
		this.data = new String[max_records][noCol];
		this.current = 0;
		this.deleted = new boolean[max_records];
		
	}
	
	//Function1: A function that checks if the page is full
	public boolean isFull()
	{
		if(this.current == max_records){
			return true;
		}
		return false;
	}
	
	//Function2: Inserting a record into the page
	public boolean insert(String [] val)
	{
		if(this.isFull()){
			System.out.println("Page is full");
			return false;
		}else{
			data[current] = val;
			current++;
			return true;
		}
		
	}
	
	//Function3: Inserting a set of records into a page - It will use Function2-
	public Page getData(int [] colNum)
	{	
		Page page = new Page(colNum.length);
		
		for (int i = 0; i < this.data.length; i++) {
			
			String [] val = new String[colNum.length];
			
			for (int j = 0; j < colNum.length; j++) {
				
				val[j] = this.data[i][colNum[j]];			
			}
			
			page.insert(val);
					
		}
		
		return page;
		
	}
	
	public void printPage(){
		
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j]+", ");
				
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		
		Page page1 = new Page(3);
		
		for (int i = 0; i < 201; i++) {
			String s = ""+i;
			String[] y = {"Mohamed",s,"GUC"};
			Boolean x = page1.insert(y);
		}
		
		
		
		int[] colNum = {1,2};
		
		Page page2 = page1.getData(colNum);

		
		page2.printPage();
		
		
	}

}
