package com.example.ecommerce.ui.cart

import android.content.res.Resources
import android.graphics.Paint
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


    var cartItemUpdated: MutableList<CheckOutItem> = mutableListOf()


    inner class MyViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    var count = 1;
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = cartItemUpdated[position]
        holder.binding.data = item

        var discount = item.discount

        var price = item.price


        var save = discount?.let { item.price?.toFloat()?.div(100)?.times(discount.toFloat()) }


        holder.binding.price.text = "Price " + item?.price.toString()

        fun price(save: Float) {
            var discountedPrice = save?.let { item?.price?.minus(it) }
        }

        fun discountedPrice(save: Float) {
            var save = discount?.let { item.price?.toFloat()?.div(100)?.times(discount.toFloat()) }
        }


        var discountedPrice = save?.let { item?.price?.minus(it) }

        holder.binding.discountPrice.text =
            "Discounted Price " + formatToTwoDecimalPlaces(discountedPrice?.toFloat()).toString()


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


        Glide.with(holder.binding.productImage.context)
            .load("${item.thumbnail}")
            .into(holder.binding.productImage)

        holder.binding.delete.setOnClickListener {
            clickListener.onItemDeleted(item, position)
        }

        holder.binding.total.text = "Total " + "${formatToTwoDecimalPlaces(discountedPrice)}"



        fun total(value: Int) {
            holder.binding.total.text = "Total $value"
        }

        var disPrice =discountedPrice
        holder.binding.plus.setOnClickListener {
            count++

            if (count < 11){
                if(disPrice!=null){
                    disPrice += discountedPrice!!
                    total(disPrice.toInt())
                }
                holder.binding.count.text = count.toString()
            }
            else count--
        }
        holder.binding.minus.setOnClickListener {

            count--
            if (count > 0){
                if(disPrice!=null){
                    disPrice -= discountedPrice!!
                    total(disPrice.toInt())
                }
                holder.binding.count.text = count.toString()
            }
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

    fun formatToTwoDecimalPlaces(value: Float?): String? {
        return value?.let {
            String.format("%.2f", it)
        }
    }

    interface ItemClickListener {
        fun onItemDeleted(item: CheckOutItem, position: Int)

    }
}