package com.example.contentprovider;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

abstract class CustomHandler extends AsyncQueryHandler {
    
    public CustomHandler(Context context) {
            super(context.getContentResolver());        
        }
    
    public static class UpdateDataHandler extends CustomHandler{
        	   
    	private Context cp;
    	
        public UpdateDataHandler(Context context) {
            super(context);
            cp = context;
        }

        @Override
        protected void onUpdateComplete(int token, Object cookie, int result) {
                Log.d("async", "update completed");
                Toast.makeText(cp, "Bulk Insert Complete",
 					   Toast.LENGTH_SHORT).show();
        }
    }
}
