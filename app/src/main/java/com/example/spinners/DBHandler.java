package com.example.spinners;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;


public class DBHandler extends SQLiteOpenHelper{
    private static DBHandler instance;
    private static final String DB_NAME = "MyDataBase";
    // below int is our database version
    private static final int DB_VERSION = 1;
    // below variable is for our table name.
    private static final String TABLE_NAME = "Details";
    // below variable is for our id column.
    private static final String District = "district";
    // below variable is for our course name column
    private static final String Taluk = "taluk";
    // below variable id for our course duration column.
    private static final String Hobli = "hobli";
    // below variable for our course description column.
    private static final String Village = "village";
    // below variable is for our course tracks column.
    private static final String Survey = "survey";
    public static synchronized DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context.getApplicationContext());
        }
        return instance;
    }
    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + District + " TEXT ," + Taluk + " TEXT, " + Hobli + " TEXT, " + Village + " TEXT, " + Survey + " TEXT)";
        db.execSQL(query);
    }
    public int addNewCourse(String DistrictDetails, String TalukDetails, String HobliDetails, String VillageDetails, String SurveyDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(District, DistrictDetails);
        values.put(Taluk, TalukDetails);
        values.put(Hobli, HobliDetails);
        values.put(Village, VillageDetails);
        values.put(Survey, SurveyDetails);
        long result = db.insert(TABLE_NAME, null, values);
        return (int) result;
    }
    public String renderData() {
        String value = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor resultSet = db.rawQuery("Select * from " + DBHandler.TABLE_NAME+" ORDER BY 1 DESC", null);
        if (resultSet.moveToFirst()) {
            String district = resultSet.getString(0);
            String taluk = resultSet.getString(1);
            System.out.println(taluk);
            String Hobli = resultSet.getString(2);
            System.out.println(Hobli);
            String Village = resultSet.getString(3);
            System.out.println(Village);
            String Survey = resultSet.getString(4);
            System.out.println(Survey);
            value = "  Selected Data from the Database: \n\n"+"  District: " + district+"\n"+"  Taluk:"+taluk+"\n"+"  Hobli: "+Hobli+"\n"+"  Village: "+Village+"\n"+"  Survey: "+Survey;
        }
        resultSet.close();
        db.close();
        return value;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
