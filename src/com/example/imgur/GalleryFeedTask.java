package com.example.imgur;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class GalleryFeedTask extends AsyncTask<String, Void, String> {
	private MainActivity _mainActivity;
	
	public GalleryFeedTask(MainActivity activity) {
		_mainActivity = activity;
	}
	
	@Override
	protected String doInBackground(String... urls) {
        try {
            HttpClient client = new DefaultHttpClient();  
            HttpGet get = new HttpGet(urls[0]);
            HttpResponse responseGet = client.execute(get);  
            HttpEntity resEntityGet = responseGet.getEntity();

            if (resEntityGet != null) {  
                return EntityUtils.toString(resEntityGet);
            }
        } catch (Exception e) {
            return e.toString();
        }

        return "No result?!";
	}

    protected void onPostExecute(String result) {
       _mainActivity.showText(result);
    }
}
