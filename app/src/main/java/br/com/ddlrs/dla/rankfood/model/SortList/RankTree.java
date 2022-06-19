package br.com.ddlrs.dla.rankfood.model.SortList;
import java.util.ArrayList;

public class RankTree {
    private RankNode raiz;

    public RankTree(){
        raiz = null;
    }

    public void add(int id, int v){
        if(raiz != null){
            raiz.add(id, v);
        }
        else{
            raiz = new RankNode(id, v);
        }
    }

    public ArrayList<Integer> getSort(){
        ArrayList<Integer> res = new ArrayList<>();

        if(raiz != null) res.addAll(raiz.getSort());

        return res;
    }
}