package com.example.ecommerce.ui.fragment

import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.CartItemBinding
import com.example.ecommerce.db.entities.CheckOutItemEntity


import kotlin.math.roundToInt

class CartAdapter(
    cartItem: ArrayList<CheckOutItemEntity>,
    private val clickListener: ItemClickListener
) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {


    var cartItemUpdated: MutableList<CheckOutItemEntity> = mutableListOf()

    private var countMap = HashMap<Int, Int>()


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


        var save = item.discount?.let {
            item.price?.toFloat()?.div(100)?.times(item!!.discount!!.toFloat())
        }


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

        var disPrice = discountedPrice
        holder.binding.plus.setOnClickListener {
            count = countMap[position] ?: 1

            count++
            if (count < 11) {
                if (disPrice != null) {
                    disPrice += discountedPrice!!
                    total(disPrice.toInt())
                }
                countMap[position] = count
                holder.binding.count.text = countMap[position].toString()
                clickListener.increment(item,position)

            } else count--
        }
        holder.binding.minus.setOnClickListener {

            count = countMap[position] ?: 1
            count--
            if (count > 0) {
                if (disPrice != null) {
                    disPrice -= discountedPrice!!
                    total(disPrice.toInt())
                }
                countMap[position] = count
                holder.binding.count.text =countMap[position].toString()
                clickListener.decrement(item,position)
            } else count++
        }
    }



    fun getCountMap(): HashMap<Int, Int> {
        return countMap
    }


    fun updateList(items: List<CheckOutItemEntity>) {
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
        fun onItemDeleted(item: CheckOutItemEntity, position: Int)

        fun increment(item: CheckOutItemEntity, position: Int)

        fun decrement(item: CheckOutItemEntity, position: Int)


    }
}