package com.example.learnenglish.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnenglish.R
import com.example.learnenglish.contract.TaskContract
import com.example.learnenglish.model.SkillModel

class SkillAdapter(private val mList: ArrayList<SkillModel>, private val onClickItem: TaskContract.OnClickListener): RecyclerView.Adapter<SkillAdapter.HomeViewModel>() {
    inner class HomeViewModel(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.skill_layout, parent, false)
        return HomeViewModel(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: HomeViewModel, position: Int) {
        holder.itemView.apply {
            findViewById<ImageView>(R.id.img_title).setImageResource(mList[position].img)
            findViewById<TextView>(R.id.tv_title).text = mList[position].title
        }
        holder.itemView.setOnClickListener {
            onClickItem.onClickListenerItemHome(position)
        }
    }
}