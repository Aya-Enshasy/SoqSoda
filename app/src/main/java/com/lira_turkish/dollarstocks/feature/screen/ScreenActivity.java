package com.lira_turkish.dollarstocks.feature.screen;


import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.LangKey;
import static com.lira_turkish.dollarstocks.feature.splash.SplashPresenter.PREF_NAME;
import static com.lira_turkish.dollarstocks.utils.Photo.PhotoTakerManager.FILE_PROVIDER_AUTHORITY;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.lira_turkish.dollarstocks.R;
import com.lira_turkish.dollarstocks.databinding.ActivityScreenBinding;
import com.lira_turkish.dollarstocks.models.CurrencyData;
import com.lira_turkish.dollarstocks.models.Screen;
import com.lira_turkish.dollarstocks.utils.AppPreferences;
import com.lira_turkish.dollarstocks.utils.formatter.DecimalFormatterManager;
import com.lira_turkish.dollarstocks.utils.formatter.SpinnerAdapter;
import com.lira_turkish.dollarstocks.utils.language.BaseActivity;
import com.lira_turkish.dollarstocks.utils.util.PermissionUtil;
import com.lira_turkish.dollarstocks.utils.util.ToolUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class ScreenActivity extends BaseActivity<ActivityScreenBinding> {

    private ScreenPresenter presenter;
    private CurrencyData city;
    private ArrayList<CurrencyData> data = new ArrayList<>();
    private ArrayList<Screen> currencies = new ArrayList<>();
    String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //screen
        setRootView(ActivityScreenBinding.inflate(getLayoutInflater()));

        lang = AppPreferences.getInstance(getBaseContext()).prefs.getString(LangKey, "");
        getRootView().ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void setDataInCreate() {
        presenter = new ScreenPresenter(this);

    }

    @Override
    protected void getDataInResume() {
        presenter.getCites();
    }

    @Override
    protected void clicks() {
        getRootView().share.setOnClickListener(view -> {
            if (PermissionUtil.isPermissionGranted(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, this)) {
                shareScreenShot();
            } else {
                permission.launch(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }
        });
        getRootView().cancel.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void onPermissionsResult(Map<String, Boolean> result) {
        if (!result.isEmpty())
            if (Boolean.TRUE.equals(result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                shareScreenShot();
            }
    }

    public void shareScreenShot() {
        try {
            View view = getRootView().screenCapture; //getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/ls";
            File dir = new File(dirPath);
            if (dir.exists())
                dir.delete();
            if (!dir.exists())
                dir.mkdirs();
            File file = new File(dirPath, System.currentTimeMillis() + ".jpg");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

            Uri uri = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(this, R.string.no_activity, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(getClass().getName() + "screen", e.getLocalizedMessage());
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setCites(ArrayList<CurrencyData> data) {
        this.data = data;
        getRootView().spinner.setAdapter(new SpinnerAdapter(this, data));
        presenter.getWorldCurrencies();
    }

    public void setWorldCurrencies(ArrayList<Screen> currencies) {
        this.currencies = currencies;

//        this.city = data.get(0);
//        getRootView().city.setText(city.getCity() + " |");
//        setScreenData();

        getRootView().spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city = data.get(i);
                setScreenData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                getRootView().spinner.setSelection(0);
            }
        });
    }

    private void setScreenData() {
        getRootView().usdBuy.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(city.getBuy())));
        getRootView().usdShell.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(city.getShell())));
        getRootView().tukBuy.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(city.getBuy())
                * Double.parseDouble(currencies.get(0).getValue())));
        getRootView().tukShell.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(city.getShell())
                * Double.parseDouble(currencies.get(0).getValue())));
        getRootView().sarBuy.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(city.getBuy())
                * Double.parseDouble(currencies.get(1).getValue())));
        getRootView().sarShell.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(city.getShell())
                * Double.parseDouble(currencies.get(1).getValue())));
        getRootView().eurBuy.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(city.getBuy())
                * Double.parseDouble(currencies.get(2).getValue())));
        getRootView().eurShell.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(city.getShell())
                * Double.parseDouble(currencies.get(2).getValue())));
        getRootView().goldBuy.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(city.getBuy()) * Double.parseDouble(currencies.get(4).getValue())));
        getRootView().goldShell.setText(DecimalFormatterManager.getFormatterBalanceWithRound().format(Double.parseDouble(city.getShell()) * Double.parseDouble(currencies.get(4).getValue())));

        getRootView().tukCursor.setImageResource(city.getState()==1 ? R.drawable.ic_cursor_up : R.drawable.ic_cursor_down);
        getRootView().usdCursor.setImageResource(city.getState()==1 ? R.drawable.ic_cursor_up : R.drawable.ic_cursor_down);
        getRootView().sarCursor.setImageResource(city.getState()==1 ? R.drawable.ic_cursor_up : R.drawable.ic_cursor_down);
        getRootView().eurCursor.setImageResource(city.getState()==1 ? R.drawable.ic_cursor_up : R.drawable.ic_cursor_down);
        getRootView().goldCursor.setImageResource(city.getState()==1 ? R.drawable.ic_cursor_up : R.drawable.ic_cursor_down);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale(AppPreferences.getInstance(getBaseContext()).prefs.getString(LangKey, "")));
        long update = System.currentTimeMillis();
        try {
            update = Objects.requireNonNull(sdf.parse(city.getUpdatedAt())).getTime() + (3 * 60 * 60 * 1000);
        } catch (Exception e) {
            Log.e("errorInToolUtils", e.getLocalizedMessage());
        }

        getRootView().city.setText(city.getCity() + " |");
        getRootView().day.setText(ToolUtil.getDayOfWeek(update));
        getRootView().hour.setText(ToolUtil.getTimeForScreen(update).replace("م","مساءا")
                .replace("ص","صباحا"));
        getRootView().date.setText(city.getUpdatedAt().split(" ")[0]);
    }
}

