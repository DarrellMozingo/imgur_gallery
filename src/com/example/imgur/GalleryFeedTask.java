package com.example.imgur;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.*;

import android.os.AsyncTask;

public class GalleryFeedTask extends AsyncTask<String, Void, String> {
	private MainActivity _mainActivity;
	private int _imageCount;
	
	public GalleryFeedTask(MainActivity activity, int imageCount) {
		_mainActivity = activity;
		_imageCount = imageCount;
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

        return "No JSON result from the gallery?!";
	}

    protected void onPostExecute(String result) {
    	String imageUrl = null;
    	String title = null;

    	try {
			JSONObject imageJsonObject = new JSONObject(result).getJSONArray("data").getJSONObject(_imageCount);
			
			title = imageJsonObject.getString("title");
			imageUrl = buildImageUrl(imageJsonObject);
		} catch (JSONException e) {
			_mainActivity.showErrorMessage(e.toString());
			return;
		}

    	_mainActivity.showTitle(title);
    	new GalleryImageTask(_mainActivity).execute(imageUrl);
    }

	private String buildImageUrl(JSONObject imageJsonObject) throws JSONException {
		String hash = imageJsonObject.getString("hash");
		String extension = imageJsonObject.getString("ext");

		return "http://i.imgur.com/" + hash + extension;
	}
}