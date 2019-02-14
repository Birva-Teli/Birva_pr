package example.com.birva_pr;

import android.view.View;

import example.com.birva_pr.database.UserDetailsBean;

public interface OnOptionClickListener {
    void onOptionClick(int Position, UserDetailsBean userDetailsBean, View view);
}
