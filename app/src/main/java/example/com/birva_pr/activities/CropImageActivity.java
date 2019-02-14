package example.com.birva_pr.activities;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.system.ErrnoException;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.birva_pr.BuildConfig;
import example.com.birva_pr.R;
import example.com.birva_pr.helpers.AppConstants;
import example.com.birva_pr.helpers.AppUtils;

public class CropImageActivity extends AppCompatActivity {

    @BindView(R.id.btnSelectImage)
    Button btnSelectImage;
    @BindView(R.id.ivSelectedImage)
    ImageView ivSelectedImage;
    private Uri mCropImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSelectImage)
    public void onViewClicked(){
        Crop.pickImage(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){
            if(requestCode==Crop.REQUEST_PICK)
            {
                Uri srcUri=data.getData();
                String Path = Environment.getExternalStorageDirectory().getPath().toString()+ "/images";
                File FPath = new File(Path);
                //Uri destUri=Uri.fromFile(FPath);
               // Uri destUri=Uri.fromFile(new File(getCacheDir(),"cropped"));
                Crop.of(srcUri,Uri.parse(String.valueOf(FPath))).asSquare().start(this);
                AppUtils.showLog(srcUri +" djg "+FPath);
            }
            else if(requestCode==Crop.REQUEST_CROP){
                handle_crop(resultCode,data);
            }
        }
    }

    private void handle_crop(int Code, Intent result) {
        if(Code==RESULT_OK){
            Glide.with(this)
                    .load(new File(Crop.getOutput(result).toString()))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(ivSelectedImage);
            AppUtils.showLog("glide 1"+Crop.getOutput(result));
        }
        else if(Code==Crop.RESULT_ERROR){
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }
}
