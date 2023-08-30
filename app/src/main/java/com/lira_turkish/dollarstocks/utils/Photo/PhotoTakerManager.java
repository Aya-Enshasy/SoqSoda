package com.lira_turkish.dollarstocks.utils.Photo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.utils.listeners.RequestListener;
import com.lira_turkish.dollarstocks.utils.util.PermissionUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PhotoTakerManager {

    public final static String FILE_PROVIDER_AUTHORITY = "com.fajer.ls.provider";
    public final static String FILE_PROVIDER_PATH = "/Android/data/com.fajer.ls/files/Pictures";

    private RequestListener<Bitmap> listener;
    private Handler backgroundHandler;
    private Uri currentPhotoUri;
    private File currentPhotoFile;
    private ActivityResultLauncher<String[]> permission;

    public static double compressValue = 0.5;

    public PhotoTakerManager(RequestListener<Bitmap> listener, ActivityResultLauncher<String[]> permission) {
        this.listener = listener;
        this.permission = permission;
        HandlerThread handlerThread = new HandlerThread("Photos Processor");
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());
    }

    @Nullable
    private Intent getPhotoCameraIntent(Context context) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) == null) {
            return null;
        }
        currentPhotoFile = FileUtil.createImageFile(context);
        if (currentPhotoFile != null) {
            currentPhotoUri = FileProvider.getUriForFile(
                    context,
                    FILE_PROVIDER_AUTHORITY,
                    currentPhotoFile);

            // Grant access to content URI so camera app doesn't crash
            List<ResolveInfo> resolvedIntentActivities = context.getPackageManager()
                    .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);

            for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                String packageName = resolvedIntentInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, currentPhotoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                                | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri);
            return takePictureIntent;
        } else {
            return null;
        }
    }

    public void processCameraPhoto(Context context) {
        backgroundHandler.post(() -> {
            context.revokeUriPermission(currentPhotoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                Bitmap bitmap = ImageUtil.rotateImageIfRequired(context, currentPhotoFile, currentPhotoUri);
                //Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                if (bitmap == null) {
                    listener.onFail(context.getString(R.string.error_in_process_the_photo));
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> listener.onSuccess(resizeImage(bitmap), ""));
                    //                    Thread thread = new Thread(() -> );
                    //                    thread.start();
                }
            } catch (IOException e) {
                listener.onFail(context.getString(R.string.error_in_input));
            }
        });
    }

    @Nullable
    private Intent getPhotoGalleryIntent(Context context) {
        Intent galleryPictureIntent = new Intent(Intent.ACTION_PICK
                , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (galleryPictureIntent.resolveActivity(context.getPackageManager()) == null) {
            return null;
        }
        return galleryPictureIntent;
    }

    public void processGalleryPhoto(final Context context, final Intent data) {
        try {
            currentPhotoUri = data.getData();
            if (currentPhotoUri != null) {
                Bitmap bitmap;
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    ImageDecoder.Source source = ImageDecoder
                            .createSource(context.getContentResolver(), currentPhotoUri);
                    bitmap = ImageDecoder.decodeBitmap(source);
                    listener.onSuccess(resizeImage(bitmap), "");
                } else {
                    ImageView imageView = new ImageView(context);
                    imageView.setImageURI(currentPhotoUri);
                    try {
                        bitmap = getBitmapFromImageView(imageView);
                        listener.onSuccess(resizeImage(bitmap), "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                listener.onFail(context.getString(R.string.error_in_process_the_photo));
            }
        } catch (IOException e) {
            listener.onFail(context.getString(R.string.error_in_input));
        }
    }

    public void deleteLastTakenPhoto() {
        FileUtil.deleteCameraImageWithUri(currentPhotoUri);
    }

    @Nullable
    public Uri getCurrentPhotoUri() {
        return currentPhotoUri;
    }

    public void galleryRequestLauncher(Context context, ActivityResultLauncher<Intent> launcher) {
        Intent intent = getPhotoGalleryIntent(context);
        if (intent != null) {
            launcher.launch(intent);
        } else {
            listener.onFail(context.getString(R.string.error_in_input));
        }
    }

    public void cameraRequestLauncher(Context context, ActivityResultLauncher<Intent> launcher) {
        if (!PermissionUtil.isPermissionGranted(new String[]{Manifest.permission.CAMERA}, context)) {
            permission.launch(new String[]{Manifest.permission.CAMERA});
        } else {
            Intent intent = getPhotoCameraIntent(context);
            if (intent != null) {
                launcher.launch(intent);
            } else {
                listener.onFail(context.getString(R.string.error_in_input));
            }
        }
    }

    private Bitmap getBitmapFromImageView(ImageView imageView) {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }

    private static Bitmap resizeImage(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * compressValue)
                , (int) (bitmap.getHeight() * compressValue), true);
    }
}