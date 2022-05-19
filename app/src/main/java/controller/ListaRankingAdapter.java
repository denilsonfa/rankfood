package controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.ddlrs.dla.rankfood.R;

public class ListaRankingAdapter extends RecyclerView.Adapter<ListaRankingAdapter.RankingViewHolder> {

    private itemActivityListener listener;

    private final ArrayList<Ranking> rankingList;

    public ListaRankingAdapter(ArrayList<Ranking> rankingList) {
        this.rankingList = rankingList;
    }

    public void vote(int position, int vote) {
        rankingList.get(position).vote(vote);
    }

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
//                if(listener == null)
                    listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public void setListener(VoteRankingAdapter.itemActivityListener data) {
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
        void onItemClick(int position);
    }
}
