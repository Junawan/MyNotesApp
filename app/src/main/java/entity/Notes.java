package entity;

import android.os.Parcel;
import android.os.Parcelable;

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

    protected Notes(Parcel in) {
        this.title = in.readString();
        this.id = in.readInt();
        this.description = in.readString();
        this.date = in.readString();
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
