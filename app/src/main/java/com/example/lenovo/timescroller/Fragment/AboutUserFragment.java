package com.example.lenovo.timescroller.Fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.timescroller.R;
import com.example.lenovo.timescroller.View.SimpleScroller;

/**
 * Created by kevin.tian on 2016/8/9.
 */
public class AboutUserFragment extends Fragment {
    private SimpleScroller myScroller;
    private Button layout;
    TextView textView;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.round_layout, null);
        ImageView view1 = (ImageView) view.findViewById(R.id.button1);
        Drawable drawable = getResources().getDrawable(R.drawable.timg);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        view1.setImageBitmap(getRoundShape(bitmap,430,3));
        return view;
    }

    private Bitmap getRoundShape(Bitmap sourceBitmap,float radius,int margin){
        int targetWidth = sourceBitmap.getWidth()-margin;
        int targetHeight = sourceBitmap.getHeight()-margin;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,targetHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(targetBitmap);
//        Path path = new Path();
//        path.addRoundRect(new RectF(margin,margin,targetWidth,targetHeight),radius,radius, Path.Direction.CW);
//        canvas.clipPath(path);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        canvas.drawRoundRect(new RectF(0,0,targetWidth,targetHeight),radius,radius,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        Bitmap scaleBitmap=sourceBitmap;
        canvas.drawBitmap(scaleBitmap,new Rect(0,0,scaleBitmap.getWidth(),scaleBitmap.getHeight()),new RectF(0,0,targetWidth,targetHeight),paint);
        return targetBitmap;
    }


}


