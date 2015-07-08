package it.ialweb.poi.barks;

import java.util.ArrayList;
import java.util.Date;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;

import it.ialweb.models.Bark;
import it.ialweb.poi.BarkerServices;

import it.ialweb.poi.R;
import it.ialweb.poi.Tools;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class BarksFragment extends Fragment {

	public static final String TAG = "barksfragment";

	private RecyclerView rvbarks;
	private ArrayList<Bark> barks = new ArrayList<Bark>();
	private BarkAdapter barksadapter;

	
	public static BarksFragment newInstance()
	{
		return new BarksFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View barksview = inflater.inflate(R.layout.fragment_barks, null);
		
		barksview.findViewById(R.id.fabBtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bark bark = new Bark("000", "Ciao", new Date());
				BarkerServices.instance().storageService.insertJSONDocument(
						Tools.dbName, Bark.collectionName, bark.getJSON(), new App42CallBack() {
							public void onSuccess(Object response) {
							    Storage storage = (Storage) response;
							    ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
							    for(int i = 0; i < jsonDocList.size(); i++) {
							        System.out.println("objectId is " + jsonDocList.get(i).getDocId());
							        //Above line will return object id of saved JSON object
							        System.out.println("CreatedAt is " + jsonDocList.get(i).getCreatedAt());
							        System.out.println("UpdatedAtis " + jsonDocList.get(i).getUpdatedAt());
							        System.out.println("Jsondoc is " + jsonDocList.get(i).getJsonDoc());
							    }
							    //Toast.makeText(getActivity(), "Messaggio salvato", Toast.LENGTH_SHORT).show();
							}
							public void onException(Exception ex) {
								Toast.makeText(getActivity(), "Non riesco a salvare il messaggio", Toast.LENGTH_SHORT).show();
							}
						}
					);
			}
		});
		
		
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
