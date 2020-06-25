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
import com.lucas.magnus.academia.dao.ModalidadeDAO;
import com.lucas.magnus.academia.model.Modalidade;

import java.util.List;

public class ModalidadeAdapter extends RecyclerView.Adapter<ModalidadeAdapter.MyViewHolder> {

    private List<Modalidade> data;
    private Integer mItemLayout;

    public interface OnItemSelectedListener {
        void onItemSelected(Modalidade modalidade);
    }

    private OnItemSelectedListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvModalidade;
        public ImageButton btRemover;

        public MyViewHolder(View view) {
            super(view);
            tvModalidade = view.findViewById(R.id.tvModalidade);
            btRemover = view.findViewById(R.id.btRemover);

        }
    }

    public ModalidadeAdapter(List<Modalidade> data, Integer itemLayout, OnItemSelectedListener listener) {
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
        final Modalidade modalidade = data.get(position);
        holder.tvModalidade.setText(modalidade.getModalidade());

        holder.tvModalidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelected(modalidade);
            }
        });

        holder.btRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Remover " + modalidade.getModalidade())
                        .setMessage("Deseja mesmo remover essa modalidade?")
                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ModalidadeDAO modalidadeDAO = new ModalidadeDAO(holder.itemView.getContext());
                                modalidadeDAO.delete(modalidade.getModalidade());
                                data.remove(modalidade);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.nao, null).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}