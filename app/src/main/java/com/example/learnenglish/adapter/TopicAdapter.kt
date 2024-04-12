package com.example.learnenglish.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.learnenglish.R
import com.example.learnenglish.contract.TaskContract
import com.example.learnenglish.model.TopicModel

class TopicAdapter(private val mList: ArrayList<TopicModel>, private val onClickItem: TaskContract.OnClickListener): RecyclerView.Adapter<TopicAdapter.TopicViewModel>() {
    inner class TopicViewModel(itemView: View): RecyclerView.ViewHolder(itemView){
        var imgTitle: ImageView = itemView.findViewById(R.id.img_topictitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.topic_layout, parent, false)
        return TopicViewModel(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: TopicViewModel, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.tv_title).text = mList[position].title
        }
//        Glide.with(holder.itemView.context)
//            .load(mList[position].img)
//            .into(holder.imgTitle)
        holder.itemView.setOnClickListener {
            onClickItem.onClickListenerItemHome(position)
        }
    }
}