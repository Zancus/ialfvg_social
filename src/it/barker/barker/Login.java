package it.barker.barker;

import it.barker.R;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.user.User;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	protected static final String USER = null;
	protected static final String PASSWORD = null;
	Button bLogin, bRegistration;
	EditText eUser, ePass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		SharedPreferences pref = getSharedPreferences("it.barker.barker", Context.MODE_PRIVATE);
		String u = pref.getString("USER", null);
		String p = pref.getString("PASSWORD", null);

		if(!u.equals(null) && !p.equals(null))
			DoLogin(u, p);
		
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
				startActivityForResult(vIntent, 0);
			}
		});
	}//create
	
	private void DoLogin(final String u, final String p){
		
		final ProgressDialog spinner = new ProgressDialog(this);
		spinner.setTitle("Loading..");
		spinner.show();
		BarkerServices.instance().userService.authenticate(u, p, new App42CallBack() {
			
		public void onSuccess(Object response)
		{	
			spinner.dismiss();
			
			SharedPreferences pref = getSharedPreferences("it.barker.barker", Context.MODE_PRIVATE);
			pref.edit().putString(USER, u);
			pref.edit().putString(PASSWORD, p);
			App42API.setLoggedInUser(u);
			
			Intent vIntent = new Intent(Login.this, MainActivity.class);
			startActivity(vIntent);
		}
		public void onException(final Exception ex) 
		{
		 spinner.dismiss();
		 runOnUiThread(new Runnable() {
			public void run() {
				
				String error = "";
				App42Exception exception = (App42Exception)ex;
				int appErrorCode  = exception.getAppErrorCode();  
				
				switch (appErrorCode) 
				{
					case 2002: error = error+" Username/Password did not match"; break;
					default: error = error + ex.getMessage().toString();
				}
				
				Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
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
