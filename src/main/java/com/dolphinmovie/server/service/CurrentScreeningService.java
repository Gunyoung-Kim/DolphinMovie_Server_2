package com.dolphinmovie.server.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.dolphinmovie.server.entity.Movie;

@Service("currentScreeningService")
public class CurrentScreeningService {
	private Environment env;
	private WebDriver driver;
	
	private List<Movie> screeningMovies;
	
	private String[] optionArguments = {
			"headless" // crawling without browser ui
	};
	/*
	 *  update Once in a day 
	 */
	
	private CurrentScreeningService(Environment env) {
		this.env = env;
		updateList();
	}
	
	private boolean updateList() {
		this.screeningMovies = new ArrayList<>();
		System.setProperty(env.getProperty("webdriver.id"), env.getProperty("webdriver.path"));
		
		ChromeOptions option = new ChromeOptions();
		option.addArguments(optionArguments);
		driver = new ChromeDriver(option);
		
		
		// get from properties file
		String base_url = env.getProperty("screeningmovie.base_url");
		
		try {
			driver.get(base_url);
			
			Document doc = Jsoup.parse(driver.getPageSource());
			
			Elements titles = doc.getElementsByClass("title _ellipsis");
			Elements imageBoxes = doc.getElementsByClass("img_box");
			Elements link = doc.getElementsByClass("btn_reserve");
			
			for(int i=0;i<titles.size();i++) {
				String title = titles.get(i).text();
				String thumbnailURL = imageBoxes.get(i).child(0).attr("src");
				String infoLink = "https://search.naver.com/search.naver" + link.get(i).attr("href");
				
	
				this.screeningMovies.add(new Movie(title,new URL(thumbnailURL),new URL(infoLink)));
			}
			
			driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div[1]/div[3]/div[2]/div/div/div/div/div[4]/div/a[2]")).click();
			Thread.sleep(100);
			
			doc = Jsoup.parse(driver.getPageSource());
			
			titles = doc.getElementsByClass("title _ellipsis");
			imageBoxes = doc.getElementsByClass("img_box");
			link = doc.getElementsByClass("btn_reserve");
			
			for(int i=0;i<titles.size();i++) {
				String title = titles.get(i).text();
				String thumbnailURL = imageBoxes.get(i).child(0).attr("src");
				String infoLink = "https://search.naver.com/search.naver" + link.get(i).attr("href");
				
				this.screeningMovies.add(new Movie(title,new URL(thumbnailURL),new URL(infoLink)));
			}
			
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if(driver != null)
				driver.close();
		}
	}
	
	public List<Movie> getScreeingMovies() {
		return this.screeningMovies;
	}
}
