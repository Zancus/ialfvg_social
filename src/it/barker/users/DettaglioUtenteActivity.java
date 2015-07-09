package it.barker.users;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import it.barker.R;
import it.barker.barker.BarkerServices;
import it.barker.barker.Tools;
import it.barker.barks.BarkAdapter;
import it.barker.barks.BarksFragment;
import it.barker.barks.IBarksCallback;
import it.barker.models.Bark;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.shephertz.app42.paas.sdk.android.App42CallBack;
import com.shephertz.app42.paas.sdk.android.storage.Query;
import com.shephertz.app42.paas.sdk.android.storage.QueryBuilder;
import com.shephertz.app42.paas.sdk.android.storage.Storage;
import com.shephertz.app42.paas.sdk.android.storage.QueryBuilder.Operator;

public class DettaglioUtenteActivity extends AppCompatActivity implements IBarksCallback {

	private String nomeutente, emailutente;
	private TextView txtNomeUtente, txtEmailUtente;
	private RecyclerView rvbarksuser;
	private BarkAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dettaglio_utente);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbardettagliouser));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setupGUI();
		nomeutente = getIntent().getExtras().getString(Tools.NOME_UTENTE);
		emailutente = getIntent().getExtras().getString(Tools.EMAIL_UTENTE);
		if(!(nomeutente.isEmpty() && emailutente.isEmpty()))
		{
			txtNomeUtente.setText(nomeutente);
			txtEmailUtente.setText(emailutente);
		}
		getBarksFromUser();
	}

	private void getBarksFromUser() {
		// TODO Auto-generated method stub
		Query q1 = QueryBuilder.build("userId", nomeutente, Operator.EQUALS); // Build query q1 for key1 equal to name and value1 equal to Nick  
		//Query query = QueryBuilder.compoundOperator(q1); 
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.barkscontainer, BarksFragment.newInstance(Tools.UTENTEBARKS, nomeutente), BarksFragment.TAG).commit();
	}

	private void setupGUI() {
		// TODO Auto-generated method stub
		txtNomeUtente = (TextView) findViewById(R.id.dettuserrname);
		txtEmailUtente = (TextView) findViewById(R.id.dettemailuser);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return true;
		}
	}

	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub
		
	}
	
}
