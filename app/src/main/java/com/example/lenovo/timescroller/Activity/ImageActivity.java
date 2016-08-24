package com.example.lenovo.timescroller.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.Util.ImageLoaderUtil;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kevin.tian on 2016/8/16.
 */
public class ImageActivity extends BaseActivity {
    @InjectView(R.id.activity_image_im)
    ImageView image;
    public static final String PICTURE = "picture";
    public static final String TITLE = "title";
    String title;
    Bitmap bitmap;

    public static Intent startImageActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(BaseActivity.OBJECT_EXTRA, url);
        intent.putExtra(TITLE, title);
        return intent;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_image_layout;
    }

    @Override
    public void initData() {
        extra = getIntent().getStringExtra(BaseActivity.OBJECT_EXTRA);
        title = getIntent().getStringExtra(TITLE);
        ViewCompat.setTransitionName(image, PICTURE);
        //ImageLoaderUtil.loadImage(this,extra,image);
        Glide.with(this).load(extra).asBitmap().placeholder(R.drawable.kevin).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                bitmap = resource;
                image.setImageBitmap(resource);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                onSave();
                return true;
            case R.id.action_share:
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    void onSave() {
        File file = new File(Environment.getExternalStorageDirectory(), "Keming");
        String fileName = title.replace('/', '-') + ".jpg";
        File pic = new File(file,fileName);
        if (!file.exists()) {
           file.mkdir();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(pic);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(ImageActivity.this, getString(R.string.save_success), Toast.LENGTH_SHORT).show();
            Uri uri = Uri.fromFile(file);
            // 通知图库更新
            Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            sendBroadcast(scannerIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmap = null;
        image = null;
        ButterKnife.reset(this);
    }

    @Override
    public void initUI() {
        setToolBarTitle(getIntent().getStringExtra(TITLE));
    }
}
