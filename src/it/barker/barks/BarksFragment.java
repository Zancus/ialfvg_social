package it.barker.barks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;

import it.barker.barker.BarkerServices;
import it.barker.barker.Tools;
import it.barker.models.Bark;
import it.barker.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import android.widget.Toast;

public class BarksFragment extends Fragment {

	public static final String TAG = "barksfragment";

	private RecyclerView rvbarks;
	private BarkAdapter barksAdapter;

	
	
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
							    getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										getBarks();
									}
								});
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
		rvbarks.setAdapter(null);

		return barksview;
	}

	private void getBarks()
	{
		BarkerServices.instance().storageService.findAllDocuments(
				Tools.dbName, Bark.collectionName, new App42CallBack() {  
			public void onSuccess(Object response)
			{
			    Storage  storage  = (Storage )response;
			    ArrayList<Storage.JSONDocument> jsonDocList = storage.getJsonDocList();
			    ArrayList<Bark> barks = new ArrayList<Bark>();
			    Bark bark;
			    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			    
			    for(int i=0;i<jsonDocList.size();i++)
			    {
			        try {
						JSONObject jsonObject = new JSONObject(jsonDocList.get(i).getJsonDoc());
						bark = new Bark(jsonObject.getString(Bark.TAG_USERID),
								jsonObject.getString(Bark.TAG_MESSAGE),
								df.parse(jsonObject.getString(Bark.TAG_DATE)));
						barks.add(bark);
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
			    }
			    barksAdapter = new BarkAdapter(getActivity(), barks);
			    getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						rvbarks.setAdapter(barksAdapter);						
					}
				});
			}  
			public void onException(Exception ex) {  
			    System.out.println("Exception Message"+ex.getMessage());          
			}  
			});
	}
}
