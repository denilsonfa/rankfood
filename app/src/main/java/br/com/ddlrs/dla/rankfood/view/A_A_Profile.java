package br.com.ddlrs.dla.rankfood.view;

import static android.view.View.GONE;
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

import java.util.ArrayList;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.controller.Data;
import br.com.ddlrs.dla.rankfood.controller.FriendProfileAdapter;
import br.com.ddlrs.dla.rankfood.controller.ProfileAdapter;
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
    ArrayList<UserPointer> searchProfileList = new ArrayList<UserPointer>();

    private void exite(){
        Intent intent = new Intent();
        intent.putExtra("Data", dataInstance);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void alert(String massege){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss() );
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.item_pop_alert, null);
        TextView titleText = dialogLayout.findViewById(R.id.TitleText);
        titleText.setText(massege);
        android.app.AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
    }

    private void updateProfileList(ArrayList<UserPointer> profiles){

        if (profiles.size() == 0){
            profileList.setVisibility(GONE);
            add_friend.setVisibility(GONE);
        } else {
            int listState = searchProfileAdapter.getItemCount();
            searchProfileAdapter.clear();
            searchProfileList.clear();

            profileList.setVisibility(VISIBLE);
            add_friend.setVisibility(VISIBLE);

            for (int i = 0; i < listState; i++) { searchProfileAdapter.notifyItemRemoved(i); }

            for (UserPointer sas : profiles) {
                Log.d("UserPointer" + sas.id , sas.user.serialize());
            }

            for (UserPointer profile: profiles) { searchProfileList.add(profile); }

            for (int i = 0; i < searchProfileList.size(); i++) {
                searchProfileAdapter.getUserPointerItems().add(searchProfileList.get(i));
                searchProfileAdapter.notifyItemInserted(i);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_profile);
        dataInstance = getIntent().getExtras().getParcelable("Data");
        userTarget = dataInstance.getDataUser(dataInstance.log);

        // Importantes
        getWindow().setStatusBarColor(Color.rgb(255,100,0)); // Cor da barra de status

        ArrayList<RelationshipPointer> dataRelationship = dataInstance.dataRelationship();
        RecyclerView friendList = findViewById(R.id.friend_list);
        TextView not_friend = findViewById(R.id.id_not_friend_message);

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
                    dataInstance.blockDataRelationship(item.relationshipId);
                    friendListAdapter.getRelationshipItems().remove(position);

                    friendListAdapter.notifyItemRemoved(position);

                    if(friendListAdapter.getItemCount() == 0){
                        not_friend.setVisibility(GONE);
                        friendList.setVisibility(GONE);
                    }
                    Log.d("OperSerialize" , dataInstance.serialize());
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

        profileList = findViewById(R.id.profile_list);
        add_friend = findViewById(R.id.id_add_friend_message);

        profileList.setVisibility(GONE);
        add_friend.setVisibility(GONE);

        searchProfileAdapter = new ProfileAdapter(new ArrayList<UserPointer>(searchProfileList));

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

                friend_title.setText("Você deseja adicionar como amigo?");

                accept.setOnClickListener(e -> {
                    friendListAdapter.getRelationshipItems().add(dataInstance.setDataRelationship(item.id));
                    friendListAdapter.notifyItemInserted(friendListAdapter.getItemCount());

                    updateProfileList(dataInstance.searchWithName(String.valueOf(searchProfile.getText())));

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

        profile_name = findViewById(R.id.id_text_profile_name);
        profile_email = findViewById(R.id.id_text_profile_email);
        change_btn = findViewById(R.id.id_change_btn_perfile);
        profile_picture = findViewById(R.id.id_profile_picture);
        register_back = findViewById(R.id.id_ic_register_back);
        searchProfile = findViewById(R.id.id_edtext_register_edName2);

        profile_picture.setImageResource(IMAGE_OPTIONS[userTarget.ProfileImage()]);
        profile_name.setText(userTarget.UserName());
        profile_email.setText(userTarget.UserEmail());

        register_back.setOnClickListener(e -> exite());

        change_btn.setOnClickListener(e -> {
            pictureOption = (pictureOption == 5)?0:pictureOption + 1;

            profile_picture.setImageResource(IMAGE_OPTIONS[pictureOption]);

            dataInstance.ProfileImage(pictureOption);
        });

        searchProfile.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Log.d("searchProfile" , String.valueOf(searchProfile.getText()));
                updateProfileList(dataInstance.searchWithName(String.valueOf(searchProfile.getText())));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


    }
}