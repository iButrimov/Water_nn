package com.example.water_nn.presentation.main.mainscreen.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.water_nn.data.database.entity.Address
import com.example.water_nn.databinding.AddressDeliveryItemBinding
import com.example.water_nn.presentation.lists.RecyclerAdapter

private val diffCallback = object : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem.address == newItem.address
    }

    override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
        return oldItem.equals(newItem)
    }
}

class AddressDeliveryAdapter(
    private val onItemClickListener: (Address) -> Unit
) : RecyclerAdapter<Address, AddressDeliveryAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: AddressDeliveryItemBinding) :
        RecyclerAdapter.ViewHolder<Address>(binding) {

        override fun bind(item: Address, position: Int) {
            binding.root.setOnClickListener {
                onItemClickListener(item)
            }
            binding.addressText.text = item.address
            binding.addressSelectCheckBox.isChecked = item.isSelected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AddressDeliveryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }
}