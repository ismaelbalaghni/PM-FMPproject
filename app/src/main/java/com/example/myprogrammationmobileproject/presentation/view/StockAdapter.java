package com.example.myprogrammationmobileproject.presentation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myprogrammationmobileproject.Constantes;
import com.example.myprogrammationmobileproject.R;
import com.example.myprogrammationmobileproject.presentation.model.StockCompany;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> implements Filterable {
    private final List<StockCompany> stockCompanies;
    private List<StockCompany> filteredCompanies;
    private Context context;
    private final StockAdapterListener stockAdapterListener;


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

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
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
    public StockAdapter(Context context, List<StockCompany> stockCompanies, StockAdapterListener listener) {
        this.stockCompanies = stockCompanies;
        this.context = context;
        this.filteredCompanies = stockCompanies;
        this.stockAdapterListener = listener;
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
        final StockCompany currentCompany = filteredCompanies.get(position);
        holder.txtHeader.setText(currentCompany.getName());
        Picasso.with(this.context).load(Constantes.IMAGE_URL+currentCompany.getSymbol()+".jpg").resize(200, 200).into(holder.imageIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stockAdapterListener.onItemClick(currentCompany);
            }
        });

        holder.txtFooter.setText(filteredCompanies.get(position).getSymbol() + " : $" + filteredCompanies.get(position).getPrice());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return filteredCompanies.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filter = constraint.toString();
                List<StockCompany> filteredStockCompanies = new ArrayList<>();
                if(filter.isEmpty()){
                    filteredStockCompanies = stockCompanies;
                } else {
                    for(StockCompany company : stockCompanies){
                        if(company.getName().toLowerCase().contains(filter.toLowerCase()) || company.getSymbol().toLowerCase().contains(filter.toLowerCase())){
                            filteredStockCompanies.add(company);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredStockCompanies.size();
                filterResults.values = filteredStockCompanies;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCompanies = (List<StockCompany>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface StockAdapterListener{
        void onItemClick(StockCompany company);
    }
}
