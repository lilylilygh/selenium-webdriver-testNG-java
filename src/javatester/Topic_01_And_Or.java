package javatester;

public class Topic_01_And_Or {

	/**
	 * And/Or là toán tử trong lập trình Java/XPath
	 * And: &&
	 * Or: ||
	 */
	public static void main(String[] args) {
		// Có 2 điều kiện
		// Kết hợp and hoặc or giữa 2 điều kiện này
		// Ra kết quả (True/False)
		boolean firstCondition;
		boolean secondCondition;
		boolean result;
		
		// AND: Nếu 1 trong 2 điều kiện bằng False = False, chỉ khi cả 2 đều True thì KQ  = True
		// ĐK 1 = 	TRUE	FALSE	FALSE	TRUE
		// ĐK 2 =	FALSE	TRUE	FALSE	TRUE
		// Result = FALSE	FALSE	FALSE	TRUE
		firstCondition = true;
		secondCondition = false;
		System.out.println(firstCondition && secondCondition);
		
		
		firstCondition = false;
		secondCondition = true;
		System.out.println(firstCondition && secondCondition);
		
		firstCondition = false;
		secondCondition = false;
		System.out.println(firstCondition && secondCondition);
		
		firstCondition = true;
		secondCondition = true;
		System.out.println(firstCondition && secondCondition);
		
		// OR: Nếu 1 trong 2 điều kiện bằng True = True 
		// ĐK 1 = 	TRUE	FALSE	FALSE	TRUE
		// ĐK 2 = 	FALSE	TRUE	FALSE	TRUE
		// Result = TRUE	TRUE	FALSE	TRUE
		
		firstCondition = true;
		secondCondition = false;
		System.out.println(firstCondition || secondCondition);
		
		
		firstCondition = false;
		secondCondition = true;
		System.out.println(firstCondition || secondCondition);
		
		firstCondition = false;
		secondCondition = false;
		System.out.println(firstCondition || secondCondition);
		
		firstCondition = true;
		secondCondition = true;
		System.out.println(firstCondition || secondCondition);
	}

}
