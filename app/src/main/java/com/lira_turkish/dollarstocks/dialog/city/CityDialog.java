package com.lira_turkish.dollarstocks.dialog.city;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;


import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.DialogCityBinding;
import com.lira_turkish.dollarstocks.dialog.crops.CropsDialog;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.services.firebase.FirebaseMessaging;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityDialog extends Dialog {

    private DialogCityBinding binding;
    private Context context;
    private CurrencyData data;
    private int state;
    private boolean isEdit;
    private CropsDialog.Listener listener;
    private boolean sendNotification;
    int x;
    ApiInterface service1;

    public void setListener(CropsDialog.Listener listener) {
        this.listener = listener;
    }

    public CityDialog(@NonNull Context context, CurrencyData data, boolean isEdit) {
        super(context);
        this.context = context;
        this.data = data;
        this.isEdit = isEdit;
        //city
        binding = DialogCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        service1 = ApiClient.getRetrofit().create(ApiInterface.class);

        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);

        setData();
        click();

        if (data.getState()==1){
            x=1;
            Log.e("s",x+"");
        }else if (data.getState()==0){
            x=0;
            Log.e("s",x+"");
        }
    }

    private void click() {
        binding.upOrDown.setOnClickListener(view -> {
            if (state==1) {
                state =0;
                x=state;
                binding.upOrDown.setImageResource(R.drawable.ic_cursor_down);
            } else {
                state =1;
                x=state;
                binding.upOrDown.setImageResource(R.drawable.ic_cursor_up);
            }
        });
        binding.addOrUpdate.setOnClickListener(view -> {
            if (isEdit) {
                editCurrency();
            } else {
                addCurrency();
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

    private void addCurrency() {
        String buy = binding.buy.getText().toString().trim();
        String sell = binding.sell.getText().toString().trim();
        String city = binding.currency.getText().toString().trim();
        if (buy.isEmpty()) {
            binding.buy.setError(context.getString(R.string.field_required));
            return;
        }

        if (sell.isEmpty()) {
            binding.sell.setError(context.getString(R.string.field_required));
            return;
        }

        if (city.isEmpty()) {
            binding.currency.setError(context.getString(R.string.field_required));
            return;
        }
        listener.beforeAction();

        HashMap<String, String> map = new HashMap<>();
        map.put("city", city);
        map.put("buy", buy);
        map.put("shell", sell);
        map.put("state", String.valueOf(state));
        listener.onAction();
    }

    private void editCurrency() {
        String buy = binding.buy.getText().toString().trim();
        String sell = binding.sell.getText().toString().trim();

        if (buy.isEmpty()) {
            binding.buy.setError(context.getString(R.string.field_required));
            return;
        }

        if (sell.isEmpty()) {
            binding.sell.setError(context.getString(R.string.field_required));
            return;
        }

        listener.beforeAction();


        HashMap<String, String> map = new HashMap<>();
        map.put("id", data.getId()+"");
        map.put("city", data.getCity());
        map.put("buy", buy);
        map.put("shell", sell);
        map.put("lbuy", data.getBuy());
        map.put("lshell", data.getShell());
        map.put("state", x+"");

        service1.updateCurrency(map).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                ToolUtil.showLongToast(context.getString(R.string.update_successfully), context);
                listener.onAction();
                if (sendNotification)
                    new FirebaseMessaging().pushFCMNotification(context, "تم تحديث سعر الصرف" +
                            (data.getId() >= 34 ?  " في " + data.getCity() : ""), ".", "");
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

    private void setData() {
        if (isEdit) {
            binding.currency.setVisibility(View.GONE);
            binding.addOrUpdate.setText(context.getString(R.string.update));
        }
        binding.tvName.setText(data.getCity());
        binding.buy.setText(data.getBuy());
        binding.sell.setText(data.getShell());
//        state = data.getState();
        binding.upOrDown.setImageResource(data.getState()==1 ? R.drawable.ic_cursor_up : R.drawable.ic_cursor_down);
    }
}