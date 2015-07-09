package it.barker.barker;

import com.shephertz.app42.paas.sdk.android.App42API;
import it.barker.push.App42GCMService;

import it.barker.barks.BarksFragment;
import it.barker.push.App42GCMController;
import it.barker.push.App42GCMController.App42GCMListener;
import it.barker.users.UsersFragment;
import it.barker.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private TabLayout tabLayout;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		tabLayout = (TabLayout) findViewById(R.id.tabLayout);
		viewPager = (ViewPager) findViewById(R.id.pager);

		FragmentPagerAdapter adapter = new FragmentPagerAdapter(
				getSupportFragmentManager()) {

			private int[] titles = new int[] { R.string.Timeline,
					R.string.Users, R.string.MyProfile };

			@Override
			public int getCount() {
				return titles.length;
			}

			@Override
			public Fragment getItem(int position) {
				
				switch (position) {
					case 0:
						return BarksFragment.newInstance(Tools.TUTTIBARKS);
					case 1:
						return UsersFragment.newInstance();
					case 2:
						return new PlaceHolder();
					default:
						return null;
				}

			}

			@Override
			public CharSequence getPageTitle(int position) {
				return getResources().getString(titles[position]);
			}
		};
		viewPager.setAdapter(adapter);

		tabLayout.setupWithViewPager(viewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            SharedPreferences preferences = getSharedPreferences(Tools.SHAREDPREFERENCES_FILE_NAME, MODE_PRIVATE);
            preferences.edit().remove(Login.USER).commit();
            preferences.edit().remove(Login.PASSWORD).commit();
            Intent intent = new  Intent(this, Login.class);
            startActivity(intent);
            finish();
            
        	return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	public void onStart() {
		super.onStart();
		if (App42GCMController.isPlayServiceAvailable(this)) {
			App42GCMController.getRegistrationId(this, Tools.GOOGLE_PROJECT_NO, new App42GCMListener() {
				
				@Override
				public void onRegisterApp42(String responseMessage) {
					App42GCMController.storeApp42Success(MainActivity.this);
					Log.d("push", "onRegisterApp42");
				}
				
				@Override
				public void onGCMRegistrationId(String gcmRegId) {
					App42GCMController.storeRegistrationId(MainActivity.this, gcmRegId);
					if(!App42GCMController.isApp42Registerd(MainActivity.this))
						App42GCMController.registerOnApp42(App42API.getLoggedInUser(), gcmRegId, this);
					Log.d("push", "onGCMRegistrationId");
				}
				
				@Override
				public void onError(String errorMsg) {
					Log.w("push", errorMsg);
				}
				
				@Override
				public void onApp42Response(String responseMessage) {
					Log.d("pushmsg", responseMessage);
				}
			});
		} else {
			Log.i("App42PushNotification",
					"No valid Google Play Services APK found.");
		}
	}
	
	public void onPause() {
		super.onPause();
		unregisterReceiver(mBroadcastReceiver);
	}
	
	public void onResume() {
		super.onResume();
		String message = getIntent().getStringExtra(App42GCMService.ExtraMessage);
		if (message != null)
			Log.d("MainActivity-onResume", "Message Recieved :" + message);
		IntentFilter filter = new IntentFilter(App42GCMService.DisplayMessageAction);
		filter.setPriority(2);
		registerReceiver(mBroadcastReceiver, filter);
	}

	final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String message = intent.getStringExtra(App42GCMService.ExtraMessage);
			Log.i("MainActivity-BroadcastReceiver", "Message Recieved " + " : "
					+ message);
		}
	};
	
	public static class PlaceHolder extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			RecyclerView recyclerView = new RecyclerView(getActivity());
			recyclerView
					.setLayoutManager(new LinearLayoutManager(getActivity()));
			recyclerView
					.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
						@Override
						public int getItemCount() {
							return 30;
						}

						@Override
						public void onBindViewHolder(ViewHolder holder,
								int position) {
							((TextView) holder.itemView).setText("Item "
									+ position);
						}

						@Override
						public ViewHolder onCreateViewHolder(ViewGroup parent,
								int type) {
							LayoutInflater layoutInflater = getActivity()
									.getLayoutInflater();
							View view = layoutInflater.inflate(
									android.R.layout.simple_list_item_1,
									parent, false);
							return new ViewHolder(view) {
							};
						}
					});
			return recyclerView;
		}
	}
}