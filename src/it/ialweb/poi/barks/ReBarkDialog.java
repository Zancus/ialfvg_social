package it.ialweb.poi.barks;

import it.ialweb.models.Bark;
import it.ialweb.poi.R;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

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
			
		}
		
		AlertDialog.Builder rebarkbuilder = new AlertDialog.Builder(getActivity());
		rebarkbuilder.setView(dialog);
		Dialog rebark = rebarkbuilder.create();
		return rebark;
	}
	
}
