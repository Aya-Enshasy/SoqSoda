package com.lira_turkish.dollarstocks.dialog.switching;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.DialogSwitchingBinding;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;

import java.util.Objects;

public class SwitchingDialog extends Dialog {

    private DialogSwitchingBinding binding;
    private Context context;

    private Listener listener;

    public void setTitle(String name, int resource) {
        binding.tvName.setText(name);
        binding.ivIcon.setImageResource(resource);
    }

    public void setDetails(String details) {
  //      binding.swSwitcher.setText(details);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public SwitchingDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        //switching
        binding = DialogSwitchingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        if (binding.swSwitcher.isChecked()==true) {
            binding.swSwitcher.setChecked(AppPreferences.getInstance(getContext()).isNotificationOn());
            AppPreferences.getInstance(context).editor.putBoolean("isNoteficationActive", true);
        }  else {
            binding.swSwitcher.setChecked(AppPreferences.getInstance(getContext()).isNotificationOff());
            AppPreferences.getInstance(context).editor.putBoolean("isNoteficationActive", false);
        }
        binding.swSwitcher.setOnClickListener(view -> listener.onChangeSwitch(binding.swSwitcher.isChecked()));
    }

    public interface Listener {
        void onChangeSwitch(boolean b);
    }
}