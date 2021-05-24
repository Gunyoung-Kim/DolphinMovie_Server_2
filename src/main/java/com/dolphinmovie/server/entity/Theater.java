package com.dolphinmovie.server.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.dolphinmovie.server.util.NoHttpResponseException;

@Entity
@Table(name="Theater")
@NamedQueries({
	@NamedQuery(name=Theater.FIND_ALL, query="select t from Theater t"),
	@NamedQuery(name=Theater.COUNT, query="select count(t.id) from Theater t")
})
public class Theater {
	
	public static final String FIND_ALL = "Theater.findAll";
	public static final String COUNT = "Theater.count";
	
	private static final String HEADER_ONE = "X-NCP-APIGW-API-KEY-ID";
	private static final String HEADER_TWO = "X-NCP-APIGW-API-KEY";
	private static final String ID = "vxy4y1l4oj";
	private static final String SECRET = "dacPpomCXNve0kdFrNJdkjHjTCA00zUzuiQP1K2I";
	
	@Id
	@Column
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(length=60, nullable = false)
	@NotNull
	private String name;
	
	@Column
	@NotNull
	private String address;
	
	@Column
	private String lot_number;
	
	@Column
	private Double xpos;
	
	@Column
	private Double ypos;
	
	@Column
	private boolean open;
	
	@Column
	private URL link;
	
	/*
	 * Constructor of this Class
	 */
	
	public Theater(@NotNull String name, @NotNull String address) {
		int isOpen = isOpened(name);
		if( isOpen == -1) 
			this.name = name;
		else {
			this.name = name.substring(0, isOpen);
			this.open = false;
		}
		
		int lot_number_index = address.indexOf("(");
		int lot_number_index_2 = address.indexOf(")");
		if(lot_number_index > -1)  {
			this.address = address.substring(0, lot_number_index);
			this.lot_number = address.substring(lot_number_index_2+1);
		} else {
			this.address = address;
			this.lot_number = null;
		}
		
		Pos p = null;
		try {
			p = getPosition(address);
		} catch(NoHttpResponseException e) {
			p = new Pos(-1,-1);
		}
		if(p != null) {
			this.xpos = p.xpos;
			this.ypos = p.ypos;
		}
	}
	
	public Theater(@NotNull String name, @NotNull String address, URL link) {
		this(name,address);
		this.link = link;
	}
	
	public Theater(@NotNull String name, @NotNull String address, Double xpos, Double ypos, boolean open, URL link) {
		this(name,address,link);
		this.xpos = xpos;
		this.ypos = ypos;
		this.open = open;
		
	}

	

	/*
	 *  Getters and Setters for fields of this Class 
	 */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLot_number() {
		return lot_number;
	}

	public void setLot_number(String lot_number) {
		this.lot_number = lot_number;
	}

	public Double getXpos() {
		return xpos;
	}

	public void setXpos(Double xpos) {
		this.xpos = xpos;
	}

	public Double getYpos() {
		return ypos;
	}

	public void setYpos(Double ypos) {
		this.ypos = ypos;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public URL getLink() {
		return link;
	}

	public void setLink(URL link) {
		this.link = link;
	}
	
	/*
	 * Theater which is not opened contains parentheses in its name
	 * 
	 * Opened -> return -1 ,  Not Opened yet -> return index of '(' to format its name
	 */
	private static int isOpened(String name) {
		char[] c = name.toCharArray();
		if(c[c.length-1] != ')') {
			return -1;
		} 
		
		for(int i=0;i<c.length;i++) {
			if(c[i] == '(') {
				return i;
			}
		}
		return -1;
	}
	
	private Pos getPosition(String address) throws NoHttpResponseException{
		Pos pos = null;
		try {
			String urlString = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
			urlString += URLEncoder.encode(address, "UTF-8");
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.addRequestProperty(HEADER_ONE, ID);
			con.addRequestProperty(HEADER_TWO, SECRET);
			JSONParser parser = new JSONParser();
			JSONObject result = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(con.getInputStream())));
			JSONArray addresses = (JSONArray) result.get("addresses");
			if(addresses.size() == 0) {
				throw new NoHttpResponseException();
			}
			double xpos = Double.parseDouble((String)(((JSONObject)addresses.get(0)).get("x")));
			double ypos = Double.parseDouble((String)(((JSONObject)addresses.get(0)).get("y")));
			pos = new Pos(xpos,ypos);
			return pos;
		} catch(MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch(NoHttpResponseException e) {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		
		json.put("name", name);
		json.put("address", address);
		json.put("lot_number", lot_number);
		json.put("xpos", xpos);
		json.put("ypos", ypos);
		json.put("open", open);
		json.put("link", link);
		
		return json;
	}
	
	private static class Pos {
		double xpos;
		double ypos;
		
		public Pos(double xpos, double ypos) {
			super();
			this.xpos = xpos;
			this.ypos = ypos;
		}
		
		
	}
}