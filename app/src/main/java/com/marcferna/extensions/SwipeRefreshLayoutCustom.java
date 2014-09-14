package com.marcferna.extensions;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by marc on 9/13/14.
 */
public class SwipeRefreshLayoutCustom extends SwipeRefreshLayout {

  @SuppressWarnings("unused")
  public SwipeRefreshLayoutCustom(Context context) {
    super(context);
  }

  @SuppressWarnings("unused")
  public SwipeRefreshLayoutCustom(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void onAttachedToWindow () {
    this.setColors();
  }

  private void setColors() {
    this.setColorSchemeResources(
      android.R.color.holo_blue_bright,
      android.R.color.holo_green_light,
      android.R.color.holo_orange_light,
      android.R.color.holo_red_light
    );
  }
}
