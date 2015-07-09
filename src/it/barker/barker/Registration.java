package it.barker.barker;

import it.barker.R;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Exception;

import android.app.Activity;
import android.app.ProgressDialog;
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
	
	private void DoRegistration(final String u, final String m, final String p)
	{   
		final ProgressDialog spinner = new ProgressDialog(this);
		spinner.setTitle("Loading..");
		spinner.show();
		BarkerServices.instance().userService.createUser(u, p, m, new App42CallBack() {  
		public void onSuccess(Object response)   
		{  
			spinner.dismiss();
			finish();
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
					case 2001: error = error+" Username already exists"; break;
					case 2005: error = error+" Email already exists"; break;
					default: error = error + ex.getMessage().toString();
				}
				
				Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_SHORT).show();
				
			}
			});
		}  
		}); 
	}//reg
	
}
