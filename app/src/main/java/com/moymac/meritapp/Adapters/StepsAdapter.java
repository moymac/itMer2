package com.moymac.meritapp.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moymac.meritapp.R;
import com.moymac.meritapp.StepItem;
import com.moymac.meritapp.TemplateItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by moymac on 11/30/17.
 */

public class StepsAdapter extends ArrayAdapter<StepItem> {

    public StepsAdapter(Context context, List<StepItem> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        StepItem stepItem = getItem(position);
        RelativeLayout stepLayout = (RelativeLayout) convertView;
        final ViewHolder viewHolder;

        if(stepLayout==null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            stepLayout = (RelativeLayout) vi.inflate(R.layout.steps_layout,parent, false);
            viewHolder.parentLayout = stepLayout.findViewById(R.id.parent_step_layout);
            viewHolder.icon = stepLayout.findViewById(R.id.parent_step_icon);
            viewHolder.parentTitle = stepLayout.findViewById(R.id.parent_step_title);
            viewHolder.difficulty = stepLayout.findViewById(R.id.parent_step_difficulty);
            viewHolder.time = stepLayout.findViewById(R.id.parent_step_time);
            viewHolder.parentPrice = stepLayout.findViewById(R.id.parent_step_price);
            viewHolder.rating = stepLayout.findViewById(R.id.parent_step_ratingBar);
            viewHolder.childrenLayout = stepLayout.findViewById(R.id.children_steps_layout);
            viewHolder.numChildrenSteps = stepLayout.findViewById(R.id.numberof_children_steps);
            viewHolder.theChildrenSteps = stepLayout.findViewById(R.id.the_children_steps);
            viewHolder.childrenPrice = stepLayout.findViewById(R.id.children_step_price);

            stepLayout.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).load(stepItem.getImageUrl()).into(viewHolder.icon);
        viewHolder.parentTitle.setText(stepItem.getTitle());
        viewHolder.difficulty.setText(stepItem.getDifficulty());
        viewHolder.time.setText(stepItem.getTime());
        viewHolder.parentPrice.setText("$" + stepItem.getPrice());
        viewHolder.rating.setRating(stepItem.getRating());

        viewHolder.numChildrenSteps.setText(stepItem.getNumChildrenSteps());
        viewHolder.theChildrenSteps.setText(stepItem.getChildrenSteps());
        viewHolder.childrenPrice.setText("$"+stepItem.getChildrenPrice());

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.childrenLayout.setVisibility(View.VISIBLE);
            }
        });
        viewHolder.collapseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.childrenLayout.setVisibility(View.GONE);
            }
        });

        return stepLayout;
    }

    private static class ViewHolder {
        RelativeLayout parentLayout;
        ImageView icon;
        TextView parentTitle;
        TextView difficulty;
        TextView time;
        TextView parentPrice;
        RatingBar rating;
        RelativeLayout childrenLayout;

        RelativeLayout collapseLayout;
        TextView numChildrenSteps;
        TextView theChildrenSteps;
        TextView childrenPrice;





    }
}
