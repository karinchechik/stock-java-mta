package il.ac.mta;

public class mainExample {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("test");
	    Stock stock = new Stock();
	    stock.setAsk((float)5.6);
	    float x = stock.getAsk();
	    System.out.println(x);
	    //stock.setDate(date);
	    //java.util.Date m = new java.util.Date(2000, 6, 26);
	    java.util.Date v1 = new java.util.Date();
	    v1.setYear(2014);
	    v1.setMonth(2); 
	    v1.setDate(21); 
	    stock.setDate(v1);
	    java.util.Date v = stock.getDate();
	    System.out.println(v);
	    
	    String s = stock.getHtmlDescription();
	    System.out.println(s);
	}

}
