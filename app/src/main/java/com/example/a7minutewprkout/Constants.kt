package com.example.a7minutewprkout

import android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo

object Constants {

    fun defaultExerciseList():ArrayList<ExerciseModel>{
        val exerciseList =ArrayList<ExerciseModel>()
        val jumpingJacks=ExerciseModel(1,"Jumping Jacks",R.drawable.jumpingjacks,false,false)
        exerciseList.add(jumpingJacks)
        val dips=ExerciseModel(2,"Dips",R.drawable.dips,false,false)
        val kneehigh=ExerciseModel(3,"Knee High", R.drawable.kneehigh,false,false)
        val lunges=ExerciseModel(4,"Lunges",R.drawable.lunges,false,false)
        val plank=ExerciseModel(5,"Plank",R.drawable.plank,false,false)
        val pushup=ExerciseModel(6,"Push Up",R.drawable.pushup,false,false)
        val pushupandRoation=ExerciseModel(7,"Push Up and Rotation",R.drawable.pushupandrotation,false,false)
        val sidePlank=ExerciseModel(8,"Side Plank",R.drawable.sideplank,false,false)
        val squat=ExerciseModel(9,"Squat",R.drawable.squat,false,false)
        val stepUp=ExerciseModel(10,"Step Up", R.drawable.stepup,false,false)
        val wallsit=ExerciseModel(11,"Wall Sit Up",R.drawable.wallsit,false,false)
        val burpees=ExerciseModel(12,"Burpeees",R.drawable.bp,false,false)
        exerciseList.add(dips)
        exerciseList.add(kneehigh)
        exerciseList.add(lunges)
        exerciseList.add(plank)
        exerciseList.add(pushup)
        exerciseList.add(pushupandRoation)
        exerciseList.add(sidePlank)
        exerciseList.add(squat)
        exerciseList.add(stepUp)
        exerciseList.add(wallsit)
        exerciseList.add(burpees)



        return exerciseList
    }

}