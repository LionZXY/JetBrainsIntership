package ru.lionzxy.jetbrainsintership.fragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.lionzxy.jetbrainsintership.R
import ru.lionzxy.jetbrainsintership.databinding.FragmentUserBinding
import ru.lionzxy.jetbrainsintership.interfaces.OnUserUpdate
import ru.lionzxy.jetbrainsintership.models.User
import ru.lionzxy.jetbrainsintership.utils.UserUpdater


/**
 * Created by Nikita Kulikov on 01.05.17.
 *
 * Возможно полное или частичное копирование
 */
class UserFragment : Fragment(), OnUserUpdate {
    val TAG = "userfragment"
    var user: User? = null
    var binding: FragmentUserBinding? = null
    var updater: UserUpdater? = null
    var swipeRefresh: SwipeRefreshLayout? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        user = arguments.getSerializable("user") as User
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_user, container, false)
        val view = binding!!.root
        binding!!.user = user
        user?.loadImage(view.findViewById(R.id.user_avatar) as ImageView)
        updater = UserUpdater(user!!)
        updater!!.updateData(this)
        swipeRefresh = view.findViewById(R.id.user_refresh) as SwipeRefreshLayout
        swipeRefresh!!.setOnRefreshListener { updater!!.updateData(this@UserFragment) }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable("user", user)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState?.getSerializable("user") != null)
            user = savedInstanceState.getSerializable("user") as User
    }

    override fun onUserUpdate(newUser: User) {
        arguments.putSerializable("user", newUser)
        user = newUser
        binding!!.user = newUser
        swipeRefresh?.isRefreshing = false

    }

}