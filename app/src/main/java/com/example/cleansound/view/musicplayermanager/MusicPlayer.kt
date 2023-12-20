package com.example.cleansound.view.musicplayermanager

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.Toast

class MusicPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var isMusicPlaying: Boolean = false

    fun playMusic(previewUrl: String?){
        if (previewUrl != null) {
            stopMusic()

            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(previewUrl)
                prepareAsync()
                setOnPreparedListener {
                    start()
                    isMusicPlaying = true
                }
                setOnCompletionListener {
                    isMusicPlaying = false
                }
            }
        } else {
            Toast.makeText(context, "Music Tidak Tersedia", Toast.LENGTH_SHORT).show()
        }
    }
    fun pauseMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isMusicPlaying = false
            }
        }
    }
    fun stopMusic(){
        mediaPlayer?.let{
            if (it.isPlaying || it.isLooping) {
                it.stop()
                it.prepareAsync()
                isMusicPlaying = false
            }
        }
    }

    fun isMusicPlaying(): Boolean {
        return isMusicPlaying
    }

    fun release(){
        mediaPlayer?.release()
        mediaPlayer = null
    }
}