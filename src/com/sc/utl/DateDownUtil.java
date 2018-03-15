package com.sc.utl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 爬虫功能
 * @author Administrator
 *
 */
public class DateDownUtil {

	/**
	 * 
	 * @param url 需要爬取的网站
	 * @param conding 编码集（由于网站的编码集有可能存在不同）
	 * 根据URL与字符集来爬取页面中的源代码
	 * @return
	 */
	public static String getHtmlUrlResource(String url,String encoding){
		URL urlObj = null;
		URLConnection connection = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		StringBuffer buffer = new StringBuffer();
		try {
			//建立网络连接
			urlObj = new URL(url);
			//打开网络连接
			connection = urlObj.openConnection();
			//建立文件输入流
			isr = new InputStreamReader(connection.getInputStream(),encoding);
			//建立缓存写入
			reader = new BufferedReader(isr);
			//写入
			String temp = null;			
			while((temp=reader.readLine())!=null){				
				buffer.append(temp+"\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(isr!=null){
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 测试获取智联相关java的招聘信息
	 * @return 
	 */
	public static List<HashMap<String, String>> getFilterInfo(String url,String encoding){
		//获取代码
		String html = getHtmlUrlResource(url, encoding);
		//解析代码
		Document document = Jsoup.parse(html);
		//获取div_id
//		Element element = document.getElementById("newlist_list_content_table");
		//获取div_class
		Elements elements = document.getElementsByClass("newlist");
		HashMap<String, String> map = null;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		for (Element el : elements) {
			map = new HashMap<String,String>();
			//获取公司名称
			String gsmc = el.getElementsByClass("gsmc").text();
			//获取职位月薪
			String zwyx = el.getElementsByClass("zwyx").text();
			//获取工作地点
			String gzdd = el.getElementsByClass("gzdd").text();
			//获取发布时间
			String gxsj = el.getElementsByClass("gxsj").text();
			map.put("gsmc", gsmc);
			map.put("zwyx", zwyx);
			map.put("gzdd", gzdd);
			map.put("gxsj", gxsj);
			list.add(map);
		}
		return list;
	}
	
	public static void main(String[] agrs){
		String url = "https://sou.zhaopin.com/jobs/searchresult.ashx?kw=java&sm=0&p=1";
		List<HashMap<String, String>> list = getFilterInfo(url, "utf-8");
		System.out.println(list);
//		System.out.println(getHtmlUrlResource("http://www.baidu.com", "utf-8")+"\n");
	}
}
