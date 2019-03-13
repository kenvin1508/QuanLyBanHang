package vn.edu.vtn.quanlybanhang.profiledetail;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import vn.edu.vtn.quanlybanhang.R;
import vn.edu.vtn.quanlybanhang.data.model.Customer;
import vn.edu.vtn.quanlybanhang.data.model.Password;

public class ProfileDetailActivity extends AppCompatActivity implements ProfileDetailMvpView {
    Customer customer, customerUpdate;
    TextInputLayout txtName, txtBirthDay, txtOldPass, txtNewPass, txtNewPassAgain, txtLoginEmail;
    TextInputEditText txtBirthDayET, txtNameET, txtLoginEmailET;
    RadioGroup radioGroup;
    String sex = "Nam";
    CheckBox ckChagePass;
    Button btnSave;
    LinearLayout llChangePass;
    Toolbar toolbar;

    ProfileDetailMvpPresenter presenter;
    boolean isVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("CUSTOMER");
        presenter = new ProfileDetailPresenter(ProfileDetailActivity.this, this);

        addControls();
        addEvents();
    }

    private void addControls() {
        txtName = findViewById(R.id.txtName);
        txtBirthDay = findViewById(R.id.txtBirthDay);
        txtOldPass = findViewById(R.id.txtOldPass);
        txtNewPass = findViewById(R.id.txtNewPass);
        txtNewPassAgain = findViewById(R.id.txtNewPassAgain);
        txtBirthDayET = findViewById(R.id.txtBirthDayET);
        radioGroup = findViewById(R.id.radioGroup);
        ckChagePass = findViewById(R.id.ckChagePass);
        btnSave = findViewById(R.id.btnSave);
        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtNameET = findViewById(R.id.txtNameET);
        txtLoginEmailET = findViewById(R.id.txtLoginEmailET);
        llChangePass = findViewById(R.id.llChangePass);
        toolbar = findViewById(R.id.toolbar) ;

        txtNameET.setText(customer.getName());
        txtLoginEmailET.setText(customer.getEmail());
        txtBirthDayET.setText(customer.getBirthday());

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Đơn hàng");
        }


    }

    private void addEvents() {
        ckChagePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llChangePass.setVisibility(View.VISIBLE);
                    isVisible = true;
                } else {
                    llChangePass.setVisibility(View.GONE);
                    isVisible = false;
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProcessUpdate();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioMale) {
                    sex = "Nam";
                }
                if (checkedId == R.id.radioFemale) {
                    sex = "Nữ";
                }
            }
        });
    }


    private void toProcessUpdate() {
        String name = txtName.getEditText().getText().toString();
        String birthDay = txtBirthDay.getEditText().getText().toString();

        customerUpdate = new Customer(customer.getIdKhachHang(), name, birthDay, sex);

        if (isVisible) {
            toProcesChangePass();
        }
        presenter.onUpdateProfile(customerUpdate); // Update user profile


    }

    private void toProcesChangePass() {
        String newPassword = txtNewPassAgain.getEditText().getText().toString();
        String oldPassword = txtOldPass.getEditText().getText().toString();
        Password passwordM = new Password(customer.getIdKhachHang(), oldPassword, newPassword);
        if (txtOldPass.getError() != null) {
            txtOldPass.setError(null);
        }
        if (!confirmPassInput()) {
            return;
        }
        presenter.onChangePass(passwordM); // Change password
    }

    @Override
    public void onUpdateSuccess(String mess) {
        Log.d("AAAA", mess);
    }

    @Override
    public void onUpdateFailure(String mess) {
        Log.d("AAAA", mess);
    }

    @Override
    public void onCheckPassFailure(String mess) {
        txtOldPass.setError(mess);
    }

    @Override
    public void onCheckPassSucess(String mess) {
        Log.d("AAAA", mess);
    }

    private boolean validateName() {
        String nameInput = txtName.getEditText().getText().toString();
        if (nameInput.isEmpty()) {
            txtName.setError("Tên không được bỏ trống !!!");
            return false;
        } else {
            txtName.setError(null);
            return true;
        }
    }

    private boolean validateOldPass() {
        String oldPassInput = txtOldPass.getEditText().getText().toString();
        if (oldPassInput.isEmpty()) {
            txtOldPass.setError("Xin vui lòng nhập vào mật khẩu cũ !!!");
            return false;
        } else {
            txtOldPass.setError(null);
            return true;
        }
    }

    private boolean validateNewPass() {
        String newPassInput = txtNewPass.getEditText().getText().toString();
        if (newPassInput.isEmpty()) {
            txtNewPass.setError("Xin vui lòng nhập vào mật khẩu mới !!!");
            return false;
        } else {
            txtNewPass.setError(null);
            return true;
        }
    }

    private boolean validateNewPassAgain() {
        String newPassAgainInput = txtNewPassAgain.getEditText().getText().toString();
        String newPassInput = txtNewPass.getEditText().getText().toString();
        if (newPassAgainInput.isEmpty()) {
            txtNewPassAgain.setError("Xin vui lòng nhập lại mật khẩu mới !!!");
            return false;
        } else if (!newPassAgainInput.equals(newPassInput)) {
            txtNewPassAgain.setError("Mật khẩu nhập lại không giống !!!");
            return false;
        } else {
            txtNewPassAgain.setError(null);
            return true;
        }
    }

    public boolean confirmPassInput() {
        return validateOldPass() && validateNewPass()
                && validateNewPassAgain();

    }
}
