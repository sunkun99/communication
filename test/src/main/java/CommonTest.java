import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sunkun
 * Date: 2017/5/8
 * Time: 14:11
 */
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
class A1{
    public void testLocal(int a) {
	}
}


public class CommonTest {
	public static void main(String[] args) throws UnsupportedEncodingException, JsonProcessingException {
		String jsonStr = "{\"ret\":\"0\",\"msg\":\"\",\"requestId\":\"null\",\"data\":{\"MSG\":\"请求成功！\",\"DATA\":{\"securitySign\":\"dNqeoa7hEH2muMA0k4NclxOEhLGRSAieYmKSDL5NwBo1JYAPyUlODDFPkJmahU84a66rQVRsAZNM\\r\\nXScnDTOPnQDrFystj9VAgLzVGwnpjHi+0gyox+FNmcq9q9JAZQlTpJnzAq/jJhhTgAaFfVArj9Ch\\r\\nZU7D4i1DXVLc6bdLpsc=\\r\\n\",\"orderTraceNo\":\"I000000000892207\",\"transactionBizNo\":\"201706192017061901212205623\",\"clientBankCode\":\"PAB\",\"clientBankAccount\":\"6225380093420398\",\"serialNo\":\"2017061991766504TS\",\"bankNo\":\"8740088\",\"orderNo\":\"201706050015071733O\",\"orderAmount\":\"12300\",\"clientName\":\"姚敬梅\",\"notifyType\":\"01\",\"merOrderNo\":\"PAJK8000023138135\",\"merchantCode\":\"2016071300000028\"},\"CODE\":\"00\"}}";
		Map<String, Object> resultMap = JSON.parseObject(jsonStr, Map.class);
		if (StringUtils.equals("0", (String) resultMap.get("ret"))) {
			//ESG返回成功
			Map<String, Object> data = (Map) resultMap.get("data");
			String msg = (String)data.get("MSG");
			Object busiData = ((JSONObject)data.get("DATA")).get("notifyType");
			System.out.println("notifyType:" + busiData);
		}
	}
}
