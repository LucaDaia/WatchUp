package com.example.watchup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageShare {

    private Context context;

    public ImageShare(Context context) {
        this.context = context;
    }

    public void shareImage(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        // Save the bitmap to a temporary file
        File imageFile = saveBitmapToFile(bitmap);

        if (imageFile != null) {
            // Create a content URI for the temporary file
            String authority = context.getPackageName() + ".fileprovider";
            String imagePath = imageFile.getAbsolutePath();
            Uri imageUri = FileProvider.getUriForFile(context, authority, new File(imagePath));

            // Create a share intent
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            // Grant temporary read permission to the content URI
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Start the share activity
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            Toast.makeText(context, "Failed to share image", Toast.LENGTH_SHORT).show();
        }
    }

    private File saveBitmapToFile(Bitmap bitmap) {
        File imageFile = null;
        try {
            File cacheDir = context.getCacheDir();
            imageFile = new File(cacheDir, "shared_image.png");
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }
}
