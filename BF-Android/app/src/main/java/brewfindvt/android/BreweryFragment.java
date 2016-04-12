package brewfindvt.android;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import brewfindvt.managers.ApiManager;
import brewfindvt.managers.CacheManager;
import brewfindvt.objects.Brewery;
import brewfindvt.objects.Drink;
import brewfindvt.objects.Rating;
import brewfindvt.objects.UntappdObject;
import cz.msebera.android.httpclient.Header;

/**
 * Created by user on 2/16/2016.
 */
public class BreweryFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private LinearLayout _bigBox;
    private LinearLayout _drinkList;

    private TextView _name;
    private TextView _desc;
    private TextView _rating;

    private ImageView _logo;
    private ImageView _hasFood;
    private ImageView _hasTour;
    private ImageView _hasGrowler;
    private ImageView _hasTap;

    private Button _contactInfoButton;
    private Button _goToMapButton;
    private Button _eventButton;

    private ExpandableRelativeLayout _contactLayout;
    private LinearLayout _contactInfoBox;

    private CacheManager cacheManager;
    private ProgressDialog dialog;

    private Brewery newBrew;
    private List<Drink> drinkList;
    private Rating rating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.brewery_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cacheManager = CacheManager.getInstance();
        dialog = new ProgressDialog(getActivity());

        _bigBox = (LinearLayout) getActivity().findViewById(R.id.bigBox);
        _drinkList = (LinearLayout) getActivity().findViewById(R.id.drinkList);

        _name = (TextView) getActivity().findViewById(R.id.brewName);
        _rating = (TextView) getActivity().findViewById(R.id.rating);

        _logo = (ImageView) getActivity().findViewById(R.id.logoImageView);

        _hasFood = (ImageView) getActivity().findViewById(R.id.hasFood);
        _hasFood.setOnClickListener(this);
        _hasTour = (ImageView) getActivity().findViewById(R.id.hasTour);
        _hasTour.setOnClickListener(this);
        _hasGrowler = (ImageView) getActivity().findViewById(R.id.hasGrowler);
        _hasGrowler.setOnClickListener(this);
        _hasTap = (ImageView) getActivity().findViewById(R.id.hasTap);
        _hasTap.setOnClickListener(this);

        _contactInfoButton = (Button) getActivity().findViewById(R.id.contactInfoButton);
        _contactLayout = (ExpandableRelativeLayout) getActivity().findViewById(R.id.contactInfoView);
        _contactInfoBox = (LinearLayout) getActivity().findViewById(R.id.contactInfoBox);

        _goToMapButton = (Button) getActivity().findViewById(R.id.mapButton);
        _eventButton = (Button) getActivity().findViewById(R.id.eventButton);
        _eventButton.setOnClickListener(this);

        _goToMapButton.setOnClickListener(this);
        _contactInfoButton.setOnClickListener(this);

        if(newBrew != null) {
            populateBrewery(newBrew);
        }
    }

    @Override
    public void onClick(View v) {
        String toast;
        switch(v.getId()) {
            case R.id.contactInfoButton:
                _contactLayout.toggle();
                break;
            case R.id.mapButton:
                goToMap();
                break;
            case R.id.eventButton:
                ((MainActivity)getActivity()).makeEventsForBrewery(newBrew.getB_breweryNum());
                break;
            case R.id.hasFood:
                makeLegendToast(newBrew.getB_hasFood(), "food available.");
                break;
            case R.id.hasGrowler:
                makeLegendToast(newBrew.getB_hasGrowler(), "growler services.");
                break;
            case R.id.hasTap:
                makeLegendToast(newBrew.getB_hasTap(), "a tap or tasting room.");
                break;
            case R.id.hasTour:
                makeLegendToast(newBrew.getB_hasTour(), "tours available.");
                break;
        }
    }

    public void makeLegendToast(Boolean b, String s) {
        String toast;
        if(b == null || !b) {
            toast = "This brewery does not have " + s;
        } else {
            toast = "This brewery has " + s;
        }
        Toast.makeText(getActivity().getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }

    public void goToMap() {
        String newName = newBrew.b_name.replace(" ", "+");
        String url = "http://www.google.com/maps/place/" + newName;

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch(ActivityNotFoundException e) {
            Toast.makeText(getActivity().getApplicationContext(), "Unable to open map.", Toast.LENGTH_LONG).show();
        }

    }

    public void getUntappdInfo(int brewNum) {

        List<Drink> cacheDrinks = cacheManager.getDrinks(brewNum);
        Rating cacheRating = cacheManager.getRating(brewNum);
        if(cacheDrinks != null && cacheRating != null) {
            drinkList = cacheDrinks;
            rating = cacheRating;
            populateUntappdInfo();
            dialog.hide();
            return;
        }

        dialog.show();
        dialog.setMessage("Loading Untappd info...");

        ApiManager.getUntappdInfoForBrewery(brewNum, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray obj = response.getJSONArray("rObj");
                    ObjectMapper mapper = new ObjectMapper();
                    UntappdObject newUt = mapper.readValue(obj.getString(0), UntappdObject.class);
                    Rating newRating = new Rating(newUt.getU_rating(), newUt.getU_ratingCount());
                    rating = newRating;
                    drinkList = newUt.u_drinkList;
                    cacheManager.updateFromUntappdObject(newBrew.getB_breweryNum(), newUt);
                    dialog.setMessage("Nearly there...");
                    populateUntappdInfo();
                    dialog.hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable e) {
                Toast.makeText(getActivity().getApplicationContext(), "Failed to get Untappd info", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, java.lang.Throwable throwable, org.json.JSONArray errorResponse) {
                Toast.makeText(getActivity().getApplicationContext(), "Failed to get Untappd info", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, java.lang.Throwable throwable, org.json.JSONObject errorResponse) {
                Toast.makeText(getActivity().getApplicationContext(), "Failed to get Untappd info", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void populateUntappdInfo(){
        String ratingText;
        if(rating.getRating() == 0) {
            ratingText = "N/A";
        } else {
            ratingText = String.format("%.2f/5", rating.getRating());
        }
        _rating.setText("Rating: " + ratingText);

        for(Drink d : drinkList) {
            makeDrinkElement(d);
        }
    }

    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }

    public void makeDrinkElement(Drink toMake) {

        Button newButton = new Button(getActivity().getApplicationContext());
        int buttonId = drinkList.indexOf(toMake);
        newButton.setBackground(getResources().getDrawable(R.drawable.drink_border));
        newButton.setId(buttonId);
        String buttonTitle = toMake.getD_name() + " - " + toMake.getD_style();
        newButton.setText(buttonTitle);
        newButton.setCompoundDrawables(null, null, null, getResources().getDrawable(android.R.drawable.arrow_down_float));
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int toId = v.getId();
                ExpandableRelativeLayout lay = (ExpandableRelativeLayout) getActivity().findViewById(toId + 666);
                lay.toggle();
            }
        });

        ExpandableRelativeLayout newLayout = new ExpandableRelativeLayout(getActivity().getApplicationContext());
        ExpandableRelativeLayout.LayoutParams p = new ExpandableRelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        p.addRule(ExpandableRelativeLayout.BELOW, buttonId);
        newLayout.setInterpolator(new BounceInterpolator());
        newLayout.setOrientation(ExpandableLayout.VERTICAL);
        newLayout.setLayoutParams(p);
        newLayout.setId(buttonId + 666);
        newLayout.setBackgroundColor(getResources().getColor(R.color.beerInfo));

        LinearLayout.LayoutParams fullHeightParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TableLayout.LayoutParams infoParams = new TableLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0.33f);

        LinearLayout big = new LinearLayout(getActivity().getApplicationContext());
        big.setOrientation(LinearLayout.VERTICAL);
        big.setLayoutParams(fullHeightParams);
        big.setMinimumHeight(100);

        LinearLayout infoBar = new LinearLayout(getActivity().getApplicationContext());
        infoBar.setOrientation(LinearLayout.HORIZONTAL);
        infoBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView ibu = new TextView(getActivity().getApplicationContext());
        String ibuText = "IBU: " + toMake.getD_ibu();
        ibu.setText(ibuText);
        ibu.setLayoutParams(infoParams);
        ibu.setTextColor(Color.BLACK);

        TextView abv = new TextView(getActivity().getApplicationContext());
        String abvText = "ABV: " + toMake.getD_abv();
        abv.setText(abvText);
        abv.setLayoutParams(infoParams);
        abv.setTextColor(Color.BLACK);

        TextView dRating = new TextView(getActivity().getApplicationContext());
        String ratingText = "Rating: " + String.format("%.2f",toMake.getD_rating()) + "/5";
        dRating.setText(ratingText);
        dRating.setLayoutParams(infoParams);
        dRating.setTextColor(Color.BLACK);

        FrameLayout descBox = new FrameLayout(getActivity().getApplicationContext());
        descBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView description = new TextView(getActivity().getApplicationContext());
        String desc = toMake.getD_description();
        if(desc == null || desc == "") {
            desc = "No description available.";
        }
        description.setText(desc);
        description.setLayoutParams(fullHeightParams);
        description.setTextColor(Color.BLACK);

        _drinkList.addView(newButton);

        newLayout.addView(big);
        big.addView(infoBar);
        big.addView(descBox);
        infoBar.addView(abv);
        infoBar.addView(ibu);
        infoBar.addView(dRating);
        descBox.addView(description);

        _drinkList.addView(newLayout);

    }

    public void populateBrewery(Brewery brew) {

        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(true);
        dialog.show();
        dialog.setMessage("Loading brewery info...");


        if(dialog.isShowing()) {
            _drinkList.removeAllViews();
            getUntappdInfo(brew.getB_breweryNum());
        }

        _name.setText(brew.getB_name());

        populateContactInfo();

        _logo.setImageBitmap(cacheManager.getLogo(brew.getB_breweryNum()));

        setLegendColor(_hasFood, brew.getB_hasFood());
        setLegendColor(_hasTour, brew.getB_hasTour());
        setLegendColor(_hasGrowler, brew.getB_hasGrowler());
        setLegendColor(_hasTap, brew.getB_hasTap());

    }

    public void populateContactInfo() {

        _contactInfoBox.removeAllViews();

        if(newBrew.getB_addr1() != null  && !newBrew.getB_addr1().equals("")) {
            TextView addr1 = new TextView(getActivity().getApplicationContext());
            addr1.setText(newBrew.getB_addr1());
            addr1.setTextSize(18f);
            _contactInfoBox.addView(addr1);
        }
        if(newBrew.getB_addr2() != null && !newBrew.getB_addr2().equals("")) {
            TextView addr2 = new TextView(getActivity().getApplicationContext());
            addr2.setText(newBrew.getB_addr2());
            addr2.setTextSize(18f);
            _contactInfoBox.addView(addr2);
        }

        String cityPlus = newBrew.getB_city() + ", VT " + newBrew.getB_zip();
        TextView cpView = new TextView(getActivity().getApplicationContext());
        cpView.setText(cityPlus);
        cpView.setTextSize(18f);
        _contactInfoBox.addView(cpView);

        if(newBrew.getB_phone() != null  && !newBrew.getB_phone().equals("")) {
            TextView phone = new TextView(getActivity().getApplicationContext());
            phone.setText(newBrew.getB_phone());
            phone.setTextSize(18f);
            phone.setPadding(0, 50, 0, 50);
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = "tel: " + newBrew.getB_phone().trim();
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(num));
                        startActivity(intent);
                    } catch(ActivityNotFoundException e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Unable to call brewery", Toast.LENGTH_LONG).show();
                    }
                }
            });
            _contactInfoBox.addView(phone);
        }

        if(newBrew.getB_email() != null  && !newBrew.getB_email().equals("")) {
            TextView email = new TextView(getActivity().getApplicationContext());
            email.setText(newBrew.getB_email());
            email.setTextSize(18f);
            _contactInfoBox.addView(email);
        }

        if(newBrew.getB_url() != null  && !newBrew.getB_url().equals("")) {
            TextView url = new TextView(getActivity().getApplicationContext());
            url.setText(newBrew.getB_url());
            url.setTextSize(18f);
            url.setMinHeight(200);
            url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String url = newBrew.getB_url();
                        if (!url.startsWith("http://")) {
                            url = "http://" + url;
                        }
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Couldn't open website", Toast.LENGTH_LONG).show();
                    }
                }
            });
            _contactInfoBox.addView(url);
        }
    }

    public void setLegendColor(ImageView i, Boolean b) {
        if(b == null) {
            i.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        } else if (b) {
            i.getDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        } else {
           i.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        }
    }

    public void setNewBrew(Brewery b) {
        newBrew = b;

    }
}