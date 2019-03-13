package vn.edu.vtn.quanlybanhang.profile;

import android.content.Context;

import com.google.gson.Gson;

import vn.edu.vtn.quanlybanhang.data.model.Customer;
import vn.edu.vtn.quanlybanhang.data.prefs.SharedPrefsHelper;

public class ProfilePresenter implements ProfileMvpPresenter {
    private Context context;
    private ProfileMvpView view;
    SharedPrefsHelper sharedPrefsHelper;

    public ProfilePresenter(Context context, ProfileMvpView view) {
        this.context = context;
        this.view = view;
        sharedPrefsHelper = new SharedPrefsHelper(context);
    }

    @Override
    public void onGetView() {
        String json = sharedPrefsHelper.sharedPreferences.getString("CUSTOMER", "");
        Customer customer = new Gson().fromJson(json, Customer.class);
        view.onSetView(customer);
    }

    @Override
    public void onLogOut() {
        sharedPrefsHelper.setLoggedin(false, null);
        view.onSetLogOut();
    }
}
