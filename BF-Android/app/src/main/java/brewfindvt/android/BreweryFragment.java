package brewfindvt.android;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import brewfindvt.managers.CacheManager;
import brewfindvt.objects.Brewery;

/**
 * Created by user on 2/16/2016.
 */
public class BreweryFragment extends android.support.v4.app.Fragment {

    private CacheManager cacheManager;
    public Map<Integer, Bitmap> imageMap;

    private TextView _name;
    private TextView _addr1;
    private TextView _addr2;
    private TextView _cityPlus;
    private TextView _phone;
    private TextView _email;

    private ImageView _logo;

    private ImageView _hasFood;
    private ImageView _hasTour;
    private ImageView _hasGrowler;
    private ImageView _hasTap;

    private TextView _desc;

    private Brewery newBrew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.brewery_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _name = (TextView) getActivity().findViewById(R.id.brewName);
        _addr1 = (TextView) getActivity().findViewById(R.id.addr1);
        _addr2 = (TextView) getActivity().findViewById(R.id.addr2);
        _cityPlus = (TextView) getActivity().findViewById(R.id.cityPlus);
        _phone = (TextView) getActivity().findViewById(R.id.phone);
        _email = (TextView) getActivity().findViewById(R.id.email);
       // _logo = (ImageView) getActivity().findViewById(R.id.logo);

        _hasFood = (ImageView) getActivity().findViewById(R.id.hasFood);
        _hasTour = (ImageView) getActivity().findViewById(R.id.hasTour);
        _hasGrowler = (ImageView) getActivity().findViewById(R.id.hasGrowler);
        _hasTap = (ImageView) getActivity().findViewById(R.id.hasTap);

        _desc = (TextView) getActivity().findViewById(R.id.descText);

        this.cacheManager = CacheManager.getInstance();
        this.imageMap = cacheManager.getB_logos();

        if(newBrew != null) {
            populateBrewery(newBrew);
        }
    }

    public void populateBrewery(Brewery brew) {
     //   _logo.setImageBitmap(imageMap.get(brew.getB_breweryNum()));
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
