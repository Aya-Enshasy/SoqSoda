package com.lira_turkish.dollarstocks.dialog.news;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.DialogNewsBinding;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.services.firebase.FirebaseMessaging;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDialog extends Dialog {

    private DialogNewsBinding binding;
    private Context context;
    private CurrencyData data;
    private int state = 1;
    private CropsDialog.Listener listener;
    private boolean sendNotification;
    ApiInterface service;

    public void setListener(CropsDialog.Listener listener) {
        this.listener = listener;
    }

    public NewsDialog(@NonNull Context context, CurrencyData data) {
        super(context);
        this.context = context;
        this.data = data;
        //news
        binding = DialogNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        service = ApiClient.getRetrofit().create(ApiInterface.class);

        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);

        setData();
        click();
    }

    private void click() {
        binding.addOrUpdate.setOnClickListener(view -> updateSp());
        binding.upOrDown.setOnClickListener(view -> {
            if (state==1) {
                state =0;
                binding.upOrDown.setImageResource(R.drawable.ic_cursor_down);
            } else {
                state = 1;
                binding.upOrDown.setImageResource(R.drawable.ic_cursor_up);
            }
        });
        binding.notification.setOnClickListener(view -> {
            if (!sendNotification) {
                sendNotification = true;
                binding.notification.setImageResource(R.drawable.ic_notification_bing_svgrepo_com);
            } else {
                sendNotification = false;
                binding.notification.setImageResource(R.drawable.ic_notifications_off);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        binding.oldPrice.setText(context.getString(R.string.old_price) + data.getBuy());
        binding.tvName.setText(data.getCity());
    }


    private void updateSp() {
        String price = binding.value.getText().toString().trim();
        if (price.isEmpty()) {
            binding.value.setError(context.getString(R.string.field_required));
            return;
        }

        listener.beforeAction();

        HashMap<String, String> map = new HashMap<>();
        map.put("id", data.getId()+"");

        map.put("city", data.getCity());
        map.put("buy", price);
        map.put("shell", price);
        map.put("lbuy", price);
        map.put("lshell", price);
        map.put("state", String.valueOf(state));
        service.updateCurrency(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ToolUtil.showLongToast(context.getString(R.string.update_successfully), context);
                listener.onAction();
                if (sendNotification)
                    new FirebaseMessaging().pushFCMNotification(context, "تم تحديث سعر الصرف", ".", "");
                dismiss();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ToolUtil.showLongToast(t.getLocalizedMessage(), context);
                listener.onAction();
                dismiss();
            }
        });
    }
}