package com.cloudcomputing.rest.jersey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.*;

@Path("/waymore")
public class WaymoreServer {
	
/*
	@GET
	@Path("/{parameter}")
	public Response responseMsg( @PathParam("parameter") String parameter,
			@DefaultValue("Nothing to say") @QueryParam("value") String value) {

		String output = "Hello from: " + parameter + " : " + value;

		return Response.status(200).entity(output).build();
	}*/
	
	
	@GET
	@Path("/print")
	@Produces(MediaType.APPLICATION_JSON)
	public Response responseMessage (@QueryParam("userId") String userId, @QueryParam("sortMethod") String sortMethod,
			@QueryParam("keywords") String keywords, @QueryParam("city") String city, @QueryParam("shareFlag") boolean shareFlag){
		
		//System.out.println("Start");

		DBManager dbm = new DBManager();
		Snippets result = new Snippets();
		
		dbm.connect();
		result = dbm.getSnippet(userId, sortMethod, keywords, city, shareFlag);
		dbm.close();
		System.out.println("Send back snippets");
		return Response.status(200).entity(result).build();
	}
	
	
	@GET
	@Path("/print/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response produceJSON(@HeaderParam("actionType") String type, @PathParam("id") String id, @HeaderParam("Content-Length") int length) {
		DBManager dbm = new DBManager();
		
		switch (type){
		case "getUserWithUserId":
			User user = new User();
			dbm.connect();
			user = dbm.getUser(id);
			dbm.close();
			System.out.println("Send back user");
			return Response.status(200).entity(user).build();
			
		case "getRouteWithRouteId":
			ReturnedRoute route = new ReturnedRoute();
			dbm.connect();
			route = dbm.getRouteWithRouteId(id);
			dbm.close();
			System.out.println("send back route with rid");
			return Response.status(200).entity(route).build();
			
		case "deleteRouteWithRouteId":
			dbm.connect();
			dbm.deleteRoute(id);
			dbm.close();
			System.out.println("Delete Route Successfully");
			return Response.status(200).entity("true").build();

		}		
		return Response.status(404).build();
	
	}
	
	
	@POST
	@Path("/send")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(@HeaderParam("actionType") String type, InputStream content) throws IOException{
		DBManager dbm = new DBManager();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        StringBuilder out = new StringBuilder();
        String line = null;
		String res = null;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        res = out.toString();
        //System.out.println("============Route: "+res);
        JSONObject message = new JSONObject(res);
        
		switch(type){
		
		case "addUser":
			User addedUser = new User(message.getString("userId"), message.getString("userName"));
			dbm.connect();
			dbm.addUsers(addedUser);
			dbm.close();
			System.out.println("Add User Successfully!");
			break;
		
		case "uploadRoute":
			
			dbm.connect();
			
			Route rt = new Route();
			rt.setRouteId(message.getString("routeId"));
			rt.setUserId(message.getString("userId"));
			rt.setCity(message.getString("city"));
			rt.setTitle(message.getString("title"));
			rt.setKeywords(message.getString("keywords"));
			rt.setSharedFlag(message.getBoolean("sharedFlag"));
			rt.setCreatedTime(message.getDouble("createdTime"));
			rt.setLastModifiedTime(message.getDouble("lastModifiedTime"));
			boolean flag = dbm.addRoute(rt);
			
			String rid = message.getString("routeId");
			dbm.deleteKeyPoints(rid);
			
			int kplen = message.getJSONArray("keyPoints").length();
			for (int i=0; i<kplen; i++){
				JSONObject keypoint = message.getJSONArray("keyPoints").getJSONObject(i);
				KeyPoint kp = new KeyPoint();
				kp.setKeyPointId(keypoint.getString("keyPointId"));
				kp.setRouteId(message.getString("routeId"));
				kp.setTitle(keypoint.getString("title"));
				kp.setLatitude(keypoint.getDouble("latitude"));
				kp.setLongitude(keypoint.getDouble("longitude"));
				kp.setPhotoUrl(keypoint.getString("photoUrl"));
				kp.setNotation(keypoint.getString("notation"));
				dbm.addKeyPoint(kp);
			}
			if(!flag){
				int mplen = message.getJSONArray("mapPoints").length();
				for (int i=0; i<mplen; i++){
					JSONObject mappoint = message.getJSONArray("mapPoints").getJSONObject(i);
					MapPoint mp = new MapPoint();
					mp.setMapPointId(mappoint.getString("mapPointId"));
					mp.setRouteId(message.getString("routeId"));
					mp.setLongitude(mappoint.getDouble("longitude"));
					mp.setLatitude(mappoint.getDouble("latitude"));
					mp.setCreatedTime(mappoint.getDouble("createdTime"));
					dbm.addMapPoint(mp);
				}
			}

			dbm.close();
			System.out.println("Add Route Successfully");
			break;
			
		case "addComment":
			
			dbm.connect();
			Comment cmt = new Comment();
			cmt.setCommentId(message.getString("commentId"));
			cmt.setRouteId(message.getString("routeId"));
			cmt.setUserId(message.getString("userId"));
			cmt.setContent(message.getString("content"));
			cmt.setCreatedTime(message.getDouble("createdTime"));
			
			dbm.addComment(cmt);
			dbm.close();
			System.out.println("Add Comment Successfully");
			break;
			
		case "setLike":
			
			dbm.connect();
			
			Like lk = new Like();
			lk.setRouteId(message.getString("routeId"));
			lk.setUserId(message.getString("userId"));
			lk.setLikeFlag(message.getBoolean("likeFlag"));
			dbm.setLike(lk);
			dbm.close();
			System.out.println("Set Like Successfully");
			break;
			
		case "setShare":
			dbm.connect();
			String routeId = message.getString("routeId");
			boolean shareFlag = message.getBoolean("shareFlag");
			dbm.setShare(routeId, shareFlag);
			dbm.close();
			System.out.println("Set Share Successfully");
			break;
			
		}
		return Response.ok().entity("{\"Submit\": \"Success\"}").build();
	}
}