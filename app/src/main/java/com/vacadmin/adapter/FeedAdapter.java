package com.vacadmin.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.vacadmin.R;
import com.vacadmin.view.DetailsActivity;
import com.vacadmin.bean.FeedItems;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Hemendra_Gangwar
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.CellFeedViewHolder> {


    private  ArrayList<FeedItems> dataList = new ArrayList<>();
    private Context context;


    public FeedAdapter(Context context, ArrayList<FeedItems> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public CellFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_row_property, parent, false);
        return new CellFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CellFeedViewHolder viewHolder, int position) {

       final FeedItems mFeedItems =  dataList.get(position);

        viewHolder.imageViewBg.setBackgroundResource(mFeedItems.getImgResourceId());
        viewHolder.textViewTitle.setText(mFeedItems.getTitle());
        viewHolder.textViewDesc.setText(mFeedItems.getDesc());

         Bitmap bitmapFromId = BitmapFactory.decodeResource(context.getResources(),mFeedItems.getImgResourceId());
         Palette.from(bitmapFromId).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                if (vibrantSwatch != null) {
                    viewHolder.linearLayoutDesc.setBackgroundColor(vibrantSwatch.getRgb());
                    viewHolder.textViewTitle.setTextColor(vibrantSwatch.getTitleTextColor());
                    viewHolder.textViewDesc.setTextColor(vibrantSwatch.getBodyTextColor());
                    viewHolder.fabButton.setBackgroundTintList(ColorStateList.valueOf(vibrantSwatch.getRgb()));
                }
            }
        });


        final Animation animationFadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        viewHolder.linearLayoutDesc.startAnimation(animationFadeOut);

        viewHolder.imageViewBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openActivityVdMultipleViews(context,
                        viewHolder.imageViewBg,
                        viewHolder.textViewTitle,
                        viewHolder.textViewDesc,
                        mFeedItems);

            }
        });
    }

    /**
     * for Multiple Shared Elements
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void openActivityVdMultipleViews(Context context,ImageView imageViewBg,
                                            TextView textViewTitle,
                                             TextView textViewDesc,
                                             FeedItems mFeedItems) {

        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("FeedItems",mFeedItems);
        Pair<View, String> p1 = Pair.create((View) imageViewBg, "profile");
        Pair<View, String> p2 = Pair.create((View) textViewTitle, "text");
        Pair<View, String> p3 = Pair.create((View) textViewDesc, "desc");

        ActivityOptionsCompat options = ActivityOptionsCompat.  makeSceneTransitionAnimation((Activity) context, p1, p2,p3);
        ActivityCompat.startActivity(context, intent, options.toBundle());

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.imageViewBg)
        ImageView imageViewBg;

        @InjectView(R.id.textViewTitle)
        TextView textViewTitle;

        @InjectView(R.id.textViewDesc)
        TextView textViewDesc;

        @InjectView(R.id.linearLayoutDesc)
        FrameLayout linearLayoutDesc;

        @InjectView(R.id.fabButton)
        FloatingActionButton fabButton;



        public CellFeedViewHolder(View view) {
            super(view);

            /**
             * inject views of xml layout to java code
             */
            ButterKnife.inject(this, view);

        }

    }

}