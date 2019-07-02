package com.test.bonialtest.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.toolbox.NetworkImageView
import com.test.bonialtest.R
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.view.activities.DetailActivity
import com.test.bonialtest.view.utils.Constants.EMPTY_STRING
import com.test.bonialtest.view.utils.Constants.NEWS
import com.test.bonialtest.view.utils.ImageRequester
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.detail_fragment.newsToolbar
import kotlinx.android.synthetic.main.detail_fragment.newsImage
import kotlinx.android.synthetic.main.item_news.newsTitle
import kotlinx.android.synthetic.main.item_news.newsOwner
import kotlinx.android.synthetic.main.news_detail_body.newsDescription
import kotlinx.android.synthetic.main.news_detail_body.newsContent

class DetailFragment : Fragment() {

    private lateinit var news: News

    companion object {
        fun newInstance() = DetailFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        news = activity?.intent?.getParcelableExtra(NEWS) as News
        setActionBar()
        setValues()
    }

    private fun setActionBar() {
        newsToolbar.title = EMPTY_STRING
        newsToolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        (activity as DetailActivity).setSupportActionBar(newsToolbar)
        (activity as DetailActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setValues() {
        newsTitle.text = news.title
        newsOwner.text = news.source?.name
        newsDescription.text = news.content
        newsContent.text = news.description
        if (news.urlToImage != null) {
            val imageRequester = ImageRequester.getInstance(newsImage.context)
            imageRequester.setImageFromUrl(newsImage as NetworkImageView, news.urlToImage.toString())
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}