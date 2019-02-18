package example.com.birva_pr.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import example.com.birva_pr.R;
import example.com.birva_pr.activities.ImageViewPagerActivity;
import example.com.birva_pr.beans.ImageDetailsBean;
import example.com.birva_pr.helpers.AppConstants;
import example.com.birva_pr.helpers.AppUtils;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private ArrayList<ImageDetailsBean> imageDetailsBeans;
    private ArrayList<Integer> selectedItems = new ArrayList<Integer>();
    private boolean multiSelect = false;
    ImageViewHolder.BirvaActionMode primaryActionModeCallback;

    public ImageAdapter(Context context, ArrayList<ImageDetailsBean> imageDetailsBeans) {

        this.context = context;
        this.imageDetailsBeans = imageDetailsBeans;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iamge_view_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        final ImageDetailsBean imageDetailsBean = imageDetailsBeans.get(position);
        Log.d(AppConstants.AppName, imageDetailsBean.getImage().toString() + (position)+"images");
        ImageView imageView = holder.imageViewAlbum;

        Glide.with(context)
                .load(Uri.fromFile(new File(imageDetailsBean.getImage().toString())))
                .into(imageView);

        if(selectedItems.contains(position)){
            holder.ibChkBtn.setVisibility(View.VISIBLE);
            holder.linearLayout.setBackgroundColor(Color.LTGRAY);
        }
        else {
            holder.ibChkBtn.setVisibility(View.INVISIBLE);
            holder.linearLayout.setBackgroundColor(Color.BLACK);

        }
    }

    public void addImage(ImageDetailsBean image, int position) {
        imageDetailsBeans.add(image);
        notifyItemInserted(imageDetailsBeans.size() - 1);
    }

    public void addAll(ArrayList<ImageDetailsBean> images) {
        imageDetailsBeans.addAll(images);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return imageDetailsBeans.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageViewAlbum;
        LinearLayout linearLayout;
        ImageButton ibChkBtn;


        public ImageViewHolder(final View itemView) {
            super(itemView);
            imageViewAlbum = itemView.findViewById(R.id.imageViewAlbum);
            ibChkBtn = itemView.findViewById(R.id.ibChkBtn);
            imageViewAlbum.setOnClickListener(this);
            ibChkBtn.setOnClickListener(this);
            linearLayout = itemView.findViewById(R.id.llImageView);
            ibChkBtn = itemView.findViewById(R.id.ibChkBtn);
            imageViewAlbum.setOnLongClickListener(myLongClickListener);
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imageViewAlbum:
                case R.id.ibChkBtn:
                    if(selectedItems.size()>0){
                        selectItem(getAdapterPosition());
                        AppUtils.showLog("selected items"+(getAdapterPosition()));
                        AppUtils.showLog(" size"+selectedItems.size());
                        return;
                    }
                    Intent i=new Intent(context,ImageViewPagerActivity.class);
                    i.putParcelableArrayListExtra(AppConstants.SELECTED_IMAGES,imageDetailsBeans);
                    i.putExtra(AppConstants.SELECTED_IMAGE_POSITION,getAdapterPosition());
                    context.startActivity(i);
                    break;
            }
        }

        void selectItem(Integer item) {

            if (multiSelect) {
                if (selectedItems.contains(item)) {
                    selectedItems.remove(item);
                    if(selectedItems.size()==0) {
                        primaryActionModeCallback.finishActionMode();
                    }
                }
                else {
                    selectedItems.add(item);
                }

                if(selectedItems.size()==0){
                    imageViewAlbum.setOnClickListener(this);
                    ibChkBtn.setOnClickListener(this);
                }
            }
            notifyItemChanged(item);
        }

        public class BirvaActionMode implements ActionMode.Callback{


            private ActionMode myMode;
            private int menuResId;
            private String title;
            private String subtitle;
            private ImageView imageViewAlbum;

            public BirvaActionMode(ImageView imageViewAlbum) {
                this.imageViewAlbum = imageViewAlbum;
            }


            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                myMode = mode;
                myMode.getMenuInflater().inflate(menuResId, menu);
                myMode.setTitle(title);
                myMode.setSubtitle(subtitle);
                multiSelect = true;
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                Collections.sort(selectedItems);
                Collections.reverse(selectedItems);


                for (int i=0;i<selectedItems.size();i++)
                {
                    int j=selectedItems.get(i);
                    AppUtils.showLog(j+"removed");
                    File file=new File(imageDetailsBeans.get(j).getImage());
                    file.delete();
                    if(!file.exists()){
                        imageDetailsBeans.remove(j);
                    }
                }

                AppUtils.showLog("images  "+imageDetailsBeans.size()+" selected items"+selectedItems.size());
                notifyDataSetChanged();
                mode.finish();
                AppUtils.showLog("images  "+imageDetailsBeans.size()+" selected items"+selectedItems.size());
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                selectedItems.clear();
                imageViewAlbum.setOnLongClickListener(myLongClickListener);
                notifyDataSetChanged();
                AppUtils.showLog("images  "+imageDetailsBeans.size()+" selected items"+selectedItems.size());
            }

            private void startActionMode(View view,
                                         int menuResId,
                                         String title,
                                         String subtitle) {
                this.menuResId = menuResId;
                this.title = title;
                this.subtitle = subtitle;
                view.startActionMode(this);
            }

            private void  finishActionMode() {
                myMode.finish();
            }
        }


        private View.OnLongClickListener myLongClickListener=new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                primaryActionModeCallback = new BirvaActionMode(imageViewAlbum);
                primaryActionModeCallback.startActionMode(view, R.menu.context_menu, "Title", "Subtitle");
                selectItem(getAdapterPosition());
                AppUtils.showLog("selected items"+(getAdapterPosition()));
                imageViewAlbum.setOnLongClickListener(null);
                return true;
            }
        };
    }
}
