package id.laka.loginregisterusingsqlite.activity.view

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class InputValidation (private val context: Context){

    /**
     * Method to check inputedittext filled
     *
     * @param textInputEdittext
     * @param textinputlayout
     * @param message
     * @return
     */

    fun isInputEditTextFilled(textInputEditText: TextInputEditText, textInputLayout: TextInputLayout, message:String): Boolean{
        val value = textInputEditText.text.toString().trim()
        if (value.isEmpty()){
            textInputLayout.error = message
            hideKeyboardFrom(textInputEditText)
            return false
        }else{
            textInputLayout.isErrorEnabled = false
        }

        return true
    }

    /**
     * Method to check both inputeditText value matches
     *
     * @param textinputEditText1
     * @param textinputEditText2
     * @param textinputLayout
     * @param message
     * @return
     */
    fun isInputEditTextMatches(textInputEditText1: TextInputEditText, textInputEditText2: TextInputEditText, textInputLayout: TextInputLayout, message: String):Boolean{
        val  value1 = textInputEditText1.text.toString().trim()
        val  value2 = textInputEditText2.text.toString().trim()
        if (!value1.contentEquals(value2)){
            textInputLayout.error = message
            hideKeyboardFrom(textInputEditText2)
            return false
        }
        return true
    }

    /**
     * method to hide keyboard
     *
     * @param view
     */

    private fun hideKeyboardFrom(view: View){
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}