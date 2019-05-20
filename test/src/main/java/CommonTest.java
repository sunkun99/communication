import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
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

public class CommonTest {
	static class MyClassLoader extends ClassLoader {
		private String classPath;

		public MyClassLoader(String classPath) {
			this.classPath = classPath;
		}

		private byte[] loadByte(String name) throws Exception {
			name = name.replaceAll("\\.", "/");
			FileInputStream fis = new FileInputStream(classPath + "/" + name
					+ ".class");
			int len = fis.available();
			byte[] data = new byte[len];
			fis.read(data);
			fis.close();
			return data;

		}

		protected Class<?> findClass(String name) throws ClassNotFoundException {
			try {
				byte[] data = loadByte(name);
				return defineClass(name, data, 0, data.length);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ClassNotFoundException();
			}
		}

	};

	public void hello() {
		System.out.println("hello");
	}

	public static void main(String args[]) throws Exception {
//		MyClassLoader classLoader = new MyClassLoader("/Users/sk/Downloads/Thunder");
//		Class clazz = classLoader.loadClass("A");
//		Object obj = clazz.newInstance();
//		Method helloMethod = clazz.getDeclaredMethod("hello", null);
//		helloMethod.invoke(obj, null);
	}
}
