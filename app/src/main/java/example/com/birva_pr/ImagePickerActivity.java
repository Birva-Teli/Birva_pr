package example.com.birva_pr;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.com.birva_pr.adapter.ImageAdapter;

import example.com.birva_pr.database.ImageDetailsBean;
import example.com.birva_pr.helpers.AppConstants;
import example.com.birva_pr.helpers.AppUtils;

public class ImagePickerActivity extends AppCompatActivity {

    @BindView(R.id.recyclerImageView)
    RecyclerView recyclerImageView;
    @BindView(R.id.btnSelect)
    Button btnSelect;
    private RecyclerView.LayoutManager layoutManager;
    private ImageAdapter imageAdapter;

    private ArrayList<ImageDetailsBean> imageDetailsBeans;
    int position=-1;
    File directory ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
        ButterKnife.bind(this);
        directory = getDir("template", Context.MODE_PRIVATE);
        recyclerImageView=(RecyclerView)findViewById(R.id.recyclerImageView);

        imageDetailsBeans = new ArrayList<>();
        imageAdapter = new ImageAdapter(getApplicationContext(), imageDetailsBeans);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerImageView.setHasFixedSize(true);
        recyclerImageView.setLayoutManager(layoutManager);
        recyclerImageView.setAdapter(imageAdapter);
        registerForContextMenu(recyclerImageView);
        addDataToAdapter(fetchImages());
    }

    private ArrayList<String> fetchImages() {

        ArrayList<String> filenames = new ArrayList<String>();
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            String file_name = files[i].getAbsolutePath().toString();
            // you can store name to arraylist and use it later
            filenames.add(file_name);
        }
        return filenames;
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return super.onContextItemSelected(item);
    }

    @OnClick(R.id.btnSelect)
    public void onViewClicked() {
        Log.d(AppConstants.AppName,"Select images");
        accessStoragePermission();

    }
    private void accessCameraPermission()
    {
        if ((ContextCompat.checkSelfPermission(ImagePickerActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.CAMERA},
                        AppConstants.CAMERA_PERMISSION_CODE);

            }
            else {
                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.CAMERA},
                        AppConstants.CAMERA_PERMISSION_CODE);
            }

        }
    }

    private void accessStoragePermission()
    {
        if ((ContextCompat.checkSelfPermission(ImagePickerActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        AppConstants.STORAGE_PERMISSION_CODE);
            }
            else {
                ActivityCompat.requestPermissions((Activity) this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        AppConstants.STORAGE_PERMISSION_CODE);

            }
        }
        else {
            Matisse.from(this)
                    .choose(MimeType.ofImage())
                    .countable(true)
                    .capture(true)
                    .captureStrategy(new CaptureStrategy(true, "example.com.birva_pr.fileprovider"))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .maxSelectable(50)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(AppConstants.REQUEST_CODE_CHOOSE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstants.STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                    accessCameraPermission();
                } else {
                    Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                    accessCameraPermission();
                }
                break;

            case AppConstants.CAMERA_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
                    Matisse.from(this)
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .capture(true)
                            .captureStrategy(new CaptureStrategy(true, "example.com.birva_pr.fileprovider"))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .maxSelectable(50)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .forResult(AppConstants.REQUEST_CODE_CHOOSE);

                }
                else {
                    Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<String> selectedImages=new ArrayList<>();
        if (requestCode == AppConstants.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

            if(Matisse.obtainPathResult(data).size()>0)
            {
               /* String timeStamp =
                        new SimpleDateFormat("yyyyMMdd_HHmmss",
                                Locale.getDefault()).format(new Date());
                String imageFileName = "IMG_" + timeStamp + "_";
                cropImage(Uri.parse(String.valueOf(Matisse.obtainPathResult(data))),Uri.parse(directory.getAbsolutePath()));*/
                for (String str:
                        Matisse.obtainPathResult(data)) {

                    String strImagePath=getImageUrlOfInternal(str);
                    if(strImagePath.equals(""))
                        continue;
                    selectedImages.add(strImagePath);
                }

                addDataToAdapter(selectedImages);
            }
        }
      /*  if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            final Uri resultUri = UCrop.getOutput(data);
            selectedImages.add(resultUri.toString());
            addDataToAdapter(selectedImages);
        }
        else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }*/
    }

  /*  private void cropImage(Uri uriSrc,Uri uriDest) {
        UCrop.of(uriSrc,uriDest)
                .withMaxResultSize(20,20)
                .withAspectRatio(10,10)
                .start(this);
    }*/


    private void addDataToAdapter(ArrayList<String> imagePaths){

        ArrayList<ImageDetailsBean> imageDetailsBeans1=new ArrayList<>();
        for (String str:
                imagePaths) {
            ImageDetailsBean imageDetailsBean = new ImageDetailsBean();
            //getImageUrlOfInternal(str);
            imageDetailsBean.setImage(str);
            imageDetailsBeans1.add(imageDetailsBean);
        }
        imageAdapter.addAll(imageDetailsBeans1);
    }

    private String getImageUrlOfInternal(String str) {

        String imageFilePath;

        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //cropImage(Uri.parse(str),Uri.parse(imageFileName));
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageFilePath = image.getAbsolutePath();
        try {
            copyFile(new File(str), new File(imageFilePath));
            return imageFilePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private boolean copyFile(File src,File dest) throws IOException{
        if(src.getAbsolutePath().toString().equals(dest.getAbsolutePath().toString()))
        {
            AppUtils.showLog("src="+src.getAbsolutePath()+"dest="+dest.getAbsolutePath());
            return true;
        }
        else {
            AppUtils.showLog("src="+src.getAbsolutePath()+"dest="+dest.getAbsolutePath());
            InputStream inputStream=new FileInputStream(src);
            OutputStream outputStream=new FileOutputStream(dest);
            byte[] buff=new byte[1024];
            int len;
            while ((len=inputStream.read(buff)) > 0 ){
                outputStream.write(buff,0,len);
            }
            inputStream.close();
            outputStream.close();
        }
        return true;
    }

}
