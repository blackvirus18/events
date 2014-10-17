package com.events.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.events.model.Category;
import com.events.model.Club;
import com.events.model.Event;
import com.events.model.Location;
import com.mysql.jdbc.Statement;

public class MySqlExporter {
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
		closeConnection();
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
		MySqlExporter my=new MySqlExporter();
		my.insertData();
	}
}
