package com.erikaosgue.sqlitedatabaseschema

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

// Option 3 Creating a Object declaration
object mDataBaseHanlerObject {
    fun create(context: Context) = DataBaseHandler(context)
}

//Option 2
// open class DataBaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,

// option 1 and 3
class DataBaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    1) {


    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_NAME VARCHAR(256),$COLUMN_AGE INTEGER)"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //onCreate(db);
        // Drop "Delete the old Table"
        db?.execSQL("DROP TABLE IF EXISTS TABLE_NAME")

        //create a New table "Again"
        onCreate(db)

    }

    // CRUD

    fun insertData(user: User) {

        val database = this.writableDatabase

        val contentValues = ContentValues()

        contentValues.put(COLUMN_NAME, user.name)
        contentValues.put(COLUMN_AGE, user.age)

        val result = database.insert(TABLE_NAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
    fun readData(): MutableList<User> {
        val list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val user = User()
                user.id = result.getString(result.getColumnIndex(COLUMN_ID)).toInt()
                user.name = result.getString(result.getColumnIndex(COLUMN_NAME))
                user.age = result.getString(result.getColumnIndex(COLUMN_AGE)).toInt()
                list.add(user)
            }
            while (result.moveToNext())
        }
        return list
    }
}