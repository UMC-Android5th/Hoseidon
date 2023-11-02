package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity(){
    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()

        setPlayer(song)
        binding.songDownIb.setOnClickListener{
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }
    }

    private fun initSong(){
        if(intent.hasExtra("title")&&intent.hasExtra("singer")){
            song =Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second",0),
                intent.getIntExtra("playTime",0),
                intent.getBooleanExtra("isPlaying",false)

            )
        }
    }

    private fun setPlayer(song: Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second/60,song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime/60,song.playTime%60)
        binding.songProgressSb.progress = (song.second*1000/song.playTime)

        setPlayerStatus(song.isPlaying)
    }
    fun setPlayerStatus(isPlaying:Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else{
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    inner class Timer(private val playTime: Int,var isPlaying: Boolean = true):Thread(){
        private var second : Int = 0
        private var mills : Float = 0f
        override fun run() {
            super.run()
            while (true){
                if(second >= playTime){
                    break

                }
                if(isPlaying){
                    sleep(50)
                    mills +=50

                    runOnUiThread{
                        //binding.songProgressSb.progress = ((mills/playTime))
                    }
                }
            }
        }
    }
}


