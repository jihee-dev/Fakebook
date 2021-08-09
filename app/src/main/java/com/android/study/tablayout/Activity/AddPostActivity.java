package com.android.study.tablayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.study.tablayout.Model.Post;
import com.android.study.tablayout.Model.PostDB;
import com.android.study.tablayout.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

public class AddPostActivity extends AppCompatActivity {
    private static final String TAG = AddPostActivity.class.getSimpleName();
    long id;
    Post findPost;
    String oldText;

    @BindView(R.id.post_profile_img)
    ImageView postProfileImg;

    @BindView(R.id.add_post_body)
    EditText addPostBody;

    @BindView(R.id.upload_text)
    TextView upLoadText;

    @OnClick(R.id.add_post_back_img)
    void onBackClick() {
        finish();
    }

    @OnClick(R.id.upload_text)
    void onUpLoadClick() {
        if (id == -1) { // 게시물 생성
            PostDB.createPost("한지희", addPostBody.getText().toString(), R.drawable.post_img_sample, false, 0);
            Toast.makeText(this, "게시 완료", Toast.LENGTH_SHORT).show();
        }

        else { // 게시물 수정
            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    findPost.setMsg(addPostBody.getText().toString());
                }
            });
            Toast.makeText(this, "수정 완료", Toast.LENGTH_SHORT).show();
        }


        finish();
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                if (id == -1) { // 게시물 생성
                    upLoadText.setTextColor(ContextCompat.getColor(AddPostActivity.this, R.color.facebook));
                    upLoadText.setClickable(true);
                } else if (oldText.equals(addPostBody.getText().toString())) { // 게시물 수정, 변경사항 x
                    upLoadText.setTextColor(ContextCompat.getColor(AddPostActivity.this, R.color.light_gray));
                    upLoadText.setClickable(false);
                } else { // 게시물 수정, 변경사항 o
                    upLoadText.setTextColor(ContextCompat.getColor(AddPostActivity.this, R.color.facebook));
                    upLoadText.setClickable(true);
                }
            }

            else {
                upLoadText.setTextColor(ContextCompat.getColor(AddPostActivity.this, R.color.light_gray));
                upLoadText.setClickable(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        ButterKnife.bind(this);

        // Glide.with(this).load(R.drawable.profile_img_sample).
        
        Glide.with(this)
                .load(R.drawable.profile_img_sample)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(postProfileImg);

        addPostBody.addTextChangedListener(textWatcher);

        Intent intent = new Intent(this.getIntent());
        this.id = intent.getLongExtra("id", -1);

        if (this.id != -1) { // 게시물 수정시 Text
            findPost = Realm.getDefaultInstance()
                    .where(Post.class)
                    .equalTo("id", id)
                    .findFirst();
            oldText = findPost.getMsg();
            addPostBody.setText(oldText);
        }
    }
}
