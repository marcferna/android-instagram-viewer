package com.marcferna.instagramclient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.marcferna.models.instagramphoto.InstagramPhoto;
import com.marcferna.models.instagramphoto.comment.Comment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

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

  private String generateUrl(String baseUrl) {
    return baseUrl + "?client_id=" + instagramClientId;
  }

  public void fetchPopularPhotos(final Object object, final Method method) {
    client.get(generateUrl(popularPhotosUrl), new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
          method.invoke(object, statusCode, headers, response);
        } catch(Exception e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
      }
    });
  }

  public ArrayList<InstagramPhoto> parseJSONResponse(JSONObject response) {
    ArrayList<InstagramPhoto> photos = new ArrayList<InstagramPhoto>();
    JSONArray photosJSONArray;
    try {
      photosJSONArray = response.getJSONArray("data");
      for (int i = 0; i < photosJSONArray.length(); i++) {
        try {
          JSONObject photoJSON = photosJSONArray.getJSONObject(i);
          InstagramPhoto photo = new InstagramPhoto();

          JSONObject user = photoJSON.getJSONObject("user");
          photo.username = user.getString("username");
          photo.avatarUrl = user.getString("profile_picture");

          photo.createdTimestamp = photoJSON.getLong("created_time");

          JSONObject caption = photoJSON.optJSONObject("caption");
          if (caption != null) {
            photo.caption = photoJSON.getJSONObject("caption").getString("text");
          }

          JSONObject commentsJSONObject = photoJSON.optJSONObject("comments");
          if (commentsJSONObject != null && commentsJSONObject.optJSONArray("data") != null) {
            photo.comments = parseCommentsJSONArray(commentsJSONObject.getJSONArray("data"));
          }

          JSONObject standardResolutionImage = photoJSON.getJSONObject("images").getJSONObject("standard_resolution");
          photo.url = standardResolutionImage.getString("url");
          photo.height = standardResolutionImage.getInt("height");
          photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");

          photos.add(photo);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return photos;
  }

  private ArrayList<Comment> parseCommentsJSONArray(JSONArray commentsJSONArray) {
    ArrayList<Comment> comments = new ArrayList<Comment>(20);
    for (int i = 0; i < commentsJSONArray.length(); i++) {
      try {
        String username = ((JSONObject) commentsJSONArray.get(i)).getJSONObject("from").getString("username");
        String text = ((JSONObject) commentsJSONArray.get(i)).getString("text");
        Comment comment = new Comment(username, text);
        comments.add(comment);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return comments;
  }
}
