package com.masudinn.todo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import androidx.recyclerview.widget.LinearLayoutManager
import com.masudinn.todo.R
import com.masudinn.todo.adapter.RecyclerAdapter
import com.masudinn.todo.database.DbContract
import com.masudinn.todo.database.ReaderDbHelper
import com.masudinn.todo.model.Todo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RecyclerAdapter.Callback {
    lateinit var dbHelper: ReaderDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = ReaderDbHelper(this);

        initListener()
    }

    private fun initListener() {
        fab.setOnClickListener {
            startActivity(
                Intent(this, DetailActivity::class.java)
                    .putExtra("status", true)
            )
        }
    }

    override fun onClick(data: Todo) {
        startActivity(
            Intent(this, DetailActivity::class.java)
                .putExtra("status", false)
                .putExtra("data", data)
        )
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            DbContract.DataEntry.COLUMN_NAMA_TITLE,
            DbContract.DataEntry.COLUMN_NAMA_DESC
        )
        val sortOrder = "${DbContract.DataEntry.COLUMN_NAMA_DESC} DESC"

        val cursor =
            db.query(
                DbContract.DataEntry.TABLE_NAME,
                projection,
                null, null, null, null,
                sortOrder)
        val dataList = mutableListOf<Todo>()
        with(cursor){
            while (moveToNext()){
                val id = getLong(getColumnIndex(BaseColumns._ID))
                val title = getString(getColumnIndexOrThrow(DbContract.DataEntry.COLUMN_NAMA_TITLE))
                val desc = getString(getColumnIndexOrThrow(DbContract.DataEntry.COLUMN_NAMA_DESC))
                dataList.add(Todo(id,title,desc))
            }
        }

        var dataAdapter = RecyclerAdapter(this)
        rv.apply {
            var linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            adapter = dataAdapter
        }
        dataAdapter.setData(dataList)
        dataAdapter.notifyDataSetChanged()
    }
}