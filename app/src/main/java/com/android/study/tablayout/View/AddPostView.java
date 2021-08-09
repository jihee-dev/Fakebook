package com.android.study.tablayout.View;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.android.study.tablayout.Activity.AddPostActivity;
import com.android.study.tablayout.R;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPostView extends LinearLayout {
    private Context context;

    @OnClick(R.id.add_post_editText)
    void onAddClick() {
        Intent intent = new Intent(context, AddPostActivity.class);
        context.startActivity(intent);
    }

    public AddPostView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AddPostView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public AddPostView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public AddPostView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.post_add, this, false);
        addView(view);

        ButterKnife.bind(this, view);
    }
}
