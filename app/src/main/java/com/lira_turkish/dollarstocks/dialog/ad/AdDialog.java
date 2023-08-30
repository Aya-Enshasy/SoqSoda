package com.lira_turkish.dollarstocks.dialog.ad;

import android.app.Dialog;
import android.view.Gravity;
import android.view.WindowManager;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.DialogAdBinding;
import com.lira_turkish.dollarstocks.feature.main.model.GetAdsData;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.util.ResourceUtil;

import java.util.Objects;

public class AdDialog extends Dialog {

    private DialogAdBinding binding;
    private BaseActivity baseActivity;
    private GetAdsData data;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public AdDialog(BaseActivity activity, GetAdsData data) {
        super(activity);
        this.baseActivity = activity;
        this.data = data;
        //ad
        binding = DialogAdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = (int) (baseActivity.getResources().getDisplayMetrics().widthPixels * 0.80);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);

        setData();
        click();
    }

    private void setData() {
        try {
            new ResourceUtil().loadImage(baseActivity, data.getImage(), binding.image);
        } catch (Exception e) {
            e.printStackTrace();
        }}

    private void click() {
    }



    public interface Listener {
        void onSelectImage();

        void onUpdate();
    }
}