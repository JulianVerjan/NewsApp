package com.test.bonialtest.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.bonialtest.R
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.repository.state.State
import com.test.bonialtest.view.adapter.viewholder.ListFooterViewHolder
import com.test.bonialtest.view.adapter.viewholder.NewsViewHolder

class NewsAdapter(private val listener: NewsViewHolder.ItemClickListener) :
    PagedListAdapter<News, RecyclerView.ViewHolder>(NewsDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state: State? = null

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return when (viewType) {
            DATA_VIEW_TYPE -> NewsViewHolder.create(view)
            FOOTER_VIEW_TYPE -> ListFooterViewHolder.create(parent)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            DATA_VIEW_TYPE -> (holder as? NewsViewHolder)?.bind(getItem(position) as News, listener)
            FOOTER_VIEW_TYPE -> (holder as? ListFooterViewHolder)?.bind(state)
        }
    }

    private fun hasExtraRow(): Boolean {
        return state != null && (state == State.LOADING || state == State.ERROR)
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            FOOTER_VIEW_TYPE
        } else {
            DATA_VIEW_TYPE
        }
    }

    fun setNetworkState(newNetworkState: State) {
        if (!currentList.isNullOrEmpty()) {
            val previousState = this.state
            val hadExtraRow = hasExtraRow()
            this.state = newNetworkState
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            } else if (hasExtraRow && previousState !== newNetworkState) {
                notifyItemChanged(itemCount - 1)
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.title == newItem.title
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }
}