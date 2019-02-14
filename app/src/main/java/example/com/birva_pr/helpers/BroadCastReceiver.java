package example.com.birva_pr.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.util.HashSet;
import java.util.Set;

public class BroadCastReceiver extends BroadcastReceiver {
    private static final int TYPE_WIFI = 1;
    private static final int TYPE_MOBILE = 2;
    private static final int TYPE_NOT_CONNECTED = 0;
    WifiManager wifiManager;
    ConnectivityManager connectivityManager;
    Context context;
    protected Set<ConnectivityReceiverListener> listeners;
    protected Boolean dataConnected;
    protected Boolean wifiConnected;
    protected String strConnectionMsg;

    public BroadCastReceiver() {
        listeners=new HashSet<ConnectivityReceiverListener>();
        strConnectionMsg="";
        wifiConnected=null;
        dataConnected=null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(null != networkInfo) {
            if (getConnectivityStatus(context) == 1) {
                wifiConnected = true;
                strConnectionMsg = "WIFI connected";
            }
            else if (getConnectivityStatus(context) == 2) {
                dataConnected = true;
                strConnectionMsg = "DATA Connected";
            }
        }
        else
        {
            dataConnected = false;
            wifiConnected = false;
            strConnectionMsg = "WIFI or DATA not Connected";
        }
        notifyStateToAll();
    }

    private void notifyStateToAll( ) {
        for(ConnectivityReceiverListener listener : listeners)
            notifyState(listener);
    }

    private void notifyState(ConnectivityReceiverListener listener) {
        if(wifiConnected == null || dataConnected==null || listener == null)
            return;
        listener.message(strConnectionMsg);

    }

    public void addListener(ConnectivityReceiverListener l) {
        listeners.add(l);
        notifyState(l);
    }

    public void removeListener(ConnectivityReceiverListener l) {
        listeners.remove(l);
    }

    public interface ConnectivityReceiverListener {
        public void message(String strConnectionMsg);
    }
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public int getConnectedType()
    {
        return getConnectivityStatus(context);
    }

}
