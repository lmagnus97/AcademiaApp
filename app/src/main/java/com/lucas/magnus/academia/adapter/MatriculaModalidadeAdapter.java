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
import com.lucas.magnus.academia.model.MatriculaModalidade;
import com.lucas.magnus.academia.util.Utils;

import java.util.List;

public class MatriculaModalidadeAdapter extends RecyclerView.Adapter<MatriculaModalidadeAdapter.MyViewHolder> {

    private List<MatriculaModalidade> data;
    private Integer mItemLayout;

    public interface OnItemSelectedListener {
        void onItemSelected(MatriculaModalidade item);
    }

    private OnItemSelectedListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPlano;
        public TextView tvModalidade;
        public TextView tvValor;
        public ImageButton btRemover;

        public MyViewHolder(View view) {
            super(view);
            tvPlano = view.findViewById(R.id.tvPlano);
            tvModalidade = view.findViewById(R.id.tvModalidade);
            tvValor = view.findViewById(R.id.tvValor);
            btRemover = view.findViewById(R.id.btRemover);

        }
    }

    public MatriculaModalidadeAdapter(List<MatriculaModalidade> data, Integer itemLayout, OnItemSelectedListener listener) {
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
        final MatriculaModalidade result = data.get(position);
        holder.tvPlano.setText(result.getPlano());
        holder.tvModalidade.setText(result.getModalidade());
        holder.tvValor.setText(Utils.convertToMoney(result.getValorMensal()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelected(result);
            }
        });

        holder.btRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Remover " + result.getModalidade())
                        .setMessage("Deseja mesmo remover essa modalidade?")
                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                MatriculaModalidadeDAO dao = new MatriculaModalidadeDAO(holder.itemView.getContext());
                                dao.delete(result);
                                data.remove(result);
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