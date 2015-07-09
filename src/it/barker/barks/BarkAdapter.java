package it.barker.barks;

import it.barker.models.Bark;
import it.barker.R;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class BarkAdapter extends RecyclerView.Adapter<BarkAdapter.BarkVH> {

	private FragmentActivity context;
	private ArrayList<Bark> barks;
	private IBarksCallback y;
	
	public BarkAdapter(FragmentActivity context, ArrayList<Bark> barks, IBarksCallback x) {
		super();
		this.context = context;
		this.barks = barks;
		this.y = x;
	}

	public class BarkVH extends ViewHolder{
		
		CardView cvbark;
        TextView bark, utente, dataora;
        ImageButton retweet, thumbup;
		
        public BarkVH(View arg0) {
			super(arg0);
			cvbark = (CardView) arg0.findViewById(R.id.cvbark);
			utente = (TextView) arg0.findViewById(R.id.username);
			bark = (TextView) arg0.findViewById(R.id.bark);
			dataora = (TextView) arg0.findViewById(R.id.datetime);
			retweet = (ImageButton) arg0.findViewById(R.id.imgretweet);
			thumbup = (ImageButton) arg0.findViewById(R.id.imgthumbup);
		}

	}

	@Override
	public int getItemCount() {
		return barks.size();
	}

	@Override
	public void onBindViewHolder(BarkVH arg0, final int arg1) {
		arg0.utente.setText(barks.get(arg1).userId);
		arg0.bark.setText(barks.get(arg1).message);
		arg0.dataora.setText("" + barks.get(arg1).date.toString());
		arg0.retweet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ReBarkDialog rebark = ReBarkDialog.newInstance(barks.get(arg1));
				BarksFragment frag = (BarksFragment)y;
				rebark.setTargetFragment(frag, 2);
				rebark.show(context.getSupportFragmentManager(), "aa");
			}
		});
	}

	@Override
	public BarkVH onCreateViewHolder(ViewGroup arg0, int arg1) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View bark = inflater.inflate(R.layout.bark, null);
		BarkVH barkVH = new BarkVH(bark);
		return barkVH;
	}

}
