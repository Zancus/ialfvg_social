package it.ialweb.poi.users;

import java.util.ArrayList;

import com.shephertz.app42.paas.sdk.android.user.User;

import it.ialweb.poi.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UsersFragment extends Fragment {

	private RecyclerView rvusers;
	private UserAdapter useradapter;
	private ArrayList<User> users = new ArrayList<User>();
	
	public static UsersFragment newInstance()
	{
		return new UsersFragment();
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View viewusers = inflater.inflate(R.layout.fragment_users, null);
		rvusers = (RecyclerView) viewusers.findViewById(R.id.rvusers);
		LinearLayoutManager llm = new LinearLayoutManager(getActivity());
		rvusers.setLayoutManager(llm);
		getUsers();
		useradapter = new UserAdapter(getActivity(), users);
		return viewusers;
	}
	
	private void getUsers()
	{
		
	}
}
