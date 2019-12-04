package com.example.coolweather.wechat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.coolweather.R;
import com.example.coolweather.WeatherActivity;
import com.example.coolweather.lbsmainactivity;

import java.util.ArrayList;
import java.util.List;

public class fragment1 extends Fragment {
    public LocationClient mLocationClient;
    private MapView mapView;
    private TextView positionText;
    private BaiduMap baiduMap;
    private boolean isFirstLocate=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.lbs_activity_main, container, false);
        mapView = (MapView)view.findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        positionText = (TextView) view.findViewById(R.id.position_text_view);
        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest. permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) { permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest. permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) { permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest. permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) { permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {

            String [] permissions = permissionList.toArray(new String[permissionList. size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        } else { requestLocation();
        }
        return view;
    }
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    private void initLocation(){

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {

                        if (result != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                    }
                    requestLocation();
                } else {

                }
                break;
            default:
        }

    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("纬度：").append(location.getLatitude()).
                            append("\n");
                    currentPosition.append("经线：").append(location.getLongitude()).
                            append("\n");
                    currentPosition.append("国家：").append(location.getCountry()).
                            append("\n");
                    currentPosition.append("省：").append(location.getProvince()).
                            append("\n");
                    currentPosition.append("市：").append(location.getCity()).
                            append("\n");
                    currentPosition.append("区：").append(location.getDistrict()).
                            append("\n");
                    currentPosition.append("街道：").append(location.getStreet()).
                            append("\n");
                    Log.d("asd","ert");
                    if (location.getLocType() == BDLocation.TypeGpsLocation) {
                        Log.d("定位方式","GPS");

                    } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                        Log.d("定位方式","网络");
                    }
                    positionText.setText(currentPosition);
                }
            });
            if (location.getLocType() == BDLocation.TypeGpsLocation
                    || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
            }
        }
    }


    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
}
