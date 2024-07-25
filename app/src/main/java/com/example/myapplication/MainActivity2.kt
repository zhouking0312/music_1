package com.example.myapplication

import android.animation.AnimatorSet
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.myapplication.Animator.MyAnimator
import com.example.myapplication.Bean.MusicInfoBean
import com.example.myapplication.Bean.SingletonMusicInfo
import com.example.myapplication.Tool.OnColorGeneratedListener
import com.example.myapplication.Tool.SetColor
import java.io.IOException
import java.util.Locale

class MainActivity2 : AppCompatActivity() , OnColorGeneratedListener {
    //list数据
    lateinit var data:MutableList<MusicInfoBean>

    //图片和背景
    lateinit var imageView_musicimage: ImageView
    lateinit var mainLayout:ConstraintLayout
    //可点击的按钮
    lateinit var imageView_play: ImageView
    lateinit var imageView_musicl: ImageView
    lateinit var imageView_musicr: ImageView
    lateinit var imageView_musicx: ImageView
    lateinit var imageView_musicmod: ImageView
    lateinit var imageView_musiclist: ImageView
    lateinit var imageView_musiclike: ImageView
    lateinit var music_progress: SeekBar

    lateinit var textViewCurrentTime: TextView
    lateinit var textViewTotalTime: TextView

    lateinit var textView_name: TextView
    lateinit var textView_author: TextView
    var i:Int = 0
    var sty: Int = 1;
    var songDuration = 0
    //动画
    val animator = MyAnimator()
    lateinit var animator_rotate:AnimatorSet
    lateinit var animator_likeadd:AnimatorSet
    lateinit var animator_likeremove:AnimatorSet
    lateinit var setbackground:SetColor

    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        data = SingletonMusicInfo.getAllMusic()

        imageView_musicimage = findViewById(R.id.music_image)
        mainLayout = findViewById(R.id.main_music)

        textView_name = findViewById(R.id.music_name)
        textView_author = findViewById(R.id.music_author)

        music_progress = findViewById(R.id.music_progress)
        //点击按钮
        imageView_play = findViewById(R.id.music_play)
        imageView_musicl= findViewById(R.id.music_l)
        imageView_musicr= findViewById(R.id.music_r)
        imageView_musicx= findViewById(R.id.music_x)
        imageView_musicmod = findViewById(R.id.music_mod)
        imageView_musiclist = findViewById(R.id.music_list)
        imageView_musiclike = findViewById(R.id.music_like)
        //设置点击
        imageView_play.setOnClickListener(clickListener)
        imageView_musicl.setOnClickListener(clickListener)
        imageView_musicr.setOnClickListener(clickListener)
        imageView_musicx.setOnClickListener(clickListener)
        imageView_musicmod.setOnClickListener(clickListener)
        imageView_musiclist.setOnClickListener(clickListener)
        imageView_musiclike.setOnClickListener(clickListener)


        if (data.isNotEmpty()){
            //初始化图片和背景
            setbackground = SetColor(this)
            setbackground.execute(data[i].coverUrl)

            setView()

            animator_rotate = animator.setAnimator(imageView_musicimage)
            animator_likeadd = animator.likeaddAnimator(imageView_musicimage)
            animator_likeremove = animator.likeremoveAnimator(imageView_musicimage)

            animator_rotate.start()

            mediaPlayer = MediaPlayer()
            mPlayer(mediaPlayer,data[i].musicUrl)
            mediaPlayer.setOnCompletionListener {
                i++
                Log.d("over_aaa","aaaaa")
                setView()
            }


            songDuration = mediaPlayer.duration
            music_progress.setMax(songDuration)
            music_progress.setProgress(0)
            val handler = Handler()
            val runnable: Runnable = object : Runnable {
                override fun run() {
                    val currentProgress: Int = music_progress.getProgress()
                    val newProgress = currentProgress + 10
                    if (newProgress <= music_progress.getMax()) {
                        music_progress.setProgress(newProgress)
                    } else {
                        music_progress.setProgress(0)
                    }
                    handler.postDelayed(this, 1000)
                }
            }
            handler.postDelayed(runnable, 1000)
            textViewCurrentTime = findViewById<TextView>(R.id.textViewCurrentTime)
            textViewTotalTime = findViewById<TextView>(R.id.textViewTotalTime)
            music_progress.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        textViewCurrentTime.setText(formatTime(progress))
                        //                    updateRecyclerView(progress);
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    mediaPlayer.pause()
                }
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    mediaPlayer.seekTo(seekBar.progress)
                    mediaPlayer.start()
                }
            })
        }
    }

    fun formatTime(milliseconds: Int): String {
        var seconds = milliseconds / 1000
        val minutes = seconds / 60
        seconds = seconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    fun mPlayer(mediaPlayer: MediaPlayer,url:String){
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
        } catch (e: IOException) {
            Log.e("errrr", e.message!!)
        }
        mediaPlayer.start()
    }
    //点击事件
    val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.music_play -> {
//                Log.d("playplay","a"+imageView_play.isSelected)
                if (data.isNotEmpty()){
                    if (imageView_play.isSelected) {
                        animator_rotate.start()
                        mediaPlayer.start()
                        imageView_play.isSelected = false

                    }
                    else{
                        animator_rotate.pause()
                        mediaPlayer.pause()
                        imageView_play.isSelected = true
                    }
                }
            }
            R.id.music_l -> {
                if (data.isNotEmpty()){
                    if(i==0){
                        i=data.size-1
                    }
                    else i--
                    setView()
                }
            }
            R.id.music_r -> {
                if (data.isNotEmpty()){
                    if(i==data.size-1){
                        i=0
                    }
                    else i++
                    setView()
                }
            }
            R.id.music_mod -> {
                when (sty){
                    1 -> {
                        sty = 2
                        imageView_musicmod.setBackgroundResource(R.drawable.mod_2)
                    }
                    2 -> {
                        sty = 3
                        imageView_musicmod.setBackgroundResource(R.drawable.mod_3)
                    }
                    3 -> {
                        sty = 1
                        imageView_musicmod.setBackgroundResource(R.drawable.mod_1)
                    }
                }
            }
            R.id.music_list -> {
                val fragment = Fragment_1()
                fragment.show(supportFragmentManager, "fragment_1")
            }
            R.id.music_like -> {
                if (imageView_musiclike.isSelected){
                    animator_likeremove.start()
                    imageView_musiclike.isSelected = false
                }
                else{
                    animator_likeadd.start()
                    imageView_musiclike.isSelected = true
                }

            }
            R.id.music_x -> {
                val intent = Intent(this,MainActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                startActivity(intent)
                overridePendingTransition(R.anim.`in`, R.anim.out)
            }
        }
    }

    override fun onColorGenerated(color: Int) {
        mainLayout.setBackgroundColor(color)
    }
    fun setView(){
        Glide.with(this)
            .load(data[i].coverUrl)
            .transform(CircleCrop())
            .into(imageView_musicimage)
        textView_name.setText(data[i].musicName)
        textView_author.setText(data[i].author)
//        setbackground.execute(data[i].coverUrl)
    }
}