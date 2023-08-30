package com.lira_turkish.dollarstocks.dialog.expectation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.DialogExpectationBinding;
import com.lira_turkish.dollarstocks.dialog.ad.AdDialog;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.feature.currency.fragments.expectation.ExpectationFragment;
import com.lira_turkish.dollarstocks.models.Expectation;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.util.ResourceUtil;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ExpectationDialog extends Dialog {

    private DialogExpectationBinding binding;
    private ExpectationFragment fragment;
    private String image = "";
    private Expectation expectation;
    private CropsDialog.Listener listener;
    private AdDialog.Listener imageListener;

    public void setListener(CropsDialog.Listener listener) {
        this.listener = listener;
    }

    public void setImageListener(AdDialog.Listener imageListener) {
        this.imageListener = imageListener;
    }

    public ExpectationDialog(@NonNull ExpectationFragment fragment, Expectation expectation, boolean isEdit) {
        super(fragment.requireContext());
        this.expectation = expectation;
        this.fragment = fragment;
        //expectation
        binding = DialogExpectationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.80);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);

        setData(isEdit);
        click(isEdit);
    }

    @SuppressLint("SetTextI18n")
    private void setData(boolean isEdit) {
        if (isEdit) {
            new ResourceUtil().loadImage(getContext(), expectation.getImg(), binding.image);
            binding.title.setText(expectation.getTitle());
            binding.content.setText(expectation.getContent());
            binding.tvName.setText(getContext().getString(R.string.update) + " " + expectation.getTitle());
            binding.update.setText(getContext().getString(R.string.update));
            image = expectation.getImg();
            binding.delete.setVisibility(View.VISIBLE);
        }
    }

    private void click(boolean isEdit) {
        binding.image.setOnClickListener(view -> imageListener.onSelectImage());
        binding.update.setOnClickListener(view -> {
            if (isEdit) {
                updateExpectation();
            } else {
                addExpectation();
            }
        });
        binding.delete.setOnClickListener(view -> deleteExpectation());
    }

    private void deleteExpectation() {
        listener.beforeAction();
        FirebaseFirestore.getInstance().collection(AppContent.EXPECTATION).document(expectation.getId()).delete()
                .addOnSuccessListener(unused -> {
                    listener.onAction();
                    dismiss();
                });
    }

    private void addExpectation() {
        String title = Objects.requireNonNull(binding.title.getText()).toString().trim();
        String content = Objects.requireNonNull(binding.content.getText()).toString().trim();

        if (title.isEmpty()) {
            binding.title.setError(getContext().getString(R.string.field_required));
            return;
        }

        if (content.isEmpty()) {
            binding.content.setError(getContext().getString(R.string.field_required));
            return;
        }
        listener.beforeAction();

        String id = FirebaseFirestore.getInstance().collection(AppContent.EXPECTATION).document().getId();
        expectation = new Expectation(id, image, title, content, new Timestamp(new Date(System.currentTimeMillis())));

        FirebaseFirestore.getInstance().collection(AppContent.EXPECTATION).document(id).set(expectation)
                .addOnSuccessListener(unused -> {
                    listener.onAction();
                    dismiss();
                });
    }

    private void updateExpectation() {
        String title = Objects.requireNonNull(binding.title.getText()).toString().trim();
        String content = Objects.requireNonNull(binding.content.getText()).toString().trim();

        if (title.isEmpty()) {
            binding.title.setError(getContext().getString(R.string.field_required));
            return;
        }

        if (content.isEmpty()) {
            binding.content.setError(getContext().getString(R.string.field_required));
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", expectation.getId());
        map.put("title", title);
        map.put("img", image);
        map.put("content", content);
        map.put("timestamp", new Timestamp(new Date(System.currentTimeMillis())));

        listener.beforeAction();
        FirebaseFirestore.getInstance().collection(AppContent.EXPECTATION).document(expectation.getId()).update(map)
                .addOnSuccessListener(unused -> {
                    listener.onAction();
                    dismiss();
                });
    }

    public void setImage(Bitmap bitmap) {
        binding.image.setImageBitmap(bitmap);
        uploadImage(bitmap);
    }

    private void uploadImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference reference = FirebaseStorage.getInstance().getReference().child(AppContent.DEI);
        reference.putBytes(data).continueWithTask(task -> reference.getDownloadUrl())
                .addOnCompleteListener(task -> {
                    fragment.getProgressDialog().dismiss();
                    image = task.getResult().toString();
                }).addOnFailureListener(e -> {
                    ToolUtil.showLongToast(e.getLocalizedMessage(), fragment.requireContext());
                    fragment.getProgressDialog().dismiss();
                });
    }

}