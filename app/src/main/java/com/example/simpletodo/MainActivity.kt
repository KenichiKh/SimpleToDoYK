package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FileUtils.writeLines
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTask = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                listOfTask.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()

            }

        }
        //1. Let's detect when the user clicks on the add button
 //       findViewById<Button>(R.id.addButton).setOnClickListener {
 //           // Code in here is going to be executed when the user clicks on a button
 //           Log.i ("Clicked", "User clicked on button")
 //       }
        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTask, onLongClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val inputTaskField = findViewById<EditText>(R.id.addTaskField)

        findViewById<Button>(R.id.addButton).setOnClickListener {
            val userInputtedTask = inputTaskField.text.toString()

            listOfTask.add(userInputtedTask)

            adapter.notifyItemInserted(listOfTask.size - 1)

            inputTaskField.setText("")

            saveItems()
        }
    }
    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    fun  loadItems() {
        try {
            listOfTask = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException){
            ioException.printStackTrace()
        }
    }
    fun  saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTask)
        }  catch(ioException: IOException){
            ioException.printStackTrace()
        }
    }
}