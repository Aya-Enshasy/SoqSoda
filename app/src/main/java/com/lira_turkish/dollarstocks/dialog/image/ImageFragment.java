package com.lira_turkish.dollarstocks.dialog.image;//package com.fajer.dei.dialog.image;
//
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//
//import androidx.fragment.app.DialogFragment;
//
//import com.fajer.dei.R;
//import com.fajer.dei.databinding.FragmentImageBinding;
//
//import java.util.Objects;
//
//public class ImageFragment extends DialogFragment {
//
//    private FragmentImageBinding binding;
//    private static final String IMG = "img";
//
//    public static ImageFragment newInstance(Bitmap bitmap) {
//        ImageFragment fragment = new ImageFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(IMG, bitmap);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    private ImageFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentImageBinding.inflate(getLayoutInflater());
//        data();
//        return binding.getRoot();
//    }
//
//    private void data() {
//        if (getArguments() != null && getArguments().getParcelable(IMG) != null) {
//            binding.ivImage.setImageBitmap(getArguments().getParcelable(IMG));
//            binding.ivImage.zoomTo(1, 1000L);
//        }
//        binding.back.setOnClickListener(view -> dismiss());
//    }
//
//    @Override
//    public void onResume() {
//        WindowManager.LayoutParams params = Objects.requireNonNull(getDialog())
//                .getWindow().getAttributes();
//        params.width = getResources().getDisplayMetrics().widthPixels;
//        params.height = getResources().getDisplayMetrics().heightPixels;
//        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.transparent);
//        getDialog().getWindow().setAttributes(params);
//        setCancelable(false);
//        super.onResume();
//        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
//            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//                this.dismiss();
//                return true;
//            } else return false;
//        });
//    }
//}