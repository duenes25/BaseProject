package org.kp.consumer.android.baseproject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.kp.consumer.android.baseproject.model.BaseItem

class MyAdapter(private val myDataset: Array<BaseItem>): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.recyclerview_item_row, parent, false)), View.OnClickListener{

        private var mTitleView: TextView? = null
        private var mSubTitleView: TextView? = null


        init {
            mTitleView = itemView.findViewById(R.id.itemTitle)
            mSubTitleView = itemView.findViewById(R.id.itemSubTitle)
        }

        fun bind(baseItem: BaseItem) {
                mTitleView?.text = baseItem.title
                mSubTitleView?.text = baseItem.subTitle
        }

        override fun onClick(v: View?) {
            Log.d("clicked", "Clicked")
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)

    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        val item : BaseItem = myDataset[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

}