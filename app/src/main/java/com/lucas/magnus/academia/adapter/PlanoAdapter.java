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
import com.lucas.magnus.academia.dao.GraduacaoDAO;
import com.lucas.magnus.academia.dao.PlanoDAO;
import com.lucas.magnus.academia.model.Plano;

import java.util.List;
import java.util.Locale;

public class PlanoAdapter extends RecyclerView.Adapter<PlanoAdapter.MyViewHolder> {

    private List<Plano> data;
    private Integer mItemLayout;

    public interface OnItemSelectedListener {
        void onItemSelected(Plano item);
    }

    private OnItemSelectedListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPlano;
        public TextView tvModalidade;
        public TextView tvValor;
        public ImageButton btRemover;

        public MyViewHolder(View view) {
            super(view);
            tvPlano = view.findViewById(R.id.tvGraduacao);
            tvModalidade = view.findViewById(R.id.tvModalidade);
            tvValor = view.findViewById(R.id.tvValor);
            btRemover = view.findViewById(R.id.btRemover);

        }
    }

    public PlanoAdapter(List<Plano> data, Integer itemLayout, OnItemSelectedListener listener) {
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
        final Plano result = data.get(position);
        holder.tvModalidade.setText(result.getModalidade());
        holder.tvPlano.setText(result.getPlano());
        holder.tvValor.setText(String.format(Locale.ROOT, "R$%.2f", result.getValorMensal()).replace(".",","));

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
                        .setMessage("Deseja mesmo remover essa graduação?")
                        .setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                PlanoDAO dao = new PlanoDAO(holder.itemView.getContext());
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