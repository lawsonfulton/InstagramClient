package com.lawsonfulton.instagramclient;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by lawson on 2/6/16.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    public static int getDisplayWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfPic = (ImageView) convertView.findViewById(R.id.ivProfPic);

        tvUserName.setText(photo.username);
        tvLikes.setText(Integer.toString(photo.likesCount) + " likes");

        Date now = new Date();
        long gmtOffset = TimeZone.getDefault().getRawOffset();
        Date created = new Date(photo.createdTime * 1000L + gmtOffset);
        String relTimeStamp = DateUtils.getRelativeTimeSpanString(photo.createdTime * 1000L, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
        tvTimeStamp.setText(relTimeStamp);

        String captionHtmlString = "<b>" + photo.username + "</b> " + photo.caption;
        tvCaption.setText(Html.fromHtml(captionHtmlString));

        ivPhoto.setImageResource(0);
        int dispWidth = getDisplayWidth(getContext());
        Picasso.with(getContext()).load(photo.imageUrl).resize(dispWidth,0).into(ivPhoto);

        Picasso.with(getContext()).load(photo.userProfPicUrl).transform(new CircleTransform()).into(ivProfPic);

        return convertView;
    }
}
