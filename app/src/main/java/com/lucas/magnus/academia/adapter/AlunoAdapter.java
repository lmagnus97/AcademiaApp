package com.lucas.magnus.academia.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lucas.magnus.academia.R;
import com.lucas.magnus.academia.model.Aluno;

import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.MyViewHolder> {

    private List<Aluno> data;
    private Integer mItemLayout;

    public interface OnItemSelectedListener {
        void onItemSelected(Aluno aluno);
    }

    private OnItemSelectedListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCodAluno, tvAluno;

        public MyViewHolder(View view) {
            super(view);
            tvCodAluno = view.findViewById(R.id.tvCodAluno);
            tvAluno = view.findViewById(R.id.tvAluno);

        }
    }

    public AlunoAdapter(List<Aluno> data, Integer itemLayout, OnItemSelectedListener listener) {
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Aluno aluno = data.get(position);
        holder.tvCodAluno.setText(String.valueOf(aluno.getCodigoAluno()));
        holder.tvAluno.setText(aluno.getAluno());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemSelected(aluno);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}