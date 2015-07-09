package it.barker.barker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.util.ArrayList;

import it.barker.R;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.upload.Upload;
import com.shephertz.app42.paas.sdk.android.upload.UploadFileType;
import com.shephertz.app42.paas.sdk.android.upload.UploadService;
import com.shephertz.app42.paas.sdk.android.util.Base64.InputStream;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Registration extends Activity {

	ImageView imvProfile;
	EditText eUser, eMail, ePass1, ePass2;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private static int RESULT_LOAD_IMAGE = 2;
	Bitmap imgProfile = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		Button bSign, btnUploadImage;
		
		
		eUser = (EditText)findViewById(R.id.eUser);
		eMail = (EditText)findViewById(R.id.eMail);
		ePass1 = (EditText)findViewById(R.id.ePass1);
		ePass2 = (EditText)findViewById(R.id.ePass2);
		
		bSign = (Button)findViewById(R.id.bSign);
		btnUploadImage = (Button) findViewById(R.id.btnUploadImage_registration);
		
		imvProfile = (ImageView) findViewById(R.id.imvProfile_registration);
		
		
		btnUploadImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				dispatchTakePictureIntent();
				
			}
			
			private void dispatchTakePictureIntent() {
			    				
				try {

			        Intent cropIntent = new Intent("com.android.camera.action.CROP");
			        // indicate image type and Uri
			        cropIntent.setDataAndType(cropIntent.getData(), "image/*");
			        //System.out.println(cropIntent.getData().toString());
			        // set crop properties
			        cropIntent.putExtra("crop", "true");
			        // indicate aspect of desired crop
			        cropIntent.putExtra("aspectX", 1);
			        cropIntent.putExtra("aspectY", 1);
			        // indicate output X and Y
			        cropIntent.putExtra("outputX", 128);
			        cropIntent.putExtra("outputY", 128);
			        // retrieve data on return
			        cropIntent.putExtra("return-data", true);
			        // start the activity - we handle returning in onActivityResult
			        startActivityForResult(cropIntent, RESULT_LOAD_IMAGE);
			    }
			    // respond to users whose devices do not support the crop action
			    catch (ActivityNotFoundException anfe) {
			        // display an error message
			        String errorMessage = "Whoops - your device doesn't support the crop action!";
			        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
			        
			    }
				
			}
		});
		
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
	private void DoRegistration(final String u, String m, String p)
	{                       
		BarkerServices.instance().userService.createUser(u, p, m, new App42CallBack() {  
		public void onSuccess(Object response)   
		{  
			if(imgProfile != null) {
				UploadFileType fileType = UploadFileType.IMAGE;  
			
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
			imgProfile.compress(CompressFormat.PNG, 0, bos); 
			byte[] bitmapdata = bos.toByteArray();
			ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
						
			UploadService uploadService = App42API.buildUploadService();   
			uploadService.uploadFile(u + ".png", bs,  fileType , "descrizione", new App42CallBack() {  
				public void onSuccess(Object response_second){
					
					Upload upload = (Upload)response_second;                         
				    ArrayList<Upload.File>  fileList = upload.getFileList();     
				    for(int i = 0; i < fileList.size();i++ )    
				    {    
				        System.out.println("fileName is :" + fileList.get(i).getName());    
				        System.out.println("fileType is :" + fileList.get(i).getType());    
				        System.out.println("fileUrl is :" + fileList.get(i).getUrl());    
				        System.out.println("Tiny Url is :"+fileList.get(i).getTinyUrl());  
				        System.out.println("fileDescription is: " + fileList.get(i).getDescription());   
				    }  
				}

				@Override
				public void onException(Exception arg0) {
					
				}
			
				}
			);
			}
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
	        imvProfile.setImageBitmap(imageBitmap);
	    }
	    if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK ) {
	        if (data != null) {
	            // get the returned data
	            Bundle extras = data.getExtras();
	            // get the cropped bitmap
	            imgProfile = extras.getParcelable("data");

	            imvProfile.setImageBitmap(imgProfile);
	        }
	    }
	}
	
}
