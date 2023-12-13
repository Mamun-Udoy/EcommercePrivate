package com.example.ecommerce.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.ecommerce.databinding.FragmentProductDetailsBinding
import com.example.ecommerce.db.entities.CheckOutItemEntity
import com.example.ecommerce.network.model.ProductsResponse
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding

    private lateinit var dataItem: ProductsResponse.Product

    private val viewModel by viewModels<CheckOutViewModel>()

    private val viewModelCheckOutItemInsertViewModel by activityViewModels<CheckOutItemInsertViewModel>() // sharedViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (arguments != null) {
            val dataStr = arguments?.getString("data_item")
            dataItem = Gson().fromJson(dataStr, ProductsResponse.Product::class.java)
            Log.d("check_item", "check item value $dataStr")
        }


        val carousel: ImageCarousel = binding.carousel
        carousel.registerLifecycle(lifecycle)
        val list = mutableListOf<CarouselItem>()



        for (item in dataItem.images!!) {
            list.add(
                CarouselItem(
                    imageUrl = item,
                )
            )
        }
        carousel.setData(list)

        binding.discount.text = "Discount: "+ dataItem.discount.toString() +"%"
        binding.brand.text ="Brand: " + dataItem.brand.toString()
        binding.categories.text ="Categories: " + dataItem.categories.toString()
        binding.stock.text ="Stock: " +dataItem.stock.toString()
        binding.rating.text ="Rating: "+ dataItem.rating.toString()


        binding.checkout.setOnClickListener {

            Toast.makeText(context, "clicked checkout", Toast.LENGTH_SHORT).show()

            Log.d("id_test", "onViewCreated: id: ${dataItem.id}")
            //inserting checkout item into database
            viewModel.insertCheckoutItem(
                checkOutItemEntity = getCheckoutItem(dataItem),
                context = requireContext()
            )
            val updatedSize = context?.let { it1 -> viewModel.getCheckoutItemsSize(it1) }
            if (updatedSize != null) {
                viewModelCheckOutItemInsertViewModel.updateDatabaseSize(updatedSize)
            }

        }




    }


    private fun getCheckoutItem(item: ProductsResponse.Product): CheckOutItemEntity {
        return CheckOutItemEntity(
            id = item.id.toLong(),
            itemId = item.id,
            discount = item.discount?.toString(),
            rating = item.rating.toFloat(),
            stock = item.stock,
            brand = item.brand,
            category = item.categories,
            thumbnail = item.thumbnail,
            title = item.title,
            price = item.price
        )
    }


}