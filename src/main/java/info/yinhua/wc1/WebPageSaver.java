package info.yinhua.wc1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

// https://stackoverflow.com/questions/7079574/saving-a-web-page-to-a-file-in-java
public class WebPageSaver {
	String baseUrl = "http://www.quanxue.cn/CT_FoJia/WuDengHY/";
	String basePath = "D:/WuDengHY/";

	String[] urls = {
			"WuDengHY01.html",
			"WuDengHY02.html",
			"WuDengHY03.html",
			"WuDengHY04.html",
			"WuDengHY05.html"
 };

	public static void main(String args[]) throws Exception {
		WebPageSaver w = new WebPageSaver();

		for (String url : w.urls) {
			w.save(w.baseUrl + url, w.basePath + url);
			System.out.println(url);
		}
	}

	public void save(String web, String file) throws Exception {

		URL url = new URL(web);
		URLConnection conn = url.openConnection();
		conn.connect();
		InputStream is = conn.getInputStream();

		OutputStream out = new FileOutputStream(file);
		copy(is, out);
		is.close();
		out.close();
	}

	private void copy(InputStream from, OutputStream to) throws IOException {
		byte[] buffer = new byte[4096];
		while (true) {
			int numBytes = from.read(buffer);
			if (numBytes == -1) {
				break;
			}
			to.write(buffer, 0, numBytes);
		}
	}
}