package com.lucas.magnus.academia.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lucas.magnus.academia.R;
import com.lucas.magnus.academia.dao.MatriculaModalidadeDAO;
import com.lucas.magnus.academia.model.FaturaMatricula;
import com.lucas.magnus.academia.model.MatriculaModalidade;
import com.lucas.magnus.academia.util.Utils;

import java.util.List;

public class FaturasAdapter extends RecyclerView.Adapter<FaturasAdapter.MyViewHolder> {

    private List<FaturaMatricula> data;
    private Integer mItemLayout;

    public interface OnItemSelectedListener {
        void onItemSelected(FaturaMatricula item);
    }

    private OnItemSelectedListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAluno;
        public TextView tvData;
        public TextView tvValor;

        public MyViewHolder(View view) {
            super(view);
            tvAluno = view.findViewById(R.id.tvAluno);
            tvData = view.findViewById(R.id.tvData);
            tvValor = view.findViewById(R.id.tvValor);

        }
    }

    public FaturasAdapter(List<FaturaMatricula> data, Integer itemLayout, OnItemSelectedListener listener) {
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
        final FaturaMatricula result = data.get(position);
        holder.tvAluno.setText(result.getAluno());
        holder.tvData.setText(Utils.calendarToString(result.getDataVencimento()));
        holder.tvValor.setText(Utils.convertToMoney(result.getValor()));

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