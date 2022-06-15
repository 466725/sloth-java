import java.io.IOException;

public class TobeDeleted {
	public static void main(String[] args) {
		System.out.println("Execute command line with Java, Started!");
		excCommand();
		System.out.println("Execute command line with Java, Ended!");
	}

	// Run cmd commands through Java
	public static void excCommand() {
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec(new String[] { "cmd.exe", "/c", "start" });
			rt.exec("cmd /c start notepad++.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
