package vn.edu.vtn.quanlybanhang.productList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.vtn.quanlybanhang.R;
import vn.edu.vtn.quanlybanhang.apdater.ProductListAdapter;
import vn.edu.vtn.quanlybanhang.data.api.remote.APIService;
import vn.edu.vtn.quanlybanhang.data.api.remote.APIUtils;
import vn.edu.vtn.quanlybanhang.data.model.PagingProduct;
import vn.edu.vtn.quanlybanhang.data.model.Product;
import vn.edu.vtn.quanlybanhang.productdetail.ProductDetailActivity;

public class ProductListFragment extends Fragment implements ProductListMvpView {
    RecyclerView rvProductList;
    ProductListAdapter productListAdapter;
    ProductListMvpPresenter presenter;
    LinearLayoutManager layoutManager;
    ProgressWheel progressWheel;

    APIService service = APIUtils.getServer();

    int id;
    int page = 1;
    int size = 10;

    ProgressBar progressBar;
    Boolean isScrolling = false;
    Boolean lastPage = true;
    int currentItem, totalItem, scrollOutItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        rvProductList = view.findViewById(R.id.rv_product_list);
        progressBar = view.findViewById(R.id.progressBar);
//        progressWheel = view.findViewById(R.id.progress_wheel);
//        progressWheel.setProgress(1);
        presenter = new ProductListPresenter(this, getContext());
        layoutManager = new LinearLayoutManager(getContext());

        if (getArguments() != null) {
            id = getArguments().getInt("position"); // Get position from ProductNestedRVAdapter when you click MORE
            PagingProduct pagingProduct = new PagingProduct(id, page, size);
            Log.d("IDD", id + "");
            presenter.getData(pagingProduct);
        }


        return view;
    }

    public void loadMore() {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PagingProduct pagingProduct = new PagingProduct(id, page, size);
                service.getProductLists(pagingProduct).enqueue(new Callback<ArrayList<Product>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                        if (response.body() != null) {
                            productListAdapter.addProducts(response.body());
                        } else {
                            lastPage = false;
                            Log.d("AAAA", "K C G");
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

                    }
                });
                progressBar.setVisibility(View.GONE); // Ẩn và mất khỏi vị trí hiện tại
            }
        }, 1500);
    }

    @Override
    public void onCallBackDataSucess(final ArrayList<Product> list) {
        productListAdapter = new ProductListAdapter(getContext(), list);

        rvProductList.setLayoutManager(layoutManager);
        rvProductList.setAdapter(productListAdapter);

        productListAdapter.setOnItemClickListener(new ProductListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) { // Click into item of Product list to open Product Detail
                Product product = list.get(position);
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtra("PRODUCTLIST", product);
                startActivity(intent);

            }
        });

        rvProductList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) // Nếu cái trạng thái là là nhấn  , giữ, cuộn
                    isScrolling = true; // Set bằng true cho biết user đang thực hiện cuộn với state trên
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = layoutManager.getChildCount(); // Số lượng item hiện tại đang xuất hiện trên màn hình
                totalItem = layoutManager.getItemCount(); // Tổng số lượng item
                scrollOutItem = layoutManager.findFirstVisibleItemPosition(); // Số lượng item đã xuất hiện khi cuộn

                if (isScrolling && (currentItem + scrollOutItem == totalItem)) {
                    isScrolling = false;
                    Log.d("AAAA", "currentItem : " + layoutManager.getChildCount()
                            + " totalItem : " + layoutManager.getItemCount() + " scrollOutItem : " + layoutManager.findFirstVisibleItemPosition());
                    if (lastPage) {
                        page++;
                        loadMore();
                    }

                    Log.d("AAAA", page + "" + "List: " + list.size());
                }
            }
        });

    }

    @Override
    public void onCallBackDataFailure(String mess) {
        Log.d("ERROR", mess);
    }
}

