package com.example.watchup;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageSaver {

    private Context context;

    public ImageSaver(Context context) {
        this.context = context;
    }

    public void saveImageToGallery(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = drawableToBitmap(drawable);

        // Save the bitmap to the gallery
        String savedImageURL = MediaStore.Images.Media.insertImage(
                context.getContentResolver(),
                bitmap,
                generateFileName(),
                "Image saved from WatchUp"
        );

        if (savedImageURL != null) {
            Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private String generateFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return "IMG_" + timeStamp + ".png";
    }
}
