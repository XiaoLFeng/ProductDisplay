package com.xlf.school.productdisplay.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xlf.school.productdisplay.R;
import com.xlf.school.productdisplay.helper.ProductDatabaseHelper;
import com.xlf.school.productdisplay.models.Product;

public class EditProductActivity extends AppCompatActivity {

    private ProductDatabaseHelper dbHelper;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        dbHelper = new ProductDatabaseHelper(this);

        int productId = getIntent().getIntExtra("productId", -1);
        currentProduct = dbHelper.getProductById(productId);

        if (currentProduct == null) {
            Toast.makeText(this, "商品不存在", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        EditText nameInput = findViewById(R.id.product_name);
        EditText priceInput = findViewById(R.id.product_price);
        EditText descriptionInput = findViewById(R.id.product_description);
        Button saveButton = findViewById(R.id.save_button);

        nameInput.setText(currentProduct.getName());
        priceInput.setText(String.valueOf(currentProduct.getPrice()));
        descriptionInput.setText(currentProduct.getDescription());

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            double price = Double.parseDouble(priceInput.getText().toString());
            String description = descriptionInput.getText().toString();

            currentProduct.setName(name);
            currentProduct.setPrice(price);
            currentProduct.setDescription(description);

            dbHelper.updateProduct(currentProduct);
            Toast.makeText(this, "商品信息已更新", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}