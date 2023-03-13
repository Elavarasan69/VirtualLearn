package com.robosoft.virtuallearnproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.robosoft.virtuallearnproject.R
import com.robosoft.virtuallearnproject.databinding.ActivityMainBinding
import com.robosoft.virtuallearnproject.databinding.CardviewChapterTestBinding

class QuestionsAdapter : RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>() {

    class QuestionsViewHolder(val binding: CardviewChapterTestBinding ) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        return QuestionsViewHolder(CardviewChapterTestBinding.inflate(LayoutInflater.from(parent.context),parent,false))
}
    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        holder.binding.questionTV
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}