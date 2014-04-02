package com.example.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MiniTwitterCP extends ContentProvider{
	
	private String TAG = getClass().getSimpleName();
	
	/** Helper constants to use with UriMatcher */
	private static final int USER = 1;
	private static final int USER_ID = 2;
	private static final int TWEET = 3;
	private static final int TWEET_ID = 4;
	private static final int FOLLOW = 5;
	private static final int FOLLOW_ID = 6;
	
	/**
	 * UriMatcher that will match different URIs
	 */
	private static final UriMatcher uriMatcher;

	/**
	 * Declare an SQLite open helper to manage database creation and versions
	 */
	private MyDbOpenHelper myDbOpenHelper;

	/**
	 * Declare a UriMatcher to match URIs
	 */
	static {

		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(MiniTwitterContract.AUTHORITY, "user", USER);
		uriMatcher.addURI(MiniTwitterContract.AUTHORITY, "user/#", USER_ID);
		uriMatcher.addURI(MiniTwitterContract.AUTHORITY, "tweet", TWEET);
		uriMatcher.addURI(MiniTwitterContract.AUTHORITY, "tweet/#", TWEET_ID);
		uriMatcher.addURI(MiniTwitterContract.AUTHORITY, "follow", FOLLOW);
		uriMatcher.addURI(MiniTwitterContract.AUTHORITY, "follow/#", FOLLOW_ID);
	}
	
	
	@Override
	public String getType(Uri uri) {
		
		switch (uriMatcher.match(uri)) {

		case USER_ID:
			return MiniTwitterContract.User.CONTENT_ITEM_TYPE;
		case USER:
			return MiniTwitterContract.User.CONTENT_TYPE;
		case TWEET_ID:
			return MiniTwitterContract.Tweet.CONTENT_ITEM_TYPE;
		case TWEET:
			return MiniTwitterContract.Tweet.CONTENT_TYPE;
		case FOLLOW_ID:
			return MiniTwitterContract.Follow.CONTENT_ITEM_TYPE;
		case FOLLOW:
			return MiniTwitterContract.Follow.CONTENT_TYPE;
		default:
			Log.e(TAG, "Unsupported URI: " + uri);
			return null;

		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		long rowId = 0;
		Uri _uri = null;
		
		switch (uriMatcher.match(uri)) {

		case USER:

			rowId = myDbOpenHelper.getWritableDatabase().insert(MiniTwitterContract.User.TABLE,
					null, values);
			// if added successfully
			if (rowId > 0) {
				_uri = ContentUris.withAppendedId(
						MiniTwitterContract.User.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(_uri, null);
			}
			break;
			
		default:

			Log.e(TAG, "Unsupported URI: " + uri);
			return null;
		}
		
		return _uri;
	}

	@Override
	public boolean onCreate() {
		
		myDbOpenHelper = new MyDbOpenHelper(getContext());
		return ((myDbOpenHelper == null) ? false : true);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values){
		
		int numInserted = 0;
		String table = null;
		
		switch(uriMatcher.match(uri)){
		
		case USER:
			table = MiniTwitterContract.User.TABLE;
			break;
		}		
		
		SQLiteDatabase myDB = myDbOpenHelper.getWritableDatabase();
		myDB.beginTransaction();
		
		try{
			for(ContentValues cv : values){
				long newID = myDB.insertOrThrow(table, null, cv);
				if(newID <= 0){
					throw new SQLException("Failed to insert row into " + uri);
				}
			}
			
			myDB.setTransactionSuccessful();
			getContext().getContentResolver().notifyChange(uri, null);
			numInserted = values.length;
		}finally{
			myDB.endTransaction();
		}
		
		return numInserted;
	}
	
}
