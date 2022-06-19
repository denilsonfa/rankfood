package br.com.ddlrs.dla.rankfood.model.SortList;

import android.util.Log;

import java.util.ArrayList;

public class RankNode {
    private final int rankingId;
    private final int vote;
    private RankNode esq, dir;

    public RankNode(int rankingId, int vote){
        this.rankingId = rankingId;
        this.vote = vote;
        esq = dir = null;
    }

    public void add(int id, int v){
        if(v > vote){
            if(dir != null) dir.add(id, v);
            else dir = new RankNode(id, v);
        }
        else{
            if(esq != null) esq.add(id, v);
            else esq = new RankNode(id, v);
        }
    }

    public ArrayList<Integer> getSort(){
        ArrayList<Integer> res = new ArrayList<>();

        if(dir != null) res.addAll(dir.getSort());
        res.add(rankingId);
        if(esq != null) res.addAll(esq.getSort());

        return res;
    }
}
