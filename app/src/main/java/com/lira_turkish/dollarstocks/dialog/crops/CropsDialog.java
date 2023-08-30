package com.lira_turkish.dollarstocks.dialog.crops;

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
import com.lira_turkish.dollarstocks.databinding.DialogCropsBinding;
import com.lira_turkish.dollarstocks.dialog.ad.AdDialog;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.model.CropsData;
import com.lira_turkish.dollarstocks.models.Crops;
import com.lira_turkish.dollarstocks.models.CropsHistory;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.util.ResourceUtil;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CropsDialog extends Dialog {

    private DialogCropsBinding binding;
    private BaseActivity baseActivity;
    private AdDialog.Listener listener;
    private Listener mListener;
    private Crops crops;
    private boolean isEdit;
    private String state = "1";
    private String image;

    public CropsDialog(@NonNull BaseActivity baseActivity, Crops crops, boolean isEdit) {
        super(baseActivity);
        this.baseActivity = baseActivity;
        this.crops = crops;
        this.isEdit = isEdit;
        //crops
        binding = DialogCropsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = (int) (baseActivity.getResources().getDisplayMetrics().widthPixels * 0.80);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);

        setData();
        click();
    }

    public void setmListener(Listener mListener) {
        this.mListener = mListener;
    }

    public void setListener(AdDialog.Listener listener) {
        this.listener = listener;
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        if (isEdit) {
            new ResourceUtil().loadImage(baseActivity, crops.getImg(), binding.image);
            binding.tvName.setText(getContext().getString(R.string.update) + " " + crops.getName());
            state = crops.getState();
            image = crops.getImg();
            binding.name.setText(crops.getName());
            binding.value.setText(crops.getPrice());
            binding.currency.setText(crops.getCode());
            binding.delete.setVisibility(View.VISIBLE);
            binding.upOrDown.setImageResource(crops.getState().equals("1") ? R.drawable.ic_cursor_up : R.drawable.ic_cursor_down);
        } else {
            binding.addOrUpdate.setText(getContext().getString(R.string.add));
            binding.tvName.setText(getContext().getString(R.string.add_crops));
        }
    }

    private void click() {
        binding.image.setOnClickListener(view -> listener.onSelectImage());

        binding.addOrUpdate.setOnClickListener(view -> {
            if (isEdit) {
                editCrops();
            } else {
                addCrops();
            }
        });

        binding.upOrDown.setOnClickListener(view -> {
            if (state.equals("1")) {
                state = "0";
                binding.upOrDown.setImageResource(R.drawable.ic_cursor_down);
            } else {
                state = "1";
                binding.upOrDown.setImageResource(R.drawable.ic_cursor_up);
            }
        });

        binding.delete.setOnClickListener(view -> deleteCorps());
    }

    private void deleteCorps() {
        mListener.beforeAction();
        FirebaseFirestore.getInstance().collection(AppContent.CROPS).document(crops.getId()).delete()
                .addOnSuccessListener(unused -> {
                    mListener.onAction();
                    dismiss();
                });
    }

    private void editCrops() {
        String value = Objects.requireNonNull(binding.value.getText()).toString().trim();
        String name = Objects.requireNonNull(binding.name.getText()).toString().trim();
        String currency = Objects.requireNonNull(binding.currency.getText()).toString().trim();

        if (value.isEmpty()) {
            binding.value.setError(getContext().getString(R.string.field_required));
            return;
        }

        if (name.isEmpty()) {
            binding.name.setError(getContext().getString(R.string.field_required));
            return;
        }

        if (currency.isEmpty()) {
            binding.currency.setError(getContext().getString(R.string.field_required));
            return;
        }

        // listener.onActionCrops();

        Map<String, Object> map = new HashMap<>();
        map.put("id", crops.getId());
        map.put("name", name);
        map.put("img", image);
        map.put("price", value);
        map.put("code", currency);
        map.put("state", state);
        map.put("timestamp", crops.getTimestamp());

//        AppController.getInstance().getApi().updateCrops(map).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                ToolUtil.showLongToast(context.getString(R.string.update_successfully), context);
//                listener.onActionCrops();
//                //new NotificationUtil().pushFCMNotification(context, "تم تحديث سعر المحصول", ".");
//                dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                ToolUtil.showLongToast(t.getLocalizedMessage(), context);
//                listener.onActionCrops();
//                dismiss();
//            }
//        });
        mListener.beforeAction();
        FirebaseFirestore.getInstance().collection(AppContent.CROPS).document(crops.getId()).update(map)
                .addOnSuccessListener(unused -> {
                    addHistory();
                    mListener.onAction();
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
        baseActivity.getProgressDialog().show();
        StorageReference reference = FirebaseStorage.getInstance().getReference().child(AppContent.DEI);
        reference.putBytes(data).continueWithTask(task -> reference.getDownloadUrl())
                .addOnCompleteListener(task -> {
                    baseActivity.getProgressDialog().dismiss();
                    image = task.getResult().toString();
                }).addOnFailureListener(e -> {
                    ToolUtil.showLongToast(e.getLocalizedMessage(), baseActivity);
                    baseActivity.getProgressDialog().dismiss();
                });
    }

    private void addCrops() {
        String name = Objects.requireNonNull(binding.name.getText()).toString().trim();
        String value = Objects.requireNonNull(binding.value.getText()).toString().trim();
        String currency = Objects.requireNonNull(binding.currency.getText()).toString().trim();

        if (name.isEmpty()) {
            binding.name.setError(getContext().getString(R.string.field_required));
            return;
        }

        if (value.isEmpty()) {
            binding.value.setError(getContext().getString(R.string.field_required));
            return;
        }

        if (currency.isEmpty()) {
            binding.currency.setError(getContext().getString(R.string.field_required));
            return;
        }
        mListener.beforeAction();

        String id = FirebaseFirestore.getInstance().collection(AppContent.CROPS).document().getId();

        crops = new Crops(id, name, value, image, state, currency, new Timestamp(new Date(System.currentTimeMillis())));

        FirebaseFirestore.getInstance().collection(AppContent.CROPS).document(id).set(crops)
                .addOnSuccessListener(unused -> {
                    addHistory();
                    mListener.onAction();
                    dismiss();
                });

    }


    private void addHistory() {
        String id = FirebaseFirestore.getInstance().collection(AppContent.CROPS_HISTORY)
                .document(crops.getId()).collection(AppContent.HISTORY).document().getId();

        FirebaseFirestore.getInstance().collection(AppContent.CROPS_HISTORY)
                .document(crops.getId()).collection(AppContent.HISTORY)
                .document(id).set(new CropsHistory(id, crops.getPrice()
                        , new Timestamp(new Date(System.currentTimeMillis()))));

    }

    public interface Listener {
        void beforeAction();

        void onAction();
    }




}