package br.com.ddlrs.dla.rankfood.controller;

import android.os.Parcel;
import android.os.Parcelable;

public class Relationship implements Parcelable {
    private int[] id;
    private boolean block = false;

    public Relationship(int id1, int id2){
        id = new int[]{id1, id2};
    }

    protected Relationship(Parcel in) {
        id = in.createIntArray();
        block = in.readByte() != 0;
    }

    public static final Creator<Relationship> CREATOR = new Creator<Relationship>() {
        @Override
        public Relationship createFromParcel(Parcel in) {
            return new Relationship(in);
        }

        @Override
        public Relationship[] newArray(int size) {
            return new Relationship[size];
        }
    };

    public void block(){ block = block?false:true; }

    public Integer getRelationship(int log){
        if (!block){
            if(log == id[0]){ return id[1]; }
            else if(log == id[1]){ return id[0]; }
            else { return null;}
        } else { return null; }
    }
    public String serialize(){
        String serialize;
        serialize = "{" +
                "\"id\":" + "[" + id[0] + "," + id[1] + "], " +
                "\"block\":" + "\"" +  (block == false? "false" : "true") + "\"" +
                "}";
        return serialize;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(id);
        parcel.writeByte((byte) (block ? 1 : 0));
    }
}
