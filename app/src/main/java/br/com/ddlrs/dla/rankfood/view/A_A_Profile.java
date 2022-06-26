package br.com.ddlrs.dla.rankfood.view;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.controller.Data;
import br.com.ddlrs.dla.rankfood.model.Adapter.FriendProfileAdapter;
import br.com.ddlrs.dla.rankfood.model.Adapter.ProfileAdapter;
import br.com.ddlrs.dla.rankfood.controller.User;
import br.com.ddlrs.dla.rankfood.model.Constants;
import br.com.ddlrs.dla.rankfood.model.RelationshipPointer;
import br.com.ddlrs.dla.rankfood.model.UserPointer;

public class A_A_Profile extends AppCompatActivity implements Constants {

    TextView     profile_name;
    TextView     profile_email;
    TextView     add_friend;
    ImageView    change_btn;
    ImageView    profile_picture;
    ImageView    register_back;
    EditText     searchProfile;
    RecyclerView profileList;
    Data         dataInstance;
    User         userTarget;
    int          pictureOption;
    FriendProfileAdapter friendListAdapter;
    ProfileAdapter searchProfileAdapter;
    RelationshipPointer notifyFriendInvit = null;

    private void exite(){
        Intent intent = new Intent();
        intent.putExtra("Data", dataInstance);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void updateProfileList(ArrayList<UserPointer> profiles){

        if (profiles.size() == 0){
            profileList.setVisibility(GONE);
            add_friend.setVisibility(GONE);
        } else {
            int listState = searchProfileAdapter.getItemCount();
            searchProfileAdapter.clear();

            profileList.setVisibility(VISIBLE);
            add_friend.setVisibility(VISIBLE);

            for (int i = 0; i < listState; i++) { searchProfileAdapter.notifyItemRemoved(i); }

            for (int i = 0; i < profiles.size(); i++) {
                searchProfileAdapter.getUserPointerItems().add(profiles.get(i));
            }

            for (int i = 0; i < profiles.size(); i++) searchProfileAdapter.notifyItemInserted(i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_profile);

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,100,0)); // Cor da barra de status

        // Definindo dados
        dataInstance = getIntent().getExtras().getParcelable("Data");
        userTarget = dataInstance.getDataUser(dataInstance.log);
        searchProfile = findViewById(R.id.id_edtext_register_edName2);

        // Campos das listas
        RecyclerView friendList     = findViewById(R.id.friend_list);
        TextView     not_friend     = findViewById(R.id.id_not_friend_message);
                     profileList    = findViewById(R.id.profile_list);
                     add_friend     = findViewById(R.id.id_add_friend_message);

        // Implementando a notificação de convite
        notifyFriendInvit = dataInstance.getInvalidRelationship();

        if(notifyFriendInvit == null) findViewById(R.id.id_ic_bell).setVisibility(INVISIBLE);

        findViewById(R.id.id_ic_bell).setOnClickListener(e ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(A_A_Profile.this);
            AlertDialog dialog = builder.create();
            LayoutInflater inflater = getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.item_pop_block, null);
            TextView accept = dialogLayout.findViewById(R.id.block_friend_accept);
            TextView cancel = dialogLayout.findViewById(R.id.block_friend_cancel);
            TextView friend_title = dialogLayout.findViewById(R.id.block_friend_title);

            friend_title.setText(
                    getString(R.string.notify_friend_massage1) +
                    notifyFriendInvit.user.UserName() +
                    getString(R.string.notify_friend_massage2)
            );

            accept.setOnClickListener(v -> {
                dataInstance.validDataRelationship(notifyFriendInvit.relationshipId);

                friendListAdapter.getRelationshipItems().add(dataInstance.getDataRelationship(notifyFriendInvit.relationshipId));
                friendListAdapter.notifyItemInserted(friendListAdapter.getItemCount());

                notifyFriendInvit = dataInstance.getInvalidRelationship();
                if(notifyFriendInvit == null) findViewById(R.id.id_ic_bell).setVisibility(INVISIBLE);

                not_friend.setVisibility(GONE);
                friendList.setVisibility(VISIBLE);

                Toast.makeText(getApplicationContext(), R.string.add_friend_success, Toast.LENGTH_SHORT).show();
                dialog.cancel();
            });

            cancel.setOnClickListener(v -> {
                dataInstance.validDataRelationship(notifyFriendInvit.relationshipId);
                dataInstance.blockDataRelationship(notifyFriendInvit.relationshipId);

                Toast.makeText(getApplicationContext(), R.string.add_friend_cancel, Toast.LENGTH_SHORT).show();
                dialog.cancel();
            });

            dialog.setView(dialogLayout);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.show();
        });

        // Implementa a lista de amigos
        ArrayList<RelationshipPointer> dataRelationship = dataInstance.dataRelationship();

        if(dataRelationship.size() != 0){ not_friend.setVisibility(GONE); }
        else { friendList.setVisibility(GONE); }

        friendListAdapter = new FriendProfileAdapter(new ArrayList<RelationshipPointer>(dataRelationship));

        friendList.setLayoutManager(new LinearLayoutManager(this));
        friendList.setAdapter(friendListAdapter);

        friendListAdapter.setListener(new FriendProfileAdapter.itemActivityListener() {
            @Override
            public void onItemClick(RelationshipPointer item, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(A_A_Profile.this);
                AlertDialog dialog = builder.create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.item_pop_block, null);
                TextView accept = dialogLayout.findViewById(R.id.block_friend_accept);
                TextView cancel = dialogLayout.findViewById(R.id.block_friend_cancel);

                accept.setOnClickListener(e -> {
                    int count = friendListAdapter.getItemCount() - 1;
                    dataInstance.blockDataRelationship(item.relationshipId);
                    friendListAdapter.getRelationshipItems().remove(position);

                    friendListAdapter.notifyItemRemoved(position);

                    if(count == 0){
                        not_friend.setVisibility(VISIBLE);
                        friendList.setVisibility(GONE);
                    }

                    Log.d("OperSerialize" , dataInstance.serialize());
                    dialog.cancel();
                });

                cancel.setOnClickListener(e -> dialog.cancel());

                dialog.setView(dialogLayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });

        // Implementando busca de usuarios por nome
        profileList.setVisibility(GONE);
        add_friend.setVisibility(GONE);

        searchProfileAdapter = new ProfileAdapter(new ArrayList<UserPointer>());

        profileList.setLayoutManager(new LinearLayoutManager(this));
        profileList.setAdapter(searchProfileAdapter);

        searchProfileAdapter.setListener(new ProfileAdapter.itemActivityListener() {
            @Override
            public void onItemClick(UserPointer item, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(A_A_Profile.this);
                AlertDialog dialog = builder.create();
                LayoutInflater inflater = getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.item_pop_block, null);
                TextView accept = dialogLayout.findViewById(R.id.block_friend_accept);
                TextView cancel = dialogLayout.findViewById(R.id.block_friend_cancel);
                TextView friend_title = dialogLayout.findViewById(R.id.block_friend_title);

                friend_title.setText(R.string.add_massage);

                accept.setOnClickListener(e -> {
                    dataInstance.setDataRelationship(item.id);

                    searchProfileAdapter.getUserPointerItems().remove(position);
                    searchProfileAdapter.notifyItemRemoved(position);

                    Toast.makeText(getApplicationContext(), R.string.add_success, Toast.LENGTH_SHORT).show();

                    dialog.cancel();
                });

                cancel.setOnClickListener(e -> {
                    dialog.cancel();
                });

                dialog.setView(dialogLayout);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });

        searchProfile.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Log.d("searchProfile" , String.valueOf(searchProfile.getText()));
                updateProfileList(dataInstance.searchWithName(String.valueOf(searchProfile.getText())));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // Alimentando os campos da tela
        profile_name = findViewById(R.id.id_text_profile_name);
        profile_email = findViewById(R.id.id_text_profile_email);
        change_btn = findViewById(R.id.id_change_btn_perfile);
        profile_picture = findViewById(R.id.id_profile_picture);
        register_back = findViewById(R.id.id_ic_register_back);

        profile_picture.setImageResource(IMAGE_OPTIONS[userTarget.ProfileImage()]);
        profile_name.setText(userTarget.UserName());
        profile_email.setText(userTarget.UserEmail());

        // Evento que modifica a imagen
        change_btn.setOnClickListener(e -> {
            pictureOption = (pictureOption == 5)?0:pictureOption + 1;

            profile_picture.setImageResource(IMAGE_OPTIONS[pictureOption]);

            dataInstance.ProfileImage(pictureOption);
        });

        // Evento de retorno
        register_back.setOnClickListener(e -> exite());

    }
}