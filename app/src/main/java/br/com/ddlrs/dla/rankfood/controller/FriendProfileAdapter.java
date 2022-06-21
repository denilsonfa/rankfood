package br.com.ddlrs.dla.rankfood.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.model.Constants;
import br.com.ddlrs.dla.rankfood.model.RelationshipPointer;

public class FriendProfileAdapter extends RecyclerView.Adapter<FriendProfileAdapter.FriendProfileHolder> implements Constants {

    private itemActivityListener listener;

    ArrayList<RelationshipPointer> dataRelationship;

    public FriendProfileAdapter(ArrayList<RelationshipPointer> dataRelationship) {
        this.dataRelationship = dataRelationship;
    }

    public ArrayList<RelationshipPointer> getRelationshipItems() { return dataRelationship; }

    public void remove() { dataRelationship.remove(getItemCount() - 1); }

    public void clear() { dataRelationship.clear(); }

    public void setListener(itemActivityListener listener) { this.listener = listener; }

    @NonNull
    @Override
    public FriendProfileAdapter.FriendProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_profile_friend_list_slot, parent,false
        );
        return new FriendProfileAdapter.FriendProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendProfileAdapter.FriendProfileHolder holder, int position) {

        RelationshipPointer item = dataRelationship.get(position);
        holder.bind(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataRelationship.size();
    }

    class FriendProfileHolder extends RecyclerView.ViewHolder{

        ImageView profileImage;
        TextView profileName;

        public FriendProfileHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            profileName = itemView.findViewById(R.id.name_profile);
        }

        public void bind(RelationshipPointer item) {
            profileName.setText(item.user.UserName());
            profileImage.setImageResource(IMAGE_OPTIONS[item.user.ProfileImage()]);
        }
    }

    public interface itemActivityListener{
        void onItemClick(RelationshipPointer item,int position);
    }
}
