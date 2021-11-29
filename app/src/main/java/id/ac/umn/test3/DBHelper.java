package id.ac.umn.test3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

//public class DBHelper extends SQLiteOpenHelper {
//    private static DBHelper dbHelper;
//    private static final String DATABASE_NAME = "Plannerly";
//    private static final String TABLE_NAME = "Planner";
//    private static final String COUNTER = "Counter";
//    private static final String ID_FIELD = "id";
//    private static final String NAME_FIELD = "name";
//    private static final String DESC_FIELD = "desc";
//    private static final String DATE_FIELD = "date";
//    private static final String TIME_FIELD = "time";
//    private static final String ADDRESS_FIELD = "address";
//    private static final String KEY_NAME = "image_name";
//    private static final String KEY_IMAGE = "image_data";
//
//    public DBHelper(Context context) {
//        super(context, DATABASE_NAME, null, 1);
//    }
//
//    public static DBHelper instanceOfDatabase(Context context) {
//        if (dbHelper == null) {
//            dbHelper = new DBHelper(context);
//        }
//        return dbHelper;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase DB) {
//        StringBuilder sql;
//        sql = new StringBuilder().append("CREATE TABLE ")
//                .append(TABLE_NAME).append("(").append(COUNTER).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
//                .append(ID_FIELD).append(" INT, ").append(NAME_FIELD).append(" TEXT, ").append(DESC_FIELD).append(" TEXT, ")
//                .append(DATE_FIELD).append(" TEXT, ").append(TIME_FIELD).append(" TEXT)");
//        DB.execSQL(sql.toString());
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
////        switch (oldVersion) {
////            case 1:
////                DB.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
////            case 2:
////                DB.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
////        }
//    }
//
//    public void insertPlan(SourcePlanner sp) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(ID_FIELD, sp.getId());
//        contentValues.put(NAME_FIELD, sp.getJudul());
//        contentValues.put(DESC_FIELD, sp.getDeskripsi());
//        contentValues.put(DATE_FIELD, String.valueOf(sp.getDate()));
//        contentValues.put(TIME_FIELD, sp.getTime());
////        contentValues.put(ADDRESS_FIELD, sp.getAddress());
//        DB.insert(TABLE_NAME, null, contentValues);
//    }
//
//    public void populatePlanListArray() {
//        SQLiteDatabase DB = this.getReadableDatabase();
//        try (Cursor result = DB.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
//            if (result.getCount() != 0) {
//                while (result.moveToNext()) {
//                    int id = result.getInt(1);
//                    String name = result.getString(2);
//                    String desc = result.getString(3);
//                    String date = result.getString(4);
//                    String time = result.getString(5);
////                    String address = result.getString(6);
//                    LocalDate date1 = getDateFromString(date);
//                    SourcePlanner sp = new SourcePlanner(id, name, desc, time, date1);
//                    SourcePlanner.sourcePlannerArrayList.add(sp);
//                }
//            }
//        }
//    }
//
//    public void updatePlanInDB(SourcePlanner sourcePlanner) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(ID_FIELD, sourcePlanner.getId());
//        contentValues.put(NAME_FIELD, sourcePlanner.getJudul());
//        contentValues.put(DESC_FIELD, sourcePlanner.getDeskripsi());
//        contentValues.put(DATE_FIELD, String.valueOf(sourcePlanner.getDate()));
//        contentValues.put(TIME_FIELD, sourcePlanner.getTime());
////        contentValues.put(ADDRESS_FIELD, sourcePlanner.getAddress());
//        DB.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[] {String.valueOf(sourcePlanner.getId())});
//    }
//
//    public void deletePlanInDB(SourcePlanner sourcePlanner) {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        DB.delete(TABLE_NAME, "name=?", new String[] {sourcePlanner.getJudul()});
//        DB.close();
//    }
//
//    private LocalDate getDateFromString(String string) {
//        try {
//            return CalendarUtils.selectedDate.parse(string);
//        } catch (NullPointerException e) {
//            return null;
//        }
////    }
//}
