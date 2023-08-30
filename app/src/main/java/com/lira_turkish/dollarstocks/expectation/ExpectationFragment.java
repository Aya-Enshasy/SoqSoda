package com.lira_turkish.dollarstocks.expectation;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.lira_turkish.dollarstocks.databinding.FragmentExpectation2Binding;
import com.lira_turkish.dollarstocks.databinding.FragmentExpectationBinding;
import com.lira_turkish.dollarstocks.models.Service;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.language.BaseFragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpectationFragment  extends BaseFragment<FragmentExpectationBinding> {

    FragmentExpectation2Binding binding;
    ExpectationAdapter adapter;
    List<ExpectationData> list;
    ApiInterface service;
    private BaseActivity baseActivity;
    private Service service1;
    Dialog dialog;
    String lang;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpectationFragment(BaseActivity baseActivity, Service service1) {
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
        binding = FragmentExpectation2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        dialog = new Dialog(getActivity());
        showNativAd();

        service = ApiClient.getRetrofit().create(ApiInterface.class);
        list = new ArrayList<>() ;
        binding.topBar.name.setText(R.string.expectations);
        lang = AppPreferences.getInstance(getContext()).prefs.getString(LangKey, "");

        getExpectation();
        toolbar();
        if (AppPreferences.getInstance(getActivity()).isAdmin()) {
            swipeToEditAndDelete();
        }
        if (AppPreferences.getInstance(getContext())==null){
            getExpectation();
            Log.e("getExpectation","getExpectation");
        }else {
            getExpectationFromShared();
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

    private void getExpectation() {
        service.getExpectation(lang).enqueue(new Callback<Expectation>() {
            @Override
            public void onResponse(Call<Expectation> call, Response<Expectation> response) {
                Log.e("", response.code()+"");

                if (response.isSuccessful()) {
                    list = response.body().getData();
                    adapter.setData(list);
                    Gson gson = new Gson();
                    String json = gson.toJson(list);
                    AppPreferences.getInstance(getContext()).editor.putString("ExpectationData", json);
                    Log.e("json",json);
                    AppPreferences.getInstance(getContext()).editor.apply();
                }
                else {
                    Log.e("", response.code()+"");
                }
            }
            @Override
            public void onFailure(Call<Expectation> call, Throwable t) {
                call.cancel();
                Log.e("", t.getMessage()+"");
            }
        });
    }


    private void toolbar(){
        binding.topBar.ivReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getExpectation2();
            }
        });
        binding.topBar.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
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
        dialog.setContentView(R.layout.dialog_add_expectation);
        WindowManager.LayoutParams params = Objects.requireNonNull(dialog.getWindow()).getAttributes();
        params.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.80);
        dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        Button add_button = dialog.findViewById(R.id.update);
        TextView title = dialog.findViewById(R.id.title);
        ProgressBar progress = dialog.findViewById(R.id.progress);

        progress.setVisibility(View.GONE);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = title.getText().toString();

                if (name.equals("")) {
                    title.setError(getContext().getString(R.string.field_required));
                    return;
                }

                progress.setVisibility(View.VISIBLE);

                addExpectation(title.getText().toString(),progress);


            }
        });
        dialog.show();
    }

    private void addExpectation(String name,ProgressBar progressBar) {

        service.addExpectation(name,lang).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("code", response.code()+"");
                if (response.isSuccessful()) {
                    Log.e("n",name);
                    progressBar.setVisibility(View.GONE);
                    dialog.dismiss();
                    getExpectation();
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Log.e("message", response.message()+"");
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                call.cancel();
                progressBar.setVisibility(View.GONE);
                Log.e("getMessage", t.getMessage()+"");
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

    private void getExpectation2() {
        refreshDialog();
        service.getExpectation(lang).enqueue(new Callback<Expectation>() {
            @Override
            public void onResponse(Call<Expectation> call, Response<Expectation> response) {
                Log.e("", response.code()+"");

                if (response.isSuccessful()) {
                    list = response.body().getData();
                    adapter.setData(list);
                    dialog.dismiss();
                }
                else {
                    Log.e("", response.code()+"");
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<Expectation> call, Throwable t) {
                call.cancel();
                dialog.dismiss();
                Log.e("", t.getMessage()+"");
            }
        });
    }

    private void getExpectationFromShared(){
        Gson gson = new Gson();
        String json = AppPreferences.getInstance(getContext()).prefs.getString("ExpectationData", null);
        Type type = new TypeToken<ArrayList<ExpectationData>>() {}.getType();
        gson.fromJson(json, type);
        Log.e("getCropsFromShared","getCropsFromShared");

        binding.expectationsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,true));
        adapter = new ExpectationAdapter(getActivity(),gson.fromJson(json, type));
        binding.expectationsRecyclerview.setAdapter(adapter);

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
                                getExpectation();
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
                        dialog.setContentView(R.layout.dialog_add_expectation);
                        WindowManager.LayoutParams params = Objects.requireNonNull(dialog.getWindow()).getAttributes();
                        params.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.80);
                        dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                        Button add_button = dialog.findViewById(R.id.update);
                        add_button.setText(R.string.update);
                        EditText title = dialog.findViewById(R.id.title);
                        TextView tv_name = dialog.findViewById(R.id.tv_name);
                        ProgressBar progress = dialog.findViewById(R.id.progress);

                        progress.setVisibility(View.GONE);
                        tv_name.setText(R.string.Update_Expectation);
                        title.setText(list.get(position).getContent());

                        add_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String name = title.getText().toString();

                                if (name.equals("")) {
                                    title.setError(getContext().getString(R.string.field_required));
                                    return;
                                }


                                progress.setVisibility(View.VISIBLE);
                                update(title.getText().toString(),list.get(position).getId(),progress);

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
        itemTouchHelper.attachToRecyclerView(binding.expectationsRecyclerview);

    }

    public void delete(int id) {
        service.deleteItem(id).enqueue(new Callback<String>() {
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

    public void update(String content,int id,ProgressBar progressBar) {
        service.updateExpectation(content,id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    getExpectation();
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.GONE);

                    Log.e("else",response.code()+"");
                }
                dialog.dismiss();
            }



            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("code", t.getMessage() + "");
                progressBar.setVisibility(View.GONE);

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