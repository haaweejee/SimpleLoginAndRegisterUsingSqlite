package id.laka.loginregisterusingsqlite.activity.activity

import android.app.ActivityOptions
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import id.laka.loginregisterusingsqlite.R
import id.laka.loginregisterusingsqlite.activity.db.DatabaseHelper
import id.laka.loginregisterusingsqlite.activity.view.InputValidation
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Move to Register Activity
        btn_register_id.setOnClickListener {
            //create variable for transition
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            val logoTransition = Pair.create(logo_login_id as View, "logo_transition")
            val nameTransition = Pair.create(name_login_id as View, "name_transition")
            val sloganTransition = Pair.create(slogan_login_id as View, "slogan_transition")
            val usernameTransition = Pair.create(username_login_layout_id as View,"username_login_transition")
            val passwordTransition = Pair.create(password_login_layout_id as View,"password_login_transition")
            val btnLoginTransition = Pair.create(btn_login_id as View,"btn_login_transisition")
            val btnRegisterTransition = Pair.create(btn_register_id as View,"btn_register_transition")

            //make transition to register Activity
            val options = ActivityOptions.makeSceneTransitionAnimation(this@LoginActivity,
                logoTransition, nameTransition, sloganTransition, usernameTransition, passwordTransition, btnLoginTransition, btnRegisterTransition)

            //excecute
            ActivityCompat.startActivity(this@LoginActivity, intent, options.toBundle())
        }

        btn_login_id.setOnClickListener{
            verifyFromSQLite()
        }
    }


    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */

    private fun verifyFromSQLite(){
        val inputValidation = InputValidation(this)
        if (!inputValidation!!.isInputEditTextFilled(username_login_edt_id!!,username_login_layout_id!!, getString(R.string.error_message_username))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(password_login_edt_id!!,password_login_layout_id!!, getString(R.string.error_message_password))){
            return
        }

        val databaseHelper = DatabaseHelper(this)

        if (databaseHelper!!.checkUser(username_login_edt_id!!.text.toString().trim(){ it <= ' ' }, password_login_edt_id!!.text.toString().trim(){ it <= ' '})){
            val accountIntent = Intent(this@LoginActivity, UserListActivity::class.java)
            accountIntent.putExtra("USERNAME", username_login_edt_id.text.toString().trim(){ it <= ' '})
            emptyInputEditText()
            startActivity(accountIntent)
        }else{

            //Snack bar to show succes message that record is wrong
            Snackbar.make(linear_layout, getString(R.string.error_valid_username_password), Snackbar.LENGTH_LONG).show()
        }

    }

    private fun emptyInputEditText() {
        username_login_edt_id!!.text = null
        password_login_edt_id.text = null
    }

    //if button back pressed call alertbox to exit app
    override fun onBackPressed() {

        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah anda ingin keluar aplikasi?")
            .setCancelable(false)
                //if click button Yes this app Destroy
            .setPositiveButton(
                "Yes", DialogInterface.OnClickListener{
                    dialog, which ->
                    super.onBackPressed()
                }
            )
                //if click button no, app still running
            .setNegativeButton("No", DialogInterface.OnClickListener{
                dialog, which ->
                dialog?.dismiss()
            })

        //call the dialogbox
        val alertDialog : AlertDialog = builder.create()
        alertDialog.show()
    }


}
