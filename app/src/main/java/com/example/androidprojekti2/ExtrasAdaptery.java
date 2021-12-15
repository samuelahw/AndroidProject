package com.example.androidprojekti2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ExtrasAdaptery extends RecyclerView.Adapter<ExtrasAdaptery.MyViewHolder>{

    private final SelectListener selectListener;
    private Context mContext;
    private List<DealModelClass> mData;

    public ExtrasAdaptery(Context mContext, SelectListener selectListener, List<DealModelClass> data) {
        this.selectListener = selectListener;
        this.mContext = mContext;
        this.mData = data;
    }

    @NonNull
    @Override
    public ExtrasAdaptery.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.deal_item, parent, false);
        return new MyViewHolder(v, selectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtrasAdaptery.MyViewHolder holder, int position) {
        holder.storeName.setText(mData.get(position).getStoreName());
        holder.dealPrice.setText(mData.get(position).getDealPrice());
        holder.normalPrice.setText(mData.get(position).getNormalPrice());

        Glide.with(mContext).load(mData.get(position).getStoreThumb()).into(holder.storeImage);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        TextView normalPrice;
        TextView dealPrice;
        ImageView storeImage;

        public MyViewHolder(@NonNull View itemView, SelectListener selectListener) {
            super(itemView);

            storeName = itemView.findViewById(R.id.storeTitle);
            normalPrice = itemView.findViewById(R.id.storeNormalPrice);
            dealPrice = itemView.findViewById(R.id.storeDealPrice);
            storeImage = itemView.findViewById(R.id.storeThumb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectListener != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            selectListener.onClicked(pos);
                        }
                    }
                }
            });
        }
    }
}
