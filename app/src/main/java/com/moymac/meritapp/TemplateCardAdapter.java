package com.moymac.meritapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.List;

/**
 * Created by moymac on 11/30/17.
 */

public class TemplateCardAdapter extends ArrayAdapter<TemplateItem> {

    public TemplateCardAdapter(Context context, List<TemplateItem> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TemplateItem templateItem = getItem(position);
        CardView card = (CardView) convertView;
        ViewHolder viewHolder;

        if(card==null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            card = (CardView) vi.inflate(R.layout.card_layout,parent, false);

            viewHolder.icon = (ImageView) card.findViewById(R.id.icon);
            viewHolder.title = (TextView) card.findViewById(R.id.titleTV);
            viewHolder.author = (TextView) card.findViewById(R.id.authorTV);
            viewHolder.description = (TextView) card.findViewById(R.id.descriptionTV);
            viewHolder.difficulty = (TextView) card.findViewById(R.id.difficultyTV);
            viewHolder.price = (TextView) card.findViewById(R.id.priceTV);
            viewHolder.rating = (RatingBar) card.findViewById(R.id.ratingBar);
            viewHolder.time = (TextView) card.findViewById(R.id.timeTV);

            card.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(templateItem.getTitle());
        viewHolder.author.setText(templateItem.getAuthor());
        viewHolder.description.setText(templateItem.getDescription());
        viewHolder.rating.setRating(templateItem.getRating());
        viewHolder.difficulty.setText(templateItem.getDifficulty());
        viewHolder.price.setText("$" + String.valueOf(templateItem.getPrice()));
        viewHolder.time.setText(templateItem.getTime());
        Picasso.with(getContext()).load(templateItem.getImageUrl()).into(viewHolder.icon);


        return card;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView author;
        TextView description;
        TextView difficulty;
        RatingBar rating;
        TextView price;
        TextView time;

        RelativeLayout stepsLayout;




    }
}
