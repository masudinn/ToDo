package com.masudinn.todo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.masudinn.todo.R
import com.masudinn.todo.adapter.RecyclerAdapter
import com.masudinn.todo.database.ReaderDbHelper
import com.masudinn.todo.model.Todo

class MainActivity : AppCompatActivity(),RecyclerAdapter.Callback {
    lateinit var dbHelper : ReaderDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(data: Todo) {
        TODO("Not yet implemented")
    }
}