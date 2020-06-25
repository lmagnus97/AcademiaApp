package com.lucas.magnus.academia.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lucas.magnus.academia.R;
import com.lucas.magnus.academia.model.Matricula;

import java.util.List;

public class MatriculaAdapter extends RecyclerView.Adapter<MatriculaAdapter.MyViewHolder> {

    private List<Matricula> data;
    private Integer mItemLayout;

    public interface OnItemSelectedListener {
        void onItemSelected(Matricula item);
    }

    private OnItemSelectedListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCodMatricula;
        public TextView tvAluno;
        public ImageButton btRemover;

        public MyViewHolder(View view) {
            super(view);
            tvCodMatricula = view.findViewById(R.id.tvCodMatricula);
            tvAluno = view.findViewById(R.id.tvAluno);

        }
    }

    public MatriculaAdapter(List<Matricula> data, Integer itemLayout, OnItemSelectedListener listener) {
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
        final Matricula result = data.get(position);

        holder.tvCodMatricula.setText(holder.itemView.getResources().getString(R.string.matricula_numero, result.getCodigoMatricula()));
        holder.tvAluno.setText(result.getAluno());

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