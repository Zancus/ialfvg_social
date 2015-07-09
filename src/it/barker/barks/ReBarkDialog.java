package it.barker.barks;

import java.util.Date;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Service;
import com.shephertz.app42.paas.sdk.android.event.App42Preferences;
import com.shephertz.app42.paas.sdk.android.user.User;

import it.barker.barker.BarkerServices;
import it.barker.barker.Tools;
import it.barker.models.Bark;
import it.barker.R;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ReBarkDialog extends DialogFragment {

	private Button rebark;
	private Dialog dialogrebark;
	private ProgressDialog progressdialog;
	
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
		final View dialog = inflater.inflate(R.layout.rebark, null);
		rebark = (Button) dialog.findViewById(R.id.btnRebark);
		Bundle getBundle = getArguments();
		if(getBundle != null)
		{
			final Bark bark =getBundle.getParcelable("bark");
			Bark newbark = new Bark(App42API.getLoggedInUser(), bark.message, bark.date);
			rebark.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					progressdialog = new ProgressDialog(getActivity());
					progressdialog.setMessage("Attendi...");
					progressdialog.show();
					progressdialog.setCancelable(false);
					progressdialog.setCanceledOnTouchOutside(false);
					BarkerServices.instance().storageService.insertJSONDocument(
							Tools.dbName, Bark.collectionName, bark.getJSON(), new App42CallBack() {
								
								@Override
								public void onSuccess(Object arg0) {
									// TODO Auto-generated method stub
									IBarksCallback barksfragm = (IBarksCallback)getTargetFragment();
									progressdialog.dismiss();
									dialogrebark.dismiss();
									barksfragm.onSuccess();
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
		dialogrebark = rebarkbuilder.create();
		return dialogrebark;
	}
	
}
