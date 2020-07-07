package id.laka.loginregisterusingsqlite.activity.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import id.laka.loginregisterusingsqlite.R
import id.laka.loginregisterusingsqlite.activity.db.DatabaseHelper
import id.laka.loginregisterusingsqlite.activity.model.User
import id.laka.loginregisterusingsqlite.activity.view.InputValidation
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register_id.setOnClickListener {
            postDatatoSQLite()
        }

        btn_to_login_activity.setOnClickListener {
            finish()
        }

    }

    private fun postDatatoSQLite(){
        val inputValidation = InputValidation(this)
        if (!inputValidation.isInputEditTextFilled(username_register_edt_id, username_register_layout_id, getString(R.string.error_message_username))){
            return
        }
        if (!inputValidation.isInputEditTextFilled(password_register_edt_id, password_register_layout_id, getString(R.string.error_message_password))){
            return
        }
        if (!inputValidation.isInputEditTextMatches(password_register_edt_id, retype_password_edt_register_id,retype_password_layout_register_id,
                getString(R.string.error_message_password_match))){
            return
        }
        val databaseHelper = DatabaseHelper(this)

        if (!databaseHelper.checkUser(username_register_edt_id.text.toString().trim())){

            var user = User(name = username_register_edt_id.text.toString().trim(),
                            password = password_register_edt_id.text.toString().trim())

            databaseHelper!!.addUser(user)

            //Snackbar to show succes message that record save successfully
            Snackbar.make(linear_layout, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
            emptyInputEditText()
        }else{
            //Snack bar to show error message thaht record already exists
            Snackbar.make(linear_layout, getString(R.string.error_message_user_exits), Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * this method is to empty all input edit textg
     */
    private fun emptyInputEditText(){
        username_register_edt_id!!.text = null
        password_register_edt_id!!.text = null
        retype_password_edt_register_id!!.text = null
    }

}
