package com.example.videomarker.ui.main.video;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.videomarker.R;
import com.example.videomarker.adapter.RecyclerAdapter;
import com.example.videomarker.data.entities.Data;
import com.example.videomarker.data.util.ContentLoader;

import java.util.List;

public class VideoFragment extends Fragment {

    private VideoViewModel videoViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        videoViewModel = ViewModelProviders.of(this).get(VideoViewModel.class);
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        Context context = v.getContext();

        List<Data> datas = ContentLoader.getContent(getActivity());
        RecyclerAdapter adapter = new RecyclerAdapter(datas, context);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        final SwipeRefreshLayout swipe_refresh = v.findViewById(R.id.recyclerViewRefresh);
//        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//                adapter.notifyDataSetChanged();
//
//                swipe_refresh.setRefreshing(false);
//            }
//        });

        return v;
    }
}
