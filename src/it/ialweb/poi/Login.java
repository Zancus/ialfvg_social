package it.ialweb.poi;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.user.User;
import com.shephertz.app42.paas.sdk.android.user.UserService;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	Button bLogin, bRegistration;
	EditText eUser, ePass;
	UserService userService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		bLogin = (Button)findViewById(R.id.bLogin);
		bRegistration = (Button)findViewById(R.id.bRegistration);
		eUser = (EditText)findViewById(R.id.eUsername);
		ePass = (EditText)findViewById(R.id.ePassword);
		
		bLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!eUser.getText().toString().equals("") && !ePass.getText().toString().equals(""))
					DoLogin(eUser.getText().toString(), ePass.getText().toString());
			}
		});
	}//create
	
	private void DoLogin(String u, String p){
		
		
		userService.authenticate(u, p, new App42CallBack() {
		public void onSuccess(Object response)
		{
			User user = (User)response;
			System.out.println("userName is " + user.getUserName());  
			System.out.println("sessionId is " + user.getSessionId());  
		}
		public void onException(Exception ex) 
		{
			System.out.println("Exception Message : "+ex.getMessage());
		}
		});

	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
