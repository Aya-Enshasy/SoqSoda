package com.lira_turkish.dollarstocks.feature.currency.fragments.world;


import com.lira_turkish.dollarstocks.models.ApiResult;
import com.lira_turkish.dollarstocks.models.Currency;
import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.language.BasePresenter;

public class WorldCurrenciesPresenter extends BasePresenter {

    private WorldCurrenciesFragment fragment;
    ApiInterface service;
    ApiResult<Currency> currencyApiResult;

    public WorldCurrenciesPresenter(WorldCurrenciesFragment fragment) {
        super(fragment.requireContext());
        this.fragment = fragment;
    }
}
//    public void getWorldCurrencies() {
//        Response response = getSharedPreferences().getDataForStorage();
//        if (response.getWorldCurrency() != null) {
//            fragment.setCurrencies(response.getWorldCurrency());
//        } else {
//            fragment.getProgressDialog().show();
//        }
//        new APIUtil<Currency>(getContext()).getData(getApi().getWorldCurrencies()
//                , new RequestListener<ApiResult<Currency>>() {
//                    @Override
//                    public void onSuccess(ApiResult<Currency> currencyApiResult, String msg) {
//                        ArrayList<CurrentCurrency> list = new ResourceUtil().getCurrencies(getContext(), currencyApiResult.getData());
//                        fragment.setCurrencies(list);
//                        response.setWorldCurrency(list);
//                        getSharedPreferences().setDataForStorage(response);
//                        fragment.getProgressDialog().dismiss();
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        fragment.getProgressDialog().dismiss();
//                        Log.e("msg",msg);
//                    }
//
//                    @Override
//                    public void onFail(String msg) {
//                        fragment.getProgressDialog().dismiss();
//                        Log.e("msg1",msg);
//
//                    }
////                });
//    }
//
//    private void getWorldCurrencies() {
////        service = AppController.getInstance().getApi();
////
////        Response response = getSharedPreferences().getDataForStorage();
////        if (response.getWorldCurrency() != null) {
////            fragment.setCurrencies(response.getWorldCurrency());
////        } else {
////            fragment.getProgressDialog().show();
////        }
////
////        service.getWorldCurrencies().enqueue(new Callback<Currency>() {
////            @Override
////            public void onResponse(Call<Currency> call, retrofit2.Response<Currency> response) {
////                Log.e("", response.code()+"");
////
////                if (response.isSuccessful()) {
////                    ArrayList<CurrentCurrency> list = new ResourceUtil().getCurrencies(getContext(), currencyApiResult.getData());
////                    fragment.setCurrencies(list);
////                    response.setWorldCurrency(list);
////                    getSharedPreferences().setDataForStorage(response);
////                    fragment.getProgressDialog().dismiss();
////                }
////                else {
////                    Log.e("", response.code()+"");
////                }
////            }
////            @Override
////            public void onFailure(Call<Currency> call, Throwable t) {
////                call.cancel();
////                Log.e("", t.getMessage()+"");
////            }
////        });
//    }
//
//}
