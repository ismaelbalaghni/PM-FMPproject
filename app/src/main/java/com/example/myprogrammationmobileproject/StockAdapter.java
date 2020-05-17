package com.example.myprogrammationmobileproject;

import android.content.Context;
import android.media.ImageReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    private List<StockCompany> stockCompanies;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView imageIcon;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.indexName);
            txtFooter = v.findViewById(R.id.indexTickerPrice);
            imageIcon = v.findViewById(R.id.icon);
        }
    }

    public void add(int position, StockCompany item) {
        stockCompanies.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        stockCompanies.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public StockAdapter(Context context, List<StockCompany> stockCompanies) {
        this.stockCompanies = stockCompanies;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final String name = stockCompanies.get(position).getName();
        holder.txtHeader.setText(name);
        String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
        Picasso.with(this.context).load(imageUri).resize(200,200).into(holder.imageIcon);
        holder.txtHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        holder.txtFooter.setText(stockCompanies.get(position).getSymbol() + " : $" + stockCompanies.get(position).getPrice());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return stockCompanies.size();
    }

}
