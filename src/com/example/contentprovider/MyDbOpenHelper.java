package com.example.contentprovider;

import com.example.contentprovider.MiniTwitterContract.User;
import com.example.contentprovider.MiniTwitterContract.Tweet;
import com.example.contentprovider.MiniTwitterContract.Follow;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDbOpenHelper extends SQLiteOpenHelper{
	
	/**
	 * Name of the database
	 */
	private static final String DATABASE_NAME = "MiniTwitter.db";

	/**
	 * Database version
	 */
	private static final int DATABASE_VERSION = 1;
	
	/**
	 * SQL table create statement
	 */
	private static final String CREATE_USER = "create table " + User.TABLE
			+ " (" + User._ID + " integer primary key autoincrement, "
			+ User.USERNAME + " text not null, " + User.PASSWORD
			+ " text not null);";
	
	/**
	 * SQL table create statement
	 */
	private static final String CREATE_TWEET = "create table " + Tweet.TABLE
			+ " (" + Tweet._ID + " integer primary key autoincrement, "
			+ Tweet.USERNAME + " text not null, " + Tweet.CONTENT + " text not null,"
			+ Tweet.TIMESTAMP + " text not null);";
	
	/**
	 * SQL table create statement
	 */
	private static final String CREATE_FOLLOW = "create table " + Follow.TABLE
			+ " (" + Follow._ID + " integer primary key autoincrement, "
			+ Follow.USERNAME + " text not null, " + Follow.FOLLOWER + " text not null,"
			+ Follow.TIMESTAMP + " text not null);";

	public MyDbOpenHelper(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CREATE_USER);
		db.execSQL(CREATE_TWEET);
		db.execSQL(CREATE_FOLLOW);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + Tweet.TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + Follow.TABLE);
		onCreate(db);
	}

}