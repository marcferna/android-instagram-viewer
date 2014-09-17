package com.marcferna.models.instagramphoto;

import com.marcferna.models.instagramphoto.comment.Comment;

import java.util.ArrayList;

/**
 * Created by marc on 9/13/14.
 */
public class InstagramPhoto {
  public String username;
  public String avatarUrl;
  public String caption;
  public String url;
  public int height;
  public int likesCount;
  public long createdTimestamp;
  public ArrayList<Comment> comments;
}
