package id.ac.umn.test3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String PLAN_NAME = "name";
    public DBHelper(Context context) {
        super(context, "Plannerly.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Planner(id integer primary key, name text, description text, time text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists Planner");
    }

    public boolean insertPlan(String name, String desc, String time) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("desc", desc);
        contentValues.put("time", time);
        long result = DB.insert("Planner", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean updatePlan(Integer id, String name, String desc, String time) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("desc", desc);
        contentValues.put("time", time);
        Cursor cursor = DB.rawQuery("Select * from Planner where id = ?", new String[] {Integer.toString(id)});
        if (cursor.getCount() > 0){
            long result = DB.update("Plan", contentValues, "id = ?", new String[] {Integer.toString(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean deletePlan(Integer id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Planner where id = ?", new String[] {Integer.toString(id)});
        if (cursor.getCount() > 0){
            long result = DB.delete("Planner",  "id=?", new String[] {Integer.toString(id)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Cursor getPlan(int id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Planner where id="+id+"", null);
        return cursor;
    }

    @SuppressLint("Range")
    public ArrayList<String> getAllPlans() {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from Planner", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            arrayList.add(res.getString(res.getColumnIndex(PLAN_NAME)));
            res.moveToNext();
        }
        return arrayList;
    }
}
