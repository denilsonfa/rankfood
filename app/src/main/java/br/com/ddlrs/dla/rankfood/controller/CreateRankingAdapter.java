package br.com.ddlrs.dla.rankfood.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.ddlrs.dla.rankfood.R;

public class CreateRankingAdapter extends RecyclerView.Adapter<CreateRankingAdapter.RankingViewHolder> {

    private final ArrayList<String> rankingItems;

    public CreateRankingAdapter(ArrayList<String> rankingItems) {
        this.rankingItems = rankingItems;
    }

    public ArrayList<String> getRankingItems() {
        return rankingItems;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_rank_create_list_slot, parent,false
        );
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        String item = rankingItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return rankingItems.size();
    }

    class RankingViewHolder extends RecyclerView.ViewHolder{

        EditText test;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            test = itemView.findViewById(R.id.slot);
        }

        public void bind(String item) {
            test.setText(item);
        }
    }
}
