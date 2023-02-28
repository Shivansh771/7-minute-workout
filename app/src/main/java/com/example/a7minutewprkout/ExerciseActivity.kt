package com.example.a7minutewprkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.a7minutewprkout.databinding.ActivityExercise2Binding

class ExerciseActivity : AppCompatActivity() {
    private var binding:ActivityExercise2Binding?=null
    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExercise2Binding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbarExercise?.setNavigationOnClickListener{
            onBackPressed()
        }
setupRestView()
    }
    private fun setupRestView(){
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress =0

        }
        setRestProgressBar()
    }
    private fun setUpExerciseView(){
        binding?.flprogress?.visibility= View.INVISIBLE //if we use GONE the text will align at the center as tvTitle is linked with flProgressBar(Rest Bar)
        binding?.tvTitle?.text="Exercise Name"
        binding?.flExerciseView?.visibility=View.VISIBLE
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        setExerciseProgressBar()
    }
    private fun setRestProgressBar(){
        binding?.progressbar?.progress=restProgress
        restTimer=object :CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressbar?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
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
                Toast.makeText(this@ExerciseActivity,"30 seconds are over lets go to the rest view",Toast.LENGTH_LONG).show()

            }

        }.start()

    }


    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null){
            restTimer?.cancel()
            restProgress =0
        }
        if(exerciseTimer!=null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        binding=null
    }
}