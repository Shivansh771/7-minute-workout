package com.example.a7minutewprkout

import android.app.Dialog
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutewprkout.databinding.ActivityExercise2Binding
import com.example.a7minutewprkout.databinding.DialogCustomBackConfirmationBinding
import java.util.*
import kotlin.collections.ArrayList

class  ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private var binding:ActivityExercise2Binding?=null
    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0
    private var player:MediaPlayer?=null
    private var restTimerDuration:Long=10
    private var exerciseTimerDuration:Long=30
    private var exerciseAdapter: ExerciseStatusAdapter?=null
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
        setupExerciseStatusRecyclerView()
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

    override fun onBackPressed() {
        customDialogForBackButton()
        //super.onBackPressed()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding=DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.tvYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener{
            customDialog.dismiss()
        }
        customDialog.show()

    }
    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter=exerciseAdapter
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
        restTimer=object :CountDownTimer(restTimerDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.flRestView?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }

        }.start()

    }
    private fun setExerciseProgressBar(){
        binding?.progressbarExercise?.progress=exerciseProgress

        exerciseTimer=object :CountDownTimer(exerciseTimerDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressbarExercise?.progress=30-exerciseProgress
                binding?.tvTimerExercise?.text=(30-exerciseProgress).toString()
            }

            override fun onFinish() {

                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)

                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
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