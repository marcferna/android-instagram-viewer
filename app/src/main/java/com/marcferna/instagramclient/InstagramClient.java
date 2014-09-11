package com.marcferna.instagramclient;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by marc on 9/11/14.
 */
public class InstagramClient {

  private static String popularPhotosUrl = "https://api.instagram.com/v1/media/popular";

  private String instagramClientId;
  private AsyncHttpClient client;

  public InstagramClient(String clientId) {
    instagramClientId = clientId;
    client = new AsyncHttpClient();
  }

  public void fetchPopularPhotos() {
    client.get(generateUrl(popularPhotosUrl), new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.i("INFO", response.toString());
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
      }
    });
  }

  private String generateUrl(String baseUrl) {
    return baseUrl + "?client_id=" + instagramClientId;
  }
}
