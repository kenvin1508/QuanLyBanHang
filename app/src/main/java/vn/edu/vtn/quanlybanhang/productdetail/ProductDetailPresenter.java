package vn.edu.vtn.quanlybanhang.productdetail;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.vtn.quanlybanhang.util.ProgressDialogF;
import vn.edu.vtn.quanlybanhang.data.api.remote.APIService;
import vn.edu.vtn.quanlybanhang.data.api.remote.APIUtils;
import vn.edu.vtn.quanlybanhang.data.model.Customer;
import vn.edu.vtn.quanlybanhang.data.model.Product;
import vn.edu.vtn.quanlybanhang.data.model.ProductFavorite;
import vn.edu.vtn.quanlybanhang.data.prefs.SharedPrefsHelper;

public class ProductDetailPresenter implements ProductDetailMvpPresenter {
    private Context context;
    private ProductDetailMvpView view;
    private APIService service;
    private SharedPrefsHelper sharedPrefsHelper;

    public ProductDetailPresenter(Context context, ProductDetailMvpView view) {
        this.context = context;
        this.view = view;
        service = APIUtils.getServer();
        sharedPrefsHelper = new SharedPrefsHelper(context);
    }

    @Override
    public void onSetViewProductDetail(Product product) {
        view.setViewProductDetail(product);
    }

    @Override
    public void onSetViewBottomSheet(Product product) {
        view.setViewBottomSheet(product);
    }

    @Override
    public void onSetLikeProduct(Product product) {
        String json = sharedPrefsHelper.sharedPreferences.getString("CUSTOMER", "");
        Customer customer = new Gson().fromJson(json, Customer.class);
        ProductFavorite productFavorite = new ProductFavorite(product.getIdSanPham(), customer.getIdKhachHang());
        ProgressDialogF.showLoading(context);
        service.setProductFavorite(productFavorite).enqueue(new Callback<ProductFavorite>() {
            @Override
            public void onResponse(Call<ProductFavorite> call, Response<ProductFavorite> response) {
                Log.d("AAAA", response.code() + "");
                if (response.code() == 200) {
                    view.setLikeProduct(response.body());
                    Log.d("AAAA", "zôzzozo");
                }
                ProgressDialogF.hideLoading();

            }

            @Override
            public void onFailure(Call<ProductFavorite> call, Throwable t) {
                Log.d("AAAA", "Failure :" + t.toString());
                ProgressDialogF.hideLoading();
            }
        });
    }

    @Override
    public void onSetDislikeProduct(int id) {
        // Log.d("AAAAB", id + "");
        ProgressDialogF.showLoading(context);
        service.deleteProductFavorite(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("AAAA", response.code() + "");
                ProgressDialogF.hideLoading();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("AAAA", t.toString());
                ProgressDialogF.hideLoading();
            }
        });
    }
}
