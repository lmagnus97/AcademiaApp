package com.lucas.magnus.academia.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lucas.magnus.academia.R;
import com.lucas.magnus.academia.model.Cidade;

import java.util.List;

public class CidadeAdapter extends RecyclerView.Adapter<CidadeAdapter.MyViewHolder> {

    private List<Cidade> data;
    private Integer mItemLayout;

    public interface OnItemSelectedListener {
        void onItemSelected(Cidade item);
    }

    private OnItemSelectedListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCidade;
        public TextView tvEstado;

        public MyViewHolder(View view) {
            super(view);
            tvCidade = view.findViewById(R.id.tvCidade);
            tvEstado = view.findViewById(R.id.tvEstado);

        }
    }

    public CidadeAdapter(List<Cidade> data, Integer itemLayout, OnItemSelectedListener listener) {
        this.data = data;
        mItemLayout = itemLayout;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(mItemLayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Cidade result = data.get(position);
        holder.tvCidade.setText(result.getCidade());
        holder.tvEstado.setText(result.getEstado());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelected(result);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}