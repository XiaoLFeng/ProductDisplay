package com.xlf.school.productdisplay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.xlf.school.productdisplay.R;
import com.xlf.school.productdisplay.models.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private final Context context;
    private List<Product> productList;
    private final ProductActionListener actionListener;

    public ProductAdapter(Context context, List<Product> productList, ProductActionListener actionListener) {
        this.context = context;
        this.productList = productList;
        this.actionListener = actionListener;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        }

        Product product = productList.get(position);

        TextView nameView = convertView.findViewById(R.id.product_name);
        TextView priceView = convertView.findViewById(R.id.product_price);
        TextView descriptionView = convertView.findViewById(R.id.product_description);
        Button editButton = convertView.findViewById(R.id.edit_button);
        Button deleteButton = convertView.findViewById(R.id.delete_button);

        nameView.setText(product.getName());
        priceView.setText(String.valueOf(product.getPrice()));
        descriptionView.setText(product.getDescription());

        // 修改按钮点击事件
        editButton.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onEdit(product);
            }
        });

        // 删除按钮点击事件
        deleteButton.setOnClickListener(v -> {
            if (actionListener != null) {
                actionListener.onDelete(product);
            }
        });

        return convertView;
    }

    public void updateData(List<Product> newList) {
        productList = newList;
        notifyDataSetChanged();
    }

    public interface ProductActionListener {
        void onEdit(Product product);

        void onDelete(Product product);
    }
}