package com.lira_turkish.dollarstocks.dialog.image;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.DialogSelectImageBinding;
import com.lira_turkish.dollarstocks.utils.Photo.PhotoTakerManager;
import com.lira_turkish.dollarstocks.utils.listeners.RequestListener;
import com.lira_turkish.dollarstocks.utils.util.PermissionUtil;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.util.Objects;

public class SelectImageDialog extends BottomSheetDialogFragment implements RequestListener<Bitmap> {

    //
    private final int REQUEST_CAMERA = 1012;
    private final int REQUEST_STUDIO = 1013;

    private DialogSelectImageBinding binding;
    private PhotoTakerManager manager;
    private Listener listener;
    private int requestCode;

    //private ActivityResultLauncher<Intent> launcher;
    private Context context;
    private boolean isSelect;

    //, ActivityResultLauncher<Intent> launcher
    //        this.launcher = launcher;
    public SelectImageDialog(Context context) {
        this.context = context;
        isSelect = true;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public boolean isSelect() {
        return isSelect;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSelectImageBinding.inflate(inflater, container, false);
        click();
        return binding.getRoot();
    }

    private void click() {
        manager = new PhotoTakerManager(this, permission);
        binding.selectImageCamera.setOnClickListener(view -> onCameraClick());
        binding.selectImageAlbum.setOnClickListener(view -> onAlbumClick());
//        binding.remove.setOnClickListener(view -> listener.onRemoveImage());
    }

    private void onCameraClick() {
        if (PermissionUtil.isPermissionGranted(new String[]{Manifest.permission.CAMERA}, requireContext())) {
            requestCode = REQUEST_CAMERA;
            manager.cameraRequestLauncher(context, launcher);
        } else {
            permission.launch(new String[]{Manifest.permission.CAMERA});
        }
    }

    private void onAlbumClick() {
        requestCode = REQUEST_STUDIO;
        manager.galleryRequestLauncher(context, launcher);
    }

    @Override
    public void onResume() {
        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog()).getWindow().getAttributes();
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        getDialog().getWindow().setAttributes(params);
        super.onResume();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                dismiss();
                return true;
            } else return false;
        });
    }

    private final ActivityResultLauncher<String[]> permission = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                if (Boolean.TRUE.equals(result.get(Manifest.permission.CAMERA))) {
                    onCameraClick();
                }
            });

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(), this::setOnActivityResult);


    @Override
    public void onSuccess(Bitmap bitmap, String msg) {
        listener.OnImageLoad(bitmap);
    }

    @Override
    public void onError(String msg) {
        ToolUtil.showLongToast(msg, requireContext());
    }

    @Override
    public void onFail(String msg) {
        ToolUtil.showLongToast(msg, requireContext());
    }

    public void setOnActivityResult(ActivityResult result) {
        isSelect = false;
        if (result.getResultCode() == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_STUDIO:
                    manager.processGalleryPhoto(context, result.getData());
                    break;
                case REQUEST_CAMERA:
                    manager.processCameraPhoto(context);
                    break;
            }
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isSelect = false;
    }

    public interface Listener {
        void OnImageLoad(Bitmap bitmap);

        void onRemoveImage();
    }
}