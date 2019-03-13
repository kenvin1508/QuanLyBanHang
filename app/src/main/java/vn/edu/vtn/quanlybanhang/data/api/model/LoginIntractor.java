package vn.edu.vtn.quanlybanhang.data.api.model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.vtn.quanlybanhang.ProgressDialogF;
import vn.edu.vtn.quanlybanhang.data.api.APIHelper;
import vn.edu.vtn.quanlybanhang.data.api.remote.APIService;
import vn.edu.vtn.quanlybanhang.data.api.remote.APIUtils;
import vn.edu.vtn.quanlybanhang.data.model.Customer;
import vn.edu.vtn.quanlybanhang.data.prefs.SharedPrefsHelper;

public class LoginIntractor {
    APIHelper apiHelper;
    APIService service;
    SharedPrefsHelper sharedPrefsHelper;
    Context context;

    public LoginIntractor(APIHelper apiHelper, Context context) {
        this.apiHelper = apiHelper;
        this.context = context;
        service = APIUtils.getServer();
        sharedPrefsHelper = new SharedPrefsHelper(context);
    }

    public void initRetrofitCall(String email, String password) {
        ProgressDialogF.showLoading(context);
        service.getInfo(email, password).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                Log.d("AAAA", response.code() + "");
                if (response.code() == 200) {
                    sharedPrefsHelper.setLoggedin(true, response.body());
                    apiHelper.onSucess("Login Success !!!");
                    ProgressDialogF.hideLoading();
                } else {
                    apiHelper.onFailure("Username or Password is incorrect");
                    ProgressDialogF.hideLoading();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                apiHelper.onFailure("onFailure " + t.toString());
                ProgressDialogF.hideLoading();
            }
        });
    }
}
