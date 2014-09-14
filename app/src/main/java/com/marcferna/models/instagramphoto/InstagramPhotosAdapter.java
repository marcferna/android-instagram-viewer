package com.marcferna.models.instagramphoto;

import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcferna.instagramviewer.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by marc on 9/13/14.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

  public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
    super(context, android.R.layout.simple_list_item_1, photos);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    InstagramPhoto photo = getItem(position);

    // Lookup the subview within the template
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
    }

    ImageView imgAvatar = (ImageView)convertView.findViewById(R.id.imgAvatar);
    TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
    ImageView imgPhoto = (ImageView)convertView.findViewById(R.id.imgPhoto);
    TextView tvLikeCount = (TextView)convertView.findViewById(R.id.tvLikeCount);
    TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);

    tvUsername.setText(photo.username);

    // clears the image in case is a recycled view
    imgAvatar.setImageResource(0);
    Picasso.with(getContext()).load(photo.avatarUrl).into(imgAvatar);

    WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    Point windowSize = new Point();
    display.getSize(windowSize);
    imgPhoto.getLayoutParams().height = windowSize.x;

    // clears the image in case is a recycled view
    imgPhoto.setImageResource(0);
    Picasso.with(getContext()).load(photo.url).into(imgPhoto);

    tvCaption.setText(Html.fromHtml("<b>" + photo.username + "</b> " + photo.caption));

    tvLikeCount.setText(Integer.toString(photo.likesCount) + " likes");

    return convertView;
  }
}
