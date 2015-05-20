package com.cloudcomputing.rest.jersey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cloudcomputing.rest.jersey.User;

public class DBManager {

	Connection conn=null;
	
	public void connect(){
		try {
	    	try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IllegalAccessException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			conn=DriverManager.getConnection("jdbc:mysql://aa2imaz7ur0qon.cynqzrqdfvg7.us-east-1.rds.amazonaws.com:3306/waymore?" +
			        "user=waymore&password=waymorecloud");
			//conn=DriverManager.getConnection("jdbc:mysql://localhost/tweetmap?" +
			//        "user=root&password=");
			System.out.println("Connect!");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	public void addUsers(User user){
	    PreparedStatement ps=null;
		
	    
		PreparedStatement checkps = null;
		ResultSet checkrs = null;
		String checksql = "select user_id from Users";
		String sql = null;
		boolean flag = false;
		
		try{
			String checkid = user.getUserId();
			checkps = conn.prepareStatement(checksql);
			checkrs = checkps.executeQuery();
			while(checkrs.next()){
				String id = checkrs.getString(1);
				if (checkid.equals(id)){
					flag = true;
					break;
				}
			}
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(!flag){
			sql="insert into Users values (?,?)";
			try{
				ps=conn.prepareStatement(sql);
				ps.setString(1, user.getUserId());
				ps.setString(2, user.getUserName());
				ps.executeUpdate();
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			sql = "update Users set user_name = ? where user_id = ?";
			try{
				ps= conn.prepareStatement(sql);
				ps.setString(1, user.getUserName());
				ps.setString(2, user.getUserId());
				ps.executeUpdate();
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	public User getUser (String UserID){
		String userID =null;
		String userName = null;
		ArrayList<String> likeRouteIds = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from Users where user_id = ?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, UserID);
			rs = ps.executeQuery();
			
			while(rs.next()){
				userID = rs.getString(1);
				userName = rs.getString(2);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql = "select route_id from Likes where user_id = ?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, UserID);
			rs = ps.executeQuery();
			
			while(rs.next()){
				likeRouteIds.add(rs.getString(1));
				System.out.println(rs.getString(1));
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User myuser = new User(userID, userName, likeRouteIds);
		return myuser;
	}
			
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public boolean addRoute(Route addedRoute) {
		// TODO Auto-generated method stub
	    PreparedStatement ps=null;
	    
		ResultSet checkrs = null;
		PreparedStatement checkps = null;
		String checksql = "select route_id from Routes";
		//String sql = null;
		boolean flag = false;
		
		try{
			String checkid = addedRoute.getRouteId();
			checkps = conn.prepareStatement(checksql);
			checkrs = checkps.executeQuery();
			while(checkrs.next()){
				String id = checkrs.getString(1);
				if (checkid.equals(id)){
					flag = true;
					break;
				}
			}
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	    
		//boolean flag = addedRoute.getSharedFlag().equals("1")?true:false;

		long createdTimeLong = (long)addedRoute.getCreatedTime()*1000;
		long lastModifiedTimeLong = (long)addedRoute.getLastModifiedTime()*1000;
		java.sql.Timestamp createdTime=new java.sql.Timestamp(createdTimeLong);
		java.sql.Timestamp lastModifiedTime=new java.sql.Timestamp(lastModifiedTimeLong);


		if(!flag){
			String sql="insert into Routes values (?,?,?,?,?,?,?,?)";
			try{
				ps=conn.prepareStatement(sql);
				ps.setString(1, addedRoute.getRouteId());
				ps.setString(2, addedRoute.getUserId());
				ps.setString(3, addedRoute.getCity());
				ps.setString(4, addedRoute.getTitle());
				ps.setString(5, addedRoute.getKeywords());
				ps.setTimestamp(6, createdTime);
				ps.setTimestamp(7, lastModifiedTime);
				ps.setBoolean(8, addedRoute.getSharedFlag());
				
				ps.executeUpdate();
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			String sql="update Routes set user_id=?, city=?, title=?, keywords=?, created_time=?, last_modified_time=?, share_flag=? "
					+ "where route_id = ?";
			try{
				ps=conn.prepareStatement(sql);
				ps.setString(8, addedRoute.getRouteId());
				ps.setString(1, addedRoute.getUserId());
				ps.setString(2, addedRoute.getCity());
				ps.setString(3, addedRoute.getTitle());
				ps.setString(4, addedRoute.getKeywords());
				ps.setTimestamp(5, createdTime);
				ps.setTimestamp(6, lastModifiedTime);
				ps.setBoolean(7,addedRoute.getSharedFlag());
				
				ps.executeUpdate();
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return flag;
	}


	public ReturnedRoute getRouteWithRouteId(String id) {
		// TODO Auto-generated method stub
		ReturnedRoute res = new ReturnedRoute();
		res.setKeyPoints(new ArrayList<KeyPoint>());
		res.setMapPoints(new ArrayList<MapPoint>());
		res.setComments(new ArrayList<Comment>());
		res.setLikeUserIds(new ArrayList<String>());
		
		PreparedStatement ps = null;
		PreparedStatement kpps = null;
		PreparedStatement mpps = null;
		PreparedStatement cmtps = null;
		PreparedStatement unps = null;
		PreparedStatement lps = null;

		ResultSet rs = null;
		ResultSet kprs = null;
		ResultSet mprs = null;
		ResultSet cmtrs = null;
		ResultSet unrs = null;
		ResultSet lrs = null;

		String sql = "select * from Routes where route_id = ?";
		String getkpsql = "select * from KeyPoints where route_id = ?";
		String getmpsql = "select * from MapPoints where route_id = ? order by created_time";
		String getcommentsql = "select * from Comments where route_id = ? order by created_time desc";
		String getusernamesql = "select user_name from Users where user_id = ?";
		String getlikesql = "select user_id from Likes where route_id = ?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()){
				res.setRouteId(rs.getString(1));
				res.setUserId(rs.getString(2));
				res.setCity(rs.getString(3));
				res.setTitle(rs.getString(4));
				res.setKeywords(rs.getString(5));
				res.setSharedFlag(rs.getBoolean(8));
				System.out.println(rs.getBoolean(8));
				double createdTime = rs.getTimestamp(6).getTime()/(double)1000;
				double modifyTime = rs.getTimestamp(7).getTime()/(double)1000;
				res.setCreatedTime(createdTime);
				res.setLastModifiedTime(modifyTime);
			}
			
			
			
			/*get key points*/
			kpps = conn.prepareStatement(getkpsql);
			kpps.setString(1, id);
			kprs = kpps.executeQuery();
			
			while(kprs.next()){
				KeyPoint kp = new KeyPoint();
				kp.setKeyPointId(kprs.getString(1));
				kp.setRouteId(kprs.getString(2));
				kp.setLongitude(kprs.getDouble(3));
				kp.setLatitude(kprs.getDouble(4));
				kp.setTitle(kprs.getString(5));
				kp.setNotation(kprs.getString(6));
				kp.setPhotoUrl(kprs.getString(7));
				res.getKeyPoints().add(kp);
			}
			
			/*get map points*/
			mpps = conn.prepareStatement(getmpsql);
			mpps.setString(1, id);
			mprs = mpps.executeQuery();
			
			while(mprs.next()){
				MapPoint mp = new MapPoint();
				mp.setMapPointId(mprs.getString(1));
				mp.setRouteId(mprs.getString(2));
				mp.setLongitude(mprs.getDouble(3));
				mp.setLatitude(mprs.getDouble(4));
				double createdTime = mprs.getTimestamp(5).getTime()/(double)1000;
				mp.setCreatedTime(createdTime);
				res.getMapPoints().add(mp);
			}
			
			/*get comments*/
			cmtps = conn.prepareStatement(getcommentsql);
			cmtps.setString(1, id);
			cmtrs = cmtps.executeQuery();
			
			while(cmtrs.next()){
				Comment cmt = new Comment();
				cmt.setCommentId(cmtrs.getString(1));
				cmt.setRouteId(cmtrs.getString(2));
				cmt.setUserId(cmtrs.getString(3));
				cmt.setContent(cmtrs.getString(4));
				double createdTime = cmtrs.getTimestamp(5).getTime()/(double)1000;
				//System.out.println("Created Time:::::::::::");
				cmt.setCreatedTime(createdTime);
				
				String uid = cmtrs.getString(3);
				unps = conn.prepareStatement(getusernamesql);
				unps.setString(1,uid);
				unrs = unps.executeQuery();
				while(unrs.next()){
					cmt.setUserName(unrs.getString(1));
				}
				res.getComments().add(cmt);
			}
			
			/* get like users */
			lps = conn.prepareStatement(getlikesql);
			lps.setString(1, id);
			lrs = lps.executeQuery();
			while(lrs.next()){
				String likeUserId = lrs.getString(1);
				res.getLikeUserIds().add(likeUserId);
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}


	public ArrayList<Route> getRouteWithUserId(String id) {
		// TODO Auto-generated method stub
		ArrayList<Route> routes = new ArrayList<Route>();

		PreparedStatement ps = null;

		ResultSet rs = null;

		String sql = "select * from Routes where user_id = ?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			
			while(rs.next()){
				Route route = new Route();
				route.setRouteId(rs.getString(1));
				route.setUserId(rs.getString(2));
				route.setCity(rs.getString(3));
				route.setTitle(rs.getString(4));
				route.setKeywords(rs.getString(5));
				route.setSharedFlag(rs.getBoolean(6));
				route.setCreatedTime(rs.getTimestamp(7).getTime());
				route.setLastModifiedTime(rs.getTimestamp(8).getTime());
				routes.add(route);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return routes;
	}


	public void addKeyPoint(KeyPoint kp) {
		// TODO Auto-generated method stub
	    PreparedStatement ps=null;
	    		
		String sql="insert into KeyPoints values (?,?,?,?,?,?,?)";
		try{
			ps=conn.prepareStatement(sql);
			ps.setString(1, kp.getKeyPointId());
			ps.setString(2, kp.getRouteId());
			ps.setDouble(3, kp.getLongitude());
			ps.setDouble(4, kp.getLatitude());
			ps.setString(5, kp.getTitle());
			ps.setString(6, kp.getNotation());
			ps.setString(7, kp.getPhotoUrl());
			ps.executeUpdate();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}


	public void addMapPoint(MapPoint mp) {
		// TODO Auto-generated method stub
		
	    PreparedStatement ps=null;
	    

	    double time = mp.getCreatedTime();
	    long timeLong = (long)time*1000;
		java.sql.Timestamp createdTime=new java.sql.Timestamp(timeLong);

		
		String sql="insert into MapPoints values (?,?,?,?,?)";
		try{
			ps=conn.prepareStatement(sql);
			ps.setString(1, mp.getMapPointId());
			ps.setString(2, mp.getRouteId());
			ps.setDouble(3, mp.getLongitude());
			ps.setDouble(4, mp.getLatitude());
			ps.setTimestamp(5, createdTime);

			ps.executeUpdate();
		
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void deleteKeyPoints(String rid) {
		// TODO Auto-generated method stub
		PreparedStatement ps=null;
		String sql = "delete from KeyPoints where route_id = ?";
		
		try{
			ps=conn.prepareStatement(sql);
			ps.setString(1, rid);
			ps.executeUpdate();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}


	public void addComment(Comment cmt) {
		// TODO Auto-generated method stub
		PreparedStatement ps=null;
		double time = cmt.getCreatedTime();
		long ctime = (long)time * 1000;
		java.sql.Timestamp createdTime=new java.sql.Timestamp(ctime);
		
		String sql = "insert into Comments values (?,?,?,?,?)";
		try{
			ps=conn.prepareStatement(sql);
			ps.setString(1, cmt.getCommentId());
			ps.setString(2, cmt.getRouteId());
			ps.setString(3, cmt.getUserId());
			ps.setString(4, cmt.getContent());
			ps.setTimestamp(5, createdTime);
			ps.executeUpdate();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	public void setLike(Like lk) {
		// TODO Auto-generated method stub
		PreparedStatement ps=null;
		boolean flag = lk.isLikeFlag();
		if(flag){
			String sql = "insert into Likes values (?,?)";
			try{
				ps=conn.prepareStatement(sql);
				ps.setString(1, lk.getUserId());
				ps.setString(2, lk.getRouteId());

				ps.executeUpdate();
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			String sql = "delete from Likes where user_id = ? and route_id = ?";
			try{
				ps=conn.prepareStatement(sql);
				ps.setString(1, lk.getUserId());
				ps.setString(2, lk.getRouteId());

				ps.executeUpdate();
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		
	}


	public void setShare(String routeId, boolean shareFlag) {
		// TODO Auto-generated method stub
		PreparedStatement ps=null;
		String sql = "update Routes set share_flag = ? where route_id = ?";
		try{
			ps = conn.prepareStatement(sql);
			ps.setBoolean(1, shareFlag);
			ps.setString(2,routeId);
			ps.executeUpdate();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	public Snippets getSnippet(String userId, String sortMethod,
			String keywords, String city, boolean shareFlag) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		PreparedStatement nameps = null;
		
		Snippets result = new Snippets();
		ArrayList<Snippet> res = new ArrayList<Snippet>();
		StringBuilder sb = new StringBuilder();
		sb.append("select R.route_id, R.user_id, city, title, keywords, created_time, count(L.user_id) as cnt "
				+ "from Routes R left outer join Likes L on R.route_id=L.route_id where 1=1");
		if(userId.length()>0){
			sb.append(" and R.user_id = '" + userId+"'");
		}
		if(city.length()>0){
			sb.append(" and city = '"+ city +"'");
		}
		if(keywords.length()>0){
			sb.append(" and keywords LIKE '%" + keywords + "%'");
		}
		if(shareFlag){
			sb.append(" and share_flag = 1");
		}
		if(sortMethod.equals("by date")){
			sb.append(" group by R.route_id order by created_time desc");
		}
		if(sortMethod.equals("by likes")){
			sb.append(" group by R.route_id order by cnt desc");
		}
		String sql = sb.toString();
		System.out.println(sql);

		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				System.out.println("Not empty!");
				Snippet snippet = new Snippet();
				snippet.setRouteId(rs.getString(1));
				snippet.setCity(rs.getString(3));
				snippet.setTitle(rs.getString(4));
				snippet.setKeywords(rs.getString(5));
				double createTime = (double)(rs.getTimestamp(6).getTime())/(double)1000;
				snippet.setCreatedTime(createTime);
				snippet.setLikeNum(rs.getInt(7));
				
				String uid = rs.getString(2);
				String unamesql = "select user_name from Users where user_id = ?";
				nameps = conn.prepareStatement(unamesql);
				nameps.setString(1, uid);
				ResultSet namers = nameps.executeQuery();
				while(namers.next()){
					System.out.println("Name: "+ namers.getString(1));
					snippet.setUsername(namers.getString(1));
				}								
				res.add(snippet);
			}
			
			result.setSnippets(res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}


	public void deleteRoute(String id) {
		// TODO Auto-generated method stub

		String deletempsql = "delete from MapPoints where route_id = ?";
		String deletekpsql = "delete from KeyPoints where route_id = ?";
		String deletecmtsql = "delete from Comments where route_id = ?";
		String deletelksql = "delete from Likes where route_id = ?";
		String deletertsql = "delete from Routes where route_id = ?";
		
		try {
			PreparedStatement delmpps = conn.prepareStatement(deletempsql);
			delmpps.setString(1, id);
			delmpps.executeUpdate();
			
			PreparedStatement delkpps = conn.prepareStatement(deletekpsql);
			delkpps.setString(1, id);
			delkpps.executeUpdate();
			
			PreparedStatement delcmtps = conn.prepareStatement(deletecmtsql);
			delcmtps.setString(1, id);
			delcmtps.executeUpdate();
			
			PreparedStatement dellkps = conn.prepareStatement(deletelksql);
			dellkps.setString(1, id);
			dellkps.executeUpdate();
			
			PreparedStatement delrtps = conn.prepareStatement(deletertsql);
			delrtps.setString(1, id);
			delrtps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}