package javatester;

// utillities: tiện ích
import java.util.Random;

public class Topic_04_Random_Number {

	public static void main(String[] args) {
		// Comment: Ctrl+Shift+C for lines
		// Auto edit format code: Ctrl+Shift+F
		// Ex: Muốn 1 lần chạy sẽ ra số khác nhau .
		// Khi mà đăng kí email thành công, đki lại thì báo đã tồn tại thì k thể đki với
		// email đó nữa thì sẽ dùng random
		Random rand = new Random();
		// Hàm nextInt(): cứ 1 lần chạy sẽ ra số khác nhau, tăng lên, cố định trong 1
		// khoảng
		// Hàm nextInt(int bound): tùy vào khoảng bị sai (0-> <10000)
		System.out.println(rand.nextInt(99999));
		System.out.println(rand.nextInt(99999));
		System.out.println(rand.nextInt(99999));
		System.out.println(rand.nextInt(99999));
		System.out.println(rand.nextInt(99999));

	}

}
