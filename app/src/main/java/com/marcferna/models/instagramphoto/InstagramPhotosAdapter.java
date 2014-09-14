package com.marcferna.models.instagramphoto;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcferna.instagramviewer.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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

    TextView tvCaption = (TextView)convertView.findViewById(R.id.tvCaption);
    ImageView imgPhoto = (ImageView)convertView.findViewById(R.id.imgPhoto);
    TextView tvLikeCount = (TextView)convertView.findViewById(R.id.tvLikeCount);

    tvCaption.setText(Html.fromHtml("<b>" + photo.username + "</b> " + photo.caption));
    tvLikeCount.setText(Integer.toString(photo.likesCount) + " likes");
    imgPhoto.getLayoutParams().height = photo.height;
    // clears the image in case is a recycled view
    imgPhoto.setImageResource(0);
    Picasso.with(getContext()).load(photo.url).into(imgPhoto);

    return convertView;
  }
}
