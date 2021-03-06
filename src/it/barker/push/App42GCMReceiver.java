/**
 * -----------------------------------------------------------------------
 *     Copyright � 2015 ShepHertz Technologies Pvt Ltd. All rights reserved.
 * -----------------------------------------------------------------------
 */
package it.barker.push;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
/**
 * @author Vishnu Garg
 *
 */
public class App42GCMReceiver  extends WakefulBroadcastReceiver {
	
	 /* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	    public void onReceive(Context context, Intent intent) {
		    System.out.println( App42GCMService.class.getName());
	        ComponentName comp = new ComponentName(context.getPackageName(),
	        		App42GCMService.class.getName());
	        startWakefulService(context, (intent.setComponent(comp)));
	        setResultCode(Activity.RESULT_OK);
	}
}