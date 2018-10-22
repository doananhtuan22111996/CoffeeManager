package com.tuan.coffeemanager.feature.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseFragment;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.coffee.CoffeeActivity;
import com.tuan.coffeemanager.feature.featureBartender.OrderBartenderActivity;
import com.tuan.coffeemanager.feature.featureManager.main.MainManagerActivity;
import com.tuan.coffeemanager.feature.main.fragment.listener.ISignInListener;
import com.tuan.coffeemanager.feature.main.reset.ResetPasswordActivity;
import com.tuan.coffeemanager.feature.main.fragment.presenter.SignInPresenter;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.ext.KeyBoardExt;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignInFragment extends BaseFragment implements ISignInListener.ISignInViewListener {

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.tvResetPassword)
    TextView tvResetPassword;
    Unbinder unbinder;

    private SignInPresenter signInPresenter;

    //Hàm khởi tạo Fragment
    public static SignInFragment newInstance() {
        Bundle args = new Bundle();
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //Hàm khởi tạo View Fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    //Hàm sau khi View đã được khởi tạo
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signInPresenter = new SignInPresenter(this);
        event();
    }

    //Hàm xự kiện Click
    private void event() {
        //2. Nhấn button Sign in
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide keyboard
                KeyBoardExt.hideKeyBoard(getActivity());
                //2.1 Get email Sign in
                String email = edtEmail.getText().toString().trim();
                //2.2 Get password Sign in
                String password = edtPassword.getText().toString().trim();
                checkInputSignIn(email, password);
            }
        });

        //Xự kiện click Reset password
        tvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //Email không hợp lệ
    private Boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //3. Xử lý check Email và Password
    private void checkInputSignIn(String email, String password) {
        if (email.isEmpty()) {
            //Email rỗng
            Toast.makeText(getActivity(), ConstApp.SIGN_IN_E001, Toast.LENGTH_SHORT).show();
        } else if (!isValidEmail(email)) {
            //Email không hợp lệ
            Toast.makeText(getActivity(), ConstApp.SIGN_IN_E002, Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            //Password rỗng
            Toast.makeText(getActivity(), ConstApp.SIGN_IN_E003, Toast.LENGTH_SHORT).show();
        } else {
            //Show loading
            showLoading();
            //Xử lý Đăng nhập
            signInPresenter.signIn(email, password);
        }
    }

    //5. Nhận dữ liệu và kết thúc đăng nhập
    @Override
    public void signInSuccess(User user) {
        hideLoading();
        if (getActivity() != null) {
            //Lưu thông tin user vào file
            DataUtil.newInstance(getActivity()).setDataUser(user);
            //Kiểm tra đối tượng -> chuyển màn hình
            //user.getPosition() lấy vị trí user
            // startActivity() chuyển màn hình
            switch (user.getPosition()) {
                case ConstApp.EMPLOYEE:
                    //Employee
                    startActivity(new Intent(getActivity(), CoffeeActivity.class));
                    getActivity().finish();
                    break;
                case ConstApp.BARTENDER_POSITION:
                    //Bartender
                    startActivity(new Intent(getActivity(), OrderBartenderActivity.class));
                    getActivity().finish();
                    break;
                default:
                    //Manager
                    startActivity(new Intent(getActivity(), MainManagerActivity.class));
                    getActivity().finish();
                    break;
            }
            //Hiển thị thông báo đăng nhập thành công
            Toast.makeText(getActivity(), ConstApp.SIGN_IN_E005, Toast.LENGTH_SHORT).show();
        }
    }

    //Hàm xử lý đăng nhập thất bại
    @Override
    public void signInFailure(String error) {
        //Hide loading
        hideLoading();
        //Hiển thị thông báo đăng nhập thất bại
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
