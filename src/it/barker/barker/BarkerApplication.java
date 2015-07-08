package it.barker.barker;

import com.shephertz.app42.paas.sdk.android.App42API;

import android.app.Application;

public class BarkerApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		App42API.initialize(this, Tools.API_KEY, Tools.SECRET_KEY);
	}

}