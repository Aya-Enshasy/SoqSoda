package com.lira_turkish.dollarstocks.dialog.wait;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.FragmentWaitBinding;

public class WaitDialog extends Dialog {

    public WaitDialog(Context context) {
        super(context);
        FragmentWaitBinding binding = FragmentWaitBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setCancelable(false);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);
    }
}
