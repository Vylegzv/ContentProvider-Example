package com.example.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;


public class MiniTwitterContract {
	
	public static final String AUTHORITY = "com.example.contentprovider.minitwitterprovider";
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
	
	/** User **/
	public static final class User implements BaseColumns {
		
		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI,  "user");
		
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/com.example.contentprovider.user";
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/com.example.contentprovider.user";
		
		/** Table name **/
		public static final String TABLE = "User";
		
		/** Column names **/
		public static final String USERNAME = "username";
		public static final String PASSWORD = "password";
	}
	
	/** Tweet **/
	public static final class Tweet implements BaseColumns {
		
		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI,  "tweet");
		
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/com.example.contentprovider.tweet";
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/com.example.contentprovider.tweet";
		
		/** Table name **/
		public static final String TABLE = "Tweet";
		
		/** Column names **/
		public static final String USERNAME = "username";
		public static final String CONTENT = "content";
		public static final String TIMESTAMP = "timestamp";
	}

	/** Follow **/
	public static final class Follow implements BaseColumns {
		
		public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI,  "follow");
		
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/com.example.contentprovider.follow";
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/com.example.contentprovider.follow";
		
		/** Table name **/
		public static final String TABLE = "Follow";
		
		/** Column names **/
		public static final String USERNAME = "username";
		public static final String FOLLOWER = "follower";
		public static final String TIMESTAMP = "timestamp";
	}
}
