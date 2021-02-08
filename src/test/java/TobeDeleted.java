import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TobeDeleted {
	public static void main(String[] args) {
		System.out.println("Execute command line with Java, Started!");
		excCommand();
		System.out.println("Execute command line with Java, Ended!");
	}

	// Parse date to long integer 
	public static void parseDateToLong() {
		String string_datedd = "Sep 23, 2019";
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
		try {
			System.out.println(format.parse(string_datedd).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// Run cmd commands through Java
	public static void excCommand() {
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec(new String[] { "cmd.exe", "/c", "start" });
			rt.exec("cmd /c start notepad++.exe");
			rt.exec("cmd /c start Appium.exe");
			rt.exec("cmd /c start studio64.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
