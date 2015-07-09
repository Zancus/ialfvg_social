package it.barker.users;
import it.barker.R;
import it.barker.barker.BarkerServices;
import it.barker.barker.Tools;
import it.barker.users.RecyclerItemClickListener.OnItemClickListener;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.user.User;

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
						rvusers.addOnItemTouchListener(new RecyclerItemClickListener
								(getActivity(), new OnItemClickListener() {
									
									@Override
									public void onItemClick(View view, int position) {
										// TODO Auto-generated method stub
										Intent dettaglioutente = new Intent(getActivity(), DettaglioUtenteActivity.class);
										Bundle dettaglioutentebundle = new Bundle();
										dettaglioutentebundle.putString(Tools.NOME_UTENTE, users.get(position).getUserName());
										dettaglioutentebundle.putString(Tools.EMAIL_UTENTE, users.get(position).getEmail());
										dettaglioutente.putExtras(dettaglioutentebundle);
										startActivity(dettaglioutente);
									}
						}));
					}
				});
				
				
			}
			
		});
	}
}
