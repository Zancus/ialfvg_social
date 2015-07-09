package it.barker.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Followers {
	public static final String collectionName = "followers";
	
	public static final String TAG_USER1 = "user1";
	public static final String TAG_USER2 = "user2";
	
	
	public String user1;
	public String user2;
	
	public Followers(String user1, String user2) {
		this.user1 = user1;
		this.user2 = user2;
	}
	
	public String getJSON() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(TAG_USER1, user1);
			jsonObject.put(TAG_USER2, user2);
		} catch (JSONException e) {	e.printStackTrace(); }
		
		return jsonObject.toString();
	}
}
