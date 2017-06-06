import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/5/8
 * Time: 14:11
 */
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum A1{
	THIRTY("30", "单步单人--顺序批示， 单步多人--协同批示"),
	ONE_HUNDRED_TWENTY("120", "协同批示"),
	ONE_THOUSAND_ONE_HUNDRED_TEN("1110", "传阅");

	private String code;

	private String desc;

	A1(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

//	@JsonValue
	public String getCode() {
		return code;
	}

//	@JsonValue
	public String getDesc() {
		return desc;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"code\":\"").append(code).append("\"");
		sb.append(", \"desc\":\"").append(desc).append("\"");
		sb.append('}');
		return sb.toString();
	}
}


public class CommonTest {
	public static void main(String[] args) throws UnsupportedEncodingException, JsonProcessingException {
		List<String> a = new ArrayList<>();
		a.add("1");
		a.add("2");
		System.out.println(a.get(1));
	}
}
