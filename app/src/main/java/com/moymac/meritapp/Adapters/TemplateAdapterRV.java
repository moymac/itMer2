package com.moymac.meritapp.Adapters;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moymac.meritapp.ApiInterface;
import com.moymac.meritapp.CustomItemClickListener;
import com.moymac.meritapp.R;
import com.moymac.meritapp.Models.StepItem;
import com.moymac.meritapp.Models.Steps;
import com.moymac.meritapp.Models.TemplateItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moymac on 12/7/17.
 */
public class TemplateAdapterRV extends RecyclerView.Adapter<TemplateAdapterRV.ViewHolder> {
    private ApiInterface apiInterface;
    private Context mContext;
    private List<TemplateItem> templatesList;
    private List<StepItem> stepsList;
    private StepsAdapterRV stepsAdapter;
    private int lastPosition = -1;
    CustomItemClickListener listener;

    // private RecyclerView parentRecyclerView;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView title;
        public TextView author;
        public TextView description;
        public TextView difficulty;
        public RatingBar rating;
        public TextView price;
        public TextView time;
        public RelativeLayout mainTemplateLayout;

        public RecyclerView stepsLayout;

        public ViewHolder(View v) {
            super(v);

            icon =  v.findViewById(R.id.icon);
            title =  v.findViewById(R.id.titleTV);
            author = v.findViewById(R.id.authorTV);
            description =  v.findViewById(R.id.descriptionTV);
            difficulty =  v.findViewById(R.id.difficultyTV);
            price =  v.findViewById(R.id.priceTV);
            rating = v.findViewById(R.id.ratingBar);
            time = v.findViewById(R.id.timeTV);
            stepsLayout = v.findViewById(R.id.steps_list_view);
            mainTemplateLayout = v.findViewById(R.id.main_template_layout);
        }
    }

    // IMPLEMENT LATER ON!!
    public TemplateAdapterRV(Context mContext, List<TemplateItem> templatesList, CustomItemClickListener listener) {
        this.mContext = mContext;
        this.templatesList = templatesList;
        this.listener = listener;
       // this.parentRecyclerView = parentRecyclerView;
    }

    public TemplateAdapterRV(Context mContext, List<TemplateItem> templatesList) {
        this.mContext = mContext;
        this.templatesList = templatesList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TemplateAdapterRV.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        final ViewHolder mViewHolder = new TemplateAdapterRV.ViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });
        return new TemplateAdapterRV.ViewHolder(itemView);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder( TemplateAdapterRV.ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final TemplateItem templateItem = templatesList.get(position);
        holder.title.setText(templateItem.getTitle());
        holder.author.setText(templateItem.getAuthor());
        holder.description.setText(templateItem.getDescription());
        holder.rating.setRating(templateItem.getRating());
        holder.difficulty.setText(templateItem.getDifficulty());
        holder.price.setText("$" + String.valueOf(templateItem.getPrice()));
        holder.time.setText(templateItem.getTime());
        Picasso.with(mContext).load(templateItem.getImageUrl()).into(holder.icon);



        createApiInterface();
        setAnimation(holder.itemView,position);
        final TemplateAdapterRV.ViewHolder xholder = holder;
        holder.mainTemplateLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (xholder.stepsLayout.getVisibility() == View.GONE){
                    stepsList = new ArrayList<>();
                    stepsAdapter = new StepsAdapterRV(mContext,stepsList);

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
                    xholder.stepsLayout.setLayoutManager(mLayoutManager);
                    //theListView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                    xholder.stepsLayout.setItemAnimator(new DefaultItemAnimator());
                    xholder.stepsLayout.setAdapter(stepsAdapter);

                    xholder.stepsLayout.setVisibility(View.VISIBLE);
                    //parentRecyclerView.smoothScrollToPosition(0);
                    apiInterface.getSteps(templateItem.getId()).enqueue(stepsCallback);
                }else{
                    xholder.stepsLayout.setVisibility(View.GONE);
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return templatesList.size();
    }

    Callback<List<Steps>> stepsCallback = new Callback<List<Steps>>() {
        @Override
        public void onResponse(Call<List<Steps>> call, Response<List<Steps>> response) {
         //   progressBar.setVisibility(View.GONE);
            List<Integer> childrenStepsId = new ArrayList<>();
            List<String> childrenStepsName = new ArrayList<>();
            List<String> childrenStepsText = new ArrayList<>();
            List<Integer> childrenStepsType = new ArrayList<>();
            final List<Steps> responseList = response.body();
            Log.e("the call", call.toString());
            for(Steps step : responseList){
                if (step.getParent() != 0){
                    childrenStepsId.add(step.getId());
                    childrenStepsName.add(step.getName());
                    childrenStepsText.add(step.getText());
                    childrenStepsType.add(0); ///////CHANGE THIS PART WHEN MUSTEFA ADDS THE INPUT TYPE FIELD PLEASE. DO NOT IGNORE THIS COMMENT BECAUSE IT IS SUPER DUPER HIPER MEGA IMPORTANT, THE DEVELOPER, MEANING MOISES MUST CHANGE THIS
                }else{
                    stepsList.add(new StepItem("http://icons.iconarchive.com/icons/graphicloads/100-flat/256/home-icon.png",step.getName(),"HARD",3,1.0,"20 mins",childrenStepsId, childrenStepsName,childrenStepsText,childrenStepsType,0.99));

                }
                Log.e("id", ""+step.getId());
                Log.e("parent", ""+step.getParent());
                Log.e("name", ""+step.getName());
                Log.e("template", ""+step.getTemplate());
                Log.e("ordinal", ""+step.getOrdinal());
                Log.e("text", ""+step.getText());
                Log.e("owner", ""+step.getOwner());

            }
            stepsAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<List<Steps>> call, Throwable t) {
            //progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(mContext,"STEPS CALL FAILED",Toast.LENGTH_SHORT).show();
        }
    };
    private void createApiInterface() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }



}