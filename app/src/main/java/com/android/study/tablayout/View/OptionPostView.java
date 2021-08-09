package com.android.study.tablayout.View;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.study.tablayout.Activity.AddPostActivity;
import com.android.study.tablayout.Activity.MainActivity;
import com.android.study.tablayout.Fragment.HomeFragment;
import com.android.study.tablayout.Model.Post;
import com.android.study.tablayout.Model.PostDB;
import com.android.study.tablayout.R;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class OptionPostView extends LinearLayout {
    private Context context;

    @OnClick(R.id.edit_post_layout)
    void onEditPostClick() {
        long postId = ((MainActivity)context).getPostId();

        Intent intent = new Intent(context, AddPostActivity.class);
        intent.putExtra("id", postId);
        context.startActivity(intent);

        Toast.makeText(context, "게시물 수정", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.delete_post_layout)
    void onDeletePostClick() {
        long postId = ((MainActivity)context).getPostId();

        PostDB.deletePost(postId);

        /*Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            Post deletePost = Realm.getDefaultInstance()
                    .where(Post.class)
                    .equalTo("id", postId)
                    .findFirst();
            @Override
            public void execute(Realm realm) {
                deletePost.deleteFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {

            @Override
            public void onSuccess() {
                ((HomeFragment)((MainActivity)context).getPagerAdapter().getItem(0)).getAdapter().deletePostById(postId);
                Toast.makeText(context, "삭제 성공", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {

            @Override
            public void onError(Throwable error) {
                Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show();
            }
        });*/

        ((HomeFragment)((MainActivity)context).getPagerAdapter().getItem(0)).getAdapter().deletePostById(postId);
        ((MainActivity)context).closeSlidingPanel();
    }

    public OptionPostView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OptionPostView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public OptionPostView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public OptionPostView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.post_option, this, false);
        addView(view);

        ButterKnife.bind(this, view);
    }
}
