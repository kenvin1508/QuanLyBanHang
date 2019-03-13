package vn.edu.vtn.quanlybanhang.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.edu.vtn.quanlybanhang.R;
import vn.edu.vtn.quanlybanhang.data.model.Customer;
import vn.edu.vtn.quanlybanhang.data.prefs.SharedPrefsHelper;
import vn.edu.vtn.quanlybanhang.main.MainActivity;
import vn.edu.vtn.quanlybanhang.productdetail.ProductDetailActivity;
import vn.edu.vtn.quanlybanhang.profiledetail.ProfileDetailActivity;

public class ProfileActivity extends AppCompatActivity implements ProfileMvpView {
    TextView txtProfileName, txtProfilePhone, txtDateRegister;
    ImageView imgEditProfile;
    Customer customer;
    ProfileMvpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        addControls();
        addEvents();
    }

    private void addEvents() {
        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileDetailActivity.class);
                intent.putExtra("CUSTOMER", customer);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        txtProfileName = findViewById(R.id.txtProfileName);
        txtProfilePhone = findViewById(R.id.txtProfilePhone);
        imgEditProfile = findViewById(R.id.imgEditProfile);
        txtDateRegister = findViewById(R.id.txtDateRegister);
        presenter = new ProfilePresenter(ProfileActivity.this, this);


    }

    public void toProcessLogout(View view) {
        presenter.onLogOut();

    }

    @Override
    public void onSetView(Customer customer) {
        txtProfileName.setText(customer.getName());
        txtProfilePhone.setText(customer.getPhone());
        txtDateRegister.setText(customer.getRegistrationDate());
    }

    @Override
    public void onSetLogOut() {
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }
}
