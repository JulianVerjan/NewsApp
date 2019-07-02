package com.test.bonialtest.view.fragments

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.test.bonialtest.R
import com.test.bonialtest.repository.model.News
import com.test.bonialtest.repository.state.State
import com.test.bonialtest.view.activities.DetailActivity
import com.test.bonialtest.view.adapter.NewsAdapter
import com.test.bonialtest.view.adapter.viewholder.NewsViewHolder
import com.test.bonialtest.view.utils.Constants.NEWS
import com.test.bonialtest.view.viewmodel.newsviewmodel.NewsViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_news.news_recycler_view
import kotlinx.android.synthetic.main.fragment_news.loadingViewNews
import kotlinx.android.synthetic.main.fragment_news.empty_animation
import kotlinx.android.synthetic.main.fragment_news.scrNews
import kotlinx.android.synthetic.main.item_news.view.newsImage
import kotlinx.android.synthetic.main.item_news.view.layoutNewsCard
import javax.inject.Inject

class NewsFragment : Fragment(), NewsViewHolder.ItemClickListener {

    @Inject
    lateinit var newsViewModel: NewsViewModel

    private lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = NewsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            initGridBehaviour(3)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            initGridBehaviour(2)
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun initViewModel() {
        newsViewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel::class.java)
        newsViewModel.initDataSource("US", verifyAvailableNetwork())
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter(this)
        initGridBehaviour(2)
        news_recycler_view.adapter = newsAdapter
        newsViewModel.getNews().observe(this, Observer {
            if (it.size > 0) {
                newsAdapter.submitList(it)
                news_recycler_view.visibility = View.VISIBLE
            } else {
                showEmptyView()
            }
            loadingViewNews.visibility = View.GONE
        })

        newsViewModel.getState().observe(this, Observer {
            newsAdapter.setNetworkState(it ?: State.DONE)
        })
    }

    override fun onItemClick(view: View, item: News) {
        val intent = Intent(this.context, DetailActivity::class.java)
        intent.putExtra(NEWS, item)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val p1 = Pair.create(view.newsImage as View, getString(R.string.transition_avatar_border))
            val p2 = Pair.create(view.layoutNewsCard as View, getString(R.string.transition_card))
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, p1, p2)
            context?.startActivity(intent, options.toBundle())
        } else {
            context?.startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
        }
    }

    private fun initGridBehaviour(spaceCount: Int) {
        news_recycler_view.layoutManager = GridLayoutManager(this.context, spaceCount).also {
            it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % 7 == 0)
                        spaceCount
                    else
                        1
                }
            }
        }
    }

    private fun showEmptyView() {
        empty_animation.visibility = View.VISIBLE
        empty_animation.playAnimation()
        showSnackBar()
    }

    private fun showSnackBar() {
        Snackbar.make(
            scrNews,
            getString(R.string.snack_bar_empty_message),
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun verifyAvailableNetwork(): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val networkInfo = connectivityManager?.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
