package com.example.videomarker.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videomarker.Listener.ClickListener;
import com.example.videomarker.R;
import com.example.videomarker.activity.InfoActivity;
import com.example.videomarker.data.entities.Data;
import com.example.videomarker.data.util.ContentLoader;
import com.example.videomarker.holder.Holder;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<Holder> implements ClickListener {

    private List<Data> datas;
    public final Context context;
    private Uri contentUri;
    private String id;
    private String name;


    public RecyclerAdapter(List<Data> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        Data data = datas.get(position);
        //holder.setId(String.valueOf(data.getResId()));
        holder.setName(data.getName());
        holder.setDur(data.getDur());
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick(view, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    @Override
    public void onItemClick(View v, int position) {
        List<Data> datas = ContentLoader.getContent(context);

        id = String.valueOf(datas.get(position).getResId());
        contentUri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id.toString());
        name = datas.get(position).getName();
        PopupMenu p = new PopupMenu(context, v);
        MenuInflater inflater = p.getMenuInflater();
        Menu menu = p.getMenu();
        inflater.inflate(R.menu.popup_menu, menu);

        p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.pPlay:
                        break;
                    case R.id.pInfo:
                        Intent intent = new Intent(context, InfoActivity.class);
                        intent.putExtra("ID", id);
                        context.startActivity(intent);
                        break;
                    case R.id.pAddpl:
                        break;
                    case R.id.pDel:
                        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("삭제");
                        builder.setMessage(name + "파일을 삭제하시겠습니까?");
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ContentLoader.deleteContent(context, contentUri, id);
                                notifyDataSetChanged();
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                }
                return false;
            }
        });
        p.show();
    }
}
