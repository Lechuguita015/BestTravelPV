package com.example.besttravel.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.besttravel.models.beaches.BeachesResponse
import com.example.besttravel.models.hotels.HotelsResponse
import com.example.besttravel.models.restaurants.RestaurantsResponse
import com.example.besttravel.databinding.ActivityPlaceDetailsBinding


class PlaceDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (intent.getBooleanExtra("isHotel", false)) {
            val hotelItem = intent.getSerializableExtra("HotelItem") as HotelsResponse
            displayHotel(hotelItem)
        } else
            if (intent.getBooleanExtra("isRestaurant", false)) {
                val restaurantItem = intent.getSerializableExtra("RestaurantItem") as RestaurantsResponse
                displayRestaurant(restaurantItem)
            } else
                if (intent.getBooleanExtra("isBeach", false)) {
                    val beachItem = intent.getSerializableExtra("BeachItem") as BeachesResponse
                    displayBeach(beachItem)
                }

//        binding.ivBack.setOnClickListener {
//            onBackPressed()
//        }
    }
    private fun displayHotel(hotelItem: HotelsResponse?) {

        binding.tvName.text = hotelItem!!.name
        binding.tvDesc.text = hotelItem.description
        binding.tvPhone.text = hotelItem.phone
        binding.cvPhone.setOnClickListener {
            val phone = hotelItem.phone
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }
        binding.moreInfo.setOnClickListener {
            binding.tvDesc.maxLines = Integer.MAX_VALUE
            binding.moreInfo.visibility = View.GONE
            binding.lessInfo.visibility = View.VISIBLE
        }
        binding.lessInfo.setOnClickListener {
            binding.tvDesc.maxLines = 5
            binding.moreInfo.visibility = View.VISIBLE
            binding.lessInfo.visibility = View.GONE
        }
        binding.cvLocation.setOnClickListener {
            val url = hotelItem.address
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        binding.ivReturn.setOnClickListener {
            onBackPressed()
        }
        val imageList = ArrayList<SlideModel>()
        for (i in 0 until hotelItem.images.size)
        {
            val slideModel = SlideModel(hotelItem.images[i].urlImage, ScaleTypes.CENTER_CROP)
            imageList.add(slideModel)
        }

        binding.imageSlider.setImageList(imageList)
    }

    private fun displayRestaurant(restaurantItem: RestaurantsResponse?) {

        binding.tvName.text = restaurantItem!!.name
        binding.tvDesc.text = restaurantItem.description
        binding.tvPhone.text = restaurantItem.phone
        binding.cvPhone.setOnClickListener {
            val phone = restaurantItem.phone
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }

        binding.moreInfo.setOnClickListener {
            binding.tvDesc.maxLines = Integer.MAX_VALUE
            binding.moreInfo.visibility = View.GONE
            binding.lessInfo.visibility = View.VISIBLE
        }
        binding.lessInfo.setOnClickListener {
            binding.tvDesc.maxLines = 5
            binding.moreInfo.visibility = View.VISIBLE
            binding.lessInfo.visibility = View.GONE
        }
        binding.cvLocation.setOnClickListener {
            val url = restaurantItem.address
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        binding.ivReturn.setOnClickListener {
            onBackPressed()
        }
        val imageList = ArrayList<SlideModel>()
        for (i in 0 until restaurantItem.images.size)
        {
            val slideModel = SlideModel(restaurantItem.images[i].urlImage, ScaleTypes.CENTER_CROP)
            imageList.add(slideModel)
        }

        binding.imageSlider.setImageList(imageList)
    }

    private fun displayBeach(beachItem: BeachesResponse?) {

        binding.tvName.text = beachItem!!.name
        binding.tvDesc.text = beachItem.description
        binding.cvPhone.visibility = View.GONE

        binding.moreInfo.setOnClickListener {
            binding.tvDesc.maxLines = Integer.MAX_VALUE
            binding.moreInfo.visibility = View.GONE
            binding.lessInfo.visibility = View.VISIBLE
        }
        binding.lessInfo.setOnClickListener {
            binding.tvDesc.maxLines = 5
            binding.moreInfo.visibility = View.VISIBLE
            binding.lessInfo.visibility = View.GONE
        }

        binding.cvLocation.setOnClickListener {
            val url = beachItem.address
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        binding.ivReturn.setOnClickListener {
            onBackPressed()
        }
        val imageList = ArrayList<SlideModel>()
        for (i in 0 until beachItem.images.size)
        {
            val slideModel = SlideModel(beachItem.images[i].urlImage, ScaleTypes.CENTER_CROP)
            imageList.add(slideModel)
        }

        binding.imageSlider.setImageList(imageList)
    }
}