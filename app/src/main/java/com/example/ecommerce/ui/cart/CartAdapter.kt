package com.example.ecommerce.ui.cart

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.CartItemBinding
import com.example.ecommerce.ui.check_out_item.CheckOutItem


import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitDataModel
import kotlin.math.roundToInt

class CartAdapter(
    cartItem: ArrayList<CheckOutItem>,
    private val clickListener: CartAdapter.ItemClickListener
) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

//    val cartItemUpdated: ArrayList<ProductEntity> = arrayListOf()

    var cartItemUpdated: MutableList<CheckOutItem> = mutableListOf()

    init {
        cartItemUpdated.addAll(cartItem)
    }

    inner class MyViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    var count = 0;
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = cartItemUpdated[position]
        holder.binding.data = item

        // this is done for add margin top for the first item in recyclerview and add margin bottom for the last item for recyclerview
        if (position == 0) {
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.topMargin = 4.dpToPx()  // Convert dp to pixels
            holder.itemView.layoutParams = layoutParams
        }

        // Set margin bottom if the item is the last one
        if (position == cartItemUpdated.size - 1) {
            val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = 4.dpToPx()  // Convert dp to pixels
            holder.itemView.layoutParams = layoutParams
        }

//        holder.binding.productName.text = item.title
//
//        holder.binding.price.text = item.price.toString()
//        holder.binding.discountPrice.text = item.discount.toString()
        Glide.with(holder.binding.productImage.context)
            .load("${item.thumbnail}")
            .into(holder.binding.productImage)

        holder.binding.delete.setOnClickListener {
            clickListener.onItemDeleted(item, position)
        }

        holder.binding.plus.setOnClickListener {
            count++
            if (count < 11)
                holder.binding.count.text = count.toString()
            else count--
        }
        holder.binding.minus.setOnClickListener {
            count--
            if (count > 0)
                holder.binding.count.text = count.toString()
            else count++
        }
    }

    fun updateList(items: List<CheckOutItem>) {
        cartItemUpdated.clear()
        cartItemUpdated.addAll(items)
        Log.d("size_db", "addItem: size: ${cartItemUpdated.size}")
        notifyDataSetChanged()
    }

    fun Int.dpToPx(): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (this * density).roundToInt()
    }


    override fun getItemCount(): Int {
        return cartItemUpdated.size
    }

    interface ItemClickListener {
        fun onItemDeleted(item: CheckOutItem, position: Int)

    }
}