package ru.skillbranch.devintensive.ui.profile

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.utils.textBitmap
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel


class ProfileActivity : AppCompatActivity() {

    companion object {
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }

    var isEditMode = false
    val viewFields by lazy {
        mapOf<String, TextView>(
            "nickname" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )
    }

    val viewModel by lazy { ViewModelProviders.of(this).get(ProfileViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initViews(savedInstanceState)
        initViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        if (outState != null) {
            super.onSaveInstanceState(outState)
        }
        outState?.putBoolean(IS_EDIT_MODE, isEditMode)
    }

    private fun initViewModel() {
        viewModel.profileData.observe(this, Observer {
            updateUI(it)
        })
        viewModel.theme.observe(this, Observer {
            updateTheme(it)
        })
    }

    private fun updateTheme(theme: Int) {
        delegate.setLocalNightMode(theme)
    }

    private fun updateUI(profile: Profile) {
        if (profile.firstName.isNotBlank() || profile.lastName.isNotBlank()) {
            val initials =
                "${profile.firstName.firstOrNull() ?: ""}${profile.lastName.firstOrNull() ?: ""}"
            val tv = TypedValue()
            theme.resolveAttribute(R.attr.colorAccent, tv, true)
            val backgroundColor = tv.data
            iv_avatar.setImageBitmap(
                textBitmap(
                    iv_avatar.layoutParams.width,
                    iv_avatar.layoutParams.height,
                    initials,
                    backgroundColor
                )
            )
        } else {
            iv_avatar.setImageDrawable(getDrawable(R.drawable.avatar_default))
        }
        profile.toMap().forEach { (key, value) ->
            viewFields[key]?.text = value.toString()
        }
    }

    private fun saveProfileInfo() {
        if (!Profile.validateRepository(et_repository.text.toString())) et_repository.setText("")
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply { viewModel.saveProfileData(this) }
    }

    private fun initViews(state: Bundle?) {
        state?.let {
            isEditMode = it.getBoolean(IS_EDIT_MODE, false)
            showCurrentMode(isEditMode)
        }

        btn_edit.setOnClickListener {
            if (isEditMode) saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }

        btn_switch_theme.setOnClickListener {
            viewModel.switchTheme()
        }
        val repoWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    if (Profile.validateRepository(it.toString())) {
                        wr_repository.error = null
                        wr_repository.isErrorEnabled = false
                    } else {
                        wr_repository.error = getString(R.string.invalid_repo_address_message)
                    }
                }
            }

        }
        et_repository.addTextChangedListener(repoWatcher)

    }

    private fun showCurrentMode(isEditMode: Boolean) {
        viewFields
            .filter { it.key in setOf("firstName", "lastName", "about", "repository") }
            .forEach { (_, view) ->
                if (view is EditText) {
                    view.isFocusable = isEditMode
                    view.isFocusableInTouchMode = isEditMode
                    view.isEnabled = isEditMode
                    view.background.alpha = if (isEditMode) 255 else 0
                }
            }

        ic_eye.visibility = if (isEditMode) View.GONE else View.VISIBLE
        wr_about.isCounterEnabled = isEditMode
        with(btn_edit) {
            val filter: ColorFilter? = if (isEditMode) {
                PorterDuffColorFilter(
                    resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN
                )
            } else {
                null
            }

            val icon: Drawable = if (isEditMode) {
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            } else {
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }
}
