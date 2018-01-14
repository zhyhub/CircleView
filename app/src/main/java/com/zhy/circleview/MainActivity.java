package com.zhy.circleview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhy.circleview.adapter.MyFragmentAdapter;
import com.zhy.circleview.fragment.Fragment1;
import com.zhy.circleview.fragment.Fragment2;
import com.zhy.circleview.fragment.Fragment3;
import com.zhy.circleview.fragment.Fragment4;
import com.zhy.circleview.fragment.Fragment5;
import com.zhy.circleview.utils.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Fragment> urls = new ArrayList<>();

    private boolean mIsChanged = false;
    private int mCurrentPagePosition = FIRST_ITEM_INDEX;
    private static final int POINT_LENGTH = 3;
    private static final int FIRST_ITEM_INDEX = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        final ViewPager viewPager = findViewById(R.id.viewPager);

        urls.add(new Fragment5());
        urls.add(new Fragment1());
        urls.add(new Fragment2());
        urls.add(new Fragment3());
        urls.add(new Fragment4());
        urls.add(new Fragment5());
        urls.add(new Fragment1());

        FragmentManager manager = getSupportFragmentManager();
        MyFragmentAdapter adapter = new MyFragmentAdapter(manager, urls);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1,false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int currentPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                L.e(TAG,"onPageSelected currentPosition + " + currentPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // ViewPager.SCROLL_STATE_IDLE 标识的状态是当前页面完全展现，并且没有动画正在进行中，如果不
                // 是此状态下执行 setCurrentItem 方法回在首位替换的时候会出现跳动！
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    // 当视图在第一个时，将页面号设置为图片的最后一张。
                    if (currentPosition == 0) {
                        viewPager.setCurrentItem(urls.size() - 2, false);
                    } else if (currentPosition == urls.size() - 1) {
                        // 当视图在最后一个是,将页面号设置为图片的第一张。
                        viewPager.setCurrentItem(1, false);
                    }
                    L.e(TAG, "onPageScrollStateChanged currentPosition + " + currentPosition + " state + " + state);
                }
            }
        });
    }

}