package com.example.myapplication

import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.myapplication.Adapter.MultipleItemQuickAdapter
import com.example.myapplication.Animator.MyAnimator
import com.example.myapplication.Bean.DataChangeListener
import com.example.myapplication.Bean.MyData
import com.example.myapplication.Bean.RecordBean
import com.example.myapplication.Bean.ResponseBean
import com.example.myapplication.Bean.SingletonMusicInfo
import com.example.myapplication.Tool.OnColorGeneratedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() , DataChangeListener , OnColorGeneratedListener {

    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var apiInterface: ApiInterface
    lateinit var data: MutableList<MyData>
    lateinit var adapter: MultipleItemQuickAdapter
    var j = 2
    lateinit var imageView_min: ImageView
    lateinit var imageView_play2: ImageView
    lateinit var imageView_list: ImageView

    lateinit var textView_a: TextView
    lateinit var textView_name: TextView
    lateinit var textView_main: TextView

//    lateinit var setbackground: SetColor

    val animator = MyAnimator()
    lateinit var animator_rotate:AnimatorSet

    var listdata = SingletonMusicInfo.getAllMusic()

    val url = "https://hotfix-service-prod.g.mi.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //注册监听
        SingletonMusicInfo.registerListener(this)

        recyclerView = findViewById(R.id.rec)
        swipeRefreshLayout = findViewById(R.id.swip)

        imageView_min =findViewById(R.id.minmusic_image)
        imageView_play2 = findViewById(R.id.music_play2)
        imageView_list = findViewById(R.id.all_list)

        textView_name = findViewById(R.id.name)
        textView_a = findViewById(R.id.a_name)
        textView_main = findViewById(R.id.music_view)

        animator_rotate = animator.setAnimator(imageView_min)

        imageView_min.setOnClickListener(clickListener)
        imageView_play2.setOnClickListener(clickListener)
        imageView_list.setOnClickListener(clickListener)

//        setbackground =  SetColor(this)


        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInterface = retrofit.create(ApiInterface::class.java)
        get1(1, 4, 1)
        recyclerView.layoutManager = LinearLayoutManager(this)
        data = mutableListOf() // 初始化为一个空的 MutableList<MyData>
        adapter = MultipleItemQuickAdapter(data)
        recyclerView.adapter = adapter

        swipeRefreshLayout = findViewById(R.id.swip)
        swipeRefreshLayout.setOnRefreshListener {
            data.clear()
            get1(1,4,1)
            swipeRefreshLayout.isRefreshing = false
        }

        adapter.loadMoreModule.setOnLoadMoreListener {

            get1(j,1,2)

            if (j==7){
                synchronized(this) {
                    j = 2
                }
            }else synchronized(this) {
                j++
            }
            adapter.loadMoreModule.loadMoreComplete()
        }

        if (listdata.isNotEmpty()){
            textView_name.setText(listdata[0].musicName)
            textView_a.setText(listdata[0].author)
            Glide.with(this)
                .load(listdata[0].coverUrl)
                .transform(CircleCrop())
                .into(imageView_min)
            animator_rotate.start()
//            setbackground.execute(listdata[0].coverUrl)
        }



    }

    val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.minmusic_image -> {
                if (listdata.isNotEmpty()) {
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.`in`, R.anim.out)
                } else {
                    Toast.makeText(this, "列表为空", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.music_play2 -> {
                if (listdata.isNotEmpty()){
                    if (imageView_play2.isSelected){
                        animator_rotate.start()
                        imageView_play2.isSelected=false
                    }else{
                        animator_rotate.pause()
                        imageView_play2.isSelected=true
                    }
                }
            }
            R.id.all_list -> {
                val fragment = Fragment_1()
                fragment.show(supportFragmentManager, "fragment_1")
            }
        }
    }

    override fun onColorGenerated(color: Int) {
//        mainLayout.setBackgroundColor(color)
        textView_main.setBackgroundColor(color)
    }

    override fun onDestroy() {
        super.onDestroy()
        SingletonMusicInfo.unregisterListener(this)
    }

    override fun onDataChanged() {
        listdata = SingletonMusicInfo.getAllMusic()
        if (listdata.isNotEmpty()){
            textView_name.setText(listdata[0].musicName)
            textView_a.setText(listdata[0].author)
            Glide.with(this)
                .load(listdata[0].coverUrl)
                .transform(CircleCrop())
                .into(imageView_min)
            animator_rotate.start()
        }
    }


    fun get1(current: Int, size: Int, text: Int) {
        val call: Call<ResponseBean> = apiInterface.getString(current, size)
        call.enqueue(object : Callback<ResponseBean> {
            override fun onResponse(call: Call<ResponseBean>, response: Response<ResponseBean>) {
                if (response.isSuccessful) {
                    val responseBean: ResponseBean? = response.body()
                    if (responseBean != null) {
                        val records:MutableList<RecordBean> = responseBean.data.records
                        when (text) {
                            1 -> {
                                set(records)
                                Log.e("MainActivity","a1 "+data.size)
                            }
                            2 -> {
                                recy(records)
                                Log.e("MainActivity","a2 "+data.size)
                            }
                            3 -> {
                                swip(records)
                                Log.e("MainActivity","a3 "+data.size)
                            }
                        }
                    } else {
                        Log.e("MainActivity", "数据为空")
                    }
                } else {
                    Log.e("MainActivity", "请求失败")
                }
            }

            override fun onFailure(call: Call<ResponseBean>, t: Throwable) {
                Log.e("网络请求", "网络请求失败", t)
            }
        })
    }

    // 设置数据到 data 中
    fun set(records: MutableList<RecordBean>) {
        for (record: RecordBean in records) {
            data.add(MyData(record.style, record.musicInfoList))
        }
        adapter.notifyDataSetChanged()
    }

    fun recy(records: MutableList<RecordBean>) {
        for (record: RecordBean in records) {
            data.add(MyData(record.style, record.musicInfoList))
        }
        adapter.notifyDataSetChanged()
        Log.d("xxxx","xxxx"+j)
    }

    fun swip(records: MutableList<RecordBean>) {
        for (record: RecordBean in records) {
            when (record.style) {
                1 -> data.add(0, MyData(record.style, record.musicInfoList))
                2 -> data.add(1, MyData(record.style, record.musicInfoList))
                3 -> data.add(2, MyData(record.style, record.musicInfoList))
                4 -> data.add(3, MyData(record.style, record.musicInfoList))
            }
        }
        adapter.notifyDataSetChanged()
    }
}
