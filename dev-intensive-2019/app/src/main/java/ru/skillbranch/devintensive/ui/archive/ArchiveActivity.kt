package ru.skillbranch.devintensive.ui.archive

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_archive.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.applyThemeColors
import ru.skillbranch.devintensive.ui.adapters.ArchiveAdapter
import ru.skillbranch.devintensive.ui.adapters.ArchiveChatItemTouchHelperCallback
import ru.skillbranch.devintensive.viewmodels.ArchiveViewModel

class ArchiveActivity : AppCompatActivity() {

    private lateinit var viewModel: ArchiveViewModel
    private val archiveAdapter: ArchiveAdapter by lazy {
        ArchiveAdapter {
            Snackbar.make(rv_archive_list, "Click on ${it.title}", Snackbar.LENGTH_LONG)
                .applyThemeColors()
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_archive)

        initToolbar()
        initViews()
        initViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }


    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews() {
        with(rv_archive_list) {
            layoutManager = LinearLayoutManager(this@ArchiveActivity)
            adapter = archiveAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@ArchiveActivity,
                    DividerItemDecoration.VERTICAL
                )
            )

            ItemTouchHelper(
                ArchiveChatItemTouchHelperCallback(
                    archiveAdapter,
                    this@ArchiveActivity
                ) {
                    viewModel.restoreFromArchive(it.id)
                    Snackbar.make(
                        this,
                        "Вы точно хотите удалить ${it.title} из архива?",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(android.R.string.cancel) { _ ->
                            viewModel.addToArchive(it.id)
                        }
                        .applyThemeColors()
                        .show()
                }).attachToRecyclerView(this)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
        viewModel.chats.observe(this, Observer {
            archiveAdapter.items = it
        })
    }
}