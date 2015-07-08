package it.ialweb.poi;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends Activity {

	Button bSign;
	EditText eUser, eMail, ePass1, ePass2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		eUser = (EditText)findViewById(R.id.eUser);
		eMail = (EditText)findViewById(R.id.eMail);
		ePass1 = (EditText)findViewById(R.id.ePass1);
		ePass2 = (EditText)findViewById(R.id.ePass2);
		bSign = (Button)findViewById(R.id.bSign);
		bSign.setOnClickListener(new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {
			String error = "";

			
			if(eUser.getText().toString().equals(""))  error = error+" username";
			if(eMail.getText().toString().equals(""))  error = error+" e-mail";
			if(!isValidEmail(eMail.getText().toString()))  error = error+" invalid e-mail";
			if(ePass1.getText().toString().equals("")) error = error+" password";
			if(ePass2.getText().toString().equals("")) error = error+" confirm password";
			if(!ePass2.getText().toString().equals(ePass1.getText().toString())) error += " password matching";
			
			if(error.equals(""))
				DoRegistration(eUser.getText().toString(), eMail.getText().toString(), ePass1.getText().toString());
			else
				Toast.makeText(getApplicationContext(), "Recheck:"+ error, Toast.LENGTH_SHORT).show();
 			}
		});
	}//create

	public final static boolean isValidEmail(CharSequence target) {
		  if (TextUtils.isEmpty(target)) 
		    return false;
		  else 
		    return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}
	private void DoRegistration(String u, String m, String p)
	{                       
		BarkerServices.instance().userService.createUser(u, p, m, new App42CallBack() {  
		public void onSuccess(Object response)   
		{  
			Intent vIntent = new Intent(Registration.this, Login.class);
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
	
}
