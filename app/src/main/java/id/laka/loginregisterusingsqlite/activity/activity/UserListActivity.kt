package id.laka.loginregisterusingsqlite.activity.activity

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.laka.loginregisterusingsqlite.R
import id.laka.loginregisterusingsqlite.activity.adapter.UsersReyclerAdapter
import id.laka.loginregisterusingsqlite.activity.db.DatabaseHelper
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : AppCompatActivity() {

    var listUsers: MutableList<String> = ArrayList()
    lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        initObjects()
    }

    /**
     * this method is to initialize objects to be used
     */
    private fun initObjects() {
        var userRecyclerAdapter = UsersReyclerAdapter(listUsers as ArrayList<String>)
        val layoutManager = LinearLayoutManager(applicationContext)
        rv_list.layoutManager = layoutManager
        rv_list.setHasFixedSize(true)
        rv_list.adapter = userRecyclerAdapter

        val activity = this@UserListActivity
        databaseHelper = DatabaseHelper(activity)

        val usernameIntent = intent.getStringExtra("USERNAME")
        tv_user_id.text = usernameIntent

        var getDataFromSQLite = GetDataFromSQLite()
        getDataFromSQLite.execute()
    }

    inner class GetDataFromSQLite : AsyncTask<Void, Void, ArrayList<String>>() {
        override fun doInBackground(vararg params: Void?): ArrayList<String>? {
            return databaseHelper.getAllUser()
        }

        override fun onPostExecute(result: ArrayList<String>?) {
            super.onPostExecute(result)
            listUsers.clear()
            listUsers.addAll(result!!)
        }
    }
    /**
     * this class is to fetch all user records from sqlite
     */
}

