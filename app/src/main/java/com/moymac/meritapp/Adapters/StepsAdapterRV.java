package com.moymac.meritapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moymac.meritapp.Composing;
import com.moymac.meritapp.R;
import com.moymac.meritapp.Models.StepItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moymac on 12/7/17.
 */
public class StepsAdapterRV extends RecyclerView.Adapter<StepsAdapterRV.ViewHolder> {

    private Context mContext;
    private List<StepItem> stepsList;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView difficulty;
        public RatingBar rating;
        public TextView price;
        public TextView time;
        public RelativeLayout parentStepLayout;

        public RelativeLayout childrenStepsLayout;
        public RelativeLayout collapseLayout;
        public TextView numChildrenSteps;
        public TextView theChildrenSteps;
        public TextView childrenPrice;

        public RelativeLayout startWritingLayout;


        public ViewHolder(View v) {
            super(v);

            icon =  v.findViewById(R.id.parent_step_icon);
            title =  v.findViewById(R.id.parent_step_title);
            difficulty =  v.findViewById(R.id.parent_step_difficulty);
            price =  v.findViewById(R.id.parent_step_price);
            rating = v.findViewById(R.id.parent_step_ratingBar);
            time = v.findViewById(R.id.parent_step_time);
            parentStepLayout = v.findViewById(R.id.parent_step_layout);
            childrenStepsLayout = v.findViewById(R.id.children_steps_layout);

            collapseLayout = v.findViewById(R.id.collapse_layout);
            numChildrenSteps = v.findViewById(R.id.numberof_children_steps);
            theChildrenSteps = v.findViewById(R.id.the_children_steps);
            childrenPrice = v.findViewById(R.id.children_step_price);
            startWritingLayout = v.findViewById(R.id.start_writing_children_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public StepsAdapterRV(Context mContext, List<StepItem> stepsList) {
        this.mContext = mContext;
        this.stepsList = stepsList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StepsAdapterRV.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_layout, parent, false);

        return new StepsAdapterRV.ViewHolder(itemView);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final StepsAdapterRV.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final StepItem stepItem = stepsList.get(position);

        String allTheSteps = "";
        for (int i =0;i< stepItem.getChildrenStepsName().size();i++){
            allTheSteps = allTheSteps + stepItem.getChildrenStepsName().get(i) + '\n'+ stepItem.getChildrenStepsText().get(i) + "\n\n";
        }

        holder.title.setText(stepItem.getTitle());
        holder.rating.setRating(stepItem.getRating());
        holder.difficulty.setText(stepItem.getDifficulty());
        holder.price.setText("$" + String.valueOf(stepItem.getPrice()));
        holder.time.setText(stepItem.getTime());
        Picasso.with(mContext).load(stepItem.getImageUrl()).into(holder.icon);

        holder.childrenPrice.setText("$"+stepItem.getChildrenPrice());
        holder.theChildrenSteps.setText(allTheSteps);
        holder.numChildrenSteps.setText(stepItem.getChildrenStepsName().size()+" STEPS TO CREATE A SUBJECT");

        holder.startWritingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mContext, Composing.class);
                myIntent.putExtra("parentTitle", stepItem.getTitle());
                myIntent.putIntegerArrayListExtra("childIds", (ArrayList<Integer>) stepItem.getChildrenStepsId());
                myIntent.putStringArrayListExtra("childNames", (ArrayList<String>) stepItem.getChildrenStepsName());
                myIntent.putStringArrayListExtra("childTexts", (ArrayList<String>) stepItem.getChildrenStepsText());
                myIntent.putIntegerArrayListExtra("childTypes", (ArrayList<Integer>) stepItem.getChildrenStepsType());
                mContext.startActivity(myIntent);
            }
        });
        holder.collapseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.childrenStepsLayout.setVisibility(View.GONE);

            }
        });

        holder.parentStepLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.childrenStepsLayout.getVisibility() == View.GONE){
                    holder.childrenStepsLayout.setVisibility(View.VISIBLE);
                }else{
                    holder.childrenStepsLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return stepsList.size();
    }


}