import java.text.SimpleDateFormat;

public class TobeDeleted {
	public static void main(String[] args) {
		String string_datedd = "Sep 23, 2019";
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
		try {
		    System.out.println(format.parse(string_datedd).getTime());
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
