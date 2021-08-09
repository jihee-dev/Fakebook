package com.android.study.tablayout.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.study.tablayout.Adapter.PostListViewAdapter;
import com.android.study.tablayout.Model.Post;
import com.android.study.tablayout.Model.PostDB;
import com.android.study.tablayout.R;
import com.android.study.tablayout.View.DividerDecoration;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private PostListViewAdapter adapter;

    private ArrayList<Post> list;

    @BindView(R.id.home_list_view)
    RecyclerView recyclerView;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle(); // intent처럼 데이터 전달할 때
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public PostListViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PostListViewAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        adapter = new PostListViewAdapter(this.getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerDecoration(this.getActivity()));

        loadPost();

        return view;
    }

    private void loadPost() {
        adapter.clean();
        adapter.addPosts(PostDB.loadList());
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPost();
    }
}
