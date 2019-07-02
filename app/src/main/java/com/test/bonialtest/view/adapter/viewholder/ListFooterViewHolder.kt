package com.test.bonialtest.view.adapter.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.bonialtest.R
import com.test.bonialtest.repository.state.State
import kotlinx.android.synthetic.main.item_list_footer.view.info_card_card_view

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: State?) {
        itemView.info_card_card_view.visibility = if (status == State.LOADING) VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_footer, parent, false)
            return ListFooterViewHolder(view)
        }
    }
}