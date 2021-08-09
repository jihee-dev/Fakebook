package com.android.study.tablayout.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.study.tablayout.Activity.MainActivity;
import com.android.study.tablayout.Model.Post;
import com.android.study.tablayout.Model.PostDB;
import com.android.study.tablayout.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class PostListViewAdapter extends RecyclerView.Adapter<PostListViewAdapter.ViewHolder> {
    private static final String TAG = PostListViewAdapter.class.getSimpleName();

    private ArrayList<Post> postList = null;

    private Context context = null;

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_text)
        TextView nameText;

        @BindView(R.id.post_text)
        TextView postText;

        @BindView(R.id.post_img)
        ImageView postImg;

        @BindView(R.id.like_text)
        TextView likeText;

        @BindView(R.id.like_count_text)
        TextView likeCountText;

        @BindView(R.id.like_small_icon)
        ImageView likeSmallIconImg;

        @BindView(R.id.post_more_img)
        ImageView postMoreImg;

        @OnClick(R.id.comment_layout)
        void onCommentClick() {
            Toast.makeText(context, "댓글을 달아보세요!", Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.share_text)
        void onShareClick() {
            Toast.makeText(context, "공유를 해 봅시다!", Toast.LENGTH_SHORT).show();
        }

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    public void setPostList(ArrayList<Post> postList) {
        this.postList = postList;
    }

    public PostListViewAdapter(Context context) {
        this.context = context;
        this.postList = new ArrayList<>();
    }

    public PostListViewAdapter(ArrayList<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.post_list, parent, false) ;
        PostListViewAdapter.ViewHolder viewHolder = new PostListViewAdapter.ViewHolder(view) ;

        return viewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Post post = postList.get(position);
        long postId = post.getId();

        viewHolder.nameText.setText(post.getName());
        viewHolder.postText.setText(post.getMsg());
        Glide.with(context).load(R.drawable.post_img_sample).into(viewHolder.postImg);

        viewHolder.likeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                    Post updatePost = Realm.getDefaultInstance()
                            .where(Post.class)
                            .equalTo("id", postId)
                            .findFirst();
                    @Override
                    public void execute(Realm realm) {
                        updatePost.setLike(!updatePost.isLike());
                        if (updatePost.isLike()) {
                            updatePost.setLikeCount(updatePost.getLikeCount() + 1);
                        } else {
                            updatePost.setLikeCount(updatePost.getLikeCount() - 1);
                        }
                        Realm.getDefaultInstance().insertOrUpdate(updatePost);
                    }
                });

                postList.get(position).setLike(!postList.get(position).isLike());
                notifyDataSetChanged();
            }
        });

        if (post.isLike()) {
            viewHolder.likeText.setTextColor(context.getColor(R.color.facebook));

        } else {
            viewHolder.likeText.setTextColor(Color.parseColor("#555555"));
        }

        if (post.getLikeCount() > 0) {
            viewHolder.likeSmallIconImg.setVisibility(View.VISIBLE);
            viewHolder.likeCountText.setVisibility(View.VISIBLE);
            viewHolder.likeCountText.setText("작성자");
        } else {
            viewHolder.likeSmallIconImg.setVisibility(View.GONE);
            viewHolder.likeCountText.setVisibility(View.GONE);
        }

        viewHolder.postMoreImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).openSlidingPanel(postId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.postList.size();
    }

    public void addPost(Post post) {
        postList.add(post);
        notifyDataSetChanged();
    }

    public void addPosts(ArrayList<Post> posts) {
        postList.addAll(posts);
        notifyDataSetChanged();
    }

    public void clean() {
        postList.clear();
        notifyDataSetChanged();
    }

    public void deletePostById(long id) {
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getId() == id) {
                postList.remove(i);
                // Toast.makeText(context, "리스트 제거 성공", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        // Toast.makeText(context, "deletePostById", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    public void editPostById(long id, String text) {
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).getId() == id) {
                postList.get(i).setMsg(text);
                // Toast.makeText(context, "리스트 수정 성공", Toast.LENGTH_SHORT).show();
                break;
            }
        }

        // Toast.makeText(context, "deletePostById", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }
}
