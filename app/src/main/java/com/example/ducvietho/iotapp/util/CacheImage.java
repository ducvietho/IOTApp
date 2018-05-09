package com.example.ducvietho.iotapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.example.ducvietho.iotapp.screen.login.LoginActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ducvietho on 3/19/2018.
 */

public class CacheImage {
    private Context mContext;

    public CacheImage(Context context) {
        mContext = context;
    }
    public void imageDownload(String url){
        Picasso.with(mContext)
                .load(url)
                .into(getTarget(url));
    }


    public Target getTarget(final String url){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        File folder = new File(mContext.getCacheDir()+"/iot/");
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                        String urlRe = url.replaceAll("/","");
                        File file = new File(mContext.getCacheDir() + "/iot/"+urlRe );
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }
}
