package example.com.birva_pr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoInternetConnectivityFragment extends Fragment {
    public NoInternetConnectivityFragment() {
    }
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.no_internet_connectivity_fragment, container, false);
}
}
