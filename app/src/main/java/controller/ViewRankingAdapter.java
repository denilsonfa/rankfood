package controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.ddlrs.dla.rankfood.R;

public class ViewRankingAdapter extends RecyclerView.Adapter<ViewRankingAdapter.RankingViewHolder> {

    private final ArrayList<Integer> rankingItems;
    private final ArrayList<String> rankingItemsNames;

    public ViewRankingAdapter(ArrayList<Integer> rankingItems, ArrayList<String> rankingItemsNames) {
        this.rankingItems = rankingItems;
        this.rankingItemsNames = rankingItemsNames;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_rank_view_list_slot, parent,false
        );
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        Integer item = rankingItems.get(position);
        holder.bind(item,position);
    }

    @Override
    public int getItemCount() {
        return rankingItems.size();
    }

    class RankingViewHolder extends RecyclerView.ViewHolder{

        TextView id_text;
        ImageView id_image;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            id_text = itemView.findViewById(R.id.id_text_rating);
            id_image = itemView.findViewById(R.id.id_image_rating);
        }

        public void bind(Integer item, int position) {

            id_text.setText(rankingItemsNames.get(item));

            if (position == 0){
                id_image.setImageResource(R.drawable.ic_rank_3rd);
            } else if (position == 1){
                id_image.setImageResource(R.drawable.ic_rank_2nd);
            } else if (position == 2){
                id_image.setImageResource(R.drawable.ic_rank_1st);
            } else {
                id_image.setImageResource(R.drawable.ic_rank_th);
            }


        }
    }
}
