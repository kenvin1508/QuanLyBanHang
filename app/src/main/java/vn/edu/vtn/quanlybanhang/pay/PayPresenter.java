package vn.edu.vtn.quanlybanhang.pay;


import android.content.Context;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.vtn.quanlybanhang.ProgressDialogF;
import vn.edu.vtn.quanlybanhang.data.api.remote.APIService;
import vn.edu.vtn.quanlybanhang.data.api.remote.APIUtils;
import vn.edu.vtn.quanlybanhang.data.model.bill.Bill;
import vn.edu.vtn.quanlybanhang.data.model.Cart;
import vn.edu.vtn.quanlybanhang.data.model.bill.ProductBill;
import vn.edu.vtn.quanlybanhang.data.model.address.AddressLists;
import vn.edu.vtn.quanlybanhang.data.prefs.SharedPrefsHelper;
import vn.edu.vtn.quanlybanhang.data.sqlite.SqliteController;

public class PayPresenter implements PayMvpPresenter {
    private Context context;
    private PayMvpView view;
    private SharedPrefsHelper sharedPrefsHelper;
    private SqliteController sqliteController;
    private APIService apiService;

    public PayPresenter(Context context, PayMvpView view) {
        this.context = context;
        this.view = view;
        sharedPrefsHelper = new SharedPrefsHelper(context);
        sqliteController = new SqliteController(context);
        apiService = APIUtils.getServer();

    }

    @Override
    public void onOpenAddressFragment() {
        view.openAddressFragment();
    }

    @Override
    public void onOpenPayFormFragment() {
        view.openPayFormFragment();
    }

    @Override
    public void onOpenPayLastFragment() {
        view.openPayLastFragment();
    }

    @Override
    public void onBackPressedOne() {
        view.backPressedOne();
    }

    @Override
    public void onBackPressedTwo() {
        view.backPressedTwo();
    }

    @Override
    public void onCreateBill() {
        String json = sharedPrefsHelper.sharedPreferences.getString("ADDRESS", "");
        AddressLists addressLists = new Gson().fromJson(json, AddressLists.class);
        ArrayList<Cart> cart = sqliteController.getDataCart();

        List<ProductBill> productBills = new ArrayList<>();// Tạo list sản phẩm
        for (int i = 0; i < cart.size(); i++) {
            productBills.add(new ProductBill(cart.get(i).getIdProduct(),
                    cart.get(i).getAmount(), cart.get(i).getSalePrice(),
                    cart.get(i).getAmount() * cart.get(i).getSalePrice()));
        }

        Calendar cal = Calendar.getInstance();//get current day
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String registrationDate = dateFormat.format(cal.getTime());

        Bill bill = new Bill(addressLists.getIdKhachHang(), 1,
                sqliteController.getTotalMoney(), registrationDate, addressLists.getSoDt()
                , addressLists.getIdDiaChiKhachHang(), "", productBills);

        ProgressDialogF.showLoadingDialog(context);
        apiService.createNewBill(bill).enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if (response.code() == 200) {
                    view.createBillSucess("Bạn đã đặt hàng thành công !!!");
                    sqliteController.toProcessDeleteAll(); // xóa giỏ hàng
                    ProgressDialogF.hideLoading();
                }

            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                view.createBillSucess(t.toString() + "");
                ProgressDialogF.hideLoading();
            }
        });
    }


}
