package com.codepath.apps.restclienttemplate

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class TimelineActivity : AppCompatActivity() {

    lateinit var swipeContainer: SwipeRefreshLayout
    lateinit var client: TwitterClient
    lateinit var rvTweets: RecyclerView
    lateinit var adapter: TweetsAdapter

    public val tweets = ArrayList<Tweet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)

        client = TwitterApplication.getRestClient(this)

        swipeContainer = findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            Log.i("TimelineActivity", "Refreshing timeline")
            populateHomeTimeline()
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        rvTweets = findViewById(R.id.rvTweets)
        adapter = TweetsAdapter(tweets)

        rvTweets.layoutManager = LinearLayoutManager(this)
        rvTweets.adapter = adapter

        populateHomeTimeline()
    }

    // Tell which menu resource to use on menu created
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private val actLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    { it ->
        if(it.resultCode == Activity.RESULT_OK){

            val tweet = it.data?.getParcelableExtra<Tweet>("tweet") as Tweet

            // Update timeline
            tweets.add(0, tweet)
            adapter.notifyItemInserted(0)
            rvTweets.smoothScrollToPosition(0)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.compose){

            // Navigate to compose screen
            var intent = Intent(this, ComposeActivity::class.java)
            actLauncher.launch(intent)

        }
        return super.onOptionsItemSelected(item)
    }

    fun populateHomeTimeline() {
        client.getHomeTimeline(object: JsonHttpResponseHandler(){
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("TimelineActivity", "Timeline fetch failed $response")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i("TimelineActivity", "Timeline fetched successfully ${json.jsonArray}")

                val jsonArray = json.jsonArray
                try {
                    // Clear out currently fetched tweets
                    adapter.clear()
                    val tweetsRetrieved = Tweet.fromJsonArray(jsonArray)
                    tweets.addAll(tweetsRetrieved)
                    adapter.notifyDataSetChanged()
                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false)
                } catch (e: JSONException){
                    Log.e("TimelineActivity", "JSON Exception: $e")
                }
            }

        })
    }
}