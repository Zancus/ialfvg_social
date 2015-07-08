package it.ialweb.poi;

import com.shephertz.app42.paas.sdk.android.App42API;

import android.app.Application;

public class BarkerApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		App42API.initialize(this,"1c2798000ce8a59c2224c6f9cb466222d9cda8e9a75705394d9b5b150f982ca0",
				"88da6d584ae244ae9c07d8044fff5dabe1c4ce092be6de3b3f24db3a96e291aa");
	}

}