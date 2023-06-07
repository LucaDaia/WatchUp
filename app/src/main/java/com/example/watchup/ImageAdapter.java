package com.example.watchup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    private List<Image> imageList;

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        public ImageViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1=itemView.findViewById(R.id.tvName);
            textView2=itemView.findViewById(R.id.tvLocation);
            textView3 = itemView.findViewById(R.id.textViewHiden);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(recyclerViewInterface != null) {
                       String name = textView1.getText().toString();
                       String data = textView3.getText().toString();
                       int pos = getAdapterPosition();

                       if(pos != RecyclerView.NO_POSITION) {
                           recyclerViewInterface.onItemClick(pos, name, data);
                       }
                   }
                }
            });
        }
    }

    public ImageAdapter(RecyclerViewInterface recyclerViewInterface, List<Image> imageList) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
         ImageViewHolder ivh = new ImageViewHolder(v, recyclerViewInterface );
         return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image currImage = imageList.get(position);

        Utils.insertImageByName(Utils.removeImagesPath(currImage.getData()),holder.imageView, true);
        System.out.println(Utils.removeImagesPath(currImage.getData()));
        System.out.println(currImage.getName());
        holder.textView1.setText(currImage.getName());
        holder.textView2.setText(Utils.formatDateTime(currImage.getCreatedAt()));
        holder.textView3.setText(currImage.getData());

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


}
