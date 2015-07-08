package it.barker.barker;

import it.barker.R;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.user.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	Button bLogin, bRegistration;
	EditText eUser, ePass;
	
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
				String error = "";
				if(eUser.getText().toString().equals(""))  error = error+" username";
				if(ePass.getText().toString().equals(""))  error = error+" password";
				if(error.equals(""))
					DoLogin(eUser.getText().toString(), ePass.getText().toString());
				else
					Toast.makeText(getApplicationContext(), "Recheck:" + error, Toast.LENGTH_SHORT).show();
		 
			}
		});
		
		bRegistration.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent vIntent = new Intent(Login.this, Registration.class);
				startActivity(vIntent);
			}
		});
	}//create
	
	private void DoLogin(String u, String p){
		
		final ProgressDialog spinner = new ProgressDialog(this);
		spinner.setTitle("Loading..");
		spinner.show();
		BarkerServices.instance().userService.authenticate(u, p, new App42CallBack() {
			
		public void onSuccess(Object response)
		{	
			spinner.dismiss();
			User user = (User)response;
			System.out.println("userName is " + user.getUserName());  
			System.out.println("sessionId is " + user.getSessionId());  
			
			Intent vIntent = new Intent(Login.this, MainActivity.class);
			startActivity(vIntent);
		}
		public void onException(final Exception ex) 
		{
			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(), "" + ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
				}
			});
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
