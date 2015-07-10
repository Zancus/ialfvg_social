package it.barker.users;

import it.barker.R;
import it.barker.barker.Tools;
import it.barker.barks.BarkAdapter;
import it.barker.barks.BarksFragment;
import it.barker.barks.IBarksCallback;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


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
		Bundle bundle = new Bundle();
		bundle.putString(BarksFragment.OPERAZIONE, Tools.UTENTEBARKS);
		bundle.putString(BarksFragment.USER_TO_FOLLOW, nomeutente);
		BarksFragment barksFragment = BarksFragment.newInstance(bundle);
		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.barkscontainer, barksFragment, BarksFragment.TAG).commit();
	}

	private void setupGUI() {
		txtNomeUtente = (TextView) findViewById(R.id.dettuserrname);
		txtEmailUtente = (TextView) findViewById(R.id.dettemailuser);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
		
	}

	@Override
	public void onError() {
		
	}
	
}
