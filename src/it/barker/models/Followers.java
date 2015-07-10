package it.barker.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Followers {
	public static final String collectionName = "followers";
	
	public static final String TAG_USER = "user";
	public static final String TAG_USER_TO_FOLLOW = "userToFollow";
	
	
	public String user;
	public String userToFollow;
	
	public Followers(String user, String userToFollow) {
		this.user = user;
		this.userToFollow = userToFollow;
	}
	
	public String getJSON() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(TAG_USER, user);
			jsonObject.put(TAG_USER_TO_FOLLOW, userToFollow);
		} catch (JSONException e) {	e.printStackTrace(); }
		
		return jsonObject.toString();
	}
}
