package ru.lionzxy.jetbrainsintership.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.lionzxy.jetbrainsintership.R
import ru.lionzxy.jetbrainsintership.interfaces.OnUserClick
import ru.lionzxy.jetbrainsintership.models.User
import ru.lionzxy.jetbrainsintership.utils.EndlessData


/**
 * Created by Nikita Kulikov on 29.04.17.
 *
 *
 * Возможно полное или частичное копирование
 */

class SearchResultListAdater(private val endlessData: EndlessData, private var onUserClick: OnUserClick?) : RecyclerView.Adapter<SearchResultListAdater.ViewHolder>(), EndlessData.OnDataReceive {
    public val TAG = "SearchAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)

        if (viewType == -1) {
            view.findViewById(R.id.user_progress_bar)?.visibility = View.VISIBLE
            view.findViewById(R.id.user_card)?.visibility = View.GONE
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User? = endlessData.getValue(position)
        if (user != null) {
            holder.mName.text = user.login
            holder.mScore.text = user.score?.toInt().toString()
            holder.listener = View.OnClickListener { onUserClick?.onUserClick(holder, user) }
            Picasso.with(holder.mAva.context).load(user.avatar_url).error(R.drawable.default_avatar).into(holder.mAva)
        }
    }

    // Показываем ProgressBar, если данные еще не прогрузились
    override fun getItemViewType(position: Int): Int {
        if (position >= endlessData.getCount() && position < endlessData.getTotalSize())
            return -1
        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return if (endlessData.getTotalSize() > endlessData.getCount()) endlessData.getCount() + 1 else endlessData.getCount()
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mName: TextView = view.findViewById(R.id.user_name) as TextView
        val mScore: TextView = view.findViewById(R.id.user_score) as TextView
        val mAva: ImageView = view.findViewById(R.id.user_avatar) as ImageView
        var listener: View.OnClickListener? = null

        init {
            view.findViewById(R.id.user_card).setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onClick(v)
        }
    }

    override fun onSomeError(e: Throwable) {
        Log.e(TAG, "Error while get page", e)
    }

    override fun onDataReceive(page: Int) {
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        endlessData.addListener(this)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        endlessData.removeListener(this)
        onUserClick = null
    }
}
