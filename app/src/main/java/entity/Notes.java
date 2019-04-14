package entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import db.DatabaseContract;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static db.DatabaseContract.getColumnInt;
import static db.DatabaseContract.getColumnString;

public class Notes implements Parcelable {
    private int id;
    private String title;
    private String description;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeString(this.date);
    }

    public Notes() {
    }

    public Notes(int id, String title, String description, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    private Notes(Parcel in) {
        this.title = in.readString();
        this.id = in.readInt();
        this.description = in.readString();
        this.date = in.readString();
    }

    public Notes(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContract.NoteColumns.TITLE);
        this.description = getColumnString(cursor, DatabaseContract.NoteColumns.DESCRIPTION);
        this.date = getColumnString(cursor, DatabaseContract.NoteColumns.DATE);
    }

    public static final Parcelable.Creator<Notes> CREATOR = new Parcelable.Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel source) {
            return new Notes(source);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };
}
