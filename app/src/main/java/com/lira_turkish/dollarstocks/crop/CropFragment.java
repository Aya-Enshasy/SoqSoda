package com.lira_turkish.dollarstocks.crop;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lira_turkish.dollarstocks.NativeTemplateStyle;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.TemplateView;
import com.lira_turkish.dollarstocks.databinding.FragmentCropBinding;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.model.Crops1;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.model.CropsData;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.MyInterface;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CropFragment  extends BaseFragment<FragmentCropBinding> {
    FragmentCropBinding binding;
    CropAdapter adapter;
    List<CropsData> list;
    ApiInterface service;
    private BaseActivity baseActivity;
    private Service service1;
    Dialog dialog;
    private int state = 1;
    String lang;
    private String state_type;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CropFragment(BaseActivity baseActivity, Service service1) {
        this.baseActivity = baseActivity;
        this.service1 = service1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCropBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        dialog = new Dialog(getActivity());
        showNativAd();

        service = ApiClient.getRetrofit().create(ApiInterface.class);
        list = new ArrayList<>() ;
        getCrops();
        binding.name.setText(R.string.crops_pricing);

        if (AppPreferences.getInstance(getActivity()).isAdmin()) {
            swipeToEditAndDelete();
        }

        toolbar();
        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");
        if (AppPreferences.getInstance(getContext())==null){
            getCrops();
            Log.e("getCrops","getCrops");
        }else {
            getCropsFromShared();
        }

        return view;
    }

    @Override
    protected void setDataInCreate() {

    }

    @Override
    protected void getDataInResume() {

    }

    @Override
    protected void clicks() {

    }

    private void getCrops() {
        service.getCropsFromApi(lang).enqueue(new Callback<Crops1>() {
            @Override
            public void onResponse(Call<Crops1> call, Response<Crops1> response) {
                Log.e("", response.code()+"");

                if (response.isSuccessful()) {
                    list = response.body().getData();
                    adapter.setData(list);
                    Gson gson = new Gson();
                    String json = gson.toJson(list);
                    AppPreferences.getInstance(getContext()).editor.putString("key", json);
                    Log.e("json",json);
                    AppPreferences.getInstance(getContext()).editor.apply();

                }
                else {
                    Log.e("", response.code()+"");
                }
            }
            @Override
            public void onFailure(Call<Crops1> call, Throwable t) {
                call.cancel();
                Log.e("", t.getMessage()+"");
            }
        });
    }


    private void toolbar(){
        binding.ivReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCropsAfterRefresh();
            }
        });

                binding.ivBack.setOnClickListener(view -> getActivity().onBackPressed());

        if (AppPreferences.getInstance(getActivity()).isAdmin()) {
            binding.addCrops.setVisibility(View.VISIBLE);
            binding.addCrops.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog();
                }
            });
        }else {
            binding.addCrops.setVisibility(View.GONE);
        }

    }

    private void dialog() {
        dialog.setContentView(R.layout.dialog_crops);
        WindowManager.LayoutParams params = Objects.requireNonNull(dialog.getWindow()).getAttributes();
        params.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.80);
        dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.setCancelable(true);
        Button add_or_update = dialog.findViewById(R.id.add_or_update);
        ImageView image = dialog.findViewById(R.id.image);
        image.setVisibility(View.GONE);
        ImageView up_or_down = dialog.findViewById(R.id.up_or_down);
        EditText name1 = dialog.findViewById(R.id.name);
        EditText currency1 = dialog.findViewById(R.id.currency);
        EditText value1 = dialog.findViewById(R.id.value);
        TextView tv_name = dialog.findViewById(R.id.tv_name);
        ProgressBar progressBar = dialog.findViewById(R.id.progress);
        tv_name.setText(R.string.add_crops);
        add_or_update.setText(R.string.add);

        up_or_down.setOnClickListener(view -> {
            if (state==1) {
                state =0;
                state_type = String.valueOf(state);
                up_or_down.setImageResource(R.drawable.ic_cursor_down);
            } else {
                state = 1;
                state_type = String.valueOf(state);
                up_or_down.setImageResource(R.drawable.ic_cursor_up);
            }
        });

        add_or_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name1.getText().toString();
                String currency = currency1.getText().toString();
                String value = value1.getText().toString();

                if (name.equals("")) {
                    name1.setError(getContext().getString(R.string.field_required));
                    return;
                }
                if (currency.equals("")) {
                    currency1.setError(getContext().getString(R.string.field_required));
                    return;
                }
                if (value.equals("")) {
                    value1.setError(getContext().getString(R.string.field_required));
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                addCrops(name,value,state,currency,progressBar);
            }
        });

        dialog.show();
    }

    private void addCrops(String name,String price,int status,String currency,ProgressBar progressBar){
        service.addCropsData(name,price,status,currency,"application/json",lang).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("response code", response.code() + "");
                    Log.e("response name", name + "");
                    Log.e("response price", price+ "");
                    if (response.isSuccessful()) {
                        Log.e("success", response.message());
                        dialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        getCropsAfterRefresh();
                    }else {
                        Log.e("else", response.code()+"");
                        progressBar.setVisibility(View.GONE);

                    }

                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("error", t.getMessage() + "");
                    progressBar.setVisibility(View.GONE);

                }
            });
        }

    private void refreshDialog() {
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent);

        ProgressBar progress = dialog.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void getCropsAfterRefresh() {
        refreshDialog();
        service.getCropsFromApi(lang).enqueue(new Callback<Crops1>() {
            @Override
            public void onResponse(Call<Crops1> call, Response<Crops1> response) {
                Log.e("", response.code()+"");
                dialog.dismiss();
                if (response.isSuccessful()) {
                    list = response.body().getData();
                    adapter.setData(list);
                }
                else {
                    Log.e("", response.code()+"");
                }
            }
            @Override
            public void onFailure(Call<Crops1> call, Throwable t) {
                call.cancel();
                dialog.dismiss();
                Log.e("", t.getMessage()+"");
            }
        });
    }

    private void getCropsFromShared(){
        Gson gson = new Gson();
        String json = AppPreferences.getInstance(getContext()).prefs.getString("key", null);
        Type type = new TypeToken<ArrayList<CropsData>>() {}.getType();
        gson.fromJson(json, type);
        Log.e("getCropsFromShared","getCropsFromShared");

        list = new ArrayList<>() ;
        binding.cropsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        adapter = new CropAdapter(getActivity(), new MyInterface() {
            @Override
            public void onItemClick(int Id) {
             //   id1 = Id;
        //        updateDialog();
            }
        },gson.fromJson(json, type));
        binding.cropsRecyclerview.setAdapter(adapter);

    }

    private void swipeToEditAndDelete() {
        // Create and add a callback
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                final int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:

                        Dialog dialog1 = new Dialog(getActivity(), R.style.DialogStyle);
                        dialog1.setContentView(R.layout.layout_custom_dialog2);

                        dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialog);

                        Button btnClose = dialog1.findViewById(R.id.cancel);
                        Button btnClear = dialog1.findViewById(R.id.clear);

                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog1.dismiss();
                                getCrops();
                            }
                        });

                        btnClear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                delete(list.get(position).getId());
                                adapter.notifyDataSetChanged();
                                dialog1.dismiss();
                                list.remove(position);
                                adapter.notifyItemRemoved(position);
                                Gson gson = new Gson();
                                String json = gson.toJson(list);
                                AppPreferences.getInstance(getContext()).editor.putString("ExpectationData", json);
                                Log.e("json",json);
                                AppPreferences.getInstance(getContext()).editor.apply();
                            }
                        });
                        dialog1.show();
                        break;

                    case ItemTouchHelper.RIGHT:

                        dialog.setContentView(R.layout.dialog_crops);
                        WindowManager.LayoutParams params = Objects.requireNonNull(dialog.getWindow()).getAttributes();
                        params.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.80);
                        dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent);
                        dialog.setCancelable(false);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                        Button add_or_update = dialog.findViewById(R.id.add_or_update);
                        ImageView image = dialog.findViewById(R.id.image);
                        image.setVisibility(View.GONE);
                        ImageView up_or_down = dialog.findViewById(R.id.up_or_down);
                        EditText name1 = dialog.findViewById(R.id.name);
                        EditText currency1 = dialog.findViewById(R.id.currency);
                        EditText value1 = dialog.findViewById(R.id.value);
                        ProgressBar progressBar = dialog.findViewById(R.id.progress);

                        name1.setText(list.get(position).getName());
                        currency1.setText(list.get(position).getCurrency());
                        value1.setText(list.get(position).getPrice());

                        up_or_down.setOnClickListener(view -> {
                            if (state==1) {
                                state = 0;
                                state_type = String.valueOf(state);
                                up_or_down.setImageResource(R.drawable.ic_cursor_down);
                            } else {
                                state = 1;
                                state_type = String.valueOf(state);
                                up_or_down.setImageResource(R.drawable.ic_cursor_up);
                            }
                        });

                        add_or_update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = name1.getText().toString();
                                String currency = currency1.getText().toString();
                                String value = value1.getText().toString();
                                progressBar.setVisibility(View.VISIBLE);
                                updateCrops(list.get(position).getId(),name,value,state,currency,progressBar);

                            }
                        });

                        dialog.show();
                        break;
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.primary))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_mode_edit_24)
                        .addSwipeRightLabel("Edit")
                        .setSwipeRightLabelColor(Color.WHITE)
                        .addSwipeLeftLabel("Delete")
                        .setSwipeLeftLabelColor(Color.WHITE)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(binding.cropsRecyclerview);

    }

    public void delete(int id) {
        service.deleteCrops(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    Log.e("code", response.code() + "");
                } else {
                    Log.e("else",response.code()+"");

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("code", t.getMessage() + "");
            }
        });

    }

    private void updateCrops(int id,String name,String price,int status,String currency,ProgressBar progressBar){
        service.updateCrops(name,price,status,currency,id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("", response.code()+"");
                if (response.isSuccessful()) {
                    getCrops();
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.e("else",response.code()+"");
                    Log.e("else",response.message()+"");
                }
                dialog.dismiss();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                call.cancel();
                Log.e("", t.getMessage()+"");
            }
        });

    }
    private void showNativAd() {
//        MobileAds.initialize(this);
        AdLoader adLoader = new AdLoader.Builder(getActivity(), "ca-app-pub-1210134677677997/1342585141")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.parseColor("#1E1F22"))).build();
                        TemplateView template = getActivity().findViewById(R.id.my_template_main);
                        template.setStyles(styles);
                        template.setNativeAd(nativeAd);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }
}



