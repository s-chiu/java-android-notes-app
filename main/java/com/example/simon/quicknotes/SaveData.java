package com.example.simon.quicknotes;

/**
 * Created by simon on 2/12/2017.
 */

public class SaveData {
    private String Last_Update_Info;
    private String Notes;

    public String getLast_Update_Info(){
        return Last_Update_Info;
    }

    public String getNotes(){
        return Notes;
    }
    public void setLast_Update_Info(String Last_Update_Info){
        this.Last_Update_Info=Last_Update_Info;
    }

    public void setNotes(String Notes){
        this.Notes=Notes;
    }
}

