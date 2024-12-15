package com.xlf.school.productdisplay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.xlf.school.productdisplay.R;
import com.xlf.school.productdisplay.adapter.ProductAdapter;
import com.xlf.school.productdisplay.helper.ProductDatabaseHelper;
import com.xlf.school.productdisplay.models.Product;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProductDatabaseHelper dbHelper;
    private ListView productList;
    private ProductAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新数据
        List<Product> updatedProducts = dbHelper.getAllProducts();
        adapter.updateData(updatedProducts);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new ProductDatabaseHelper(this);
        productList = findViewById(R.id.product_list);

        List<Product> products = dbHelper.getAllProducts();
        adapter = new ProductAdapter(this, products, new ProductAdapter.ProductActionListener() {
            @Override
            public void onEdit(Product product) {
                // 跳转到编辑页面
                Intent intent = new Intent(MainActivity.this, EditProductActivity.class);
                intent.putExtra("productId", product.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(Product product) {
                // 弹出删除确认对话框
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("确认删除")
                        .setMessage("确定要删除商品 " + product.getName() + " 吗？")
                        .setPositiveButton("删除", (dialog, which) -> {
                            dbHelper.deleteProduct(product.getId());
                            adapter.updateData(dbHelper.getAllProducts());
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

        productList.setAdapter(adapter);

        // 添加商品
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
            startActivity(intent);
        });

        // 查询商品
        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            String query = ((EditText) findViewById(R.id.search_input)).getText().toString();
            List<Product> filteredProducts = dbHelper.searchProducts(query);
            adapter.updateData(filteredProducts);
        });
    }
}