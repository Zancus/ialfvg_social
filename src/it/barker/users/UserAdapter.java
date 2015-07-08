package it.barker.users;

import it.barker.R;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shephertz.app42.paas.sdk.android.user.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH>{

	private Context context;
	private ArrayList<User> users;
	
	public UserAdapter(Context context, ArrayList<User> users) {
		super();
		this.context = context;
		this.users = users;
	}

	public class UserVH extends ViewHolder {

		CardView cvuser;
		TextView username, emailuser;
		
		public UserVH(View arg0) {
			super(arg0);
			cvuser = (CardView) arg0.findViewById(R.id.cvuser);
			username = (TextView) arg0.findViewById(R.id.userrname);
			emailuser = (TextView) arg0.findViewById(R.id.emailuser);
		}

	}

	@Override
	public int getItemCount() {
		return users.size();
	}

	@Override
	public void onBindViewHolder(UserVH arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.username.setText(users.get(arg1).getUserName());
		arg0.emailuser.setText(users.get(arg1).getEmail());
	}

	@Override
	public UserVH onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		View user = inflater.inflate(R.layout.user, null);
		UserVH userVH = new UserVH(user);
		return userVH;
	}

}
