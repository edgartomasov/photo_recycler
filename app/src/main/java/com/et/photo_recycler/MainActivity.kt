package com.et.photo_recycler

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.et.photo_recycler.adapters.PhotoAdapter
import com.et.photo_recycler.api.NetworkApi
import com.et.photo_recycler.api.RetrofitClient
import com.et.photo_recycler.model.PhotoModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var title: TextView?= null
    private var photo_recycler: RecyclerView?= null

    private val service: NetworkApi get() = RetrofitClient.createClient(NetworkApi::class.java)
    private var photoListMain: ArrayList<PhotoModel> = ArrayList()

    private var photoAdapter: PhotoAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = findViewById(R.id.title)
        photo_recycler = findViewById(R.id.photo_recycler)
        getPhoto()
    }

    private fun getPhoto(){
        service.getPhoto()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { v ->
                photoListMain.addAll(v)
                createList()},
                { e -> println("Error: $e")}
            )

    }

    private fun createList(){
        photoAdapter = PhotoAdapter(photoListMain)
        photo_recycler?.adapter = photoAdapter
        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        photo_recycler?.layoutManager = layoutManager

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(photo_recycler)

        photo_recycler!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    val first = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (first >= 0){
                        title!!.text = photoListMain[first%photoListMain.size].title
                    }  else {
                        // Nothing
                    }
                } else {
                    // Nothing
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        title!!.text = photoListMain[0].title
        photo_recycler!!.scrollToPosition(1 + Integer.MAX_VALUE / 2)
    }
}