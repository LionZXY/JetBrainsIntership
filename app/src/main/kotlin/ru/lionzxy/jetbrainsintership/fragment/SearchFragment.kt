package ru.lionzxy.jetbrainsintership.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Fade
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.arlib.floatingsearchview.FloatingSearchView
import ru.lionzxy.jetbrainsintership.JetbrainsIntership
import ru.lionzxy.jetbrainsintership.R
import ru.lionzxy.jetbrainsintership.adapters.SearchResultListAdater
import ru.lionzxy.jetbrainsintership.animations.DetailsTransition
import ru.lionzxy.jetbrainsintership.exceptions.HTTPCodeException
import ru.lionzxy.jetbrainsintership.interfaces.EndlessScrollListener
import ru.lionzxy.jetbrainsintership.interfaces.HideViewListener
import ru.lionzxy.jetbrainsintership.interfaces.OnUserClick
import ru.lionzxy.jetbrainsintership.interfaces.SearchQueryListener
import ru.lionzxy.jetbrainsintership.models.User
import ru.lionzxy.jetbrainsintership.utils.EndlessData
import java.lang.ref.WeakReference
import java.net.UnknownHostException


/**
 * Created by Nikita Kulikov on 28.04.17.
 *
 * Возможно полное или частичное копирование
 */

open class SearchFragment : Fragment(), EndlessData.OnDataReceive, EndlessData.OnChangeQuery, OnUserClick {
    public val TAG = "searchfragment";

    private var mSearchQuery: SearchQueryListener? = null
    private var mSearchView: FloatingSearchView? = null
    private var mRecyclerView: RecyclerView? = null
    private var mEndlessData: EndlessData? = null
    private var mProgressBar: ProgressBar? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = view?.findViewById(R.id.search_list) as RecyclerView
        mSearchView = view.findViewById(R.id.search_input) as FloatingSearchView
        mProgressBar = view.findViewById(R.id.search_progress) as ProgressBar

        mEndlessData = EndlessData()
        mEndlessData!!.setQuery(JetbrainsIntership.getLastQuery())
        mRecyclerView!!.adapter = SearchResultListAdater(mEndlessData!!, this)
        mRecyclerView!!.layoutManager = LinearLayoutManager(context)
        mRecyclerView!!.addOnScrollListener(EndlessScrollListener(WeakReference(mEndlessData!!)))
        mRecyclerView!!.addOnScrollListener(HideViewListener(WeakReference(mSearchView!!)))

        mSearchQuery = SearchQueryListener(mSearchView!!, mEndlessData!!)
        mSearchView?.setSearchBarTitle(JetbrainsIntership.getLastQuery())
        mSearchView?.setOnQueryChangeListener(mSearchQuery)
        mSearchView?.setOnSearchListener(mSearchQuery)
        mSearchView?.setOnFocusChangeListener(mSearchQuery)
        mSearchView?.setShowSearchKey(true)
        mSearchView?.setOnMenuItemClickListener(mSearchQuery)

        mEndlessData?.addListener(this)
        mEndlessData?.addListenerQuery(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "OnCreateView")
        return inflater!!.inflate(R.layout.fragment_main_search, container, false)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
        //Todo https://github.com/arimorty/floatingsearchview/issues/218
        mSearchView?.visibility = View.GONE
        mSearchView?.visibility = View.VISIBLE
        mSearchView?.refreshDrawableState()
    }

    override fun onDestroy() {
        super.onDestroy()
        mEndlessData?.onDestroy()
        mSearchQuery?.onClose()
    }

    override fun onDataReceive(page: Int) {
        mProgressBar?.visibility = View.GONE
        mRecyclerView?.visibility = View.VISIBLE
    }

    // Прогрузка первого результата
    override fun onChangeQuery(oldQuery: String?, newQuery: String?) {
        Log.d(TAG, "OnChangeQuery()")
        mProgressBar?.visibility = View.VISIBLE
        mRecyclerView?.visibility = View.GONE
    }


    override fun onSomeError(e: Throwable) {
        if (e is HTTPCodeException) {
            var code: Int = e.code
            when (code) {
                404 -> Toast.makeText(context, R.string.error_none_answer, Toast.LENGTH_LONG).show()
                403 -> Toast.makeText(context, R.string.error_ddos, Toast.LENGTH_LONG).show()
                else -> {
                    Toast.makeText(context, "Сервер вернул ошибку: $code", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            if (e is UnknownHostException) {
                Toast.makeText(context, R.string.error_network, Toast.LENGTH_LONG).show()
            }
            Log.e(TAG, "onSomeError() ${e.message} ${e.javaClass}")
        }
    }

    override fun onUserClick(holder: SearchResultListAdater.ViewHolder, user: User) {
        val userFragment: Fragment = UserFragment()
        val args = Bundle()
        args.putSerializable("user", user)
        userFragment.arguments = args

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            userFragment.sharedElementEnterTransition = DetailsTransition()
            userFragment.enterTransition = Fade()
            exitTransition = Fade()
            userFragment.sharedElementReturnTransition = DetailsTransition()
        }

        // Анимация
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.addToBackStack(TAG)
        fragmentTransaction.addSharedElement(holder.mAva, "user_avatar")
        fragmentTransaction.addSharedElement(holder.mName, "user_name")
        fragmentTransaction.addSharedElement(holder.mScore, "user_score")
        fragmentTransaction.addSharedElement(view?.findViewById(R.id.user_star), "user_score_image")
        fragmentTransaction.replace(R.id.fragment_container, userFragment)
        fragmentTransaction.commit()
    }


}