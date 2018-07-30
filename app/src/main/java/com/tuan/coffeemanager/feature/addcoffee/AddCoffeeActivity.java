package com.tuan.coffeemanager.feature.addcoffee;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.feature.addcoffee.presenter.EditCoffeePresenter;
import com.tuan.coffeemanager.feature.addcoffee.presenter.PostCoffeePresenter;
import com.tuan.coffeemanager.feature.addcoffee.presenter.PostImagePresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;
import com.tuan.coffeemanager.widget.CustomGlide;
import com.tuan.coffeemanager.widget.CustomKeyBoard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCoffeeActivity extends AppCompatActivity implements ViewListener.ViewDataListener<Drink>, ViewListener.ViewPostListener, View.OnClickListener, ViewListener.ViewPostImageListener {

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

    private String id = null;
    private Drink drink = null;
    private Uri uri = null;
    private EditCoffeePresenter editCoffeePresenter;
    private PostCoffeePresenter postCoffeePresenter;
    private PostImagePresenter postImagePresenter;

    private static final int PICK_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coffee);
        ButterKnife.bind(this);
        FirebaseDataApp.isActivity = true;

        id = Objects.requireNonNull(getIntent().getExtras()).getString(ContactBaseApp.DRINK_ID, "").trim();
        postImagePresenter = new PostImagePresenter(this);
        if (!Objects.requireNonNull(id).isEmpty()) {
            CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
            editCoffeePresenter = new EditCoffeePresenter(this, this, this);
            editCoffeePresenter.getDataDrink(id);
            tvTitle.setText(R.string.text_edit_drink_title);
        } else {
            tvTitle.setText(R.string.text_add_drink_title);
            postCoffeePresenter = new PostCoffeePresenter(this);
        }

        clContent.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivCoffee.setOnClickListener(this);
        btnSaveCoffee.setOnClickListener(this);
    }

    @Override
    public void onSuccess(Drink drink) {
        CustomDialogLoadingFragment.hideLoading();
        this.drink = drink;
        setView(drink);
    }

    private void setView(Drink drink) {
        if (drink != null) {
            edtNameCoffee.setText(drink.getName());
            edtDescriptionCoffee.setText(drink.getDescription());
            edtPriceCoffee.setText(String.valueOf(drink.getPrice()));
            if (drink.getUrl() != null) {
                CustomGlide.showImage(this, ivCoffee, drink.getUrl());
            }
        }
    }

    @Override
    public void onFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                onBackPressed();
                break;
            }
            case R.id.btnSaveCoffee: {
                CustomKeyBoard.hideKeyBoard(this);
                String name = edtNameCoffee.getText().toString().trim();
                String description = edtDescriptionCoffee.getText().toString().trim();
                String price = edtPriceCoffee.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(this, R.string.text_message_name_empty, Toast.LENGTH_SHORT).show();
                } else if (description.isEmpty()) {
                    Toast.makeText(this, R.string.text_message_description_empty, Toast.LENGTH_SHORT).show();
                } else if (price.isEmpty()) {
                    Toast.makeText(this, R.string.text_message_price_empty, Toast.LENGTH_SHORT).show();
                } else {
                    CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
                    if (id.isEmpty()) {
                        drink = new Drink(null, name, description,price, null, null, true);
                        if (uri != null) {
                            postImagePresenter.postDataImage(this, uri);
                        } else {
                            postCoffeePresenter.postDataDrink(this, drink);
                        }
                    } else {
                        drink = new Drink(id, name, description, price, this.drink.getUuid(), this.drink.getUrl(), true);
                        if (uri != null && this.drink.getUrl() == null) {
                            postImagePresenter.postDataImage(this, uri);
                        } else if (uri != null && this.drink.getUrl() != null) {
                            editCoffeePresenter.editDataImage(this, uri, this.drink.getUuid());
                        } else {
                            editCoffeePresenter.editDataDrink(this, drink);
                        }
                    }
                }
                break;
            }
            case R.id.clContent: {
                CustomKeyBoard.hideKeyBoard(this);
                break;
            }
            case R.id.ivCoffee: {
                try {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                        File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        String strImage = imageFile.getPath();
                        Uri data = Uri.parse(strImage);
                        galleryIntent.setDataAndType(data, "image/*");
                        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY) {
            if (data != null) {
                uri = data.getData();
                try {
                    InputStream inputStream = this.getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                    ivCoffee.setImageBitmap(scaled);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void postSuccess(String message) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(this);
        if (intent != null) {
            intent.putExtra("FLAG", 0);
            NavUtils.navigateUpTo(AddCoffeeActivity.this, intent);
        }
    }

    @Override
    public void postFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postImageSucces(String uuid, String url) {
        drink.setUuid(uuid);
        drink.setUrl(url);
        if (id.isEmpty()) {
            postCoffeePresenter.postDataDrink(this, drink);
        } else {
            editCoffeePresenter.editDataDrink(this, drink);
        }
    }

    @Override
    public void postImageFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDataApp.isActivity = false;
    }
}
