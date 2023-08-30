package com.lira_turkish.dollarstocks.dialog.admin;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;


import com.lira_turkish.dollarstocks.services.firebase.FirebaseMessaging;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.DialogAdminBinding;

import java.util.Objects;

public class AdminDialog extends Dialog {

    private DialogAdminBinding binding;
    private Context context;

    public AdminDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        //Admin
        binding = DialogAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);

        binding.send.setOnClickListener(view -> validate());
    }

    private void validate() {
        String title = Objects.requireNonNull(binding.title.getText()).toString().trim();
        String message = Objects.requireNonNull(binding.message.getText()).toString().trim();
        String link = Objects.requireNonNull(binding.link.getText()).toString().trim();

        if (title.isEmpty()) {
            binding.title.setText(context.getString(R.string.field_required));
            return;
        }

        if (message.isEmpty()) {
            binding.message.setText(context.getString(R.string.field_required));
            return;
        }

        if (link.isEmpty()) {
            binding.link.setText(context.getString(R.string.field_required));
            return;
        }


        new FirebaseMessaging().pushFCMNotification(context, title, message, link);
        dismiss();
    }
}