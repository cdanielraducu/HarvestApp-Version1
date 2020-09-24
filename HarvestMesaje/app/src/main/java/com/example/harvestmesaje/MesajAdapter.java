package com.example.harvestmesaje;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.MesajAdapterViewHolder> {

    private List<Mesaj> mMesajData;
    final private MesajAdapterOnClickHandler mClickHandler;

    public interface MesajAdapterOnClickHandler {
        void onClick(Mesaj mesaj);
    }

    public MesajAdapter(MesajAdapter.MesajAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MesajAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTextViewMesajTitlu;

        public MesajAdapterViewHolder(View itemView) {
            super(itemView);
            mTextViewMesajTitlu = (TextView) itemView.findViewById(R.id.tv_mesaj_titlu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mMesajData.get(adapterPosition));
        }
    }

    @Override
    public MesajAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.mesaj_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MesajAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MesajAdapterViewHolder holder, int position) {
//        Mesaj mesajClick = mMesajData.get(position);]
        System.out.println(position);
        holder.mTextViewMesajTitlu.setText(mMesajData.size() - position + ". " + mMesajData.get(position).getTitlu());
    }

    @Override
    public int getItemCount() {
        if(null == mMesajData) return 0;
        return mMesajData.size();
    }

    public void setMesajData(List<Mesaj> mesajData) {
        mMesajData = mesajData;
        notifyDataSetChanged();
    }
}
