import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class CommonTest {
	public static void main(String[] args) throws UnsupportedEncodingException, JsonProcessingException {
		List<String> a = new ArrayList<>();
		a.add("1");
		a.add("2");
		System.out.println(a.get(1));
	}
}
