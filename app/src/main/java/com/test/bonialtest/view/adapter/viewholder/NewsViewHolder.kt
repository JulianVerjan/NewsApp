package com.test.bonialtest.view.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.test.bonialtest.R
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.view.utils.Constants.SERVER_PATTERN
import com.test.bonialtest.view.utils.ImageRequester
import com.test.bonialtest.view.utils.TimePassed
import kotlinx.android.synthetic.main.item_news.view.newsTitle
import kotlinx.android.synthetic.main.item_news.view.newsImage
import kotlinx.android.synthetic.main.item_news.view.newsDate
import kotlinx.android.synthetic.main.item_news.view.newsOwner
import java.text.SimpleDateFormat
import java.util.Locale

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var news: News

    fun bind(item: News, listener: ItemClickListener) = with(itemView) {
        news = item
        newsTitle.text = item.title

        if (item.urlToImage != null) {
            val imageRequester = ImageRequester.getInstance(context)
            imageRequester.setImageFromUrl(newsImage, item.urlToImage)
        }

        if (!item.publishedAt.isNullOrEmpty()) {
            val formatter = SimpleDateFormat(SERVER_PATTERN, Locale.ENGLISH)
            val date = formatter.parse(item.publishedAt)
            newsDate.text = TimePassed.getTimeAgo(date, context)
        } else newsDate.text = context.getString(R.string.text_not_date)

        if (!item.author.isNullOrEmpty()) newsOwner.text = item.author
        else newsOwner.text = context.getString(R.string.text_no_author)
        setOnClickListener { listener.onItemClick(itemView, item) }
    }

    interface ItemClickListener {
        fun onItemClick(view: View, item: News)
    }

    companion object {
        fun create(view: View): NewsViewHolder {
            return NewsViewHolder(view)
        }
    }
}