package it.barker.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Bark implements Parcelable{

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
			jsonObject.put(TAG_DATE, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date));
		} catch (JSONException e) {	e.printStackTrace(); }
		
		return jsonObject.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(userId);
		dest.writeString(message);
		dest.writeString("" + date);
	}

}