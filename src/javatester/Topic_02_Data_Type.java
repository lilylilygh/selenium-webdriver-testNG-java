package javatester;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Topic_02_Data_Type {

	public static void main(String[] args) {
		// Kiểu dữ liệu sẽ quy ước 1 cái thuộc tính nào đó ngoài đời thực
		// Thông tin của 1 nhân viên
		// Tên/ tuổi/ ngày tháng năm sinh/ giới tính/ quê quán/ lương
	// Ánh xạ thông tin trên vào trong lập trình, phần mềm
		
		// Cách khai báo 1 biến
		// 1 - Kiểu dữ liệu của biến
		// 2 - Tên biến
		// 3 - Giá trị của biến
		
		// 2 cách khai báo và gán giá trị
		// 1 - Vừa khai báo vừa gán trực tiếp luôn:
		String name = "Automation Testing";
		// 2 - Khai báo trước rồi gán sau:
		String aname;
		name = "Automation Testing";
		// Lưu ý: Sẽ lấy giá trị gán cuối cùng, mới nhất
		name = "Automation";
		
		// 2 loại kiểu dữ liệu
		// I - Kiểu dữ liệu nguyên thủy (Primitive): 8 loại
		// Số nguyên: byte/ short/ int/ long (Tuổi: int) : Số mà không có phần thập phân
		byte bNumber = 5; 					//byte range: 0-256
		short sNumber = 500; 				//range rộng hơn -> k lỗi
		
		int iNumber = 6000;			
		long lNumber = 1363464663;			//range lớn nhất, có thể lên tới hàng triệu  
		
		// Số thực: float/ double (Lương: float) : Số dạng có phần thập phân
		float salary = 15.5f;
		double point = 9.8d;
		
		// Kí tự: char : chỉ dành cho 1 kí tự, chỉ đc phép dùng dấu nháy đơn
		char a = 'a';
		
		// Logic: boolean (True/ False) (Giới tính)
		boolean marriedStatus =  true;
		marriedStatus =  false;
		
		// II - Kiểu dữ liệu tham chiếu (Reference):
		// Chuỗi: String (ex: Tên/ quê quán) : Bao gồm chữ/số/kí tự đặc biệt, phải dùng dấu nháy đôi
		String emailInvalid = "afc@1234@$!.Gmail.com";
		
		// Class/ Interface (DateTime)
		Date date = new Date();
		WebDriver driver = new FirefoxDriver();
		
		// Đối tượng: Object
		Object students;			// 1 kiểu root class: Nếu như mà có 1 kiểu dữ liệu nào đó mà trả về object thì có thể hoàn toàn add kiểu object qua những kiểu dữ liệu khác được
		
		// Array: Mảng - Cố định số lượng : bắt buộc cùng kiểu dữ liệu, không được khác kiểu dữ liệu. (Khai báo số lượng dữ liệu trước)
		int numbers[] = {15, 20, 45};
		String addresses [] = {"Da Nang", "Ha Noi", "HCM"};
		
		// List/ Set/ Queue (Collection) : Động (Khai báo rồi add số lượng sau)
		List<Integer> studentNumber = new ArrayList<Integer>();				// List được lưu trùng
		List<String> studentAddress = new ArrayList<String>();
		
		Set<String> studentCity = new LinkedHashSet<String>();				// Set k được lưu trùng
		
	}

}
