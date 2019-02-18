package example.com.birva_pr.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import example.com.birva_pr.R;
import example.com.birva_pr.beans.ImageDetailsBean;

public class CustomSwipeAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ImageDetailsBean> imageDetailsBeans;


    public CustomSwipeAdapter(Context context,ArrayList<ImageDetailsBean> imageDetailsBeans) {
        this.context=context;
        this.imageDetailsBeans=imageDetailsBeans;
    }

    @Override
    public int getCount() {
        return imageDetailsBeans.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view==(RelativeLayout)o);
    }


    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.swipe_layout,container,false);

        final ImageView imageViewer=(ImageView) view.findViewById(R.id.image_view);
        final ImageDetailsBean imageDetailsBean = imageDetailsBeans.get(position);
        Glide.with(context)
                .load(new File(imageDetailsBean.getImage().toString()))
                .into(imageViewer);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
