package it.ialweb.poi;

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
