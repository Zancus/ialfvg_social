package it.barker.barks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Query;
import com.shephertz.app42.paas.sdk.android.storage.QueryBuilder;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.QueryBuilder.Operator;

import it.barker.barker.BarkerServices;
import it.barker.barker.Tools;
import it.barker.models.Bark;
import it.barker.users.DettaglioUtenteActivity;
import it.barker.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


import android.widget.Toast;

public class BarksFragment extends Fragment implements IBarksCallback {

	public static final String TAG = "barksfragment";
	public static final String OPERAZIONE = "operazione";
	public static final String UTENTE = "utente";
	private String azione, user;
	private Bundle getbarksbundle;
	
	private RecyclerView rvbarks;
	private BarkAdapter barksAdapter;
	
	public static BarksFragment newInstance(String operazione)
	{
		BarksFragment barksfrag = new BarksFragment();
		Bundle barksbundle = new Bundle();
		barksbundle.putString(OPERAZIONE, operazione);
		barksfrag.setArguments(barksbundle);
		return barksfrag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View barksview = inflater.inflate(R.layout.fragment_barks, null);
		
		barksview.findViewById(R.id.fabBtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NewBarkDialog newbark = NewBarkDialog.newInstance();
				newbark.setTargetFragment(BarksFragment.this, 1);
				newbark.show(getFragmentManager(), "new bark");

			}
		});
		
		rvbarks = (RecyclerView) barksview.findViewById(R.id.rvbarks);
		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		rvbarks.setLayoutManager(llm);
		getbarksbundle = getArguments();
		if(getbarksbundle != null)
		{
			azione = getbarksbundle.getString(OPERAZIONE);
		}
		if(azione.equals(Tools.TUTTIBARKS))
			getBarks();
		else if(azione.equals(Tools.UTENTEBARKS))
			getBarksFromUser();
		rvbarks.setAdapter(null);
		return barksview;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void getBarks()
	{
		BarkerServices.instance().storageService.findAllDocuments(
				Tools.dbName, Bark.collectionName, new App42CallBack() {  

			public void onSuccess(Object response)
			{
			    Storage  storage  = (Storage)response;
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
			    barksAdapter = new BarkAdapter(getActivity(), barks, BarksFragment.this);
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
	
	private void getBarksFromUser() {
		// TODO Auto-generated method stub
		Query q1 = QueryBuilder.build("userId", App42API.userSessionId, Operator.EQUALS); // Build query q1 for key1 equal to name and value1 equal to Nick  
		//Query query = QueryBuilder.compoundOperator(q1);
		
		BarkerServices.instance().storageService.findDocumentsByQuery
		(Tools.dbName, Bark.collectionName, q1, new App42CallBack() {
			
			@Override
			public void onSuccess(Object arg0) {
				// TODO Auto-generated method stub
				    Storage  storage  = (Storage)arg0;
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
				    barksAdapter = new BarkAdapter(getActivity(), barks, BarksFragment.this);
				    getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							rvbarks.setAdapter(barksAdapter);						
						}
					});
			}
			
			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				System.out.println("Exception Message"+arg0.getMessage());
			}
		});
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		getBarks();
	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub
		
	}
}
