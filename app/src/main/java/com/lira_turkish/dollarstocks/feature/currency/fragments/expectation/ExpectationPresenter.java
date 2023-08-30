package com.lira_turkish.dollarstocks.feature.currency.fragments.expectation;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lira_turkish.dollarstocks.models.Expectation;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.language.BasePresenter;

import java.util.ArrayList;

public class ExpectationPresenter extends BasePresenter {

    private ExpectationFragment fragment;

    public ExpectationPresenter(ExpectationFragment fragment) {
        super(fragment.requireContext());
        this.fragment = fragment;
    }

    protected void getExpectation() {
//        Response response = getSharedPreferences().getDataForStorage();
//        if (response.getExpectations() != null) {
//            fragment.setData(response.getExpectations().getData());
//        } else {
//            fragment.getProgressDialog().show();
//        }
//        new APIUtil<ArrayList<Expectation>>(getContext()).getData(getApi().getExpectations(), new RequestListener<ApiResult<ArrayList<Expectation>>>() {
//            @Override
//            public void onSuccess(ApiResult<ArrayList<Expectation>> arrayListApiResult, String msg) {
//                fragment.setData(arrayListApiResult.getData());
//                response.setExpectations(arrayListApiResult);
//                getSharedPreferences().setDataForStorage(response);
//                fragment.getProgressDialog().dismiss();
//            }
//
//            @Override
//            public void onError(String msg) {
//                fragment.getProgressDialog().dismiss();
//            }
//
//            @Override
//            public void onFail(String msg) {
//                fragment.getProgressDialog().dismiss();
//            }
//        });
        fragment.getProgressDialog().show();

        FirebaseFirestore.getInstance().collection(AppContent.EXPECTATION).orderBy(AppContent.TIMESTAMP)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Expectation> expectations = new ArrayList<>();
                    for (DocumentSnapshot q : queryDocumentSnapshots.getDocuments()) {
                        expectations.add(q.toObject(Expectation.class));
                    }
                    fragment.setData(expectations);
//                    response.setExpectations(expectations);
//                    getSharedPreferences().setDataForStorage(response);
                    fragment.getProgressDialog().dismiss();
                });
    }
}
