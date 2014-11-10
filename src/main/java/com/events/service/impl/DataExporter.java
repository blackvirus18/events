package com.events.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.events.data.DataUtil;
import com.events.model.Category;
import com.events.model.Club;
import com.events.model.Event;
import com.events.model.Location;
import com.events.service.interfaces.IDataExporter;
import com.mysql.jdbc.Statement;

public class DataExporter implements IDataExporter {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://54.187.75.199:3306/events";
	private Connection conn;
		public void insertData() {
		createConnection();
		DataUtil du=new DataUtil();
		try {
			List<Club> clubs=du.createData(getUrls());
			for(Club club:clubs){
				insertClubData(club);
				insertLocationData(club);
				insertCategoryData(club);
				insertEventData(club);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			closeConnection();
		}
	}
		public List<Club> getData(){
			createConnection();
			List<Club> clubs=getClubData();
			List<Club> finalClubData=new ArrayList<Club>();
			for(Club club:clubs){
				club.setLocation(getLocationData(club.getId()));
				club.setCategories(getCategoryData(club.getId()));
				club.setEvents(getEventsData(club.getId()));
				finalClubData.add(club);
			}
			closeConnection();
			return clubs;
		}
		private Location getLocationData(String id) {
			String query = "select * from location where club_id="+id;
			Location location =new Location();
			try{
				PreparedStatement st = conn.prepareStatement(query);
			    ResultSet rs = st.executeQuery();
			    while(rs.next()){
			    	location.setCity(rs.getString("city"));
			    	location.setCountry(rs.getString("country"));
			    	location.setLatitude(rs.getString("latitude"));
			    	location.setLongitude(rs.getString("longitude"));
			    	location.setZip(rs.getString("zip"));
			    }
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			System.out.println(location);
			return location;
		}
		private List<Category> getCategoryData(String id) {
			String query = "select * from category where club_id="+id;
			List<Category> categories=new ArrayList<Category>();
			try{
				PreparedStatement st = conn.prepareStatement(query);
			    ResultSet rs = st.executeQuery();
			    while(rs.next()){
			    	Category category=new Category();
			    	category.setName(rs.getString("name"));
			    	categories.add(category);
			    }
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			System.out.println(categories);
			return categories;
		}
		private List<Event> getEventsData(String id) {
			String query = "select * from event_data where club_id="+id;
			List<Event> events=new ArrayList<Event>();
			try{
				PreparedStatement st = conn.prepareStatement(query);
			    ResultSet rs = st.executeQuery();
			    while(rs.next()){
			    	Event event=new Event();
			    	event.setId(rs.getString("event_id"));
			    	event.setName(rs.getString("name"));
			    	event.setDescription(rs.getString("description"));
			    	event.setStartDate(rs.getString("event_date"));
			    	event.setLink(rs.getString("link"));
			    	event.setImageSrc(rs.getString("image_src"));
			    	event.setValidTill(rs.getString("validTill"));
			    	events.add(event);
			    }
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			System.out.println(events);
			return events;
		}
		private List<Club> getClubData(){
			List<Club> clubs=new ArrayList<Club>();
			String query = "select * from club";
			try{
			PreparedStatement st = conn.prepareStatement(query);
		    ResultSet rs = st.executeQuery();
		      while (rs.next())
		      {
		    	Club club=new Club();  
		        club.setId(rs.getString("club_id"));
		        club.setName(rs.getString("name"));
		        club.setCoverPhoto(rs.getString("cover_photo"));
		        club.setWebsite(rs.getString("website"));
		        club.setFacebookPage(rs.getString("facebook_page"));
		        club.setContactNumber(rs.getString("contact_number"));
		        club.setDescription(rs.getString("description"));
		        club.setProfilePic(rs.getString("profile_pic"));
		       // System.out.println(club);
		        clubs.add(club);
		      }
		      st.close();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			//System.out.println(clubs);
			return clubs;
		}
	
		private List<String> getUrls(){
			List<String> urls=new ArrayList<String>();
			
			String query = "select * from club_url";
			try{
			PreparedStatement st = conn.prepareStatement(query);
		    ResultSet rs = st.executeQuery();
		      while (rs.next())
		      {
		        String url = rs.getString("url");
		        urls.add(url);
		        System.out.println(url);
		      }
		      st.close();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
//			urls.add("https://www.facebook.com/TURQUOISE.COTTAGE");
//			urls.add("https://www.facebook.com/hypetheclub");
			System.out.println(urls);
			return urls;
		}
		private void insertClubData(Club club) {
			PreparedStatement  stmt=null;
		String sql="INSERT INTO club values(\""+club.getId()+"\",\""+club.getName()
				+"\",\""+club.getCoverPhoto()+"\",\""+club.getWebsite()+"\",\""+club.getFacebookPage()
				+"\",\""+club.getContactNumber()+"\",\""+club.getDescription()+"\",\""+club.getProfilePic()+"\")";
		try {
			stmt=conn.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private void insertLocationData(Club club) {
		Location location=club.getLocation();
		PreparedStatement  stmt=null;
		String sql="INSERT INTO location values(\""+club.getId()+"\",\""+location.getCountry()
				+"\",\""+location.getCity()+"\",\""+location.getLatitude()+"\",\""+location.getLongitude()
				+"\",\""+location.getZip()+"\")";
		try {
			System.out.println(sql);
			stmt=conn.prepareStatement(sql);
			stmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	private void insertCategoryData(Club club) throws SQLException {
		PreparedStatement  stmt=null;
		List<Category> categories=club.getCategories();
		String sql = "insert into category(club_id, name) values(?, ?)";
		stmt=conn.prepareStatement(sql);
		for(Category category:categories){
			stmt.setString(1, club.getId());
			stmt.setString(2, category.getName());
			stmt.addBatch();
		}
		stmt.executeBatch();
	}



	private void insertEventData(Club club) throws SQLException {
		PreparedStatement  stmt=null;
		List<Event> events=club.getEvents();
		String sql = "insert into event_data(event_id, club_id, name, description,event_date, link, image_src) values(?, ?, ?,?, ?, ?,?)";
		stmt=conn.prepareStatement(sql);
		for(Event event:events){
			System.out.println(event.getName());
			stmt.setString(1, event.getId());
			stmt.setString(2, club.getId());
			stmt.setString(3, event.getName());
			stmt.setString(4, event.getDescription());
			stmt.setString(5, event.getStartDate());
			stmt.setString(6, event.getLink());
			stmt.setString(7, event.getImageSrc());
			stmt.addBatch();
		}
		stmt.executeBatch();
	}


	public void updateApprovedEvents(List<HashMap> events){
		PreparedStatement  stmt=null;
		String sql = "update event_data set name=?,description=?,isVerified=?,validTill=? where event_id=?";
		try{
			createConnection();
			stmt=conn.prepareStatement(sql);
			for(HashMap event:events){
				stmt.setString(1, (String)event.get("name"));
				stmt.setString(2, (String)event.get("description"));
				stmt.setInt(3, 1);
				stmt.setString(4, (String)event.get("validTill"));
				stmt.setString(5, (String)event.get("id"));
				stmt.addBatch();
			}
			stmt.executeBatch();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			closeConnection();
		}
	}
	private void createConnection() {
		try {
			Class.forName(JDBC_DRIVER).newInstance();
			conn = DriverManager.getConnection(DB_URL, "root", "root");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String arg[]){
		DataExporter my=new DataExporter();
		my.getData();
	}
}
