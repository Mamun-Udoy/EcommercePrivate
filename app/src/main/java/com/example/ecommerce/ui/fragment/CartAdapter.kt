package com.example.ecommerce.ui.fragment

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.CartItemBinding
import com.example.ecommerce.db.entities.CheckOutItemEntity

class CartAdapter(
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    var cartItemUpdated: MutableList<CheckOutItemEntity> = mutableListOf()

    inner class MyViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = cartItemUpdated[position]
        holder.binding.data = item

        var discountedPrice = item.discount?.let {
            item.price?.toFloat()?.div(100)?.times(it.toFloat())
        }

        discountedPrice = item.price?.toFloat()?.minus(discountedPrice!!)

        setData(holder, item, discountedPrice)
        setImage(holder, item)

        with(holder.binding) {
            delete.setOnClickListener { clickListener.onItemDeleted(item, position) }
            plus.setOnClickListener {
                // increment
                val currentValue = count.text.toString().toIntOrNull()
                if (currentValue != null && currentValue <= 10) {
                    val updatedValue = currentValue+1
                    count.text = "$updatedValue"
                    item.qty = updatedValue
                    clickListener.updateItem(item)
                }

            }
            minus.setOnClickListener {
                // decrement
                val currentValue = count.text.toString().toIntOrNull()
                if (currentValue != null && currentValue > 1) {
                    val updatedValue = currentValue-1
                    count.text = "$updatedValue"
                    item.qty = updatedValue
                    clickListener.updateItem(item)
                }
            }
        }


    }
    private fun CartAdapter.setData(
        holder: MyViewHolder,
        item: CheckOutItemEntity,
        discountedPrice: Float?
    ) {
        with(holder.binding) {
            price.text = "Price ${item.price}"
            discountPrice.text = "Discounted Price ${formatToTwoDecimalPlaces(discountedPrice)}"
            total.text = "Total ${formatToTwoDecimalPlaces(discountedPrice)}"
            count.text = "${item.qty}"
        }
    }

    private fun setImage(
        holder: MyViewHolder,
        item: CheckOutItemEntity
    ) {
        Glide.with(holder.binding.productImage.context)
            .load(item.thumbnail)
            .into(holder.binding.productImage)
    }


    fun updateList(items: List<CheckOutItemEntity>) {
        cartItemUpdated.clear()
        cartItemUpdated.addAll(items)
        notifyDataSetChanged()
    }

    fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    private fun formatToTwoDecimalPlaces(value: Float?): String? = value?.let { "%.2f".format(it) }

    interface ItemClickListener {
        fun onItemDeleted(item: CheckOutItemEntity, position: Int)
        fun updateItem(item: CheckOutItemEntity)
    }

    override fun getItemCount(): Int = cartItemUpdated.size
}



