package com.dolphinmovie.server.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.dolphinmovie.server.entity.Theater;


@Service("theaterUpdateService")
public class TheaterUpdateService {
	private Environment env;
	
	private WebDriver driver;
	
	private String[] optionArguments = {
			"headless" // crawling without browser ui
	};
	
	private TheaterUpdateService(Environment env) {
		this.env = env;
		updateTheaterList();
	}
	
	/*
	 *  return null if there is exception
	 */
	public List<Theater> updateTheaterList() {
		List<Theater> theaterList = new ArrayList<>();
		System.setProperty(env.getProperty("webdriver.id"), env.getProperty("webdriver.path"));
		
		ChromeOptions option = new ChromeOptions();
		option.addArguments(optionArguments);
		driver = new ChromeDriver(option);
		
		String base_url = env.getProperty("theaterupdate.base_url");
		
		try {
			driver.get(base_url);
			
			for(int i=0;i<7;i++) {
				WebElement[] page = new WebElement[5];
				page[0] = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[1]/div[7]/div[6]/div/a[1]"));
				page[1] = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[1]/div[7]/div[6]/div/a[2]"));
				page[2] = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[1]/div[7]/div[6]/div/a[3]"));
				page[3] = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[1]/div[7]/div[6]/div/a[4]"));
				page[4] = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[1]/div[7]/div[6]/div/a[5]"));
				WebElement next_page = driver.findElement(By.xpath("/html/body/div[5]/div[2]/div[1]/div[7]/div[6]/div/button[2]"));
				
				Actions actions = new Actions(driver);
				
				for(int j=1;j<=5;j++) {
					actions.moveToElement(page[j-1]).click().build().perform();
					Thread.sleep(300);
					Document doc = Jsoup.parse(driver.getPageSource());
					
					Elements eles = doc.getElementsByClass("link_name");
					Elements address = doc.getElementsByClass("addr");
					Elements links = doc.getElementsByClass("homepage");
					
					
					for(int k=0;k<eles.size();k++) {
						String name = eles.get(k).text();
						String addr = address.get(k).text();
						String link = links.get(k).absUrl("href");
						
						if(link.equals(""))
							theaterList.add(new Theater(name,addr));
						else
							theaterList.add(new Theater(name,addr,new URL(link)));
					}
					if(i==6 && j==4)
						break;
					
				}
				Thread.sleep(300);
				if(i <6) 
					actions.moveToElement(next_page).click().build().perform();
			}
			
			System.out.println(theaterList.size());
			return theaterList;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(driver != null)
				driver.close();
		}
	}
	

}
