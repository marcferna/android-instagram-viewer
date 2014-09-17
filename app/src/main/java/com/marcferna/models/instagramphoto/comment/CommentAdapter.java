package com.marcferna.models.instagramphoto.comment;

import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcferna.instagramviewer.R;
import com.marcferna.models.instagramphoto.InstagramPhoto;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by marc on 9/16/14.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

  public CommentAdapter(Context context, List<Comment> photos) {
    super(context, android.R.layout.simple_list_item_1, photos);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Comment comment = getItem(position);

    // Lookup the subview within the template
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
    }

    TextView tvComment = (TextView)convertView.findViewById(R.id.tvComment);
    tvComment.setText(Html.fromHtml("<b>" + comment.username + "</b> " + comment.text));
    return convertView;
  }
}
