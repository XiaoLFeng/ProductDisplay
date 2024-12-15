package com.xlf.school.productdisplay.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xlf.school.productdisplay.R;
import com.xlf.school.productdisplay.helper.ProductDatabaseHelper;

public class AddProductActivity extends AppCompatActivity {

    private ProductDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        dbHelper = new ProductDatabaseHelper(this);

        Button addProductButton = findViewById(R.id.add_product_button);
        addProductButton.setOnClickListener(v -> {
            String name = ((EditText) findViewById(R.id.product_name)).getText().toString();
            double price = Double.parseDouble(((EditText) findViewById(R.id.product_price)).getText().toString());
            String description = ((EditText) findViewById(R.id.product_description)).getText().toString();

            dbHelper.addProduct(name, price, description);
            Toast.makeText(this, "商品已添加", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}