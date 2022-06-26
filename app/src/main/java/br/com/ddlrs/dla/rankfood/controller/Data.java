package br.com.ddlrs.dla.rankfood.controller;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.ddlrs.dla.rankfood.R;
import br.com.ddlrs.dla.rankfood.model.RelationshipPointer;
import br.com.ddlrs.dla.rankfood.model.UserPointer;


public class Data implements Parcelable {
    private ArrayList<User> dataUser = new ArrayList<User>();
    private ArrayList<Ranking> dataRanking = new ArrayList<Ranking>();
    private ArrayList<Relationship> dataRelationship = new ArrayList<Relationship>();
    public boolean guestEnable = false;
    public Integer log = null;

    public Data(){ initializeValues(); }

    protected Data(Parcel in) {
        dataUser = in.createTypedArrayList(User.CREATOR);
        dataRanking = in.createTypedArrayList(Ranking.CREATOR);
        dataRelationship = in.createTypedArrayList(Relationship.CREATOR);
        guestEnable = in.readByte() != 0;
        if (in.readByte() == 0) {
            log = null;
        } else {
            log = in.readInt();
        }
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    private User TesteUser(String nome, String email, String senha, int pictureOption){
        User user = new User();
        user.createUser(nome,email,senha,pictureOption);
        return user;
    }
    private Ranking TesteRanking(String name, String description, Boolean visibility, int OwnerUserId, String[] items){
        Ranking ranking = new Ranking(OwnerUserId);
        ranking.setName(name);
        ranking.setDescription(description);
        ranking.setVisibility(visibility);
        for ( String item: items ) ranking.setItemOfRanking(item);

        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < new Random().nextInt(9); j++) {
                ranking.vote(i);
            }
        }
        return ranking;
    }

    private Relationship TesteRelationship(int id1, int id2, boolean vilid){
        Relationship relationship = new Relationship(id1, id2);
        if(vilid) relationship.validate();
        return relationship;
    }

    private void initializeValues(){
        dataUser.add(TesteUser("Danusa","danusa@gmail.com","123", 0));
        dataUser.add(TesteUser("Denilson","denilson@gmail.com","123", 1));
        dataUser.add(TesteUser("Sara","sara@gmail.com","123", 2));
        dataUser.add(TesteUser("Leandro","leandro@gmail.com","123", 3));

        dataRanking.add(TesteRanking(
                "Pizzas",
                "Lista De Pizzas",
                true,
                1,
                new String[]{
                        "Calabresa",
                        "4 Queijos",
                        "Catupiry"
                }
        ));
        dataRanking.add(TesteRanking(
                "Melhores Marcas de Carros",
                "Lista De Carros",
                false,
                2,
                new String[]{
                        "Audi",
                        "BYD",
                        "BMW",
                        "Chevrolet",
                        "Bentley"
                }
        ));
        dataRanking.add(TesteRanking(
                "Filmes",
                "Lista De Filmes",
                true,
                3,
                new String[]{
                        "Amnésia",
                        "Senhor dos Anéis",
                        "Homem Aranha",
                        "Doutor Estranho",
                        "Duro de Matar"
                }
        ));
        dataRanking.add(TesteRanking(
                "Canais de Televisão",
                "Lista De Canais de Televisão",
                false,
                3,
                new String[]{
                        "SBT",
                        "Globo",
                        "Record",
                        "Band"
                }
        ));

    }

    public void setStatusOfinstance(Integer log, boolean guestEnable){
        this.log = log;
        this.guestEnable = guestEnable;
    }

    public ArrayList<UserPointer> searchWithName(String name){
        ArrayList<UserPointer> userPointer = new ArrayList<>();
        String lowerName;
        name = name.toLowerCase();

        if (name.equals("")) return userPointer;

        for (int i = 0; i < dataUser.size(); i++)
            if (log != null)
                if (log != i){
                    lowerName = dataUser.get(i).UserName().toLowerCase();

                    if ((lowerName.split(name).length > 1 || lowerName.equals(name)) && dataRelationship(i, true))
                        userPointer.add(new UserPointer(i, dataUser.get(i)));
                }


        return userPointer;
    }

    public void ProfileImage(int i){ dataUser.get(log).ProfileImage(i); }

    public void Update(Data updatedData){
        dataUser.clear();
        dataRanking.clear();
        dataRelationship.clear();
        guestEnable = updatedData.guestEnable;
        log = updatedData.log;

        ArrayList<User> updatedDataUser = updatedData.getDataUser();
        ArrayList<Ranking> updatedDataRanking = updatedData.getDataRanking();
        ArrayList<Relationship> updatedDataRelationship = updatedData.getDataRelationship();

        for (int i = 0; i < updatedDataUser.size(); i++)
            dataUser.add(updatedDataUser.get(i));


        for (int i = 0; i < updatedDataRanking.size(); i++)
            dataRanking.add(updatedDataRanking.get(i));


        for (int i = 0; i < updatedDataRelationship.size(); i++)
            dataRelationship.add(updatedDataRelationship.get(i));

    }

    public ArrayList<Relationship> getDataRelationship()    { return dataRelationship; }
    public RelationshipPointer getDataRelationship(int id){
        if (id < dataRelationship.size()){
            Integer friendId = dataRelationship.get(id).pureRelationship(log);
            return new RelationshipPointer( friendId, dataUser.get(friendId), id);
        }
        return null;
    }
    public void setDataRelationship(int id){
        if (dataRelationship(id, true))
            dataRelationship.add(new Relationship(log, id));
    }
    public ArrayList<RelationshipPointer> dataRelationship(){
        ArrayList<RelationshipPointer> RelationshipPointer = new ArrayList<>();
        Relationship targetRelationship;
        Integer friendId;
        for (int i = 0; i < dataRelationship.size(); i++) {
            targetRelationship = dataRelationship.get(i);
            friendId = targetRelationship.getRelationship(log);
            if(friendId != null){
                Log.d("dataRelationship Log" , String.valueOf(friendId));
                Log.d("dataRelationship Use" , dataUser.get(friendId).serialize());
                Log.d("dataRelationship In" , String.valueOf(i));
                RelationshipPointer.add(new RelationshipPointer(
                        friendId,
                        dataUser.get(friendId),
                        i
                ));
            }
        }
        return RelationshipPointer;
    }
    public boolean dataRelationship(int id, boolean pure){
        Relationship targetRelationship;
        Integer friendId;
        boolean res = true;

        for (int i = 0; i < dataRelationship.size(); i++) {
            targetRelationship = dataRelationship.get(i);
            friendId = pure?targetRelationship.pureRelationship(log):targetRelationship.getRelationship(log);
            if(friendId != null)
                if(friendId == id){res = false;}
        }

        return res;
    }
    public RelationshipPointer getInvalidRelationship(){
        Relationship targetRelationship;
        Integer friendId;

        for (int i = 0; i < dataRelationship.size(); i++) {
            targetRelationship = dataRelationship.get(i);
            friendId = targetRelationship.getInvalid(log);
            if(friendId != null)
                return new RelationshipPointer( friendId, dataUser.get(friendId), i);
        }

        return null;
    }
    public void blockDataRelationship(int i){ dataRelationship.get(i).block();      }
    public void validDataRelationship(int i){ dataRelationship.get(i).validate();   }

    public ArrayList<User> getDataUser()    { return dataUser;              }
    public User getDataUser(int i)          { return dataUser.get(i);       }
    public void setDataUser(User dataUser)  { this.dataUser.add(dataUser);  }
    public boolean setDataUser(int i, User user)  {
        if(!dataUser.contains(i)){
            dataUser.set(i, user);
            return true;
        }
        return false;
    }

    public ArrayList<Ranking> getDataRanking() { return dataRanking; }
    public Ranking getDataRanking(int i) { return dataRanking.get(i); }
    public void setDataRanking(Ranking dataRanking)  { this.dataRanking.add(dataRanking);  }
    public boolean setDataRanking(int i, Ranking ranking)  {
        if(!dataRanking.contains(i)){
            dataRanking.set(i, ranking);
            return true;
        }
        return false;
    }
    public int getQuantityVotes(int i)  {return dataRanking.get(i).quantityVotes();}

    public String serialize(){
        String serialize;
        serialize = "{" +
                "\"guestEnable\":" + "\"" + (guestEnable ? "true" : "false") + "\"," +
                "\"log\":" + "\"" + log + "\"," +
                "\"dataUser\": [";
        for (int i = 0; i < dataUser.size(); i++){
            User data = dataUser.get(i);
            serialize += data.serialize();
            serialize += (dataUser.size() - 1) == i ? "" : ",";
        }
        serialize += "],";

        serialize += "\"dataRanking\": [";

        for (int i = 0; i < dataRanking.size(); i++){
            Ranking data = dataRanking.get(i);
            serialize += data.serialize();
            serialize += (dataRanking.size() - 1) == i ? "" : ",";
        }
        serialize += "],";

        serialize += "\"dataRelationship\": [";

        for (int i = 0; i < dataRelationship.size(); i++){
            Relationship data = dataRelationship.get(i);
            serialize += data.serialize();
            serialize += (dataRelationship.size() - 1) == i ? "" : ",";
        }
        serialize += "]";

        serialize += "}";

        return serialize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(dataUser);
        parcel.writeTypedList(dataRanking);
        parcel.writeTypedList(dataRelationship);
        parcel.writeByte((byte) (guestEnable ? 1 : 0));
        if (log == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(log);
        }
    }
}

