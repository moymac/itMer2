///https://stackoverflow.com/questions/31844373/saving-edittext-content-in-recyclerview


package com.moymac.meritapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.moymac.meritapp.CustomItemClickListener;
import com.moymac.meritapp.Models.InputsItem;
import com.moymac.meritapp.R;

import java.util.List;

import jp.wasabeef.richeditor.RichEditor;


/**
 * Created by moymac on 12/10/17.
 */

public class InputsAdapterRVFixed extends RecyclerView.Adapter<InputsAdapterRVFixed.ViewHolder> {

    private Context mContext;
    private List<InputsItem> inputsList;
    CustomItemClickListener listener;
    //private String[] mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder{

       // public ImageView imageView;
        //public EditText editText;

        public RichEditor editor;
      //  public TextView timeTV;
      //  public VideoView videoView;
      //  public MyCustomEditTextListener myCustomEditTextListener;

        public ViewHolder(View itemView) {
            super(itemView);
       //     timeTV = itemView.findViewById(R.id.input_time_tv);
            editor = itemView.findViewById(R.id.editor);
            //editor.setEditorHeight(200);
           // editor.setEditorWidth(15);
            editor.setEditorFontSize(15);
            editor.setEditorFontColor(Color.DKGRAY);
            //mEditor.setEditorBackgroundColor(Color.BLUE);
            editor.setBackgroundColor(Color.WHITE);
            //mEditor.setBackgroundResource(R.drawable.bg);
            //editor.setPadding(10, 10, 10, 10);
            //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
            editor.setPlaceholder("...");
            //mEditor.setInputEnabled(false);
//            editText = itemView.findViewById(R.id.input_content_et);
//            this.myCustomEditTextListener = myCustomEditTextListener;
//            editText.addTextChangedListener(myCustomEditTextListener);
       //     imageView = itemView.findViewById(R.id.input_content_iv);
        //    videoView = itemView.findViewById(R.id.input_content_video);
            //editText.requestFocus();
        }


    }
    public InputsAdapterRVFixed(Context mContext, List<InputsItem> inputsList, CustomItemClickListener listener){
        this.mContext = mContext;
        this.inputsList = inputsList;
        this.listener = listener;
    }
    @Override
    public InputsAdapterRVFixed.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.input_card,parent,false);
        final ViewHolder mViewHolder = new InputsAdapterRVFixed.ViewHolder(itemView);
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
    public void onBindViewHolder(final InputsAdapterRVFixed.ViewHolder holder, int position) {
        InputsItem inputsItem = inputsList.get(position);
        int step = inputsItem.getStep();
        String content = inputsItem.getContent().toString();
        int cont_type = inputsItem.getContent_type();
       // Date creationTime = inputsItem.getCreationTime();
       // holder.timeTV.setText(creationTime.toString());
      //  holder.editText.setText(mDataset[holder.getAdapterPosition()]);


//        holder.editText.setVisibility(View.VISIBLE);
        holder.editor.setVisibility(View.VISIBLE);

        holder.editor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Log.e("touch", "en EditText");
                    listener.onItemClick(v, holder.getAdapterPosition());
                }
                return false;
            }
        });
//        holder.editText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    //Log.e("touch", "en EditText");
//                    listener.onItemClick(v, holder.getAdapterPosition());
//                }
//                return false;
//            }
//        });
//        holder.editText.setText(content.toString());
               // holder.editText.setText(content.toString());
            //    holder.imageView.setVisibility(View.GONE);
            //    holder.videoView.setVisibility(View.GONE);

//
//        if (cont_type == ){
//            holder.editText.setVisibility(View.VISIBLE);
//            holder.editText.setText(content.toString());
//            holder.imageView.setVisibility(View.GONE);
//            holder.videoView.setVisibility(View.GONE);
//            if(holder.getAdapterPosition()==inputsList.size()-1)holder.editText.requestFocus();
//
//            // update MyCustomEditTextListener every time we bind a new item
//            // so that it knows what item in mDataset to update
//            //holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
//          //  String temp = theInputObjectList[previousInput].toString();
//           // holder.editText.setText(temp);
//
//
//        }else if (content instanceof File) {
//            holder.editText.setVisibility(View.GONE);
//            holder.imageView.setVisibility(View.VISIBLE);
//            holder.videoView.setVisibility(View.GONE);
//
//            File f = (File) content;
//
//            String mimeType = URLConnection.guessContentTypeFromName(f.getAbsolutePath());
//            if (mimeType != null && mimeType.startsWith("image")) {
//                Bitmap myBitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
//                holder.imageView.setImageBitmap(myBitmap);
//            }
//            if (mimeType != null && mimeType.startsWith("video")) {
//                holder.videoView.setVideoPath(f.getAbsolutePath());
//            }
//
//        }

    }

    @Override
    public int getItemCount() {
        return inputsList.size();
    }


}
