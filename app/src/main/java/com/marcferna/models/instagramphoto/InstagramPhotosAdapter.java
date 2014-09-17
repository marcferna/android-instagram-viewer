package com.marcferna.models.instagramphoto;

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
import android.widget.ListView;
import android.widget.TextView;

import com.marcferna.instagramviewer.R;
import com.marcferna.models.instagramphoto.comment.Comment;
import com.marcferna.models.instagramphoto.comment.CommentAdapter;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    TextView tvTimeAgo = (TextView)convertView.findViewById(R.id.tvTimeAgo);
    ImageView imgPhoto = (ImageView)convertView.findViewById(R.id.imgPhoto);
    TextView tvLikeCount = (TextView)convertView.findViewById(R.id.tvLikeCount);
    ListView lvComments = (ListView)convertView.findViewById(R.id.lvComments);

    tvUsername.setText(photo.username);

    // clears the image in case is a recycled view
    imgAvatar.setImageResource(0);
    Picasso.with(getContext()).load(photo.avatarUrl).into(imgAvatar);

    java.util.Date createdAtDate = new java.util.Date(photo.createdTimestamp * 1000);
    java.util.Date now = new java.util.Date();
    tvTimeAgo.setText(DateUtils.getRelativeTimeSpanString(createdAtDate.getTime(), now.getTime(), DateUtils.SECOND_IN_MILLIS));

    WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    Point windowSize = new Point();
    display.getSize(windowSize);
    imgPhoto.getLayoutParams().height = windowSize.x;

    // clears the image in case is a recycled view
    imgPhoto.setImageResource(0);
    Picasso.with(getContext()).load(photo.url).into(imgPhoto);

    tvLikeCount.setText(NumberFormat.getInstance().format(photo.likesCount) + " likes");

    // TODO: Clean this up - Subclass the type of ListView
    lvComments.setScrollContainer(false);
    lvComments.addHeaderView(new View(getContext()));
    lvComments.addFooterView(new View(getContext()));
    photo.comments.add(0, new Comment(photo.username, photo.caption));
    int commentsSize = photo.comments.size();
    CommentAdapter commentAdapter = new CommentAdapter(getContext(), new ArrayList<Comment>(photo.comments.subList(0, Math.min(commentsSize, 3))));
    lvComments.setAdapter(commentAdapter);

    int totalHeight = 0;
    for (int i = 0, length = commentAdapter.getCount(); i < length; i++) {
      View listItem = commentAdapter.getView(i, null, lvComments);
      listItem.measure(0, 0);
      totalHeight += listItem.getMeasuredHeight();
    }

    ViewGroup.LayoutParams params = lvComments.getLayoutParams();
    params.height = totalHeight + (lvComments.getDividerHeight() * (commentAdapter.getCount() - 1));
    lvComments.setLayoutParams(params);
    commentAdapter.notifyDataSetChanged();
    return convertView;
  }
}
