package com.marcferna.models.instagramphoto.comment;

/**
 * Created by marc on 9/16/14.
 */
public class Comment {

  public String username;
  public String text;

  public Comment(String username, String text){
    this.username = username;
    this.text = text;
  }

  public String toString(){
    return this.text;
  }
}
