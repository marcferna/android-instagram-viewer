package com.marcferna.instagramclient;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.marcferna.models.InstagramPhoto;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
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
        JSONArray photosJSONArray = null;
        try {
          photosJSONArray = response.getJSONArray("data");
          for (int i = 0; i < photosJSONArray.length(); i++) {
            JSONObject photoJSON = photosJSONArray.getJSONObject(i);
            InstagramPhoto photo = new InstagramPhoto();
            photo.username = photoJSON.getJSONObject("user").getString("username");
            photo.caption = photoJSON.getJSONObject("caption").getString("text");
            JSONObject standardResolutionImage = photoJSON.getJSONObject("images").getJSONObject("standard_resolution");
            photo.url = standardResolutionImage.getString("url");
            photo.height = standardResolutionImage.getInt("height");
            photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");

          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
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
