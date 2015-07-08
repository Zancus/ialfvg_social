package it.ialweb.poi;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.storage.StorageService;
import com.shephertz.app42.paas.sdk.android.user.UserService;

public class BarkerServices {
	public UserService userService;
	public StorageService storageService;
	
	private static BarkerServices mInstance = null;
	
	private BarkerServices() {
		this.userService = App42API.buildUserService();
		this.storageService = App42API.buildStorageService();
	}

	public static BarkerServices instance() {
		if (mInstance == null) {
			mInstance = new BarkerServices();
		}
		return mInstance;
	}
}
