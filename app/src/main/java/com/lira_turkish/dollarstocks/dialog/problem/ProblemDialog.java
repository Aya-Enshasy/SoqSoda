package com.lira_turkish.dollarstocks.dialog.problem;

import android.app.Dialog;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;


import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.DialogProblemBinding;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.util.Objects;

public class ProblemDialog extends Dialog {

    private DialogProblemBinding binding;
    private BaseActivity baseActivity;

    public ProblemDialog(@NonNull BaseActivity baseActivity) {
        super(baseActivity);
        this.baseActivity = baseActivity;
        //problem
        binding = DialogProblemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = (int) (baseActivity.getResources().getDisplayMetrics().widthPixels * 0.80);
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);

        onClick();
    }

    private void onClick() {
        binding.btnNext.setOnClickListener(view -> {
            String problem = Objects.requireNonNull(binding.problem.getText()).toString().trim();
            if (problem.equals("Esam734")) {
                AppPreferences.getInstance(getContext()).setAdmin();
                ToolUtil.showLongToast(baseActivity.getString(R.string.be_admin), baseActivity);
                baseActivity.recreate();
            } else {
                ToolUtil.showLongToast(baseActivity.getString(R.string.send_problem), baseActivity);
            }
            dismiss();
        });
    }
}