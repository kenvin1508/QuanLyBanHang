package vn.edu.vtn.quanlybanhang.signup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.vtn.quanlybanhang.R;
import vn.edu.vtn.quanlybanhang.data.model.Customer;
import vn.edu.vtn.quanlybanhang.login.LoginActivity;
import vn.edu.vtn.quanlybanhang.data.api.remote.APIService;
import vn.edu.vtn.quanlybanhang.data.api.remote.APIUtils;
import vn.edu.vtn.quanlybanhang.main.MainActivity;

public class SignUpActivity extends AppCompatActivity implements SignUpMvpView, View.OnClickListener {
    TextInputLayout txtName, txtPhone, txtLoginEmail, txtLoginPassword, txtBirthDay, txtUserName;
    TextInputEditText txtBirthDayET;
    TextView txtHaveAccount;
    Button btnSignup;
    RadioButton radioMale, radioFemale;
    RadioGroup radioGroup;
    String sex = "Nam";

    SignUpMvpPresenter signUpPresenter;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        addControls();
        addEvents();
    }


    private void addControls() {
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtLoginEmail = findViewById(R.id.txtLoginEmail);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);
        txtHaveAccount = findViewById(R.id.txtHaveAccount);
        txtBirthDay = findViewById(R.id.txtBirthDay);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioGroup = findViewById(R.id.radioGroup);
        txtBirthDayET = findViewById(R.id.txtBirthDayET);
        txtUserName = findViewById(R.id.txtUsername);


        btnSignup = findViewById(R.id.btnSignup);


        signUpPresenter = new SignUpPresenter(SignUpActivity.this, this);
        btnSignup.setOnClickListener(this);
        txtHaveAccount.setOnClickListener(this);
    }

    private void addEvents() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioMale) {
                    sex = "Nam";
                }
                if (checkedId == R.id.radioFemale) {
                    sex = "Nu";
                }
            }
        });
        txtBirthDayET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProcessShowDatePicker();
            }
        });

    }

    @Override
    public void onSuscess(String mess) {
        Toast.makeText(SignUpActivity.this, mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String mess) {
        Toast.makeText(SignUpActivity.this, mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckInputData(String mess) {
        Toast.makeText(SignUpActivity.this, mess, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOpenLoginActivity() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignup:
                toProcessSignup();
                break;
            case R.id.txtHaveAccount:
                signUpPresenter.onClickOpenLogin();
                break;
        }
    }

    private void toProcessSignup() {
        String name = txtName.getEditText().getText().toString();
        String phone = txtPhone.getEditText().getText().toString();
        String email = txtLoginEmail.getEditText().getText().toString();
        String password = txtLoginPassword.getEditText().getText().toString();
        String birthday = txtBirthDay.getEditText().getText().toString();
        String username = txtUserName.getEditText().getText().toString();

        Calendar cal = Calendar.getInstance();
        String registrationDate = sdf.format(cal.getTime());

        Customer customer = new Customer(username, password, name, phone, email, sex, birthday, registrationDate);
        signUpPresenter.onLoadData(customer);
    }

    private void toProcessShowDatePicker() {

        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                txtBirthDayET.setText(sdf.format(calendar.getTime()));
            }
        };
        DatePickerDialog dpd = new DatePickerDialog(SignUpActivity.this, callBack, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }
}
