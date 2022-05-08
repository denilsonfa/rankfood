package controller;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Ranking implements Parcelable {
    private String name;
    private final int OwnerUserId;
    private ArrayList<String[]> itemsOfRanking = new ArrayList();

    public Ranking(int id){ OwnerUserId = id; }

    protected Ranking(Parcel in) {
        name = in.readString();
        OwnerUserId = in.readInt();
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

    public ArrayList<String[]> getItemsOfRanking() { return itemsOfRanking; }
    public void setItemOfRanking(String itemName, String itemDescription) {
        itemsOfRanking.add(new String[]{itemName, itemDescription, "0"});
    }
    public boolean setItemOfRanking(int i, String itemName, String itemDescription) {
        if(!itemsOfRanking.contains(i)){
            itemsOfRanking.set(i, ( new String[]{
                    itemName,
                    itemDescription,
                    itemsOfRanking.get(i)[2]
                })
            );
            return true;
        }
        return false;
    }

    public boolean vote(int i){
        if(!itemsOfRanking.contains(i)){
            itemsOfRanking.set(i , new String[]{
                    itemsOfRanking.get(i)[0],
                    itemsOfRanking.get(i)[1],
                    String.valueOf(Integer.parseInt(itemsOfRanking.get(i)[2]) + 1)
            });
            return true;
        }

        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(OwnerUserId);
    }
}
