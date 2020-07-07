package id.laka.loginregisterusingsqlite.activity.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.laka.loginregisterusingsqlite.R

class UsersReyclerAdapter (private val listUsers: ArrayList<String>) : RecyclerView.Adapter<UsersReyclerAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // inflating recycler item view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_recycler, parent, false)

        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        holder.textViewName.text = listUsers[position]
        holder.textViewPassword.text = listUsers[position]
    }


    override fun getItemCount(): Int {
        return listUsers.size
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var textViewName: TextView = view.findViewById(R.id.tvName)
        var textViewPassword: TextView = view.findViewById(R.id.tvPassword)

    }



}