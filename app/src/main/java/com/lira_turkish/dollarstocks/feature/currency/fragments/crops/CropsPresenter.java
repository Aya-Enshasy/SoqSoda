package com.lira_turkish.dollarstocks.feature.currency.fragments.crops;


import com.lira_turkish.dollarstocks.services.api.ApiInterface;
import com.lira_turkish.dollarstocks.utils.language.BasePresenter;

public class CropsPresenter extends BasePresenter {
    ApiInterface service;

    private CropsFragment fragment;

    public CropsPresenter(CropsFragment fragment) {
        super(fragment.requireContext());
        this.fragment = fragment;
    }

//    protected void getCrops() {
//        Response response = getSharedPreferences().getDataForStorage();
//        if (response.getCrops() != null) {
//            fragment.setData(response.getCrops());
//        } else {
//            fragment.getProgressDialog().show();
//        }
//        new APIUtil<ArrayList<Crops>>(getContext()).getData(getApi().getCrops(),
//                new RequestListener<ApiResult<ArrayList<Crops>>>() {
//                    @Override
//                    public void onSuccess(ApiResult<ArrayList<Crops>> arrayListApiResult, String msg) {
//                        fragment.setData(arrayListApiResult.getData());
//                        response.setCrops(arrayListApiResult);
//                        getSharedPreferences().setDataForStorage(response);
//                        fragment.getProgressDialog().dismiss();
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//                        fragment.getProgressDialog().dismiss();
//                    }
//
//                    @Override
//                    public void onFail(String msg) {
//                        fragment.getProgressDialog().dismiss();
//                    }
//                });
//    }

//    public void getCropsFirebase() {
//        fragment.getProgressDialog().show();
//        FirebaseFirestore.getInstance().collection(AppContent.CROPS).orderBy(AppContent.TIMESTAMP)
//                .get().addOnSuccessListener(queryDocumentSnapshots -> {
//                    ArrayList<Crops> crops = new ArrayList<>();
//                    for (DocumentSnapshot q : queryDocumentSnapshots.getDocuments()) {
//                        crops.add(q.toObject(Crops.class));
//                    }
//                    fragment.setData(crops);
//                    fragment.getProgressDialog().dismiss();
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        ToolUtil.showLongToast(e.getLocalizedMessage(), fragment.requireContext());
//                        Log.e(getClass().getName() + "firebase", e.getLocalizedMessage());
//                    }
//                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (!task.isSuccessful()) {
//                            ToolUtil.showLongToast(Objects.requireNonNull(task.getException()).getLocalizedMessage(), fragment.requireContext());
//                            Log.e(getClass().getName() + "firebase", task.getException().getLocalizedMessage());
//                        }
//                    }
//                });
//    }

}
