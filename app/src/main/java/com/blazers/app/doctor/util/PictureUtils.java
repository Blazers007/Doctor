package com.blazers.app.doctor.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;

/**
 * Created by Blazers on 15/5/28.
 */
public class PictureUtils {

    public static String compressBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        File temp = new File(path);
        File file = new File(path.substring(0, path.lastIndexOf(".")) + "_compressed.jpg");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 35, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            temp.delete();
            return path.substring(0, path.lastIndexOf(".")) + "_compressed.jpg";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }
}
