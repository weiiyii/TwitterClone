package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.textfield.TextInputLayout
import okhttp3.Headers

class ComposeActivity : AppCompatActivity() {

    lateinit var etCompose: EditText
    lateinit var btnTweet: Button

    lateinit var client: TwitterClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        Log.i("ComposeActivity", "Compose page launched!")
        etCompose = findViewById(R.id.etTweet)
        btnTweet = findViewById(R.id.btnTweet)
        client = TwitterApplication.getRestClient(this)

        etCompose.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, end: Int) {

            }

            override fun afterTextChanged(s: Editable) {

                btnTweet.isEnabled = s.length <= 280
            }

        })

        btnTweet.setOnClickListener {

            val tweetContent = etCompose.text.toString()

            if(tweetContent.isEmpty()){
                Toast.makeText(this, "Empty tweet!", Toast.LENGTH_SHORT).show()
            }
            else if(tweetContent.length > 280){
                Toast.makeText(this, "Too long!", Toast.LENGTH_SHORT).show()
            }
            else{

                // TODO: API call to publish the tweet
                client.publishTweet(tweetContent, object : JsonHttpResponseHandler(){
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e("ComposeActivity", "Failed to publish tweet", throwable)
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        // Send the tweet back to the timeline activity
                        // Get back to TimelineActivity
                        // See new tweet without pull to refresh (avoid making another get request)
                        val tweet = Tweet.fromJson(json.jsonObject)

                        val intent = Intent()
                        intent.putExtra("tweet", tweet)
                        setResult(RESULT_OK, intent)
                        finish()

                    }

                })
            }


        }
    }
}