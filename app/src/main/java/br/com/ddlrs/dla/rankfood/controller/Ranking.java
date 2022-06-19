package br.com.ddlrs.dla.rankfood.controller;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Ranking implements Parcelable {
    private String name;
    private String description;
    private boolean publicRanking;
    private final int OwnerUserId;
    private ArrayList<String> itemsNameOfRanking = new ArrayList();
    private ArrayList<String> itemsVoteOfRanking = new ArrayList();
    private ArrayList<String> userVoteInRanking = new ArrayList();

    public Ranking( int OwnerUserId){
        this.OwnerUserId = OwnerUserId;
    }

    protected Ranking(Parcel in) {
        name = in.readString();
        description = in.readString();
        publicRanking = in.readByte() != 0;
        OwnerUserId = in.readInt();
        itemsNameOfRanking = in.createStringArrayList();
        itemsVoteOfRanking = in.createStringArrayList();
        userVoteInRanking = in.createStringArrayList();
    }

    public static final Creator<Ranking> CREATOR = new Creator<Ranking>() {
        @Override
        public Ranking createFromParcel(Parcel in) {
            return new Ranking(in);
        }

        @Override
        public Ranking[] newArray(int size) {
            return new Ranking[size];
        }
    };

    public String getName()             { return name;      }
    public void setName(String name)    { this.name = name; }

    public String getDescription()                  { return description;               }
    public void setDescription(String description)  { this.description = description;   }

    public Boolean getVisibility()                    { return publicRanking;               }
    public void setVisibility(Boolean publicRanking)  { this.publicRanking = publicRanking;   }

    public ArrayList[] getItemsOfRanking() { return new ArrayList[]{ itemsVoteOfRanking, itemsNameOfRanking, userVoteInRanking }; }
    public void setItemOfRanking(String itemName) {
        itemsVoteOfRanking.add("0");
        itemsNameOfRanking.add(itemName);
    }
    public boolean setItemOfRanking(int i, String itemName) {
        if(!itemsNameOfRanking.contains(i)){
            itemsNameOfRanking.set(
                    i,
                    itemName
            );
            return true;
        }
        return false;
    }
    public boolean removeItemOfRanking(int i) {
        if(!itemsNameOfRanking.contains(i)){
            itemsNameOfRanking.remove(i);
            itemsVoteOfRanking.remove(i);
            return true;
        }
        return false;
    }

    public boolean vote(int i, int e){
        if(!itemsVoteOfRanking.contains(i)){
            itemsVoteOfRanking.set(
                    i,
                    String.valueOf(Integer.parseInt(itemsVoteOfRanking.get(i)) + 1)
            );

            userVoteInRanking.add(String.valueOf(e));
            return true;
        }

        return false;
    }
    public boolean vote(int i){
        if(!itemsVoteOfRanking.contains(i)){
            itemsVoteOfRanking.set(
                    i,
                    String.valueOf(Integer.parseInt(itemsVoteOfRanking.get(i)) + 1)
            );
            return true;
        }

        return false;
    }

    public int quantityVotes(){

        int counter = 0;
        for (int i = 0; i< itemsVoteOfRanking.size(); i++)
            counter += Integer.parseInt(itemsVoteOfRanking.get(i));

        return counter;
    }

    public Boolean repeatVote(int log){
        Boolean res = false;

        for(int i = 0; i < userVoteInRanking.size(); i++)
            if (Integer.parseInt(userVoteInRanking.get(i)) == log) res = true;

        return res;
    }


    public String serialize(){
        String serialize;
        serialize = "{" +
                "\"name\":" + "\"" + name + "\"," +
                "\"description\":" + "\"" + description + "\"," +
                "\"publicRanking\":" + "\"" + (publicRanking ? "true" : "false") + "\"," +
                "\"OwnerUserId\":" + "\"" + OwnerUserId + "\"," +
                "\"UserVoteInRanking\":" + "\"" + userVoteInRanking + "\",";

        serialize += "\"itemsOfRanking\": [";

        for (int i = 0; i< itemsVoteOfRanking.size(); i++){
            serialize += "["+itemsVoteOfRanking.get(i)+",\""+itemsNameOfRanking.get(i)+"\"]";
            serialize += (itemsVoteOfRanking.size() - 1) == i ? "" : ",";
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
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeByte((byte) (publicRanking ? 1 : 0));
        parcel.writeInt(OwnerUserId);
        parcel.writeStringList(itemsNameOfRanking);
        parcel.writeStringList(itemsVoteOfRanking);
        parcel.writeStringList(userVoteInRanking);
    }
}
