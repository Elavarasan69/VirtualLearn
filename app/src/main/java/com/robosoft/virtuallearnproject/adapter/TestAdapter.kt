package com.robosoft.virtuallearnproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.communicator.Communicator
import com.robosoft.virtuallearnproject.dataclass.test.TestResponseData
import kotlinx.android.synthetic.main.fragment_login.view.*

class TestAdapter(val data: TestResponseData,
                  private val communicator: Communicator,
                  val context: Context?
) :
    RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    private var answer = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_test_screen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.totalQuestion.text = " of " + data?.totalQuestions.toString()
        holder.question.text = data?.questions?.get(position).toString()
        holder.currentQuestion.text =
            "Question " + (data?.questions?.indexOf(data.questions?.get(position)))?.plus(1)
                .toString()
        holder.optionOne.text = data?.options?.get(position)?.get(0)
        holder.optionTwo.text = data?.options?.get(position)?.get(1)
        holder.optionThree.text = data?.options?.get(position)?.get(2)
        holder.optionFour.text = data?.options?.get(position)?.get(3)

        holder.optionOne.isClickable = true
        holder.optionTwo.isClickable = true
        holder.optionThree.isClickable = true
        holder.optionFour.isClickable = true

            holder.optionGroup.setOnCheckedChangeListener { radioGroup, i ->
                when (i) {
                    holder.optionOne.id -> {
                        holder.optionTwo.isClickable = false
                        holder.optionThree.isClickable = false
                        holder.optionFour.isClickable = false
                        answer?.add(position, 0)
                        holder.optionOne.isClickable = false
                    }
                    holder.optionTwo.id -> {
                        holder.optionOne.isClickable = false
                        holder.optionThree.isClickable = false
                        holder.optionFour.isClickable = false
                        answer?.add(position, 1)
                        holder.optionTwo.isClickable = false

                    }
                    holder.optionThree.id -> {
                        holder.optionTwo.isClickable = false
                        holder.optionOne.isClickable = false
                        holder.optionFour.isClickable = false
                        answer?.add(position, 2)
                        holder.optionThree.isClickable = false
                    }
                    holder.optionFour.id -> {
                        holder.optionTwo.isClickable = false
                        holder.optionThree.isClickable = false
                        holder.optionOne.isClickable = false
                        answer?.add(position, 3)
                        holder.optionFour.isClickable = false
                    }
                }
            }
        communicator.getOptions(answer)
    }

    override fun getItemCount(): Int {
        return data.totalQuestions
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currentQuestion: TextView = itemView.findViewById(R.id.current_of_questions)!!
        val totalQuestion: TextView = itemView.findViewById(R.id.no_of_questions)!!
        val question: TextView = itemView.findViewById(R.id.question_tv)!!
        val optionGroup: RadioGroup = itemView.findViewById(R.id.option_group)!!
        val optionOne: RadioButton = itemView.findViewById(R.id.option_one)!!
        val optionTwo: RadioButton = itemView.findViewById(R.id.option_two)!!
        val optionThree: RadioButton = itemView.findViewById(R.id.option_three)!!
        val optionFour: RadioButton = itemView.findViewById(R.id.option_four)!!
    }
}