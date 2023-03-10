package com.example.a7minutewprkout

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.a7minutewprkout.databinding.ActivityExercise2Binding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private var binding:ActivityExercise2Binding?=null
    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0
    private var player:MediaPlayer?=null
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1
    private var tts:TextToSpeech?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExercise2Binding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseList=Constants.defaultExerciseList()
        tts= TextToSpeech(this,this)
        binding?.toolbarExercise?.setNavigationOnClickListener{
            onBackPressed()
        }
setupRestView()
    }

   override fun onInit(status: Int){
        if(status==TextToSpeech.SUCCESS){
            val result=tts?.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS","The language specified is not supported!")

            }
        }
            else{
                Log.e("TTS","Initialization failed!")
            }
        }

    private fun speakOut(text : String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupRestView(){
        try{
            val soundURI= Uri.parse("android.resource://com.example.a7minutewprkout/"+R.raw.app_src_main_res_raw_press_start)
            player=MediaPlayer.create(applicationContext,soundURI)
            player?.isLooping=false

            player?.start()
        }catch(e:Exception){
            e.printStackTrace()
        }

        binding?.flRestView?.visibility= View.VISIBLE //if we use GONE the text will align at the center as tvTitle is linked with flProgressBar(Rest Bar)
        binding?.tvTitle?.visibility=View.VISIBLE
        binding?.tvExercise?.visibility=View.INVISIBLE
        binding?.flExerciseView?.visibility=View.INVISIBLE
        binding?.ivImage?.visibility=View.INVISIBLE
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress =0

        }
        setRestProgressBar()
    }
    private fun setUpExerciseView(){
        binding?.flRestView?.visibility= View.INVISIBLE //if we use GONE the text will align at the center as tvTitle is linked with flProgressBar(Rest Bar)
        binding?.tvTitle?.visibility=View.INVISIBLE
        binding?.tvExercise?.visibility=View.VISIBLE
        binding?.flExerciseView?.visibility=View.VISIBLE
        binding?.ivImage?.visibility=View.VISIBLE
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        speakOut(exerciseList!![currentExercisePosition].getname())
        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExercise?.text=exerciseList!![currentExercisePosition].getname()
        setExerciseProgressBar()
    }
    private fun setRestProgressBar(){
        binding?.flRestView?.progress=restProgress

        binding?.tvTitle?.text="Get Ready For ${exerciseList!![currentExercisePosition+1].getname()}"
        restTimer=object :CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.flRestView?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setUpExerciseView()
            }

        }.start()

    }
    private fun setExerciseProgressBar(){
        binding?.progressbarExercise?.progress=exerciseProgress

        exerciseTimer=object :CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressbarExercise?.progress=30-exerciseProgress
                binding?.tvTimerExercise?.text=(30-exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition<exerciseList?.size!!-1){
                    setupRestView()
                }
                else{
                    Toast.makeText(this@ExerciseActivity,"Congratulations! You have completed the 7 minutes workout",Toast.LENGTH_SHORT).show()
                }
            }

        }.start()

    }


    override fun onDestroy() {
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress =0
        }
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        //shutting down the tts feature when activity is destroyed
        //START
        if(player!=null){
            player!!.stop()
        }
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
        binding=null

    }
}