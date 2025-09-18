package com.soniqueforge.soniqueforge.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class InternetConnectivity {
    public void isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCallback networkCallback = new NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                Toast.makeText(context, "Network Available: ", Toast.LENGTH_SHORT).show();
            }
            
            @Override
            public void onLost(@NonNull Network network) {
                Toast.makeText(context, "Network Lost", Toast.LENGTH_SHORT).show();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        }
    }
}
