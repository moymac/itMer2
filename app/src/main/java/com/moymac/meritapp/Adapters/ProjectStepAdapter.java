package com.moymac.meritapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.moymac.meritapp.CustomItemClickListener;
import com.moymac.meritapp.Models.Steps;
import com.moymac.meritapp.R;

import java.util.List;


/**
 * Created by moymac on 12/9/17.
 */

public class ProjectStepAdapter extends RecyclerView.Adapter<ProjectStepAdapter.ViewHolder> {

    private Context mContext;
    private List<Steps> stepItemList;
    CustomItemClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleTV;
        public TextView textTV;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.step_title);
            textTV = itemView.findViewById(R.id.step_text);
        }


    }
    public ProjectStepAdapter(Context mContext, List<Steps> stepItemList, CustomItemClickListener listener){
        this.mContext = mContext;
        this.stepItemList = stepItemList;
        this.listener = listener;
    }
    @Override
    public ProjectStepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_step_card,parent,false);
        final ViewHolder mViewHolder = new ProjectStepAdapter.ViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ProjectStepAdapter.ViewHolder holder, int position) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int itemwidth = deviceWidth - (deviceWidth/100 * 10);

        holder.titleTV.setWidth(itemwidth);
        holder.textTV.setWidth(itemwidth);

        Steps stepItem = stepItemList.get(position);
        holder.titleTV.setText(stepItem.getName());
        holder.textTV.setText(stepItem.getText());
    }

    @Override
    public int getItemCount() {
        return stepItemList.size();
    }
}
