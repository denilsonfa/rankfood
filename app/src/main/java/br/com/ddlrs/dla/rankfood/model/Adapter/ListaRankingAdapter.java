package br.com.ddlrs.dla.rankfood.model.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.controller.Data;
import br.com.ddlrs.dla.rankfood.controller.Ranking;
import br.com.ddlrs.dla.rankfood.model.Constants;

public class ListaRankingAdapter extends RecyclerView.Adapter<ListaRankingAdapter.RankingViewHolder> implements Constants {

    private itemActivityListener listener;

    private final ArrayList<Ranking> rankingList;
    private final ArrayList<Integer> rankingListId;

    public ListaRankingAdapter(ArrayList<Ranking> rankingList, Integer operation, Data dataInstance) {

        ArrayList<Integer> allowedRankingListId = new ArrayList<>();
        ArrayList<Ranking> allowedRankingList = new ArrayList<>();
        Integer log = dataInstance.log;

        for (int i = 0; i < rankingList.size(); i++){
            Ranking item = rankingList.get(i);
            Boolean verify = true;

            if (operation == GUEST_MODE_ACTIVITY_REQUEST_CODE) {
                if (!item.getVisibility()) verify = false;
                Log.d("OperSerialize" , "GUEST_MODE_ACTIVITY_REQUEST_CODE");
            }
            else {
                if(!item.getVisibility()){
                    Log.d("OperSerialize" , "RANKI PRIVADO " +  item.getName() );
                    if (dataInstance.dataRelationship(item.getOwnerUserId(), false)){
                        verify = false;
                        Log.d("OperSerialize" , dataInstance.getDataUser(log).UserName() + " não é amigo de " +  dataInstance.getDataUser(item.getOwnerUserId()).UserName() );
                    }
                }

                if (operation == VOTE_ACTIVITY_REQUEST_CODE){
                    Log.d("OperSerialize" , "VOTE_ACTIVITY_REQUEST_CODE");
                    if(item.repeatVote(log)) {
                        verify = false;
                        Log.d("OperSerialize" , "já voto aqui");
                    }

                } else if (operation == VIEW_RANK_ACTIVITY_REQUEST_CODE){
                    Log.d("OperSerialize" , "VIEW_RANK_ACTIVITY_REQUEST_CODE");
                    if(!item.repeatVote(log)) {
                        verify = false;
                        Log.d("OperSerialize" , "não voto aqui");
                    }
                    if(item.getOwnerUserId() == log){ verify = true; }
                }
            }

            Log.d( verify + "for" , item.serialize());
            if(verify) {
                allowedRankingList.add(item);
                allowedRankingListId.add(i);
            }
        }

        this.rankingListId = allowedRankingListId;
        this.rankingList = allowedRankingList;
    }

    public void vote(int position, int vote) {
        rankingList.get(position).vote(vote);
    }

    public ArrayList<Ranking> getRankingItems() { return rankingList; }

    public void remove() { rankingList.remove(getItemCount() - 1); }

    public void setListener(itemActivityListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_rank_select_list_slot, parent,false
        );
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {

        Ranking item = rankingList.get(position);
        holder.bind(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(rankingListId.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    class RankingViewHolder extends RecyclerView.ViewHolder{

        TextView nome_ranking;
        TextView valor_voto;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            nome_ranking = itemView.findViewById(R.id.nome_ranking);
            valor_voto = itemView.findViewById(R.id.valor_voto);
        }

        public void bind(Ranking item) {
            nome_ranking.setText(item.getName());
            valor_voto.setText(Integer.toString(item.quantityVotes()));
        }
    }

    public interface itemActivityListener{
        void onItemClick(int listPosition, int position);
    }
}
