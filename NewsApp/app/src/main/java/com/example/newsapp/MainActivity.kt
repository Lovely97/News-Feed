package com.example.newsapp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.Toast
import android.net.Uri
import com.android.volley.Request
import androidx.browser.customtabs.CustomTabsIntent
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(),NewsItemClicked
{
    private lateinit var mAdapter: NewsListAdapter  //  m is used to denote it is a member variable means it is accessible everywhere
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
       // val items=
            fetchData()
      //  val adapter: NewsListAdapter= NewsListAdapter(listener,this)  // we say that activity is our listener  ,for this mainactivity implements listener interface 'NewsItemCLicked'
        mAdapter=NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }
    private fun fetchData()
    {
       val url="https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=1d04d79bdd4540e69a24cbabd1c8b0c0"
        //val url="https://saurav.tech/NewsAPI/top-headlines/category/sports/in.json"
        // for simplicity uncomment the above url and comment newsapi url and also remove object: from jsonobjectrequest and also  comment fungetheaders functionality
        val jsonObjectRequest = object: JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener {
                    val newsJsonArray = it.getJSONArray("articles")  // extracting array of json obects named article
                    val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()) {
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("url"),
                                newsJsonObject.getString("urlToImage")
                        )
                        newsArray.add(news)
                    }
                    mAdapter.updateNews(newsArray)
                },
                Response.ErrorListener {

                }
        )
       {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
               return headers
            }
       }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    override fun onItemClicked(item: News)
    {
        // implementing custom tabs
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}