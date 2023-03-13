package com.robosoft.virtuallearnproject.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.dataclass.test.TestResultResponse

class ResultAdapter(val data: TestResultResponse) : RecyclerView.Adapter<ResultAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question: TextView = itemView.findViewById(R.id.question_tv)
        val answer: TextView = itemView.findViewById(R.id.answer_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_tab,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedOption = data?.selectedAndActualAnswerSet?.get(position)?.get(0).toString()
        val actualOption = data?.selectedAndActualAnswerSet?.get(position)?.get(1).toString()
        holder.question.text = "Question"+ (data?.questions?.indexOf(data.questions?.get(position)))?.plus(1)
            .toString()
        if(selectedOption == actualOption){
            holder.answer.text = "Correct Answer"
        }else {
            holder.answer.text = "Wrong Answer"
            holder.answer.setTextColor(Color.parseColor("#EA2626"))
        }
    }

    override fun getItemCount(): Int {
        return data.options.size
    }

}