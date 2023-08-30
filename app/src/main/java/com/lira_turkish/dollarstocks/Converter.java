package com.lira_turkish.dollarstocks;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.lira_turkish.dollarstocks.type.WorldCurrency;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Converter extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    TextView fromConvert, toConvert, result;
    EditText edValue;
    Button convert;
    ImageView toFlag, fromFlag;
    ProgressBar prog;
    double rate = 0;
    ArrayList<WorldCurrency> worldPriceModels;
    boolean isReady = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

//        showNativAd();
        getWorldPrice();

        fromConvert = (TextView) findViewById(R.id.fromConvert);
        toConvert = (TextView) findViewById(R.id.toConvert);
        result = (TextView) findViewById(R.id.convert_result);
        edValue = (EditText) findViewById(R.id.money);
        convert = (Button) findViewById(R.id.convertBtn);
        prog = (ProgressBar) findViewById(R.id.convert_prog);
        toFlag = (ImageView) findViewById(R.id.to_flag);
        worldPriceModels = new ArrayList<WorldCurrency>();

        toConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isReady){
//                    View v = LayoutInflater.from(Converter.this).inflate(R.layout.dialog_select_item_coverter, null);
//                    BottomSheetDialog dialog = new BottomSheetDialog(Converter.this);
//                    dialog.setContentView(v);
//                    RecyclerView recy = (RecyclerView) v.findViewById(R.id.recy_item_convert);
//                    recy.setHasFixedSize(true);
//                    recy.setItemViewCacheSize(23);
//                    LinearLayoutManager linear = new LinearLayoutManager(Converter.this,LinearLayoutManager.VERTICAL, true);
//                    linear.setReverseLayout(true);
//                    recy.setLayoutManager(linear);
//                    ItemConverterAdapter adapter = new ItemConverterAdapter(Converter.this, worldPriceModels, dialog);
//                    recy.setAdapter(adapter);
//                    recy.scrollToPosition(worldPriceModels.size() -1);
//                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialogInterface) {
//                            setFromData();
//                        }
//                    });
//                    dialog.show();
                } else{
//                    View vi = LayoutInflater.from(Converter.this).inflate(R.layout.activity_converter, null);
//                    Snackbar.make(Converter.this, vi, "يتم جلب اسعار الصرف , لحظة من فضلك ...", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();

                }

            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(Converter.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
                if (isReady){
                    String value = edValue.getText().toString();
                    if (value.length() > 0){
                        result.setText(""+Double.parseDouble(value) * rate);
                        Log.d("converterr", "value = "+value+", rate = "+rate);
                    }

                }
            }
        });
    }

    private void setFromData() {
        SharedPreferences shared = getSharedPreferences("data", MODE_PRIVATE);
        WorldCurrency currency = getCureencyFromCode(shared.getString("c_code", "TRY"));
        toConvert.setText(currency.getCountry());
        toFlag.setImageResource(currency.getFlag());
        rate = currency.getValue();
        Log.d("badder23", shared.getString("c_code", "TRY"));

    }

    public void getWorldPrice(){
        String[] codesCountry = new String[]{"TRY","EUR","GBP","BTC","AUD","SAR","EGP","AED","KWD","JOD","QAR","BHD","CAD","RUB"
                ,"JPY","CNH","DZD","ARS","CHF","INR","MAD","IRR","LYD","TND"};

        int[] flags = new int[]{ R.drawable.tr,R.drawable.eu, R.drawable.gb, R.drawable.btc, R.drawable.au,
                R.drawable.sa, R.drawable.eg, R.drawable.ae, R.drawable.kw,
                R.drawable.jo, R.drawable.qa, R.drawable.bh, R.drawable.ca,
                R.drawable.ru, R.drawable.jp, R.drawable.cn,
                R.drawable.dz, R.drawable.ar, R.drawable.ch, R.drawable.in,
                R.drawable.ma, R.drawable.ir, R.drawable.ly, R.drawable.tn};

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET,
                "https://openexchangerates.org/api/latest.json?app_id=927ba0da38274cf3b281e4aabf1de735"
                , null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject json = response.getJSONObject("rates");
                            double price;
                            for (int i= codesCountry.length -1; i >= 0 ; i--){
                                if (codesCountry[i].equals("BTC")){
                                    price = 1 / json.getDouble(codesCountry[i]);
                                } else{
                                    price = json.getDouble(codesCountry[i]);
                                }
                                worldPriceModels.add(new WorldCurrency(
                                        codesCountry[i],
                                        price,
                                        getCountryFromCode(codesCountry[i]),
                                        flags[i]));
                            }
                            prog.setVisibility(View.GONE);
                            setFromData();
                            isReady = true;
                        } catch (JSONException e) {
                            Log.d("ibrahim", "error : "+e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ibrahim", "Error : "+error.getMessage());

                    }
                }
        );

        queue.add(getRequest);

    }

    public String getCountryFromCode(String code){
        String country = "n";

        if (code.equals("TRY")) country = "الليرة التركية";
        else if (code.equals("EUR")) country = "يورو";
        else if (code.equals("DZD")) country = "دينار جزائري";
        else if (code.equals("ARS")) country = "بيزو أرجنتيني";
        else if (code.equals("AUD")) country = "دولار استرالي";
        else if (code.equals("BHD")) country = "دينار بحريني";
        else if (code.equals("CAD")) country = "دولار كندي";
        else if (code.equals("CHF")) country = "فرنك سويسري";
        else if (code.equals("JPY")) country = "ين ياباني";
        else if (code.equals("GBP")) country = "جنيه استرليني";
        else if (code.equals("RUB")) country = "روبل روسي";
        else if (code.equals("CNH")) country = "يوان صيني";
        else if (code.equals("AED")) country = "درهم اماراتي";
        else if (code.equals("BTC")) country = "بيتكوين";
        else if (code.equals("EGP")) country = "جنية مصري";
        else if (code.equals("INR")) country = "روبية هندية";
        else if (code.equals("MAD")) country = "درهم مغربي";
        else if (code.equals("IRR")) country = "ريال ايراني";
        else if (code.equals("JOD")) country = "دينار اردني";
        else if (code.equals("KWD")) country = "دينار كويتي";
        else if (code.equals("LYD")) country = "دينار ليبي";
        else if (code.equals("QAR")) country = "ريال قطري";
        else if (code.equals("SAR")) country = "ريال سعودي";
        else if (code.equals("TND")) country = "دينار تونسي";

        return country;
    }

    public WorldCurrency getCureencyFromCode(String code){
        WorldCurrency worldCurrency = null;

        for (int i = 0; i < worldPriceModels.size(); i++) {

            if ((worldPriceModels.get(i).getCode()).equals(code)){
                worldCurrency = worldPriceModels.get(i);
            }
        }

        return worldCurrency;
    }

    private void showNativAd() {
        MobileAds.initialize(this);
        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-1210134677677997/1342585141")
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAd) {
//                        NativeTemplateStyle styles = new
//                                NativeTemplateStyle.Builder().withMainBackgroundColor(new ColorDrawable(Color.parseColor("#1E1F22"))).build();
//                        TemplateView template = findViewById(R.id.my_template_converter);
//                        template.setStyles(styles);
//                        template.setNativeAd(nativeAd);
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void showFullAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-1210134677677997/6546826271", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("badder", "onAdLoaded");

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("badder", loadAdError.toString());
                        mInterstitialAd = null;
                        //showFullAd();
                    }
                });

    }
}