package com.example.damian.projectandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damian.projectandroid.CategoriesActivity;
import com.example.damian.projectandroid.CategoryItemActivity;
import com.example.damian.projectandroid.Models.Category;
import com.example.damian.projectandroid.Models.User;
import com.example.damian.projectandroid.R;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Category> categoriesList;
    private User loggedUser;

    public CategoryAdapter(Context context, ArrayList<Category> categories, User loggedUser) {
        this.context = context;
        this.categoriesList = categories;
        this.loggedUser = loggedUser;
    }

    @Override
    public int getCount() {
        return categoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_category, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            Category currentCategory = (Category) getItem(position);
            viewHolder.categoryTitle.setText(currentCategory.getCategoryTitle());
            viewHolder.categroyImage.setImageResource(currentCategory.getCategoryImageBanner());
            viewHolder._id = currentCategory.getCategory_id();

            convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, ("My toast,\n position: "+ position + ",\n Title: "+viewHolder.categoryTitle.getText() +
                        ", \n DbCategoryID: " + viewHolder._id),
                        Toast.LENGTH_LONG).show();


                Intent intent = new Intent(context, CategoryItemActivity.class);
                intent.putExtra("categoryID",viewHolder._id);
                intent.putExtra("loggedUser",loggedUser);
                context.startActivity(intent);

            }
        });

            // get the TextView, and ImageView for item(category) name and image banner.
        return convertView;
    }

    private static class ViewHolder {
        int _id;
        TextView categoryTitle ;
        ImageView categroyImage;

        public ViewHolder(View view) {
            this.categoryTitle = (TextView) view.findViewById(R.id.category_title);
            this.categroyImage = (ImageView) view.findViewById(R.id.category_image);
        }
    }
}
