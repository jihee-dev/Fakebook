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
import android.widget.ImageView;

import com.android.study.tablayout.Adapter.PostListViewAdapter;
import com.android.study.tablayout.Model.Post;
import com.android.study.tablayout.Model.PostDB;
import com.android.study.tablayout.R;
import com.android.study.tablayout.View.DividerDecoration;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private PostListViewAdapter adapter;
    private ArrayList<Post> list;

    @BindView(R.id.profile_back_img)
    ImageView backImg;

    @BindView(R.id.profile_list_view)
    RecyclerView recyclerView;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle(); // intent처럼 데이터 전달할 때
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);

        Glide.with(this.getActivity()).load(R.drawable.background_img_sample).into(backImg);

        list = PostDB.loadList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        adapter = new PostListViewAdapter(list, this.getActivity());

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerDecoration(this.getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.clean();
        adapter.addPosts(PostDB.loadList());
    }
}
