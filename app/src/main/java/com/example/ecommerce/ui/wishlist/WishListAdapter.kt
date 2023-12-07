package com.example.ecommerce.ui.wishlist

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.ecommerce.databinding.WishlistItemBinding
import com.example.ecommerce.ui.check_out_item.CheckOutItem

import kotlin.math.roundToInt

class WishListAdapter(wishListitem: ArrayList<WishListEntity>, private val clickListener: WishListAdapter.ItemClickListener, private val addtocart: WishListAdapter.AddtoCart) :
    RecyclerView.Adapter<WishListAdapter.MyViewHolder>() {


    var wishListItemUpdated: MutableList<WishListEntity> = mutableListOf()


    inner class MyViewHolder(val binding: WishlistItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            WishlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return wishListItemUpdated.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = wishListItemUpdated[position]
        holder.binding.data = item

        var discount = item.discount


        var save = discount?.let { item.price?.toFloat()?.div(100)?.times(discount.toFloat()) }


        holder.binding.price.text = "Price " + item?.price.toString()


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
        if (position == wishListItemUpdated.size - 1) {
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

        holder.binding.addcart.setOnClickListener {
            addtocart.onAddToCart(item,position)
        }

    }

    fun updateList(items: List<WishListEntity>) {
        wishListItemUpdated.clear()
        wishListItemUpdated.addAll(items)
        Log.d("size_db", "addItem: size: ${wishListItemUpdated.size}")
        notifyDataSetChanged()
    }

    fun Int.dpToPx(): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (this * density).roundToInt()
    }


    fun formatToTwoDecimalPlaces(value: Float?): String? {
        return value?.let {
            String.format("%.2f", it)
        }
    }


    interface ItemClickListener {
        fun onItemDeleted(item: WishListEntity, position: Int)

    }

    interface  AddtoCart{
        fun onAddToCart(item: WishListEntity, position: Int)
    }
}