package brewfindvt.android;

import android.app.ActionBar;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private TextView _addr1;
    private TextView _addr2;
    private TextView _cityPlus;
    private TextView _phone;
    private TextView _email;
    private TextView _desc;
    private TextView _rating;

    private ImageView _logo;
    private ImageView _hasFood;
    private ImageView _hasTour;
    private ImageView _hasGrowler;
    private ImageView _hasTap;

    private Button _contactInfoButton;
    private Button _goToMapButton;

    private ExpandableRelativeLayout _contactLayout;

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
        _addr1 = (TextView) getActivity().findViewById(R.id.addr1);
        _addr2 = (TextView) getActivity().findViewById(R.id.addr2);
        _cityPlus = (TextView) getActivity().findViewById(R.id.cityPlus);
        _phone = (TextView) getActivity().findViewById(R.id.phoneNumber);
        _email = (TextView) getActivity().findViewById(R.id.email);
        _rating = (TextView) getActivity().findViewById(R.id.rating);

        _logo = (ImageView) getActivity().findViewById(R.id.logoImageView);
        _hasFood = (ImageView) getActivity().findViewById(R.id.hasFood);
        _hasTour = (ImageView) getActivity().findViewById(R.id.hasTour);
        _hasGrowler = (ImageView) getActivity().findViewById(R.id.hasGrowler);
        _hasTap = (ImageView) getActivity().findViewById(R.id.hasTap);

        _contactInfoButton = (Button) getActivity().findViewById(R.id.contactInfoButton);
        _contactLayout = (ExpandableRelativeLayout) getActivity().findViewById(R.id.contactInfoView);

        _goToMapButton = (Button) getActivity().findViewById(R.id.mapButton);

        _goToMapButton.setOnClickListener(this);
        _contactInfoButton.setOnClickListener(this);

        if(newBrew != null) {
            populateBrewery(newBrew);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.contactInfoButton:
                _contactLayout.toggle();
                break;
            case R.id.mapButton:
                goToMap();
                break;
        }
    }

    public void goToMap() {
        String newName = newBrew.b_name.replace(" ", "+");
        String url = "http://www.google.com/maps/place/" + newName;

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void getUntappdInfo(int brewNum) {

        List<Drink> cacheDrinks = cacheManager.getDrinks(brewNum);
        Rating cacheRating = cacheManager.getRating(brewNum);
        if(cacheDrinks != null && cacheRating != null) {
            drinkList = cacheDrinks;
            rating = cacheRating;
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
        _rating.setText("Untappd Rating: " + rating.getRating() + "/5");

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
        newButton.setId(buttonId);
        String buttonTitle = toMake.getD_name() + " - " + toMake.getD_style();
        newButton.setText(buttonTitle);
        newButton.setCompoundDrawables(null, null, null, getResources().getDrawable(android.R.drawable.arrow_down_float));
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int toId = v.getId();
                ExpandableRelativeLayout lay = (ExpandableRelativeLayout) getActivity().findViewById(toId + 666);
                Toast.makeText(getActivity().getApplicationContext(), String.valueOf(getAllChildren(lay).size()), Toast.LENGTH_LONG).show();
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
        newLayout.setBackgroundColor(Color.GRAY);

        LinearLayout.LayoutParams fullHeightParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams sharedHeightParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.25f);

        LinearLayout big = new LinearLayout(getActivity().getApplicationContext());
        big.setOrientation(LinearLayout.HORIZONTAL);
        big.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 400));

        LinearLayout left = new LinearLayout(getActivity().getApplicationContext());
        left.setOrientation(LinearLayout.VERTICAL);
        left.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.6f));

        TextView description = new TextView(getActivity().getApplicationContext());
        description.setText(toMake.getD_description());
        description.setLayoutParams(fullHeightParams);
        description.setTextColor(Color.BLACK);

        LinearLayout right = new LinearLayout(getActivity().getApplicationContext());
        right.setOrientation(LinearLayout.VERTICAL);
        right.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.4f));

        TextView ibu = new TextView(getActivity().getApplicationContext());
        String ibuText = "IBU: " + toMake.getD_ibu();
        ibu.setText(ibuText);
        ibu.setLayoutParams(sharedHeightParams);
        ibu.setTextColor(Color.BLACK);

        TextView abv = new TextView(getActivity().getApplicationContext());
        String abvText = "ABV: " + toMake.getD_abv();
        abv.setText(abvText);
        abv.setLayoutParams(sharedHeightParams);
        abv.setTextColor(Color.BLACK);

        TextView dRating = new TextView(getActivity().getApplicationContext());
        String ratingText = "Rating: " + toMake.getD_rating() + "/5";
        dRating.setText(ratingText);
        dRating.setLayoutParams(sharedHeightParams);
        dRating.setTextColor(Color.BLACK);

        _drinkList.addView(newButton);

        newLayout.addView(big);
        big.addView(left);
        big.addView(right);
        left.addView(description);
        right.addView(abv);
        right.addView(ibu);
        right.addView(dRating);
        _drinkList.addView(newLayout);

    }

    public void populateBrewery(Brewery brew) {

        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(true);
        dialog.show();
        dialog.setMessage("Loading brewery info...");

        _drinkList.removeAllViews();
        getUntappdInfo(brew.getB_breweryNum());

        _name.setText(brew.getB_name());
        _addr1.setText(brew.getB_addr1());
        if(brew.getB_addr2() == null || brew.getB_addr2() == "") {
            _addr2.setVisibility(View.GONE);
        } else {
            _addr2.setText(brew.getB_addr2());
        }

        String cityPlus = brew.getB_city() + ", " + brew.getB_state() + " " + brew.getB_zip();
        _cityPlus.setText(cityPlus);

        _phone.setText(brew.getB_phone());
        _email.setText(brew.getB_email());

        _logo.setImageBitmap(cacheManager.getLogo(brew.getB_breweryNum()));

        if(brew.getB_hasFood() == null) {
            _hasFood.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        } else if (brew.getB_hasFood()) {
            _hasFood.getDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        } else {
            _hasFood.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        }

        if(brew.getB_hasTour() == null) {
            _hasTour.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        } else if (brew.getB_hasTour()) {
            _hasTour.getDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        } else {
            _hasTour.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        }

        if(brew.getB_hasGrowler() == null) {
            _hasGrowler.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        } else if (brew.getB_hasGrowler()) {
            _hasGrowler.getDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        } else {
            _hasGrowler.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        }

        if(brew.getB_hasTap() == null) {
            _hasTap.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        } else if (brew.getB_hasTap()) {
            _hasTap.getDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
        } else {
            _hasTap.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        }

    }

    public void setNewBrew(Brewery b) {
        newBrew = b;

    }
}