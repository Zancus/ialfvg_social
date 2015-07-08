package it.ialweb.models;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.os.ParcelableCompat;

public class Bark extends ParcelableCompat{

	public static final String collectionName = "barks";
	
	public static final String TAG_USERID = "userId";
	public static final String TAG_MESSAGE = "messsage";
	public static final String TAG_DATE = "date";
	
	public Bark(String userId, String message, Date date) {
		super();
		this.userId = userId;
		this.message = message;
		this.date = date;
	}

	public String userId;
	public String message;
	public Date date;
	
	public String getJSON() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(TAG_USERID, userId);
			jsonObject.put(TAG_MESSAGE, message);
			jsonObject.put(TAG_DATE, com.shephertz.app42.paas.sdk.android.util.Util.getUTCFormattedTimestamp(date));
		} catch (JSONException e) {	e.printStackTrace(); }
		
		return jsonObject.toString();
	}
}