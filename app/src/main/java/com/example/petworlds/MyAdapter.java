package com.example.petworlds;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<Dataclass> dataList;

    public MyAdapter(Context context, List<Dataclass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getDatauploadImage()).into(holder.recImage);
        holder.recTopic.setText(dataList.get(position).getDatauploadTopic());
        holder.recPrice.setText(dataList.get(position).getDataPrice());
        holder.recAge.setText(dataList.get(position).getDataAge());
        holder.recBreed.setText(dataList.get(position).getDataBreed());
        holder.recGender.setText(dataList.get(position).getDataGender());
        holder.recColor.setText(dataList.get(position).getDataColor());
        holder.recDes.setText(dataList.get(position).getDataDescription());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getDatauploadImage());
                intent.putExtra("Topic", dataList.get(holder.getAdapterPosition()).getDatauploadTopic());
                intent.putExtra("Price", dataList.get(holder.getAdapterPosition()).getDataPrice());
                intent.putExtra("Age", dataList.get(holder.getAdapterPosition()).getDataAge());
                intent.putExtra("Breed", dataList.get(holder.getAdapterPosition()).getDataBreed());
                intent.putExtra("Gender", dataList.get(holder.getAdapterPosition()).getDataGender());
                intent.putExtra("Color", dataList.get(holder.getAdapterPosition()).getDataColor());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDataDescription());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<Dataclass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView recImage;
        TextView recTopic, recPrice, recAge, recBreed, recGender, recColor, recDes;
        CardView recCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recImage = itemView.findViewById(R.id.recuploadImage);
            recTopic = itemView.findViewById(R.id.recuploadTopic);
            recPrice = itemView.findViewById(R.id.recPrice);
            recAge = itemView.findViewById(R.id.recAge);
            recBreed = itemView.findViewById(R.id.recBreed);
            recGender = itemView.findViewById(R.id.recGender);
            recColor = itemView.findViewById(R.id.recColor);
            recDes = itemView.findViewById(R.id.recDes);
            recCard = itemView.findViewById(R.id.recCard);
        }
    }
}