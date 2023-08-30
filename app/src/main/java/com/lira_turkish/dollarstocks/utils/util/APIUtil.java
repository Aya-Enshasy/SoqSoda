package com.lira_turkish.dollarstocks.utils.util;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.models.ApiResult;
import com.lira_turkish.dollarstocks.utils.listeners.RequestListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIUtil<T> {

    private final Context context;

    public APIUtil(Context context) {
        this.context = context;
    }

    public void getData(Call<ApiResult<T>> call, RequestListener<ApiResult<T>> listener) {
//        if (ToolUtil.checkTheInternet()) {
            call.enqueue(new Callback<ApiResult<T>>() {
                @Override
                public void onResponse(@NonNull Call<ApiResult<T>> call, @NonNull Response<ApiResult<T>> response) {
                    Log.e(getClass().getName() + " : apiRequest", response.toString());
                    try {
                        if (response.body() != null && response.isSuccessful()) {
                           listener.onSuccess(response.body(), response.message());
                            Log.e(getClass().getName() + " : successRequest", response.body().toString());
                        } else {
                            Log.e(getClass().getName() + " : errorRequest", response.message());
//
//                            String message = ToolUtil.showError(context, response.errorBody());
//                            listener.onError(message);
//                            ToolUtil.showLongToast(message, context);
                        }
                    } catch (Exception e) {
                        //ToolUtil.showLongToast(e.getLocalizedMessage(), context);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResult<T>> call, @NonNull Throwable t) {
                    try {
                        String message = t.getLocalizedMessage();
                        listener.onFail(message);
                        ToolUtil.showLongToast(message, context);
                        Log.e(getClass().getName() + " : failureRequest", message);
                    } catch (Exception e) {
                        ToolUtil.showLongToast(e.getLocalizedMessage(), context);
                        e.printStackTrace();
                    }
                    t.printStackTrace();
                }
            });
//        }
//        else {
//            listener.onFail(context.getString(R.string.no_connection));
//        }
    }
}
