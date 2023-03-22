package com.example.a7minutewprkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutewprkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW="METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW="US_UNIT_VIEW"
    }

    private var binding:ActivityBmiBinding?=null
    private var currentVisibleView:String= METRIC_UNITS_VIEW
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="Calculate BMI"

        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressed()
        }
        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener{_,checkedId: Int->

            if(checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }
            else{
                makeVisibleUscUnitsView()
            }
        }
        binding?.btnCalculateUnits?.setOnClickListener{
            calculateUnits()
        }

    }
    private fun makeVisibleMetricUnitsView(){
        currentVisibleView= METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility=View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility=View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility=View.GONE
        binding?.tilMetricUsUnitHeightFeet?.visibility=View.GONE
        binding?.tilMetricUsUnitHeightInch?.visibility=View.GONE
        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()
        binding?.llDiplayBMIResult?.visibility=View.INVISIBLE
    }
    private fun makeVisibleUscUnitsView(){
        currentVisibleView= US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility=View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility=View.INVISIBLE
        binding?.tilUsMetricUnitWeight?.visibility=View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility=View.VISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility=View.VISIBLE
        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()
        binding?.llDiplayBMIResult?.visibility=View.INVISIBLE
    }
    private fun displayBMIResults(bmi:Float){

        binding?.llDiplayBMIResult?.visibility= View.VISIBLE
        val bmiLabel:String
        val bmiDescription:String
        if(bmi.compareTo(15f)<=0){
            bmiLabel="Very severely underweight"
            bmiDescription="Opps! You really need to take better care of youself! Eat more!"
        }
        else if(bmi.compareTo(15f)>0 && bmi.compareTo(16f)<=0){
            bmiLabel="Severely underweight"
            bmiDescription="Opps! You really need to take better care of youself! Eat more!"
        }
        else if(bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0){
            bmiLabel="Underweight"
            bmiDescription="Opps! You really need to take better care of youself! Eat more!"
        }
        else if(bmi.compareTo(18.5f)>0 && bmi.compareTo(25f)<=0){
            bmiLabel="Normal"
            bmiDescription="Congratulations! You are in good shape!"
        }
        else if(bmi.compareTo(25f)>0 && bmi.compareTo(30f)<=0){
            bmiLabel="Overweight"
            bmiDescription="Opps! You really need to take care of yourself! Workout"
        }
        else if(bmi.compareTo(30f)>0 && bmi.compareTo(35f)<=0){
            bmiLabel="Obese Class |(Moderately obese)"
            bmiDescription="Opps! You really need to take care of yourself! Workout"
        }
        else if(bmi.compareTo(35f)>0 && bmi.compareTo(40f)<=0){
            bmiLabel="Obese Class || (Severely obese)"
            bmiDescription="OMG! You are in a very dangerous condition! Act now!"
        }
        else{
            bmiLabel="Obese Class ||| (Very Severely obese)"
            bmiDescription="OMG! You are in very dangerous condition! Act now!"
        }
        val bmivalue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.llDiplayBMIResult?.visibility=View.VISIBLE
        binding?.tvBMIValue?.text=bmivalue
        binding?.tvBMIType?.text=bmiLabel
        binding?.tvBMIDescription?.text=bmiDescription
    }

    private fun validateMetricUnits():Boolean{
        var isvalid=true
        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isvalid=false
        }else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isvalid=false

        }
        return isvalid
    }
    private fun calculateUnits(){
        if(currentVisibleView== METRIC_UNITS_VIEW){
            if(validateMetricUnits()){
                val heightvalue:Float=binding?.etMetricUnitHeight?.text.toString().toFloat() /100
                val weightvalue:Float=binding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi= weightvalue/(heightvalue*heightvalue)
                displayBMIResults(bmi)

            }else{
                Toast.makeText(this@BMIActivity,"Please Enter valid values.",Toast.LENGTH_SHORT).show()
            }

        }
        else{
            if(validateUSUnits()){
                val usUnitsHeightValueFeet:String=binding?.etUsMetricUnitHeightFeet?.text.toString()
                val usUnitsHeightValueInch:String=binding?.etUsMetricUnitHeightInch?.text.toString()
                val usUnitWeightValue:Float=binding?.etUsMetricUnitWeight?.text.toString().toFloat()
                val heightValue=usUnitsHeightValueInch.toFloat()+usUnitsHeightValueFeet.toFloat()*12
                val bmi =703* (usUnitWeightValue/(heightValue*heightValue))
                displayBMIResults(bmi)
            }else{
                Toast.makeText(this@BMIActivity,"Please enter valid values.",Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun validateUSUnits():Boolean{
        var isvalid=true
        when{
            binding?.etUsMetricUnitWeight?.text.toString().isEmpty()->{
                isvalid=false
            }
            binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty()->{
                isvalid=false
            }
            binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty()->{
                isvalid=false
            }
        }
        return isvalid
    }
}