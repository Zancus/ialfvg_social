package it.ialweb.poi.users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.user.User;

import it.ialweb.models.Bark;
import it.ialweb.poi.BarkerServices;
import it.ialweb.poi.R;
import it.ialweb.poi.Tools;
import it.ialweb.poi.barks.BarkAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UsersFragment extends Fragment {

	private View viewusers;
	private RecyclerView rvusers;
	private UserAdapter useradapter;
	private ArrayList<User> users;
	
	public static UsersFragment newInstance()
	{
		return new UsersFragment();
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		viewusers = inflater.inflate(R.layout.fragment_users, null);
		rvusers = (RecyclerView) viewusers.findViewById(R.id.rvusers);
		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		rvusers.setLayoutManager(llm);
		getUsers();
		return viewusers;
	}
	
	private void getUsers()
	{
		BarkerServices.instance().userService.getAllUsers(new App42CallBack(){

			@Override
			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
				Snackbar.make(viewusers, "Errore!", Snackbar.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(Object arg0) {
				// TODO Auto-generated method stub
				
				users = (ArrayList<User>)arg0;
				useradapter = new UserAdapter(getActivity(), users);
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						rvusers.setAdapter(useradapter);						
					}
				});
			}
			
		});
	}
}
