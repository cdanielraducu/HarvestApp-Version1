package com.example.harvestmesaje;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.SerieAdapterViewHolder> {

    private List<Serie> mSerieData;
    final private SerieAdapterOnClickHandler mClickHandler;

    private int[] mImages;

    public interface SerieAdapterOnClickHandler {
        void onClick(Serie item);
    }

    public SerieAdapter(SerieAdapterOnClickHandler clickHandler, int[] images) {
        mClickHandler = clickHandler;
        mImages = images;
    }

    public class SerieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private final ImageView mSerieImage;

        public SerieAdapterViewHolder(View itemView) {
            super(itemView);
            mSerieImage = (ImageView) itemView.findViewById(R.id.img_mesaj);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mSerieData.get(adapterPosition));
        }
    }

    @Override
    public SerieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.serie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new SerieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerieAdapterViewHolder holder, int position) {
        Serie serieClicked = mSerieData.get(position);
        int image_id = mImages[position];
        holder.mSerieImage.setImageResource(image_id);
    }

    @Override
    public int getItemCount() {
        if(null == mSerieData) return 0;
        return mSerieData.size();
    }

    public void setSerieData(List<Serie> serieData) {
        mSerieData = serieData;
        notifyDataSetChanged();
    }
}
