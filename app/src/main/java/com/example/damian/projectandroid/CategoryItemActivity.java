package com.example.damian.projectandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.damian.projectandroid.Adapters.ItemAdapter;
import com.example.damian.projectandroid.Models.Item;
import com.example.damian.projectandroid.Models.User;

import java.util.ArrayList;

public class CategoryItemActivity extends AppCompatActivity {

    ArrayList<Item> itemsArrayList = new ArrayList<>();
    ShopDbHelper shopDbHelper = new ShopDbHelper(this);
    private boolean isMyCart = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);

        itemsArrayList = shopDbHelper.getItemsFromCategory(getIntent().getIntExtra("categoryID",1));


        ItemAdapter adapter = new ItemAdapter(this, itemsArrayList,(User) getIntent().getSerializableExtra("loggedUser"), isMyCart);

// get the ListView and attach the adapter
        ListView itemsListView  = (ListView) findViewById(R.id.items_listView);
        itemsListView.setAdapter(adapter);

    }
}
