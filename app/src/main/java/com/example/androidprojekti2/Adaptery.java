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

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder>{

    private final SelectListener selectListener;
    private Context mContext;
    private List<GameModelClass> mData;


    public Adaptery(Context mContext, List<GameModelClass> mData, SelectListener listener1) {
        this.mContext = mContext;
        this.mData = mData;
        this.selectListener = listener1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.game_item, parent, false);

        return new MyViewHolder(v, selectListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
        holder.sPrice.setText(mData.get(position).getSalePrice());
        holder.nPrice.setText(mData.get(position).getNormalPrice());

        Glide.with(mContext).load(mData.get(position).getImgUrl()).into(holder.gImage);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

            TextView name;
            TextView sPrice;
            TextView nPrice;
            ImageView gImage;

        public MyViewHolder(@NonNull View itemView, SelectListener selectListener) {


            super(itemView);
            name = itemView.findViewById(R.id.name_txt);
            nPrice = itemView.findViewById(R.id.normalPrice);
            sPrice = itemView.findViewById(R.id.salePrice);
            gImage = itemView.findViewById(R.id.gameThumbnailView);


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
