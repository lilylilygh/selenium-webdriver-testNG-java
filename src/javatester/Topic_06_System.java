package javatester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class Topic_06_System {
	public static void main(String[] args) {
		String projectPath = System.getProperty("user.dir");
		// Image name
		String imageName1 = "MDC_1612.JPG";
		String imageName2 = "MDC_1911.JPG";
		String imageName3 = "MDC_1912.JPG";
		String imageUploadFolder = projectPath + File.separator + "uploadFiles" + File.separator;
		//String image1FilePath, image2FilePath, image3FilePath;
		
		//Image Path
		String image1FilePath = projectPath + "/uploadFiles/" + imageName1;
		String image2FilePath = projectPath + "/uploadFiles/" + imageName2;
		String image3FilePath = projectPath + "/uploadFiles/" + imageName3;
		
		System.out.println(image1FilePath);
		System.out.println(image2FilePath);
		System.out.println(image3FilePath);
		
		File directoryPath = new File(imageUploadFolder);
		// List of all files and directories
		String contents[] = directoryPath.list();
		
		//Lấy ra rồi lưu trong mảng ArrayList
		List<String> fileNames = new ArrayList<String>();
		
		System.out.println("List of files and directories in the specified directory:");
		for (int i = 0; i < contents.length; i++) {
			System.out.println(contents[i]);
		}
	}
}
