package com.android.study.tablayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.study.tablayout.View.FragmentViewPager;
import com.android.study.tablayout.Fragment.HomeFragment;
import com.android.study.tablayout.Adapter.MyPagerAdapter;
import com.android.study.tablayout.Fragment.NoticeFragment;
import com.android.study.tablayout.Model.PostDB;
import com.android.study.tablayout.Fragment.ProfileFragment;
import com.android.study.tablayout.R;
import com.android.study.tablayout.Fragment.SettingFragment;
import com.android.study.tablayout.Fragment.WatchFragment;
import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private long postId;
    private MyPagerAdapter pagerAdapter;

    @BindView(R.id.view_pager)
    FragmentViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.message_img)
    ImageView messageImg;

    @BindView(R.id.logo_img)
    ImageView logoImg;

    @BindView(R.id.post_edit_layout)
    LinearLayout postEditLayout;

    @BindView(R.id.sliding_panel)
    SlidingUpPanelLayout slidingPanel;

    @OnClick(R.id.search_img)
    void onSearchClick() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.message_img)
    void onMsgClick() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
        startActivity(intent);
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public MyPagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public void setPagerAdapter(MyPagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        // home, watch, profile, notice, setting
        pagerAdapter.addItem(HomeFragment.newInstance());
        pagerAdapter.addItem(WatchFragment.newInstance());
        pagerAdapter.addItem(ProfileFragment.newInstance());
        pagerAdapter.addItem(NoticeFragment.newInstance());
        pagerAdapter.addItem(SettingFragment.newInstance());

        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (checkAppFirstExecute()) {
            PostDB.createPost("작성자", "포스트 내용1", R.drawable.post_img_sample, false, 0);
            PostDB.createPost("작성자", "포스트 내용2", R.drawable.post_img_sample, false, 0);
            PostDB.createPost("작성자", "포스트 내용3", R.drawable.post_img_sample, false, 0);
        }

        slidingPanel.setPanelHeight(0);
        slidingPanel.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeSlidingPanel();
            }
        });
    }

    public boolean checkAppFirstExecute() {
        SharedPreferences preferences = getSharedPreferences("isFrist", Activity.MODE_PRIVATE);
        boolean isFirst = preferences.getBoolean("isFirst", false);
        if (!isFirst) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirst", true);
            editor.commit();
        }
        return !isFirst;
    }

    public void openSlidingPanel(long postId) {
        this.postId = postId;
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    public void closeSlidingPanel() {
        slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }
}