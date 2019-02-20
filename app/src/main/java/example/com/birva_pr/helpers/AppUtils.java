package example.com.birva_pr.helpers;

import android.util.Log;

public class AppUtils{
    private static boolean isDebug=true;
    public static void showLog(String message){
        if(isDebug)
            Log.d(AppConstants.AppName,message);
    }

}
