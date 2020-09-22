package com.masudinn.todo.activity

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.Toast
import com.masudinn.todo.R
import com.masudinn.todo.database.DbContract
import com.masudinn.todo.database.ReaderDbHelper
import com.masudinn.todo.model.Todo
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    lateinit var dbHelper: ReaderDbHelper
    lateinit var db: SQLiteDatabase
    lateinit var data: Todo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        dbHelper = ReaderDbHelper(this)
        db = dbHelper.writableDatabase

        var status = intent.getBooleanExtra("status", false)
        if (status) {
            initListener()
        } else {
            data = intent.getParcelableExtra("data")!!
            edtTitle.setText(data.title)
            edtDesc.setText(data.desc)

            btnDelete.visibility = View.VISIBLE
            btnSave.text = "Edit Plan"
            initListenerEdit()

        }
    }

    private fun initListenerEdit() {
        btnDelete.setOnClickListener {
            val selection = "${BaseColumns._ID} LIKE ?"
            val selectionArg = arrayOf(data.id.toString())

            val deleteRows = db.delete(DbContract.DataEntry.TABLE_NAME, selection, selectionArg)
            if (deleteRows == -1) {
                Toast.makeText(this, "Data Gagal diHapus", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Data Berhasil diHapus", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        btnSave.setOnClickListener {
            var mTitle = edtTitle.text.toString()
            var mDesc = edtDesc.text.toString()

            if (mTitle.isNullOrEmpty()) {
                edtTitle.error = "Silahkan Masukan Title"
                edtDesc.requestFocus()
            } else if (mDesc.isNullOrEmpty()) {
                edtTitle.requestFocus()
                edtDesc.error = "Silahkan Masukan Description"
            } else {
                val selection = "${BaseColumns._ID} LIKE ?"
                val selectionArg = arrayOf(data.id.toString())

                val values = ContentValues().apply {
                    put(DbContract.DataEntry.COLUMN_NAMA_TITLE, mTitle)
                    put(DbContract.DataEntry.COLUMN_NAMA_DESC, mDesc)
                }

                val newRowId = db.update(DbContract.DataEntry.TABLE_NAME, values,selection, selectionArg)
                if (newRowId == -1) {
                    Toast.makeText(this, "Data Gagal diEdit", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Data Berhasil diEdit", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

    }

    private fun initListener() {
        btnSave.setOnClickListener {
            var mTitle = edtTitle.text.toString()
            var mDesc = edtDesc.text.toString()

            if (mTitle.isNullOrEmpty()) {
                edtTitle.error = "Silahkan Masukan Title"
                edtDesc.requestFocus()
            } else if (mDesc.isNullOrEmpty()) {
                edtTitle.requestFocus()
                edtDesc.error = "Silahkan Masukan Description"
            } else {
                val values = ContentValues().apply {
                    put(DbContract.DataEntry.COLUMN_NAMA_TITLE, mTitle)
                    put(DbContract.DataEntry.COLUMN_NAMA_DESC, mDesc)
                }

                val newRowId = db.insert(DbContract.DataEntry.TABLE_NAME, null, values)
                if (newRowId == -1L) {
                    Toast.makeText(this, "Data Gagal diSimpan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Data Berhasil diSimpan", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}