package com.example.simplelist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.simplelist.Stats;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
public class Task implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long mId;
    @ColumnInfo(name = "title")
    private String mTitle;
    @ColumnInfo(name = "state")
    private Stats mStats;
    @ColumnInfo(name = "date")
    private Date mDate;
    @ColumnInfo(name = "description")
    private String mDescription;
    @ColumnInfo(name = "uuid")
    private UUID mTaskID;
    @ColumnInfo(name = "user_id")
    private String mUserID;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public void setTaskID(UUID taskID) {
        mTaskID = taskID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }

    public String getUserID() {
        return mUserID;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Stats getStats() {
        return mStats;
    }

    public void setStats(Stats stats) {
        mStats = stats;
    }

    public UUID getTaskID() {
        return mTaskID;
    }

    public Task() {
    }

    public Task(String mTitle, Stats stats, Date date, String description, UUID taskID, String userID) {
        this.mTitle = mTitle;
        mStats = stats;
        mDate = date;
        mDescription = description;
        mTaskID = taskID;
        mUserID = userID;
    }

    public Task(String userID){
        mUserID = userID;
        mTaskID = UUID.randomUUID();
    }

    public Task(String mTitle, String userID) {
        this.mTitle = mTitle;
        mDate=new Date();
        mStats= Stats.randomState();
        mTaskID = UUID.randomUUID();
        mUserID = userID;
    }
    public static class DateConverters {
        @TypeConverter
        public static Date fromTimestamp(Long value) {
            return value == null ? null : new Date(value);
        }

        @TypeConverter
        public static Long dateToTimestamp(Date date) {
            return date == null ? null : date.getTime();
        }
    }

    public static class UUidConverter{
        @TypeConverter
        public static UUID fromString(String value) {
            return UUID.fromString(value);
        }

        @TypeConverter
        public static String uuidToString(UUID uuid) {
            return uuid.toString();
        }
    }

    public static class StatesConverter{
        @TypeConverter
        public static Stats fromString(String value) {
            if (value.equals(Stats.TODO.toString())){
                return Stats.TODO;
            }else if (value.equals(Stats.DOING.toString())){
                return Stats.DOING;
            }else return Stats.DONE;
        }

        @TypeConverter
        public static String StatesToString(Stats stats) {
            return stats.toString();
        }
    }


    public String getPhotoFileName(){
        return "IMG_"+getTaskID()+".jpg";
    }
}
