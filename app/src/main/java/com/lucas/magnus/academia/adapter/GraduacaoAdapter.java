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
import com.lucas.magnus.academia.model.Graduacao;

import java.util.List;

public class GraduacaoAdapter extends RecyclerView.Adapter<GraduacaoAdapter.MyViewHolder> {

    private List<Graduacao> data;
    private Integer mItemLayout;

    public interface OnItemSelectedListener {
        void onItemSelected(Graduacao item);
    }

    private OnItemSelectedListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvGraduacao;
        public ImageButton btRemover;

        public MyViewHolder(View view) {
            super(view);
            tvGraduacao = view.findViewById(R.id.tvGraduacao);
            btRemover = view.findViewById(R.id.btRemover);

        }
    }

    public GraduacaoAdapter(List<Graduacao> data, Integer itemLayout, OnItemSelectedListener listener) {
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
        final Graduacao result = data.get(position);
        holder.tvGraduacao.setText(result.getGraduacao());

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
                                GraduacaoDAO dao = new GraduacaoDAO(holder.itemView.getContext());
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