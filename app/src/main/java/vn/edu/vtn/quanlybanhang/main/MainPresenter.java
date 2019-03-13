package vn.edu.vtn.quanlybanhang.main;

import android.content.Context;

import vn.edu.vtn.quanlybanhang.data.prefs.SharedPrefsHelper;

public class MainPresenter implements MainMvpPresenter {
    private MainMvpView view;
    private Context context;
    private SharedPrefsHelper sharedPrefsHelper;

    public MainPresenter(MainMvpView view, Context context) {
        this.context = context;
        this.view = view;
        sharedPrefsHelper = new SharedPrefsHelper(context);
    }

    @Override
    public void onClickHomePage() {
        view.onOpenHomePage();
    }

    @Override
    public void onProductCategory() {
        view.onOpenProductCategory();
    }

    @Override
    public void onClickOderList() {
        if (sharedPrefsHelper.checklogin()) { //check login ????
            view.onOpenOderList();
        } else {
            view.onOpenLogin();
        }
    }

    @Override
    public void onClickFavoriteProducts() {
        if (sharedPrefsHelper.checklogin()) { //check login ????
            view.onOpenFavoriteProducts();
        } else {
            view.onOpenLogin();
        }

    }

    @Override
    public void onClickProfile() {
        if (sharedPrefsHelper.checklogin()) { //check login ????
            view.onOpenProfile();
        } else {
            view.onOpenLogin();
        }
    }

    @Override
    public void onClickNotification() {
        view.onOpenNotification();
    }

    @Override
    public void onClickSuport() {
        view.onOpenSuport();
    }
}
