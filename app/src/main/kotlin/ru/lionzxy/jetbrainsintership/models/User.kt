package ru.lionzxy.jetbrainsintership.models

import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.lionzxy.jetbrainsintership.R
import java.io.Serializable


open class User(val id: Int) : Serializable {
    open val login: String? = null
    open var avatar_url: String? = null
    open var gravatar_id: String? = null
    open var url: String? = null
    open var html_url: String? = null
    open var followers_url: String? = null
    open var following_url: String? = null
    open var gists_url: String? = null
    open var starred_url: String? = null
    open var subscriptions_url: String? = null
    open var organizations_url: String? = null
    open var repos_url: String? = null
    open var events_url: String? = null
    open var received_events_url: String? = null
    open var type: String? = null
    open var site_admin: Boolean? = null
    open var name: String? = null
    open var company: String? = null
    open var blog: String? = null
    private var location: String? = null
    open var email: String? = null
    open var hireable: Boolean? = null
    open var bio: String? = null
    open var public_repos: Int? = null
    open var public_gists: Int? = null
    open var followers: Int? = null
    open var following: Int? = null
    open var created_at: String? = null
    open var updated_at: String? = null
    open var score: Double? = null
    override fun equals(other: Any?): Boolean {
        if (other !is User)
            return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }

    fun loadImage(view: ImageView) {
        Picasso.with(view.getContext())
                .load(avatar_url)
                .placeholder(R.drawable.default_avatar)
                .into(view)
    }

    fun getLocation(): String {
        return if (location == null) "Unknown" else location!!
    }

}