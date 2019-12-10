package com.example.coolweather;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

public class datedisplay extends ListActivity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final dateDBHelper helper = new dateDBHelper(this, "USERSS.db", null, 1);
        Cursor c=helper.query();
        String[] from={"_id", "name", "hobby","date"};
        int[] to={R.id.id,R.id.name,R.id.like,R.id.time};
        SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,R.layout.date_display_item, c, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        ListView listView=getListView();
        listView.setAdapter(adapter);
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, final long arg3){
                final long temp=arg3;
                builder.setMessage("删除还是查看?").setPositiveButton("删除", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        helper.del((int)temp);
                        Cursor c=helper.query();
                        String[] from={"_id", "name", "hobby","date"};
                        int[] to={R.id.id,R.id.name,R.id.like,R.id.time};
                        SimpleCursorAdapter adapter=new SimpleCursorAdapter(getApplicationContext(),R.layout.date_display_item,c,from,to,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
                        ListView listView=getListView();
                        listView.setAdapter (adapter);
                    }
                }).setNegativeButton("查看", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db =helper.getReadableDatabase();
                        Cursor cursor = db.rawQuery("select * from date where _id = ?", new String[] { String.valueOf(temp) });
                        if (cursor.moveToFirst())
                        {
                            String uname = cursor.getString(cursor.getColumnIndex("name"));
                            String uhobby = cursor.getString(cursor.getColumnIndex("hobby"));
                            String udate = cursor.getString(cursor.getColumnIndex("date"));
                            Intent intent = new Intent(datedisplay.this, datedetail.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("uname",uname);
                            bundle.putString("uhobby",uhobby);
                            bundle.putString("udate",udate);
                            bundle.putString("id", String.valueOf(temp));
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                        cursor.close();
                    }
                });
                AlertDialog ad=builder. create();
                ad.show();
            }
        });
        helper.close();
    }
}
