package it.barker.barks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;

import com.shephertz.app42.paas.sdk.android.App42API;
import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.App42Exception;
import com.shephertz.app42.paas.sdk.android.storage.Query;
import com.shephertz.app42.paas.sdk.android.storage.QueryBuilder;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.QueryBuilder.Operator;

import it.barker.barker.BarkerServices;
import it.barker.barker.DateComparator;
import it.barker.barker.Tools;
import it.barker.models.Bark;
import it.barker.models.Followers;
import it.barker.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;


public class BarksFragment extends Fragment implements IBarksCallback {

	public static final String TAG = "barksfragment";
	public static final String OPERAZIONE = "operazione";
	public static final String UTENTE = "utente";
	public static final String USER_TO_FOLLOW = "USER_TO_FOLLOW";
	private String azione;
	private String userToFollow;
	private Bundle barksBundle;
	
	private RecyclerView rvbarks;
	private BarkAdapter barksAdapter;
	private ProgressDialog progressDialog;
	
	public static BarksFragment newInstance(Bundle bundle)
	{
		BarksFragment barksfrag = new BarksFragment();
		barksfrag.setArguments(bundle);
		return barksfrag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View barksview = inflater.inflate(R.layout.fragment_barks, null);
		
		final FloatingActionButton fabButton = (FloatingActionButton) barksview.findViewById(R.id.fabBtn);
		rvbarks = (RecyclerView) barksview.findViewById(R.id.rvbarks);
		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		rvbarks.setLayoutManager(llm);
		rvbarks.setAdapter(null);

		OnClickListener fabClickListener = null;
		barksBundle = getArguments();
		if(barksBundle != null)
		{
			azione = barksBundle.getString(OPERAZIONE);
			
			if(azione.equals(Tools.TUTTIBARKS)) {
				getBarks();
				fabClickListener = new OnClickListener() {
					@Override
					public void onClick(View v) {
						NewBarkDialog newbark = NewBarkDialog.newInstance();
						newbark.setTargetFragment(BarksFragment.this, 1);
						newbark.show(getFragmentManager(), "new bark");
					}
				};
			}
			else if(azione.equals(Tools.UTENTEBARKS)) {
				userToFollow = barksBundle.getString(USER_TO_FOLLOW);
				getBarksFromUser();
				fabButton.setVisibility(View.GONE);

				if(! userToFollow.equals(App42API.loggedInUser)) {
					Query q1 = QueryBuilder.build(Followers.TAG_USER, App42API.getLoggedInUser(), Operator.EQUALS);
					Query q2 = QueryBuilder.build(Followers.TAG_USER_TO_FOLLOW, userToFollow, Operator.EQUALS);
					Query q3 = QueryBuilder.compoundOperator(q1, Operator.AND, q2);
					BarkerServices.instance().storageService.findDocumentsByQuery
						(Tools.dbName, Followers.collectionName, q3, new App42CallBack() {
							@Override
							public void onSuccess(Object response) { }
							@Override
							public void onException(Exception ex) {
								App42Exception exception = (App42Exception)ex;  
							    if(exception.getAppErrorCode() == 2608) {
							    	getActivity().runOnUiThread(new Runnable() {
										@Override
										public void run() {
											fabButton.setVisibility(View.VISIBLE);								
										}
									});
								}
							}
						});
				}
				
				fabClickListener = new OnClickListener() {
					@Override
					public void onClick(View v) {
						follow();
					}
				};
			}
		}
		
		fabButton.setOnClickListener(fabClickListener);
		
		return barksview;
	}

	private void follow() {
		Followers followers = new Followers(App42API.getLoggedInUser(), userToFollow);
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("Attendi...");
		progressDialog.show();
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		BarkerServices.instance().storageService.insertJSONDocument(
			Tools.dbName, Followers.collectionName, followers.getJSON(), new App42CallBack(){
	
				@Override
				public void onSuccess(Object arg0) {
					Snackbar.make(getView(), "Da ora segui questo utente", Snackbar.LENGTH_SHORT).show();
					IBarksCallback barksfrag = (IBarksCallback)getTargetFragment();
					progressDialog.dismiss();
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							getView().findViewById(R.id.fabBtn).setVisibility(View.GONE);
						}
					});
					//barksfrag.onSuccess();
				}

				@Override
				public void onException(Exception arg0) {
					Snackbar.make(getView(), "Errore, riprova", Snackbar.LENGTH_SHORT).show();
				}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
			    Collections.sort(barks, new DateComparator());
			    
			    barksAdapter = new BarkAdapter(getActivity(), barks, BarksFragment.this);
			    getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						rvbarks.setAdapter(barksAdapter);						
					}
				});
			}  
			public void onException(Exception ex) {  
				App42Exception ecc = (App42Exception)ex;
			    System.out.println("Exception Message"+ex.getMessage());          
			}  
			});
	}
	
	private void getBarksFromUser() {
		Query q1 = QueryBuilder.build("userId", userToFollow, Operator.EQUALS);
		
		BarkerServices.instance().storageService.findDocumentsByQuery
		(Tools.dbName, Bark.collectionName, q1, new App42CallBack() {
			
			@Override
			public void onSuccess(Object arg0) {
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
				System.out.println("Exception Message"+arg0.getMessage());
			}
		});
	}

	@Override
	public void onSuccess() {
		if(userToFollow == null)
			getBarks();
		else
			getBarksFromUser();
	}

	@Override
	public void onError() {
		
	}
}
