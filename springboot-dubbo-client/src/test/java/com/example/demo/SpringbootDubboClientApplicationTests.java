package com.example.demo;

import com.example.demo.api.model.Categories;
import com.example.demo.api.service.CategoriesService;
import org.apache.dubbo.config.annotation.Reference;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDubboClientApplicationTests {

	@Reference(protocol = "dubbo")
	private CategoriesService categoriesService;

	public static String ALLITEBOOKS_URL = "http://www.allitebooks.org";

	@Test
	public void contextLoads() throws IOException {
		Document doc = Jsoup.connect(ALLITEBOOKS_URL).get();
		Element keywords = doc.select("li[id=menu-item-65]").select("ul").first();
		List<Element> elements = keywords.children().stream().filter(x -> x!=null).collect(Collectors.toList());
		elements.forEach(element -> {
			Element elia = element.select("li a").first();
			Elements eullis = element.select("ul li a");
			Categories categories = new Categories();
			categories.setDisplay(elia.text());
			categories.setType(0);
			Categories id0 = categoriesService.select(categories);
			eullis.forEach( element1 -> {
				Categories categories1 = new Categories();
				categories.setName(element1.attr("href").split("/")[4]);
				categories.setType(id0.getId());
				categories.setDisplay(element1.text());
				Categories id = categoriesService.select(categories);
				if(null == id){
					categories.setCreateBy("1");
					categories.setUpdateBy("1");
					categories.setType(id0.getId());
					categories.setGmtCreate(new Date());
					categories.setGmtModified(new Date());
					categories.setIsDelete(0L);
					categoriesService.insert(categories);
				}else{
					id.setDisplay(categories.getDisplay());
					id.setName(categories.getName());
					categoriesService.update(categories1);
				}
				System.out.println(elia.text()+","+ element1.attr("href")+","+element1.text());
			});
		});
	}


	public static void main(String[] args) {

		String aa = "http://www.allitebooks.org/web-development/rails/";
		String[] aas = aa.split("/");
		System.out.println(aas[4]);
	}
}
