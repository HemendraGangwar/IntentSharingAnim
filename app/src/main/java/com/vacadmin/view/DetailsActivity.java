package com.vacadmin.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vacadmin.R;
import com.vacadmin.bean.FeedItems;
import com.vacadmin.listener.AppBarStateChangeListener;
import com.vacadmin.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DetailsActivity extends AppCompatActivity {

    @InjectView(R.id.imageViewBg)
    ImageView imageViewBg;

    @InjectView(R.id.textViewTitle)
    TextView textViewTitle;


    @InjectView(R.id.textViewDesc)
    TextView textViewDesc;

    @InjectView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.appbar)
    AppBarLayout appbar;

    @InjectView(R.id.gradientView)
    View gradientView;

    @InjectView(R.id.fabButton)
    FloatingActionButton fabButton;

    private FeedItems mFeedItems;
    private int color_collapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_details);

        /**
         * inject views of xml layout to java code
         */
        ButterKnife.inject(this);

        Intent dataIntent = getIntent();
        mFeedItems = (FeedItems) dataIntent.getSerializableExtra("FeedItems");
        imageViewBg.setImageResource(mFeedItems.getImgResourceId());
        textViewTitle.setText(mFeedItems.getTitle());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageViewBg.setTransitionName("profile");
            textViewTitle.setTransitionName("text");
            textViewDesc.setTransitionName("desc");
        }


        Bitmap bitmapFromId = BitmapFactory.decodeResource(DetailsActivity.this.getResources(), mFeedItems.getImgResourceId());
        Palette.from(bitmapFromId).generate(new Palette.PaletteAsyncListener() {
            int color = 0xFFFFFFFF;
            int transparent = Color.argb(0, Color.red(color), Color.green(color), Color.blue(color));

            public void onGenerated(Palette palette) {
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                if (vibrantSwatch != null) {
                    textViewTitle.setTextColor(vibrantSwatch.getTitleTextColor());
                    collapsingToolbar.setExpandedTitleColor(Color.WHITE);
                    color_collapsed = vibrantSwatch.getRgb();
                    GradientDrawable bgShape = (GradientDrawable) gradientView.getBackground();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        bgShape.setColors(new int[]{vibrantSwatch.getRgb(), transparent, transparent});
                        bgShape.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                    }

                    fabButton.setBackgroundTintList(ColorStateList.valueOf(vibrantSwatch.getRgb()));

                    //  collapsingToolbar.setCollapsedTitleTextColor(vibrantSwatch.getTitleTextColor());
                    // scrim.setBackgroundColor(vibrantSwatch.getRgb());
                    // collapsingToolbar.setBackgroundColor(vibrantSwatch.getRgb());
                    /*collapsingToolbar.setStatusBarScrimColor(vibrantSwatch.getRgb());
                   collapsingToolbar.setContentScrimColor(vibrantSwatch.getRgb());*/

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(vibrantSwatch.getRgb());
                    }
                }
            }
        });

        SimpleDateFormat localDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String convertedDateTime = localDateFormat.format(new Date());

        /**
         * Customizing toolbar and header
         */
        collapsingToolbar.setTitle(mFeedItems.getTitle());


        collapsingToolbar.setContentDescription("Created On : "+convertedDateTime);

        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsActivity.this.finish();
            }
        });
        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {

                Log.e("tg", "verticalOffset = " + verticalOffset);
                /**
                 * changing toolbar color on scroll changed
                 */
                if (state == state.IDLE) {
                    gradientView.setVisibility(View.VISIBLE);
                    toolbar.setBackgroundColor(Utils.getColor(DetailsActivity.this, android.R.color.transparent));

                } else if (state == State.COLLAPSED) {
                    toolbar.setBackgroundColor(color_collapsed);
                    gradientView.setVisibility(View.GONE);
                } else {
                    toolbar.setBackgroundColor(Utils.getColor(DetailsActivity.this, android.R.color.transparent));
                    gradientView.setVisibility(View.GONE);

                }
            }
        });
    }


}
