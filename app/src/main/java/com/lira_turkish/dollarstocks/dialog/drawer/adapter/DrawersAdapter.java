package com.lira_turkish.dollarstocks.dialog.drawer.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.closeApp.CloseAppDialog;
import com.lira_turkish.dollarstocks.databinding.ItemDrawerBinding;
import com.lira_turkish.dollarstocks.dialog.admin.AdminDialog;
import com.lira_turkish.dollarstocks.dialog.drawer.DrawerDialog;
import com.lira_turkish.dollarstocks.dialog.problem.ProblemDialog;
import com.lira_turkish.dollarstocks.dialog.switching.SwitchingDialog;
import com.lira_turkish.dollarstocks.feature.ads.RemoveAdsActivity;
import com.lira_turkish.dollarstocks.feature.language.LanguageActivity;
import com.lira_turkish.dollarstocks.models.Drawer;
import com.lira_turkish.dollarstocks.utils.AppContent;
import com.lira_turkish.dollarstocks.utils.AppController;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.util.NavigateUtil;

import java.util.ArrayList;

public class DrawersAdapter extends RecyclerView.Adapter<DrawersAdapter.DrawerHolder> {

    private ArrayList<Drawer> items;
    private BaseActivity baseActivity;
    private DrawerDialog dialog;

    public DrawersAdapter(BaseActivity baseActivity, ArrayList<Drawer> items, DrawerDialog dialog) {
        this.baseActivity = baseActivity;
        this.items = items;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public DrawerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //drawer
        return new DrawerHolder(ItemDrawerBinding.inflate(LayoutInflater
                .from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Drawer item) {
        this.items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(ArrayList<Drawer> items) {
        if (items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void deleteItem(Drawer item) {
        int i = this.items.indexOf(item);
        this.items.remove(item);
        notifyItemRemoved(i);
    }

    public void deleteAll() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public class DrawerHolder extends RecyclerView.ViewHolder {

        private ItemDrawerBinding binding;
        private Drawer drawer;

        public DrawerHolder(ItemDrawerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.background.setOnClickListener(view -> navigate());
        }

        private void navigate() {
            switch (drawer.getId()) {
                case AppContent.REMOVE_ADS:
                    new NavigateUtil().activityIntent(baseActivity, RemoveAdsActivity.class, true);
                    break;

                case AppContent.NOTIFICATIONS:
                    SwitchingDialog switchingDialog = new SwitchingDialog(baseActivity);
                    switchingDialog.setTitle(baseActivity.getString(R.string.notifications), R.drawable.ic_bell);
                    switchingDialog.setDetails(baseActivity.getString(R.string.sound_notifications));
                    switchingDialog.setListener(b -> {
                        AppPreferences.getInstance(dialog.getContext()).setNotification(b);
                        //switchingDialog.dismiss()
                    });
                    switchingDialog.show();
                    break;


                case AppContent.CHANGE_LANGUAGE:
                    new NavigateUtil().activityIntent(baseActivity, LanguageActivity.class, true);
                    break;
                case AppContent.SHARE:
                    new NavigateUtil().share(baseActivity, "https://play.google.com/store/apps/details?id=com.fajer.ls");
                    break;
                case AppContent.RATE:
                    new NavigateUtil().openLink(baseActivity, "https://play.google.com/store/apps/details?id=com.fajer.ls");
                    break;
//                case AppContent.CONTACT_US:
//                    new NavigateUtil().openLink(baseActivity, "https://instagram.com/dei1sy");
//                    break;
                case AppContent.PROBLEM:
                    ProblemDialog problemDialog = new ProblemDialog(baseActivity);
                    problemDialog.show();
                    break;
                case AppContent.PRIVACY:
                    new NavigateUtil().openLink(baseActivity, "https://docs.google.com/document/u/1/d/e/2PACX-1vR-rI410ri4jHQ-8h5TrbsL2X5rDIepfVBez8Y9gh42TfyYvvo73-rVk6Vk3dDAHlSYT5vAKjjZniRk/pub");
                    break;
                case AppContent.ADMIN_NOTIFICATIONS:
                    AdminDialog adminDialog = new AdminDialog(baseActivity);
                    adminDialog.show();
                    break;

                case AppContent.CLOSE_APP:
                    CloseAppDialog closeAppDialog = new CloseAppDialog(baseActivity);
                    closeAppDialog.show();
                    break;

            }
            dialog.dismiss();
        }

        public void setData(Drawer item) {
            this.drawer = item;
            binding.ivIcon.setImageResource(item.getResource());
            binding.tvName.setText(item.getName());
        }
    }
}
