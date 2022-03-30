package com.example.water_nn.presentation.lists

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class RecyclerAdapter<TModel, TViewHolder : RecyclerAdapter.ViewHolder<TModel>>(
    diffCallback: DiffUtil.ItemCallback<TModel>? = null
) : RecyclerView.Adapter<TViewHolder>() {

    protected var data: List<TModel> = emptyList()

    abstract class ViewHolder<TModel>(view: View) : RecyclerView.ViewHolder(view) {
        constructor(binding: ViewBinding) : this(binding.root)

        abstract fun bind(item: TModel, position: Int)
    }

    override fun onBindViewHolder(holder: TViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int = data.size

    private val asyncListDiffer: AsyncListDiffer<TModel>? = diffCallback?.let {
        AsyncListDiffer(this, diffCallback)
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun setListData(newData: Collection<TModel>): Boolean {
        val newList = newData.toList()
        if (data == newList) return false

        data = newList
        asyncListDiffer?.submitList(data) ?: notifyDataSetChanged()
        return true
    }
}