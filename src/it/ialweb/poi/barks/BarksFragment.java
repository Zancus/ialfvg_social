package it.ialweb.poi.barks;

import java.util.ArrayList;

import it.ialweb.models.Bark;
import it.ialweb.poi.R;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BarksFragment extends Fragment {

	private RecyclerView rvbarks;
	private ArrayList<Bark> barks = new ArrayList<Bark>();
	private BarkAdapter barksadapter;
	
	public static BarksFragment newInstance()
	{
		return new BarksFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View barksview = inflater.inflate(R.layout.fragment_barks, null);
		rvbarks = (RecyclerView) barksview.findViewById(R.id.rvbarks);
		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		rvbarks.setLayoutManager(llm);
		getBarks();
		barksadapter = new BarkAdapter(getActivity(), barks);
		rvbarks.setAdapter(barksadapter);
		return barksview;
	}
	
	private void getBarks()
	{
		
	}
	
}
