package org.wildlifeimages.android.wildlifeimages;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
/**
 * The parent for the other activities allowing for fun helper functions.
 */
public abstract class WireActivity extends Activity{
	
	AlertDialog scanDialog;

	@Override
	protected void onCreate(Bundle bundle){
		super.onCreate(bundle);
		/* Use saved version of contentManager if activity just restarted */
		//ContentManager contentManager = (ContentManager)getLastNonConfigurationInstance();
		//if (null == contentManager){
		//if (ContentManager.getSelf() == null){
		//	new ContentManager(this.getCacheDir(), this.getAssets());
		//}
		//}else{
		//	ContentManager.setSelf(contentManager);
		//}

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		scanDialog = Common.createScanDialog(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Common.menuItemProcess(this, item.getItemId(), ContentManager.getSelf().getExhibitList());
		return true;
	}

	/**
	 * Gets called when an activity has been started and a request has been given for when it ends.
	 * 
	 * @param an int requestCode that has the code for what action to do.
	 * @param an int resultCode that has the resulting code from the request.
	 * @param a Intent intent that is the intent used to start the process.	
	 */	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		Common.processActivityResult(this, requestCode, resultCode, intent);
	}

	String loadString(int resId){
		return getResources().getString(resId);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		scanDialog.dismiss();
	}
	
	//@Override
	//public Object onRetainNonConfigurationInstance() {
	//	final ContentManager data = ContentManager.getSelf();
	//	return data;
	//	}
}
