///https://stackoverflow.com/questions/31844373/saving-edittext-content-in-recyclerview


package com.moymac.meritapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.moymac.meritapp.CustomItemClickListener;
import com.moymac.meritapp.Models.InputsItem;
import com.moymac.meritapp.Models.Steps;
import com.moymac.meritapp.R;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by moymac on 12/10/17.
 */

public class InputsAdapterRV extends RecyclerView.Adapter<InputsAdapterRV.ViewHolder> {

    private Context mContext;
    private List<InputsItem> inputsList;
    CustomItemClickListener listener;
    //private String[] mDataset;
    private Object[] theInputObjectList;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public EditText editText;
      //  public TextView timeTV;
        public VideoView videoView;
      //  public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View itemView) {
            super(itemView);
       //     timeTV = itemView.findViewById(R.id.input_time_tv);
            editText = itemView.findViewById(R.id.input_content_et);
//            this.myCustomEditTextListener = myCustomEditTextListener;
//            editText.addTextChangedListener(myCustomEditTextListener);
            imageView = itemView.findViewById(R.id.input_content_iv);
            videoView = itemView.findViewById(R.id.input_content_video);
            //editText.requestFocus();
        }


    }
    public InputsAdapterRV(Context mContext, List<InputsItem> inputsList,Object[] theInputObjectList, CustomItemClickListener listener){
        this.mContext = mContext;
        this.inputsList = inputsList;
        this.listener = listener;
        this.theInputObjectList = theInputObjectList;
    }
    @Override
    public InputsAdapterRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.input_card,parent,false);
        final ViewHolder mViewHolder = new InputsAdapterRV.ViewHolder(itemView);
//
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  listener.onItemClick(v, mViewHolder.getAdapterPosition());
//            }
//        });

        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final InputsAdapterRV.ViewHolder holder, int position) {
        InputsItem inputsItem = inputsList.get(position);
        int step = inputsItem.getStep();
        Object content = inputsItem.getContent();
       // Date creationTime = inputsItem.getCreationTime();
       // holder.timeTV.setText(creationTime.toString());
      //  holder.editText.setText(mDataset[holder.getAdapterPosition()]);
        holder.editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.e("touch", "en EditText");
                    listener.onItemClick(v, holder.getAdapterPosition());
                }
                return false;
            }
        });
        if (content instanceof String){
            holder.editText.setVisibility(View.VISIBLE);
            holder.editText.setText(content.toString());
            holder.imageView.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.GONE);
            if(holder.getAdapterPosition()==inputsList.size()-1)holder.editText.requestFocus();

            // update MyCustomEditTextListener every time we bind a new item
            // so that it knows what item in mDataset to update
            //holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
          //  String temp = theInputObjectList[previousInput].toString();
           // holder.editText.setText(temp);


        }else if (content instanceof File) {
            holder.editText.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.GONE);

            File f = (File) content;

            String mimeType = URLConnection.guessContentTypeFromName(f.getAbsolutePath());
            if (mimeType != null && mimeType.startsWith("image")) {
                Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                holder.imageView.setImageBitmap(myBitmap);
            }
            if (mimeType != null && mimeType.startsWith("video")) {
                holder.videoView.setVideoPath(f.getAbsolutePath());
            }

        }

    }

    @Override
    public int getItemCount() {
        return inputsList.size();
    }

    // we make TextWatcher to be aware of the position it currently works with
    // this way, once a new item is attached in onBindViewHolder, it will
    // update current position MyCustomEditTextListener, reference to which is kept by ViewHolder
//    private class MyCustomEditTextListener implements TextWatcher {
//        private int position;
//
//        public void updatePosition(int position) {
//            this.position = position;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//            // no op
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//     //       theInputObjectList[position] =  charSequence.toString();
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            // no op
//        }
//    }


}
