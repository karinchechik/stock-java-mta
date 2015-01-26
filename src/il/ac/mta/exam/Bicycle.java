package il.ac.mta.exam;

public class Bicycle {
	private final int WHEELS_NUMBER;
	private String ownerName;
	private Brand brand;

	public Bicycle( ) { 
		WHEELS_NUMBER = 2;
		ownerName = "Unknown";
	}
	
	public Bicycle(String name) { 
		WHEELS_NUMBER = 2;
		ownerName = name;
	}
	public String getOwnerName( ) { 
		return ownerName;
	}
	public void setOwnerName(String name) {
		ownerName = name;
	}

	public Brand getBrand() {
		return brand;
	}
	
	/*public String getBrand1() {
		return brandName;
	}*/
	
	public void setBrand(String brandName) {
		this.brand = new Brand(brandName);
		System.out.println("this.brand address: "+ this.brand);
	}
	
	public class Brand {
		private String brandName;
		public Brand(String brandName){
			this.brandName = brandName;
		}
		
		public String getBrand1() {
			return brandName;
		}
	}
public static void main(String[] args) {
		
		Bicycle bike1, bike2;
		//String owner1, owner2;
		
		bike1 = new Bicycle( );
		bike1.setOwnerName("Adam Smith");
		bike1.setBrand("Fasta");
		
	//	System.out.println("Bike 1 owner: "+bike1.getOwnerName());
		
	//	System.out.println("Bike 1 owner: "+bike1.getBrand());
	//	System.out.println("Bike 1 owner: "+bike1.brand.getBrand1());

		bike2 = bike1;
		bike2.setOwnerName("Ben Jones");
		bike2.setBrand("Shina");
		
		System.out.println("Bike 2 owner: "+bike2.getOwnerName());
	//	System.out.println("Bike 1 owner: "+bike1.getOwnerName());
		System.out.println("Bike 2 owner: "+bike2.getBrand());
	//	System.out.println("Bike 2 owner: "+bike2.brand.getBrand1());
		
		//System.out.println("Bike 1 owner: "+bike1.getBrand());
	//	System.out.println("Bike 1 owner: "+bike1.brand.getBrand1());
	}
}
