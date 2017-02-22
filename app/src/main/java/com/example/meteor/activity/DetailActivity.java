package com.example.meteor.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.widget.NestedScrollView;
import android.view.MenuItem;
import android.view.View;

import com.example.meteor.R;
import com.example.meteor.databinding.ActivityDetailBinding;
import com.example.meteor.network.model.Meteor;
import com.example.meteor.ui.DetailView;
import com.example.meteor.viewmodel.DetailViewModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import timber.log.Timber;

/**
 * @author martin
 * @since 21/02/2017.
 */

public class DetailActivity extends BaseActivity<DetailView, DetailViewModel, ActivityDetailBinding> implements DetailView, OnMapReadyCallback {
    public static final String ARG_ID = "arg_id";
    private static final int ZOOM = 5;

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ARG_ID, id);
        return intent;
    }

    private GoogleMap mMap;
    private BottomSheetBehavior<NestedScrollView> mBottomSheetBehavior;

    @Override
    public ActivityDetailBinding setBindingContentView() {
        return DataBindingUtil.setContentView(this, R.layout.activity_detail);
    }

    @Nullable
    @Override
    public Class<DetailViewModel> getViewModelClass() {
        return DetailViewModel.class;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setModelView(this);

        setActionBar("", IndicatorType.BACK);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setupBottomSheet();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mMap == null) {
            mMap = googleMap;
            changeMapPaddingBottom(getBinding().detailBottomSheet.getHeight());

            mMap.setOnMapClickListener(latLng -> {
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            });
        }

        Meteor meteor = getViewModel().getMeteor();

        if (!meteor.isLoaded()) {
            meteor.addChangeListener(element -> onMapReady(mMap));
            return;
        }

        LatLng position = new LatLng(meteor.getLatitude(), meteor.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(position));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));

    }

    @Override
    public void showMeteor(Meteor meteor) {
        getBinding().setMeteor(meteor);
        getBinding().executePendingBindings();
    }

    private void setupBottomSheet() {
        mBottomSheetBehavior = BottomSheetBehavior.from(getBinding().detailBottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        changeMapPaddingBottom(mBottomSheetBehavior.getPeekHeight());
                        animateMapCamera(ZOOM);
                        Timber.d("State collapsed");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        changeMapPaddingBottom(getBinding().detailBottomSheet.getHeight());
                        animateMapCamera(-ZOOM);
                        Timber.d("State expanded");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void changeMapPaddingBottom(int padding) {
        mMap.setPadding(0, 0, 0, padding);
        onMapReady(mMap);
    }

    private void animateMapCamera(int zoom) {
        CameraUpdate zoomCamera = CameraUpdateFactory.zoomTo(zoom);
        new Handler().postDelayed(() -> mMap.animateCamera(zoomCamera), 100);
    }
}
