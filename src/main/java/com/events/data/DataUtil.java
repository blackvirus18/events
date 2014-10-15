package com.events.data;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
	private static String access_token="1524213444481981%7C8hnd6Ub7AxOOfi6GAQa5i2VGb7g";
	private static String base_url="https://graph.facebook.com/";
	private static List<String> meaningfulStrings = new ArrayList<String>(Arrays.asList("night", "nite", "music","musical","#musical"));
	private List<String> getUrls(){
		List<String> urls=new ArrayList<String>();
		urls.add("https://www.facebook.com/TURQUOISE.COTTAGE");
		urls.add("https://www.facebook.com/hypetheclub");
		return urls;
	}
	public List<Club> createData() throws ClientProtocolException, IOException{
		List<Club> clubs=new ArrayList<Club>();
		clubs=parseIdsPage();
		for(int i=0;i<clubs.size();i++){
			Club club=clubs.get(i);
			List<Event> events=parsePageFeedData(club.getId());
			events=getEventsData(club.getId(),events);
			club.setEvents(events);
			clubs.set(i, club);
		}
		System.out.println(clubs);
		return clubs;
	}
	private List<Event> getEventsData(String id,List<Event> events) throws ClientProtocolException, IOException {
		List<String> eventIds=getEventIds(id);
		for(String eventId:eventIds){
			Event event=parseEventPage(eventId);
			events.add(event);
		}
		return events;
	}
	private List<String> getEventIds(String id) {
		String url=id+"/events/";
		List<String> eventIds=new ArrayList<String>();
		try {
			Map<String, Object> jsonEventsData=makeApiRequest(url, true);
			List<HashMap<String,Object>> eventsList=(List<HashMap<String, Object>>) jsonEventsData.get("data");
			if(eventsList!=null && eventsList.size()>0){
			for(HashMap<String,Object> event:eventsList){
				if(eventtDateisValid((String)event.get("start_time"))){
					eventIds.add((String)event.get("id"));
				}
			}
		} 
	}
		catch (IOException e) {
			e.printStackTrace();
		}
		return eventIds;
	}
	private Event parseEventPage(String eventId) throws ClientProtocolException, IOException {
		Map<String, Object> eventPageJsonData=makeApiRequest(eventId, true);
		Event event=new Event();
		event.setId((String)eventPageJsonData.get("id"));
		event.setName((String)eventPageJsonData.get("name"));
		event.setImageSrc(getImageUrl(eventId,"large","400","400"));
		event.setDescription((String)eventPageJsonData.get("description"));
		event.setStartDate((String)eventPageJsonData.get("start_time"));
		return event;
	}
	private List<Event> parsePageFeedData(String id) {
		String feedUrl=id+"/feed";
		List<Event> events=new ArrayList<Event>();
		try {
			Map<String, Object> jsonFeedData=makeApiRequest(feedUrl,true);
			List<HashMap<String,Object>> messageList=(List<HashMap<String, Object>>) jsonFeedData.get("data");
			for(HashMap<String,Object> messageData:messageList){
				String objectId=(String) messageData.get("object_id");
				if(StringUtils.isNotBlank(objectId)){
					Event event=new Event();
					event.setDescription((String) messageData.get("message"));
					event.setImageSrc(getImageUrl(objectId,"normal","400","400"));
					event.setName((String)messageData.get("message"));
					event.setId(objectId);
					event.setStartDate((String)messageData.get("created_time"));
					event.setLink((String)messageData.get("link"));
					events.add(event);
				}
				/*String[] tokenArray=message.split(" ");
				for(String token:tokenArray){
					if(messageList.contains(token))
						matchCount++;
				}
				if(matchCount>=2)
					meaningfulMessage=true;
				if(meaningfulMessage){
					Event event=new Event();
					String messageLink=(String) messageData.get("link");
					if(messageLink.indexOf("facebook")>0)
						parseMessageLink=true;
					if(parseMessageLink){
						
					}
					else{
						
					}
				}*///TODO create intelligence for meaningful message
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return events;
	}
	private List<Club> parseIdsPage() {
		List<String> urlList=getUrls();
		String urls=StringUtils.join(urlList, ",");
		String apiUrl="?ids="+urls;
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
				club.setProfilePic(getImageUrl(club.getId(),"large","400","400"));
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
  	  HttpGet request = new HttpGet(base_url+url);
  	  HttpResponse response = client.execute(request);
  	  ObjectMapper mapper = new ObjectMapper();
  	  Map<String, Object> jsonMap = mapper.readValue(response.getEntity().getContent(), new TypeReference<HashMap<String,Object>>() {});
  	  return jsonMap;
  }
	private String getImageUrl(String id,String type,String width,String height){
		String url=id+"/picture?redirect=0&type="+type+"&width="+width+"&height="+height;
		//System.out.println(id);
		Map<String, Object> imageDataObject=null;
		try {
			imageDataObject = makeApiRequest(url,false);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> imageObject=(Map<String, Object>)imageDataObject.get("data");
		//System.out.println(imageDataObject);
		return (String)imageObject.get("url");
	}
	private boolean eventtDateisValid(String eventDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		boolean isValidDate=false;
        try
        {
             Date event = simpleDateFormat.parse(eventDate);
             Calendar c = Calendar.getInstance();
	         c.set(Calendar.HOUR, 0);
	         c.set(Calendar.MINUTE, 0);
	         c.set(Calendar.SECOND, 0);
	         Date today = c.getTime();
         if(event.after(today)){
        	 isValidDate=true;
         }
      }
      catch (ParseException ex)        {
            System.out.println("Exception "+ex);
      }
		return isValidDate;
	}
	public static void main(String args[]) throws ClientProtocolException, IOException{
		DataUtil du=new DataUtil();
		du.createData();
	}
}

