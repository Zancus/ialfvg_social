package it.barker.barks;

import it.barker.R;
import it.barker.barker.BarkerServices;
import it.barker.barker.Tools;
import it.barker.models.Bark;

import java.util.Date;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewBarkDialog extends DialogFragment {

	private View viewnewbark;
	private EditText edtxtbark;
	private String nuovobark;
	private Button btnInserisciBark;
	private ProgressDialog progressdialog;
	private Dialog dialognewbark;
	public static NewBarkDialog newInstance()
	{
		return new NewBarkDialog();
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder newbark = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		viewnewbark = inflater.inflate(R.layout.newbark, null);
		newbark.setView(viewnewbark);
		edtxtbark = (EditText) viewnewbark.findViewById(R.id.edtxtnewbark);
		btnInserisciBark = (Button) viewnewbark.findViewById(R.id.btnInserisciBark);
		btnInserisciBark.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				insertNewBark();
			}
		});
		insertNewBark();
		dialognewbark = newbark.create();
		return dialognewbark;
	}
	
	@Override
	public void onSaveInstanceState(Bundle arg0) {
		// TODO Auto-generated method stub
		arg0.putBoolean("prog", progressdialog.isShowing());
	}

	private void insertNewBark()
	{
		nuovobark = edtxtbark.getText().toString();
		if(!(nuovobark.isEmpty()))
		{
			final Bark newbark = new Bark(App42API.getLoggedInUser(), nuovobark, new Date());
					progressdialog = new ProgressDialog(getActivity());
					progressdialog.setMessage("Attendi...");
					progressdialog.show();
					progressdialog.setCancelable(false);
					progressdialog.setCanceledOnTouchOutside(false);
					BarkerServices.instance().storageService.insertJSONDocument(
							Tools.dbName, Bark.collectionName, newbark.getJSON(), new App42CallBack(){

								@Override
								public void onException(Exception arg0) {
									//Snackbar.make(viewnewbark, "Errore!", Snackbar.LENGTH_SHORT).show();
								}

								@Override
								public void onSuccess(Object arg0) {
									IBarksCallback barksfrag = (IBarksCallback)getTargetFragment();
									progressdialog.dismiss();
									dialognewbark.dismiss();
									barksfrag.onSuccess();
								}
					});
		}
	}
}
