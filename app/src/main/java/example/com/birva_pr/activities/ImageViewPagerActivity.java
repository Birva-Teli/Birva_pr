package example.com.birva_pr.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.birva_pr.R;
import example.com.birva_pr.adapter.CustomSwipeAdapter;
import example.com.birva_pr.beans.ImageDetailsBean;
import example.com.birva_pr.helpers.AppConstants;

public class ImageViewPagerActivity extends AppCompatActivity {

    ViewPager viewPager;
    CustomSwipeAdapter customSwipeAdapter;
    @BindView(R.id.vpImage)
    ViewPager imageViewPager;
    @BindView(R.id.btnLeftArrow)
    ImageButton btnLeftArrow;
    @BindView(R.id.btnRightArrow)
    ImageButton btnRightArrow;
    @BindView(R.id.tvImageCount)
    TextView tvImageCount;
    private ArrayList<ImageDetailsBean> imageDetailsBeans;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_pager);
        ButterKnife.bind(this);
        viewPager = (ViewPager) findViewById(R.id.vpImage);
        imageDetailsBeans = new ArrayList<>();
        imageDetailsBeans = getIntent().getParcelableArrayListExtra(AppConstants.SELECTED_IMAGES);
        customSwipeAdapter = new CustomSwipeAdapter(this, imageDetailsBeans);
        viewPager.setAdapter(customSwipeAdapter);
        selectedPosition=getIntent().getIntExtra(AppConstants.SELECTED_IMAGE_POSITION, 0);
        viewPager.setCurrentItem(selectedPosition);
        tvImageCount.setText((selectedPosition+1)+"/"+imageDetailsBeans.size());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                tvImageCount.setText((i+1)+"/"+imageDetailsBeans.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick({R.id.btnLeftArrow, R.id.btnRightArrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLeftArrow:
                int currentItemId = viewPager.getCurrentItem();
                Log.d(AppConstants.AppName,"currentId"+currentItemId);
                if (currentItemId > 0) {
                    currentItemId--;
                    viewPager.setCurrentItem(currentItemId);
                } else {
                    viewPager.setCurrentItem(currentItemId);
                }
                break;
            case R.id.btnRightArrow:
                int currentItem = viewPager.getCurrentItem();
                if (!(imageDetailsBeans.size() == currentItem + 1)) {
                    currentItem++;
                    viewPager.setCurrentItem(currentItem);
                }
                break;
        }
    }
}