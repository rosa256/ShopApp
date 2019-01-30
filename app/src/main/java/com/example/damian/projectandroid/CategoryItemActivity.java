package com.example.damian.projectandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.damian.projectandroid.Adapters.ItemAdapter;
import com.example.damian.projectandroid.Models.Item;
import com.example.damian.projectandroid.Models.User;

import java.util.ArrayList;

public class CategoryItemActivity extends AppCompatActivity {

    ArrayList<Item> itemsArrayList = new ArrayList<>();
    ShopDbHelper shopDbHelper = new ShopDbHelper(this);
    private boolean isMyCart = false;
    private ImageButton searchButton;
    private TextView searchTextView;
    private ItemAdapter adapter;
    private ListView itemsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        searchTextView = (TextView) findViewById(R.id.search_field);

        itemsArrayList = shopDbHelper.getItemsFromCategory(getIntent().getIntExtra("categoryID",1));

        adapter = new ItemAdapter(this, itemsArrayList,(User) getIntent().getSerializableExtra("loggedUser"), isMyCart);

        itemsListView  = (ListView) findViewById(R.id.items_listView);
        itemsListView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displaySearchedItems(searchTextView.getText().toString());
            }
        });
    }

    private void displaySearchedItems(String searchingContent) {
        ArrayList<Item> searchedItemsList = new ArrayList<>();
        for(Item item: itemsArrayList){
            if(item.getName().toUpperCase().contains(searchingContent.toUpperCase()))
                searchedItemsList.add(item);
        }

            adapter = new ItemAdapter(CategoryItemActivity.this, searchedItemsList,(User) getIntent().getSerializableExtra("loggedUser"), isMyCart);
            itemsListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

    }
}
