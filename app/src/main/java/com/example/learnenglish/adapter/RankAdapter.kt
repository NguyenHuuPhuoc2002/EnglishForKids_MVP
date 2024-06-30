package com.example.learnenglish.adapter

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnenglish.R
import com.example.learnenglish.model.UserModel

class RankAdapter(private val mList: List<UserModel>): RecyclerView.Adapter<RankAdapter.RankViewModel>() {
//private val rank: Int,
    inner class RankViewModel(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rank_layout, parent, false)
        return RankViewModel(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onBindViewHolder(holder: RankViewModel, position: Int) {
        holder.itemView.apply {
            val newPos =  position + 1
            if(newPos == 1){
                findViewById<ImageView>(R.id.img_rank).visibility = View.VISIBLE
                findViewById<TextView>(R.id.tv_rank).visibility = View.GONE
                findViewById<ImageView>(R.id.img_rank).setImageResource(R.drawable.img_rank1)
                findViewById<TextView>(R.id.tv_name).text = mList[position].name
                findViewById<TextView>(R.id.tv_point).text = mList[position].point.toString()
            }else if(newPos == 2){
                findViewById<ImageView>(R.id.img_rank).visibility = View.VISIBLE
                findViewById<TextView>(R.id.tv_rank).visibility = View.GONE
                findViewById<ImageView>(R.id.img_rank).setImageResource(R.drawable.img_rank2)
                findViewById<TextView>(R.id.tv_name).text = mList[position].name
                findViewById<TextView>(R.id.tv_point).text = mList[position].point.toString()
            }
            else if(newPos == 3){
                findViewById<ImageView>(R.id.img_rank).visibility = View.VISIBLE
                findViewById<TextView>(R.id.tv_rank).visibility = View.GONE
                findViewById<ImageView>(R.id.img_rank).setImageResource(R.drawable.img_rank3)
                findViewById<TextView>(R.id.tv_name).text = mList[position].name
                findViewById<TextView>(R.id.tv_point).text = mList[position].point.toString()
            }else{
                findViewById<TextView>(R.id.tv_rank).visibility = View.VISIBLE
                findViewById<ImageView>(R.id.img_rank).visibility = View.GONE
                findViewById<TextView>(R.id.tv_name).text = mList[position].name
                findViewById<TextView>(R.id.tv_point).text = mList[position].point.toString()
                findViewById<TextView>(R.id.tv_rank).text = newPos.toString()
            }
        }
    }
}