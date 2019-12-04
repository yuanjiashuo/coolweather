package com.example.coolweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {
    private Context mContext;
    //private static final String DB_NAME="USERSS.db";
    private static final String TABLE_NAME="DATA";
    private static final String LAN_USER = "create table DATA(_id INTEGER DEFAULT '1' NOT NULL PRIMARY KEY AUTOINCREMENT,number String,password String)";
    private SQLiteDatabase db;
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LAN_USER);
        Toast.makeText(mContext,"创建数据库成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public void insert(ContentValues values){
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public Cursor query(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor c=db.query(TABLE_NAME,null,null,null,null,null,null);
        return c;
    }
    public void del(int id){
        if(db==null){
            SQLiteDatabase db=getWritableDatabase();
            db.delete(TABLE_NAME,"_id=?",new String[]{String.valueOf(id)});
        }
    }
    public void close(){
        if(db!=null){
            db.close();
        }
    }
}
