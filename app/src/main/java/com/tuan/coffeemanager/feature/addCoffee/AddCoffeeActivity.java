package com.tuan.coffeemanager.feature.addCoffee;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.ext.KeyBoardExt;
import com.tuan.coffeemanager.feature.addCoffee.listener.IAddCoffeeListener;
import com.tuan.coffeemanager.feature.addCoffee.presenter.AddCoffeePresenter;
import com.tuan.coffeemanager.model.Drink;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCoffeeActivity extends BaseActivity implements View.OnClickListener, IAddCoffeeListener.IAddCoffeeViewListener {

    private static final int REQUEST_ID_IMAGE_CAPTURE = 1;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivCoffee)
    ImageView ivCoffee;
    @BindView(R.id.edtNameCoffee)
    EditText edtNameCoffee;
    @BindView(R.id.edtDescriptionCoffee)
    EditText edtDescriptionCoffee;
    @BindView(R.id.edtPriceCoffee)
    EditText edtPriceCoffee;
    @BindView(R.id.btnSaveCoffee)
    Button btnSaveCoffee;
    @BindView(R.id.clContent)
    ConstraintLayout clContent;

    private Uri uri = null;
    private AddCoffeePresenter addCoffeePresenter;

    //Hàm khở tạo view
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coffee);
        ButterKnife.bind(this);

        init();
    }

    //Hàm khởi tạo sự kiên
    private void init() {
        clContent.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivCoffee.setOnClickListener(this);
        btnSaveCoffee.setOnClickListener(this);
        addCoffeePresenter = new AddCoffeePresenter(this);
    }

    //Hàm xử lý sự kiện click
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                onBackPressed();
                break;
            }
            //3. Nhấn Save
            case R.id.btnSaveCoffee: {
                KeyBoardExt.hideKeyBoard(this);
                //3.1 Get Name Coffee
                String name = edtNameCoffee.getText().toString().trim();
                //3.2 Get Description Coffee
                String description = edtDescriptionCoffee.getText().toString().trim();
                //3.3 Get Price Coffee
                String price = edtPriceCoffee.getText().toString().trim();
                //4. Xử lý check name coffee và price coffee
                if (checkAddCoffee(name, price)) {
                    showLoading();
                    requestAddCoffee(name, description, price);
                }
                break;
            }
            case R.id.clContent: {
                KeyBoardExt.hideKeyBoard(this);
                break;
            }
            //2. Nhấn Image Coffee
            case R.id.ivCoffee: {
                //2.1 Check Permission
                checkPermission();
                break;
            }
        }
    }

    //4. Xử lý check name coffee và price coffee
    private Boolean checkAddCoffee(String name, String price) {
        if (name.isEmpty()) {
            //4.1 Name Coffee
            Toast.makeText(this, ConstApp.ADD_COFFEE_E005, Toast.LENGTH_SHORT).show();
            return false;
        } else if (price.isEmpty()) {
            //4.2 Price Coffee
            Toast.makeText(this, ConstApp.ADD_COFFEE_E006, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void requestAddCoffee(String name, String description, String price) {
        Drink drink = new Drink(name, description, price, true);
        //5. Xử lý thêm coffee
        addCoffeePresenter.requestAddCoffee(drink, uri);
    }

    //2.2 Image response
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            if (data != null && data.getExtras() != null) {
                //Nhận data image -> bitmap
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                //show image
                ivCoffee.setImageBitmap(bitmap);
                if (bitmap != null) {
                    //2.4 Convert Uri
                    uri = convertUri(bitmap);
                }
            }
        }
    }

    //2.4 Convert Uri
    private Uri convertUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //Scale image
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "Image", null);
        return Uri.parse(path);
    }

    //2.1 Check Permission
    private void checkPermission() {
        //Kiểm tra quyền đọc và viết bộ nhớ device
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ID_IMAGE_CAPTURE);
        } else {
            //Open camera
            if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                this.startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
            }
        }
    }

    //5.d Nhận dữ liệu và kết thúc
    //Thêm drink thành công
    @Override
    public void addCoffeeSuccess(String error) {
        hideLoading();
        //Hiện thông báo
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        Intent intent = getParentActivityIntent();
        assert intent != null;
        NavUtils.navigateUpTo(this, intent);
    }

    //Thêm Drink thất bại
    @Override
    public void addCoffeeFailure(String error) {
        hideLoading();
        //Hiện thông báo
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
