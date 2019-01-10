package com.sopt.famfam.fragment

import android.app.*
import android.os.Bundle
import android.view.View
import android.widget.*
import com.sopt.famfam.R
import com.sopt.famfam.activity.SignupActivity
import java.text.DateFormat
import java.util.Calendar


class DatePickerFragment3 : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var calendar: Calendar


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Initialize a calendar instance
        calendar = Calendar.getInstance()

        // Get the system current date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)



        /*
            **** reference source developer.android.com ***

            DatePickerDialog(Context context)
                Creates a new date picker dialog for the current date using the
                parent context's default date picker dialog theme.

            DatePickerDialog(Context context, int themeResId)
                Creates a new date picker dialog for the current date.

            DatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener,
            int year, int month, int dayOfMonth)
                Creates a new date picker dialog for the specified date using the parent
                context's default date picker dialog theme.

            DatePickerDialog(Context context, int themeResId, DatePickerDialog.OnDateSetListener
            listener, int year, int monthOfYear, int dayOfMonth)
                Creates a new date picker dialog for the specified date.
        */

        // Initialize a new date picker dialog and return it
        return DatePickerDialog(
            activity, // Context
            // Put 0 to system default theme or remove this parameter
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, // Theme
            this, // DatePickerDialog.OnDateSetListener
            year, // Year
            month, // Month of year
            day // Day of month
        )
    }


    // When date set and press ok button in date picker dialog
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
//        Toast.makeText(
//                activity,
//                "Date Set : ${formatDate(year,month,day)}"
//                ,Toast.LENGTH_SHORT
//        ).show()

        var plushMonth : Int = month + 1
        var realMonth : String = plushMonth.toString()
        var realDay : String = day.toString()
        when (realMonth) {
            "1" -> realMonth = "01"
            "2" -> realMonth = "02"
            "3" -> realMonth = "03"
            "4" -> realMonth = "04"
            "5" -> realMonth = "05"
            "6" -> realMonth = "06"
            "7" -> realMonth = "07"
            "8" -> realMonth = "08"
            "9" -> realMonth = "09"
        }

        when (realDay) {
            "1" -> realDay = "01"
            "2" -> realDay = "02"
            "3" -> realDay = "03"
            "4" -> realDay = "04"
            "5" -> realDay = "05"
            "6" -> realDay = "06"
            "7" -> realDay = "07"
            "8" -> realDay = "08"
            "9" -> realDay = "09"
        }

        var birth: String = "${year}-${realMonth}-${realDay}"
        // Display the selected date in text view
        // formatDate(year,month,day)
        activity.findViewById<TextView>(R.id.tv_more_editprofile_birth_text).hint = "$year-$realMonth-$realDay"
        activity.findViewById<TextView>(R.id.tv_more_editprofile_birth_text).text = birth
    }


    // Custom method to format date
    private fun formatDate(year: Int, month: Int, day: Int): String {
        // Create a Date variable/object with user chosen date
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time

        // Format the date picker selected date
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM)
        return df.format(chosenDate)
    }
}