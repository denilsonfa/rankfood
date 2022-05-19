package controller;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Ranking implements Parcelable {
    private String name;
    private String description;
    private final int OwnerUserId;
    private ArrayList<String> itemsNameOfRanking = new ArrayList();
    private ArrayList<String> itemsVoteOfRanking = new ArrayList();

    public Ranking( int OwnerUserId){
        this.name = name;
        this.description = description;
        this.OwnerUserId = OwnerUserId;
    }

    protected Ranking(Parcel in) {
        name = in.readString();
        description = in.readString();
        OwnerUserId = in.readInt();
        itemsNameOfRanking = in.createStringArrayList();
        itemsVoteOfRanking = in.createStringArrayList();
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

    public ArrayList[] getItemsOfRanking() { return new ArrayList[]{ itemsVoteOfRanking, itemsNameOfRanking }; }
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


    public String serialize(){
        String serialize;
        serialize = "{" +
                "\"name\":" + "\"" + name + "\"," +
                "\"description\":" + "\"" + description + "\"," +
                "\"OwnerUserId\":" + "\"" + OwnerUserId + "\",";

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
        parcel.writeInt(OwnerUserId);
        parcel.writeStringList(itemsNameOfRanking);
        parcel.writeStringList(itemsVoteOfRanking);
    }
}
