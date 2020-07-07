package id.laka.loginregisterusingsqlite.activity.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import id.laka.loginregisterusingsqlite.activity.model.User



class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    //create table sql query
    private val CREATE_USER_TABLE =("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PASS + " TEXT" + ")")

    //Drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Drop User Table if exists
        db?.execSQL(DROP_USER_TABLE)

        //create table again
        onCreate(db)
    }

    /**
    This Method is to fetch and return the list of user record

    @return list
     */

    fun getAllUser(): ArrayList<String>{

        //array of columns to fetch
        val columns= arrayOf(COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_PASS)

        //sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList = ArrayList<String>()

        val db = this.readableDatabase

        //query the user table
        val cursor = db.query(TABLE_USER, // Table to Query
        columns, // columns to return
        null, // columns for the WHERE clause
        null, // the values for the WHERE clause
        null,  // group the rows
            null, // filter by row groups
            sortOrder) // the sort order
        if (cursor.moveToFirst()){
            do{
                val user = User(id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                                name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                                password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASS)))
                userList.add(user.toString())
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    /** this method is to create user record
     *
     * @param user
     */

    fun addUser(user: User){
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_PASS, user.password)

        //Inserting row
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    /**
     * This method to check user exist or not
     *
     * @param username
     * @return true/false
     */
    fun checkUser(username: String): Boolean {
        // array of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_USER_NAME = ?"
        // selection argument
        val selectionArgs = arrayOf(username)
        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         */
        val cursor = db.query(TABLE_USER, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if (cursorCount > 0) {
            return true
        }
        return false
    }

    /**
     * This Method to check user exits or not
     *
     * @param username
     * @param password
     * @return true/false
     */

    fun checkUser(username: String, password: String): Boolean{

        // array of colums to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase

        //selection criteria
        val selection =  "$COLUMN_USER_NAME = ? AND $COLUMN_USER_PASS = ?"

        //selection arguments
        val selectionArgs = arrayOf(username, password)

        //query user table with conditions

        val cursor = db.query(TABLE_USER, // Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, // the values for the Where clause
            null, // group the row
            null, //filter by row group
            null) //the sort order
        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false

    }

    companion object{
        // Database Version
        private const val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "userDb"

        // User table Name
        private val TABLE_USER = "user"

        // User Table Column names
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_PASS = "user_password"
    }
}