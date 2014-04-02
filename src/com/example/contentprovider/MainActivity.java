package com.example.contentprovider;

import com.example.contentprovider.CustomHandler.UpdateDataHandler;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.Loader.OnLoadCompleteListener;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	String TAG = getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	public void insert(View v) {

		Log.d(TAG, "insert clicked");
		ContentValues newValues = new ContentValues();

		newValues.put(MiniTwitterContract.User.USERNAME, "Abc");
		newValues.put(MiniTwitterContract.User.PASSWORD, "123abc123");

		new InsertTask().execute(newValues);
	}

	public void bulkInsert(View v) {

		int count = 10;
		ContentValues[] cvs = new ContentValues[count];
		for (int i = 0; i < count; i++) {

			ContentValues cv = new ContentValues();
			cv.put(MiniTwitterContract.User.USERNAME, "user" + i);
			cv.put(MiniTwitterContract.User.PASSWORD, "pwd" + i);
			cvs[count] = cv;
		}

		new BulkInsertTask().execute(cvs);

	}

	public void update(View v) {

		UpdateDataHandler myUpdateHandler = new UpdateDataHandler(
				MainActivity.this);

		ContentValues updateValues = new ContentValues();
		updateValues.put(MiniTwitterContract.User.PASSWORD, "xyz789xyz"); // update
																			// ounces
																			// or
																			// wtv

		String usrName = "Abc";
		String where = MiniTwitterContract.User.USERNAME + "='" + usrName + "'";

		myUpdateHandler.startUpdate(0, 0, MiniTwitterContract.User.CONTENT_URI,
				updateValues, where, null);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public void query(){
		
		CursorLoader loader = null;
		
		String[] resultColumns = new String[] {
				MiniTwitterContract.User._ID,
				MiniTwitterContract.User.USERNAME };
		
		String pwd = "123abc123";
		String where = MiniTwitterContract.User.USERNAME + "='" + pwd + "'";

		loader = new CursorLoader(MainActivity.this,
				MiniTwitterContract.User.CONTENT_URI,
			resultColumns, where, null, null);
		loader.registerListener(1, new MyOnLoadCompleteListener());
		loader.startLoading();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class InsertTask extends AsyncTask<ContentValues, Void, Uri> {

		@Override
		protected Uri doInBackground(ContentValues... params) {

			return getContentResolver().insert(
					MiniTwitterContract.User.CONTENT_URI, params[0]);
		}

		protected void onPostExecute(final Uri uri) {

			if (uri != null) {
				Toast.makeText(getApplicationContext(), "Insert Complete",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "There was a problem",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class BulkInsertTask extends
			AsyncTask<ContentValues[], Void, Integer> {

		@Override
		protected Integer doInBackground(ContentValues[]... params) {

			return getContentResolver().bulkInsert(
					MiniTwitterContract.User.CONTENT_URI, params[0]);
		}

		protected void onPostExecute(final Integer numInserted) {

			if (numInserted > 0) {
				Toast.makeText(getApplicationContext(), "Bulk Insert Complete",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "There was a problem",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class MyOnLoadCompleteListener implements
			OnLoadCompleteListener<Cursor> {

		@Override
		public void onLoadComplete(Loader<Cursor> loader, Cursor cursor) {
			
			Log.d(TAG, "on load complete called");

			while (cursor != null && cursor.moveToNext()) {
				Log.d(TAG, cursor.getString(1));

			}
			
		}
	}
}
