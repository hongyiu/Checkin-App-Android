package com.example.mobileappproject

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class PlaceAdapter(private val context: Context, private val placeList: MutableList<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    private val _inflater: LayoutInflater = LayoutInflater.from(context)
    private var _placeList = placeList
    private var _rowListener: PlaceRowListener = context as PlaceRowListener

    //Set click event on whole item
    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener { fun onItemClick(position: Int) }
    fun setOnItemClickListener(listener: OnItemClickListener) { mListener = listener }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_rows, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name: String = _placeList[position].placeName.toString()
        val desc: String = _placeList[position].placeDesc.toString()
        val isFav: Boolean = _placeList[position].isFav.toString().toBoolean()
        val objectId: String = _placeList[position].objectId.toString()

        holder.name.text = name
        holder.desc.text = desc
        holder.isFav.isChecked = isFav
        holder.isFav.setOnClickListener{ _rowListener.onFavClick(objectId, !isFav) }
        holder.removeBtn.setOnClickListener{ _rowListener.onPlaceDelete(objectId, name) }
    }

    override fun getItemCount(): Int {
        return _placeList.size
    }

    class ViewHolder(ItemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.tvPlaceName) as TextView
        val desc: TextView = itemView.findViewById(R.id.tvPlaceDesc) as TextView
        val isFav: CheckBox = itemView.findViewById(R.id.imgPlaceFav) as CheckBox
        val removeBtn: ImageButton = itemView.findViewById(R.id.btnRemove) as ImageButton

        init {
            itemView.setOnClickListener{ listener.onItemClick(bindingAdapterPosition) }
        }
    }

}