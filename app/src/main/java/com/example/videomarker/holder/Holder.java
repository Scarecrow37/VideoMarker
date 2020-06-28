package com.example.videomarker.holder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.videomarker.R;
import com.example.videomarker.activity.PlayerActivity;

import java.security.AccessController;

public class Holder extends RecyclerView.ViewHolder{

    private TextView textName;
    private TextView textDur;
    private TextView textTag;
    //private TextView textId;
    public final ImageButton btnMore;


    public Holder(View itemView) {
        super(itemView);
        textName = (TextView) itemView.findViewById(R.id.textName);
        textDur = (TextView) itemView.findViewById(R.id.textDur);
        //textId = (TextView) itemView.findViewById(R.id.textView3);
        btnMore = (ImageButton) itemView.findViewById(R.id.btnMore);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int pos = getAdapterPosition();
//                if(pos != RecyclerView.NO_POSITION) {
//                    Intent intent1 = new Intent(context, PlayerActivity.class);
//                    intent1.putExtra("ID", id);
//                    context.startActivity(intent1);
//
//                }
//            }
//        });
    }
  
//    public String getId() {
//        return textId.getText().toString();
//    }
//    public void setId(String value) {
//        textId.setText(value);
//    }
    
    public String getName() {
        return textName.getText().toString();
    }
    public void setName(String value) {
        textName.setText(value);
    }
    public String getDur() {
        return textDur.getText().toString();
    }
    public void setDur(String value) {
        textDur.setText(value);
    }
}