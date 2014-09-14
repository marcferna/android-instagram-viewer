package com.marcferna.instagramviewer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.marcferna.instagramclient.InstagramClient;
import com.marcferna.models.instagramphoto.InstagramPhoto;
import com.marcferna.models.instagramphoto.InstagramPhotosAdapter;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;


public class PhotosActivity extends Activity {
  public static String instagramClientId = "53ffa3808b744761ab55756cf6d2e39f";
  private ArrayList<InstagramPhoto> photos = new ArrayList<InstagramPhoto>();
  private InstagramClient client;

  private InstagramPhotosAdapter adapterPhotos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_photos);
    client = new InstagramClient(instagramClientId);
    try {
      Class[] parameterTypes = new Class[3];
      parameterTypes[0] = int.class;
      parameterTypes[1] = Header[].class;
      parameterTypes[2] = JSONObject.class;
      Method method = PhotosActivity.class.getMethod("fetchPopularPhotosSuccess", parameterTypes);
      client.fetchPopularPhotos(this, method);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @SuppressWarnings("unused")
  public void fetchPopularPhotosSuccess(int statusCode, Header[] headers, JSONObject response) {
    JSONArray photosJSONArray;
    photos.clear();
    photos = client.parseJSONResponse(response);
    this.populatePhotosListView();
  }

  private void populatePhotosListView() {
    ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
    adapterPhotos = new InstagramPhotosAdapter(this, photos);
    lvPhotos.setAdapter(adapterPhotos);
    adapterPhotos.notifyDataSetChanged();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.photos, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
