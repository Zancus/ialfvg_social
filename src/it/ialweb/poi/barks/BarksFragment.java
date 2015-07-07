package it.ialweb.poi.barks;

import it.ialweb.poi.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BarksFragment extends Fragment {

	public static BarksFragment newInstance()
	{
		return new BarksFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View barksview = inflater.inflate(R.layout.fragment_barks, null);
		return barksview;
	}
	
	
}
