package com.events.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;



import com.events.model.Category;
import com.events.model.Club;
import com.events.model.Event;
import com.events.model.Location;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
public class DataUtil {
	private static String access_token="1524213444481981|8hnd6Ub7AxOOfi6GAQa5i2VGb7g";
	private List<String> getUrls(){
		List<String> urls=new ArrayList<String>();
		urls.add("https://www.facebook.com/TURQUOISE.COTTAGE");
		urls.add("https://www.facebook.com/hypetheclub");
		return urls;
	}
	public List<Club> createData(){
		List<Club> clubs=new ArrayList<Club>();
		clubs=parseIdsPage();
		for(int i=0;i<clubs.size();i++){
			Club club=clubs.get(i);
			List<Event> events=parsePageFeedData(club.getId());
		}
		return clubs;
	}
	private List<Event> parsePageFeedData(String id) {
		return null;
	}
	private List<Club> parseIdsPage() {
		List<String> urlList=getUrls();
		String urls=StringUtils.join(urlList, ",");
		String apiUrl="https://graph.facebook.com?ids="+urls;
		List<Club> clubs=new ArrayList<Club>();
		try {
			Map<String, Object> jsonData=makeApiRequest(apiUrl,false);
			for(String url:urlList){
				Club club=new Club();
				HashMap<String,Object> jsonObject=(HashMap<String, Object>) jsonData.get(url);
				club.setId((String) jsonObject.get("id"));
				club.setName((String) jsonObject.get("name"));
				club.setDescription((String)jsonObject.get("description"));
				club.setFacebookPage(url);
				club.setWebsite((String) jsonObject.get("website"));
				club.setContactNumber((String) jsonObject.get("phone"));
				club.setProfilePic(getImageUrl(club.getId(),"large"));
				HashMap<String,Object> coverObject=(HashMap<String, Object>) jsonObject.get("cover");
				club.setCoverPhoto((String)coverObject.get("source"));
				HashMap<String,Object> locationObject=(HashMap<String, Object>) jsonObject.get("location");
				Location location=new Location();
				location.setCity((String)locationObject.get("city"));
				location.setCountry((String)locationObject.get("country"));
				location.setLatitude(String.valueOf((Double)locationObject.get("latitude")));
				location.setLongitude(String.valueOf((Double)locationObject.get("longitude")));
				location.setZip((String)locationObject.get("zip"));
				club.setLocation(location);
				List<Category> categories=new ArrayList<Category>();
				List<HashMap<String,Object>> categoryList=(List<HashMap<String,Object>>)jsonObject.get("category_list");
				for(HashMap<String,Object> categoryObject:categoryList){
					Category category=new Category();
					category.setName((String) categoryObject.get("name"));
					categories.add(category);
				}
				club.setCategories(categories);
				clubs.add(club);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clubs;
	}
	private Map<String, Object> makeApiRequest(String url,boolean authRequired) throws ClientProtocolException, IOException{
  	  HttpClient client = new DefaultHttpClient();
  	  if(authRequired){
  		url=url+"?access_token="+access_token;
  	  }
  	  HttpGet request = new HttpGet(url);
  	  HttpResponse response = client.execute(request);
  	  ObjectMapper mapper = new ObjectMapper();
  	  Map<String, Object> jsonMap = mapper.readValue(response.getEntity().getContent(), new TypeReference<HashMap<String,Object>>() {});
  	  return jsonMap;
  }
	private String getImageUrl(String id,String type){
		String url="https://graph.facebook.com/"+id+"/picture?redirect=0&type="+type;
		Map<String, Object> imageDataObject=null;
		try {
			imageDataObject = makeApiRequest(url,false);
			System.out.println(imageDataObject);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> imageObject=(Map<String, Object>)imageDataObject.get("data");
		return (String)imageObject.get("url");
	}
	public static void main(String args[]){
		DataUtil du=new DataUtil();
		du.createData();
	}
}

