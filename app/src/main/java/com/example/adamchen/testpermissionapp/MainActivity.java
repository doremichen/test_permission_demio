package com.example.adamchen.testpermissionapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "PermDemo";
    public static final int START_SERVICE_PERMISSION = 0;
    public static final int STOP_SERVICE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartSvr(View v) {

        int permission = ActivityCompat.checkSelfPermission(this, "com.adam.app.permission.SECUR_SERVICE");

        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "com.adam.app.permission.SECUR_SERVICE")) {
                ActivityCompat.requestPermissions(this, new String[]{"com.adam.app.permission.SECUR_SERVICE"}, START_SERVICE_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{"com.adam.app.permission.SECUR_SERVICE"}, START_SERVICE_PERMISSION);
            }
        } else {
            startRemoteService();
        }


    }


    public void onExit(View v) {

        int permission = ActivityCompat.checkSelfPermission(this, "com.adam.app.permission.SECUR_SERVICE");

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            //Start dialog to tell user permission usage
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "com.adam.app.permission.SECUR_SERVICE")) {
                // try again to request the permission.
                ActivityCompat.requestPermissions(this, new String[]{"com.adam.app.permission.SECUR_SERVICE"}, STOP_SERVICE_PERMISSION);
            } else {
                // No explanation needed; request the permission The callback method gets the
                // result of the request.
                ActivityCompat.requestPermissions(this, new String[]{"com.adam.app.permission.SECUR_SERVICE"}, STOP_SERVICE_PERMISSION);
            }
        } else {
            stopRemoteService();
            this.finish();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == START_SERVICE_PERMISSION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRemoteService();
            } else {
                // permission denied
                Toast.makeText(this, "start Permission denied", Toast.LENGTH_SHORT).show();
            }


        } else if (requestCode == STOP_SERVICE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                stopRemoteService();
                this.finish();
            } else {
                Toast.makeText(this, "stop Permission denied", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void startRemoteService() {
        showInfo("[startRemoteService] enter");
        Intent it = new Intent();
        it.setClassName("com.adam.app.demoset", "com.adam.app.demoset.SecurService");
        this.startForegroundService(it);
        showInfo("[startRemoteService] exit");
    }


    private void stopRemoteService() {
        showInfo("[stopRemoteService] enter");
        Intent it = new Intent();
        it.setClassName("com.adam.app.demoset", "com.adam.app.demoset.SecurService");
        stopService(it);
        showInfo("[stopRemoteService] exit");
    }

    private void showInfo(String message) {
        Log.i(TAG, message);
    }

}
