package br.com.ddlrs.dla.rankfood.model.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.ddlrs.dla.rankfood.R;

public class VoteRankingAdapter extends RecyclerView.Adapter<VoteRankingAdapter.RankingViewHolder> {

    private itemActivityListener listener;

    private final ArrayList<String> rankingItems;
    private final ArrayList<Boolean> rankingItemsActivity = new ArrayList<Boolean>();

    public VoteRankingAdapter(ArrayList<String> rankingItems) {
        this.rankingItems = rankingItems;
        for (int i = 0; i < rankingItems.size(); i++){
            this.rankingItemsActivity.add(false);
        }
    }

    public void setListener(itemActivityListener listener) {
        this.listener = listener;
    }

    public int setRankingItemsActivity(int position){
        int previousPosition = 0;
        for (int i = 0; i < rankingItems.size(); i++){
            if (rankingItemsActivity.get(i) == true){
                previousPosition = i;
            }
            rankingItemsActivity.set(i,false);
        }
        rankingItemsActivity.set(position,true);
        return previousPosition;
    }
    public int getItemActivity(){
        for (int i = 0; i < rankingItems.size(); i++){
            if (rankingItemsActivity.get(i) == true){
                return i;
            }
        }

        return rankingItems.size() + 4;
    }
    public ArrayList<Boolean> RankingItemsActivity(){ return rankingItemsActivity;}

    public ArrayList<String> getRankingItems() { return rankingItems; }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_rank_vote_list_slot, parent,false
        );
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, final int position) {
        String item = rankingItems.get(position);
        boolean itemActivity = rankingItemsActivity.get(position);
        holder.bind(item,itemActivity);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(listener == null)
                    listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rankingItems.size();
    }

    class RankingViewHolder extends RecyclerView.ViewHolder{

        TextView slot;
        ImageView imageOfSlot;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            slot = itemView.findViewById(R.id.slot);
            imageOfSlot = itemView.findViewById(R.id.option_slot);
        }

        public void bind(String item, boolean itemActivity) {

            slot.setText(item);
            if (itemActivity) {
                imageOfSlot.setImageResource(R.drawable.ic_vote_aprove);
            } else {
                imageOfSlot.setImageResource(R.drawable.ic_vote_null);
            }
        }
    }

    public interface itemActivityListener{
        void onItemClick(int position);
    }
}
