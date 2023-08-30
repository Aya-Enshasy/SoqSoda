package com.lira_turkish.dollarstocks.closeApp;

import static android.content.Context.MODE_PRIVATE;

import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.closeApp.model.CloseApp;
import com.lira_turkish.dollarstocks.databinding.ActivityCloseAppDialogBinding;
import com.lira_turkish.dollarstocks.feature.currency.fragments.crops.model.CropsData;
import com.lira_turkish.dollarstocks.services.api.ApiClient;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloseAppDialog extends Dialog {

    private ActivityCloseAppDialogBinding binding;
    private Context context;
    FirebaseFirestore database;
    List<CropsData> list;
    ApiInterface service;
    int id = 0;

    public CloseAppDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        binding = ActivityCloseAppDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        service= ApiClient.getRetrofit().create(ApiInterface.class);

             list = new ArrayList<>();

            binding.delete.setVisibility(View.GONE);
            database = FirebaseFirestore.getInstance();
            binding.progressBar.setVisibility(View.GONE);

            if (AppPreferences.getInstance(getContext()) !=null){
                String name = AppPreferences.getInstance(getContext()).prefs.getString("name", "");
                String url = AppPreferences.getInstance(getContext()).prefs.getString("url", "");
                int status = AppPreferences.getInstance(getContext()).prefs.getInt("status", 0);
                int iid = AppPreferences.getInstance(getContext()).prefs.getInt("id", 0);
                binding.title.setText(name);
                binding.link.setText(url);
                if (status == 1) {
                    binding.swSwitcher.setChecked(true);
                } else {
                    binding.swSwitcher.setChecked(false);
                }
                if (iid<=0) {
                    binding.delete.setVisibility(View.GONE);
                } else {
                    binding.delete.setVisibility(View.VISIBLE);
                }
            }

            WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
            params.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
            getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER);
            getWindow().setBackgroundDrawableResource(R.drawable.transparent);

            getCloseApp();



        }

        private void getCloseApp() {
            service.closeAPP().enqueue(new Callback<CloseApp>() {
                @Override
                public void onResponse(Call<CloseApp> call, Response<CloseApp> response) {
                    Log.e("", response.code() + "");

                    if (response.isSuccessful()) {
                        String title = Objects.requireNonNull(binding.title.getText()).toString().trim();
                        String link = Objects.requireNonNull(binding.link.getText()).toString().trim();

                        if (response.body().getData() != null) {
                            int id1 = response.body().getData().get(0).getId();
                            id = id1;
                            Log.e("id", id + "");
                        }//get id


                        if (id <= 0) {
                            Log.e("i",id+"");
                            binding.send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (binding.swSwitcher.isChecked() == true) {
                                        addCloseAPP(binding.title.getText().toString(), binding.link.getText().toString(), 1);

                                    } else if (binding.swSwitcher.isChecked() == false) {
                                        addCloseAPP(binding.title.getText().toString(), binding.link.getText().toString(), 0);

                                    } else if (title.isEmpty() || link.isEmpty()) {
                                        binding.title.setText(context.getString(R.string.field_required));
                                        binding.link.setText(context.getString(R.string.field_required));
                                        return;
                                    } else
                                        Toast.makeText(context, "something error", Toast.LENGTH_SHORT).show();

                                }
                            });
                            binding.delete.setVisibility(View.GONE);
                        }
                        else {
                            Log.e("ii", id + "");
                            getCloseAppWhenUpdate();
                            binding.delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    delete();
                                }
                            });
                            binding.send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    if (binding.swSwitcher.isChecked() == true) {
                                        updateCloseAPP(id, binding.title.getText().toString(), binding.link.getText().toString(), 1);

                                    } else if (binding.swSwitcher.isChecked() == false) {
                                        updateCloseAPP(id, binding.title.getText().toString(), binding.link.getText().toString(), 0);

                                    } else if (title.isEmpty() || link.isEmpty()) {
                                        binding.title.setText(context.getString(R.string.field_required));
                                        binding.link.setText(context.getString(R.string.field_required));
                                        return;
                                    } else
                                        Toast.makeText(context, "something error", Toast.LENGTH_SHORT).show();

                                }
                            });
                            binding.delete.setVisibility(View.VISIBLE);

                        }


                    } else {
                        Log.e("", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<CloseApp> call, Throwable t) {
                    call.cancel();
                    Log.e("", t.getMessage() + "");
                }
            });
        }

        private void addCloseAPP(String name, String url, int status) {
            binding.progressBar.setVisibility(View.VISIBLE);
            service.addCloseAPP(name, url, status).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("", response.code() + "");

                    if (response.isSuccessful()) {
                        Log.e("", response.code() + "");
                        binding.progressBar.setVisibility(View.GONE);
                        AppPreferences.getInstance(getContext()).editor.putString("name", name);
                        AppPreferences.getInstance(getContext()).editor.putString("url", url);
                        AppPreferences.getInstance(getContext()).editor.putInt("status", status);
                        AppPreferences.getInstance(getContext()).editor.apply();
                        dismiss();
                    } else {
                        Log.e("", response.code() + "");
                        binding.progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    call.cancel();
                    Log.e("", t.getMessage() + "");
                    binding.progressBar.setVisibility(View.GONE);

                }
            });
        }

        private void delete() {
            binding.progressBar.setVisibility(View.VISIBLE);
            service.deleteCloseAPP(id).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("", response.code() + "");

                    if (response.isSuccessful()) {
                        Log.e("", response.code() + "");
                        binding.progressBar.setVisibility(View.GONE);
                        AppPreferences.getInstance(getContext()).editor.putString("name", "");
                        AppPreferences.getInstance(getContext()).editor.putString("url", "");
                        AppPreferences.getInstance(getContext()).editor.putInt("status", 0);
                        AppPreferences.getInstance(getContext()).editor.apply();
                        dismiss();
                    } else {
                        Log.e("", response.code() + "");
                        binding.progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    call.cancel();
                    Log.e("", t.getMessage() + "");
                    binding.progressBar.setVisibility(View.GONE);

                }
            });

        }

        private void updateCloseAPP(int id, String name, String url, int status) {
            binding.progressBar.setVisibility(View.VISIBLE);
            service.updateCloseAPP(id, name, url, status).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("", response.code() + "");

                    if (response.isSuccessful()) {
                        AppPreferences.getInstance(getContext()).editor.putString("name", name);
                        AppPreferences.getInstance(getContext()).editor.putString("url", url);
                        AppPreferences.getInstance(getContext()).editor.putInt("status", status);
                        AppPreferences.getInstance(getContext()).editor.putInt("id", id);
                        AppPreferences.getInstance(getContext()).editor.apply();
                        Log.e("", response.code() + "");
                        binding.progressBar.setVisibility(View.GONE);
                        dismiss();
                    } else {
                        Log.e("", response.code() + "");
                        binding.progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    call.cancel();
                    Log.e("", t.getMessage() + "");
                    binding.progressBar.setVisibility(View.GONE);

                }
            });
        }

        private void getCloseAppWhenUpdate() {
            service.closeAPP().enqueue(new Callback<CloseApp>() {
                @Override
                public void onResponse(Call<CloseApp> call, Response<CloseApp> response) {
                    Log.e("", response.code() + "");

                    if (response.isSuccessful()) {
                        binding.title.setText(response.body().getData().get(0).getTitle());
                        binding.link.setText(response.body().getData().get(0).getUrl());
                        if (response.body().getData().get(0).getStatus() == 1) {
                            binding.swSwitcher.setChecked(true);
                        } else {
                            binding.swSwitcher.setChecked(false);
                        }
                    } else {
                        Log.e("", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<CloseApp> call, Throwable t) {
                    call.cancel();
                    Log.e("", t.getMessage() + "");
                }
            });
        }


    }
