package inheritance;
/* Honda kế thừa Car: 
 * 1 class kế thừa 1 class: extends
 * 1 class kế thừa 1 interface: implements
*/
public class Honda extends Car {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Honda honda = new Honda();
		honda.setCarName("Civic 2022");
		System.out.println(honda.getCarName());
	}

}
