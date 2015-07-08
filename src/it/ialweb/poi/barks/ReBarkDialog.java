package it.ialweb.poi.barks;

import com.shephertz.app42.paas.sdk.android.App42CallBack;

import it.ialweb.models.Bark;
import it.ialweb.poi.BarkerServices;
import it.ialweb.poi.R;
import it.ialweb.poi.Tools;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ReBarkDialog extends DialogFragment {

	private Button rebark;
	
	public static ReBarkDialog newInstance(Bark bark)
	{
		ReBarkDialog dialog = new ReBarkDialog();
		Bundle bundle = new Bundle();
		bundle.putParcelable("bark", (Parcelable) bark);
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		View dialog = inflater.inflate(R.layout.rebark, null);
		rebark = (Button) dialog.findViewById(R.id.btnRebark);
		Bundle getBundle = getArguments();
		if(getBundle != null)
		{
			final Bark bark =getBundle.getParcelable("bark");
			
			rebark.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					BarkerServices.instance().storageService.insertJSONDocument(
							Tools.dbName, Bark.collectionName, bark.getJSON(), new App42CallBack() {
								
								@Override
								public void onSuccess(Object arg0) {
									// TODO Auto-generated method stub
									
								}
								
								@Override
								public void onException(Exception arg0) {
									// TODO Auto-generated method stub
									
								}
							});
				}
			});
		}
		
		AlertDialog.Builder rebarkbuilder = new AlertDialog.Builder(getActivity());
		rebarkbuilder.setView(dialog);
		Dialog rebark = rebarkbuilder.create();
		return rebark;
	}
	
}
