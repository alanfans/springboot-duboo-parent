package com.example.demo;

import com.example.demo.api.model.Book;
import com.example.demo.api.model.Bookimg;
import com.example.demo.api.model.Categories;
import com.example.demo.api.model.Downloadlink;
import com.example.demo.api.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.json.JSON;
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
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDubboClientApplicationTests {

	@Reference(protocol = "dubbo")
	private CategoriesService categoriesService;

    @Reference(protocol = "dubbo")
    private BookService bookService;

    @Reference(protocol = "dubbo")
    private BookImgService bookImgService;

	@Reference(protocol = "dubbo")
	private DownloadlinkService downloadlinkService;

	@Reference(protocol = "dubbo",async = true)
	RedisService redisService;

    //Categories
	public void contextLoads() {
		Document doc = null;
		try {
			doc = getDoc(CategoriesService.ALLITEBOOKS_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element keywords = doc.select("li[id=menu-item-65]").select("ul").first();
		List<Element> elements = keywords.children().stream().filter(x -> x!=null).collect(toList());
		elements.forEach(element -> {
			Element elia = element.select("li a").first();
			Elements eullis = element.select("ul li a");
			Categories categories = new Categories();
			categories.setDisplay(elia.text());
			categories.setType(0);
			Categories id0 = categoriesService.select(categories);
			eullis.forEach( element1 -> {
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
					categoriesService.update(id);
				}
				System.out.println(elia.text()+","+ element1.attr("href")+","+element1.text());
			});
		});
	}

	@Test
	public void getBookDetil() {
		Document doc = null;
		try {
			doc = getDoc(CategoriesService.ONEBOOK_URL);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String single_title = doc.selectFirst("h1[class=single-title]").text();
		Elements entry_meta = doc.select("div[class=entry-meta clearfix] img");
		Elements entry_headerh4 = doc.select("header[class=entry-header] h4");
		Elements book_detail = doc.select("div[class=book-detail] dl dd");
		Elements entry_content = doc.select("div[class=entry-content]");
		Elements download_links = doc.select("span[class=download-links] a");
        book_detail.forEach( element -> System.out.println(element.text()));
		List<String> details = book_detail.stream().map(Element::text).collect(toList());
        Book book = new Book();
        book.setBookTitie(single_title);
        book.setBookDesc(entry_headerh4.text());
        book.setAuthor(details.get(0));
        book.setIsbn(details.get(1));
        book.setYear(Integer.parseInt(details.get(2)));
        book.setPages(details.get(3));
        book.setLanguage(details.get(4));
        book.setFileSize(details.get(5));
        book.setFileFormat(details.get(6));
        Categories categories = new Categories();
        categories.setDisplay(details.get(7));
        categories = categoriesService.select(categories);
        book.setCategory(categories.getId());
        book.setBookDescription(entry_content.html());

        Book book1 = bookService.selectbyName(single_title);
        if(Objects.isNull(book1)){
			book.setCreateBy("1");
			book.setUpdateBy("1");
			book.setGmtCreate(new Date());
			book.setGmtModified(new Date());
			book.setIsDelete(0L);
            Integer id = bookService.addBook(book);
            Bookimg bookimg = new Bookimg();
            bookimg.setBookId(id);
            bookimg.setHeigth(Integer.parseInt(entry_meta.attr("height")));
            bookimg.setImgPath(entry_meta.attr("src"));
            bookimg.setWidth(Integer.parseInt(entry_meta.attr("width")));
            bookimg.setAlt(entry_meta.attr("alt"));
			bookimg.setCreateBy("1");
			bookimg.setUpdateBy("1");
			bookimg.setGmtCreate(new Date());
			bookimg.setGmtModified(new Date());
			bookimg.setIsDelete(0L);
            bookImgService.addBookImg(bookimg);
			download_links.forEach(element -> {
				Downloadlink downloadlink = new Downloadlink();
				downloadlink.setBookId(id);
				downloadlink.setLink(element.attr("href"));
				downloadlinkService.addDownloadlink(downloadlink);
			});
        }
	}



	@Test
	public void getbookUrl(){
		//大类
		Categories categories = new Categories();
		categories.setType(0);
//		categories.setName("datebases");
		List<Categories> categoriesList = categoriesService.selectByType(categories);
		categoriesList.forEach( categories1 -> {
				for (Integer i = 1; i < Integer.MAX_VALUE; i++) {
					String Url = CategoriesService.ALLITEBOOKS_URL + categories1.getName() +"/page/"+i;
					try {
						Document doc = getDoc(Url);
						String no_book = doc.select("h1[class=page-title]").text();
						if(StringUtils.equals("No Posts Found.",no_book)){
							break;
						}
						//get Url
						List<String> books_url = execute(doc);
						System.out.println(JSON.json(books_url));
					} catch (IOException e) {
						e.printStackTrace();
						break;
					}}
		});
	}

	@Test
	public void testGEtURL(){
		String aa = "http://www.allitebooks.org/datebases/page/6/";
		try {
			Document document = getDoc(aa);
			List<String> url = execute(document);
			System.out.println(JSON.json(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	private  List<String> execute(Document doc) {
		Elements elements = doc.select("h2[class=entry-title] a");
		List<String> bookUrls = elements.stream().map( stringurl -> stringurl.attr("href")).collect(toList());
		try {
			System.out.println(JSON.json(bookUrls));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bookUrls;
	}


	private  Document getDoc(String Url) throws IOException {
		try{
			Document doc = Jsoup.parse(new URL(Url),90000);
			return doc;
		}catch (SocketTimeoutException se){
			getDoc(Url);
		}
		return null;
	}

}
