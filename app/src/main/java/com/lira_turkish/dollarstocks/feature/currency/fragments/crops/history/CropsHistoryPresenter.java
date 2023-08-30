package com.lira_turkish.dollarstocks.feature.currency.fragments.crops.history;


import com.lira_turkish.dollarstocks.utils.language.BasePresenter;

public class CropsHistoryPresenter extends BasePresenter {

    private CropsHistoryActivity activity;

    public CropsHistoryPresenter(CropsHistoryActivity activity) {
        super(activity);
        this.activity = activity;
    }

//    public void getCropsHistoryFirebase(String id) {
//        activity.getProgressDialog().show();
//        FirebaseFirestore.getInstance().collection(AppContent.CROPS_HISTORY)
//                .document(id).collection(AppContent.HISTORY).orderBy(AppContent.TIMESTAMP)
//                .get().addOnSuccessListener(queryDocumentSnapshots -> {
//                    for (DocumentSnapshot q : queryDocumentSnapshots.getDocuments()) {
//                        activity.setData(q.toObject(CropsHistory.class));
//                    }
//                    activity.getProgressDialog().dismiss();
//                });
//    }
}
