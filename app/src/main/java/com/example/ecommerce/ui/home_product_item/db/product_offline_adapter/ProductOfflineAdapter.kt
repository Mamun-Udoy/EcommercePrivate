package com.example.ecommerce.ui.home_product_item.db.product_offline_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ProductItemBinding
import android.graphics.Paint
import com.example.ecommerce.ui.home_product_item.network_retrofit.RetrofitDataModel

class ProductOfflineAdapter(
    productItem: ArrayList<RetrofitDataModel.Product>
): RecyclerView.Adapter<ProductOfflineAdapter.MyViewHolder>() {

    var productOffline : MutableList<RetrofitDataModel.Product> = mutableListOf()

    init {
        productOffline.addAll(productItem)
        notifyDataSetChanged()
    }



    inner class MyViewHolder(val binding:ProductItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context),false)
//        return MyViewHolder(binding)

        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productOffline.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = productOffline[position]
        holder.binding.data = item


        holder.binding.price.text = item.price.toString()
        holder.binding.price.paintFlags = holder.binding.price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        holder.binding.discountedPrice.text =item.discount.toString()



        Glide.with(holder.binding.image.context)
            .load("${item.thumbnail}")
            .into(holder.binding.image)

    }
}