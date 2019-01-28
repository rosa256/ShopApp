package com.example.damian.projectandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damian.projectandroid.MainActivity;
import com.example.damian.projectandroid.Models.Item;
import com.example.damian.projectandroid.Models.User;
import com.example.damian.projectandroid.MyCart;
import com.example.damian.projectandroid.R;
import com.example.damian.projectandroid.ShopDbHelper;


import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Item> items;
    private User loggedUser;
    private ShopDbHelper shopDbHelper;
    private boolean isMyCart;

    public ItemAdapter(Context context, ArrayList<Item> items, User loggedUser, boolean isMyCart) {
        this.context = context;
        this.items = items;
        this.loggedUser = loggedUser;
        this.isMyCart = isMyCart;
        shopDbHelper = new ShopDbHelper(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        final Item currentItem = (Item) getItem(position);
        viewHolder.nameTextView.setText(currentItem.getName());
        viewHolder.itemImage.setImageResource(currentItem.getItemBanner());
        viewHolder.categoryTextView.setText(String.valueOf(currentItem.getCategoryFK()));
        viewHolder.priceTextView.setText(currentItem.getPrice().toString());
        viewHolder._id = currentItem.get_id();

        if(isMyCart)
            viewHolder.addToCardButton.setVisibility(View.GONE);
        else
            viewHolder.deleteFromCardButton.setVisibility(View.GONE);


        viewHolder.addToCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ("My toast,\n position: "+ position + ",\n ItemName: "+viewHolder.nameTextView.getText() +
                                ", \n ItemID: " + viewHolder._id + "\n LoggedUser Name: "+loggedUser.getUsername()),
                        Toast.LENGTH_LONG).show();
                shopDbHelper.addToUserCard(loggedUser.get_id(),viewHolder._id);
            }
        });

        viewHolder.deleteFromCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shopDbHelper.deleteFromCart(loggedUser.get_id(),viewHolder._id);
                items.remove(position);
                notifyDataSetChanged();

                MyCart.totalPrice -= Double.parseDouble(viewHolder.priceTextView.getText().toString());
                MyCart.totalPrice *= 100;
                MyCart.totalPrice = Double.valueOf(Math.round(MyCart.totalPrice));
                MyCart.totalPrice /= 100;

                MyCart.totalPriceTextView.setText("Podsumowanie: "+ MyCart.totalPrice +"zł");
            }
        });


        return convertView;
    }
    private static class ViewHolder {
        int _id;
        TextView nameTextView ;
        TextView categoryTextView ;
        TextView priceTextView ;
        ImageView itemImage;
        ImageView addToCardButton;
        ImageView deleteFromCardButton;


        public ViewHolder(View view) {
            this.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            this.categoryTextView = (TextView) view.findViewById(R.id.categoryTextView);
            this.priceTextView = (TextView) view.findViewById(R.id.priceTextView);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.addToCardButton = (ImageView) view.findViewById(R.id.add_to_cardButton);
            this.deleteFromCardButton = (ImageView) view.findViewById(R.id.delete_from_cardButton);
        }
    }
}