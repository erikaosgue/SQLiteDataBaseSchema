package com.erikaosgue.sqlitedatabaseschema

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.erikaosgue.sqlitedatabaseschema.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
	lateinit var  activityMainBinding: ActivityMainBinding
 	override fun onCreate(savedInstanceState: Bundle?) {
 		super.onCreate(savedInstanceState)
 		activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
 		setContentView(activityMainBinding.root)

		//1. Get Instance of the DataBase
		// val db = DataBaseHandler(this)

		//2. Creating an Object declaration to use as a singleton
		//val db = object : DataBaseHandler(this) {}

		//3. Calling the object declaration with the function to use as a singleton
		val db = mDataBaseHanlerObject.create(this)

		//Insert the data to the Database when click button
		activityMainBinding.btnInsert.setOnClickListener {

			if (editTextName.text.toString().isNotEmpty() && editTextAge.text.toString().isNotEmpty()) {

				val user = User()
				user.name = editTextName.text.toString()
				user.age = editTextAge.text.toString().toInt()
				db.insertData(user)
				clearField()

			}
			else {
				Toast.makeText(this, "Please Fill All Data's", Toast.LENGTH_SHORT).show()
			}
		}

		// Read data when clicking the button
		btnRead.setOnClickListener {

			// data is a list of users
			val data = db.readData()

			tvResult.text = ""

			// Add the id, name and age to the tvResult
			for (i in 0 until data.size) {
				tvResult.append("ID: " + data[i].id.toString() + "  |  " + "Name: " + data[i].name + "  |  " + "Age:" + data[i].age + "\n")
			}
		}
	}

	private fun clearField() {
		editTextName.text.clear()
		editTextAge.text.clear()
	}
}
