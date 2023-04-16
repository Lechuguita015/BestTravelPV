package com.example.besttravel.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
    private fun displayHotel(hotelItem: HotelsResponse?) {

        binding.tvName.text = hotelItem!!.name
        binding.tvDesc.text = hotelItem.description
        binding.btnPhone.setOnClickListener {
            val phone = hotelItem.phone
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }


        binding.btnLocation.setOnClickListener {
            val url = hotelItem.address
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        val imageList = ArrayList<SlideModel>()
        for (i in 0 until hotelItem.images.size)
        {
            imageList.add(SlideModel(hotelItem.images[i].urlImage))
        }

        binding.imageSlider.setImageList(imageList)
    }

    private fun displayRestaurant(restaurantItem: RestaurantsResponse?) {

        binding.tvName.text = restaurantItem!!.name
        binding.tvDesc.text = restaurantItem.description
        binding.btnPhone.setOnClickListener {
            val phone = restaurantItem.phone
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
        }

        binding.btnLocation.setOnClickListener {
            val url = restaurantItem.address
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        val imageList = ArrayList<SlideModel>()
        for (i in 0 until restaurantItem.images.size)
        {
            imageList.add(SlideModel(restaurantItem.images[i].urlImage))
        }

        binding.imageSlider.setImageList(imageList)
    }

    private fun displayBeach(beachItem: BeachesResponse?) {

        binding.tvName.text = beachItem!!.name
        binding.tvDesc.text = beachItem.description
        binding.btnPhone.visibility = View.GONE

        binding.btnLocation.setOnClickListener {
            val url = beachItem.address
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        val imageList = ArrayList<SlideModel>()
        for (i in 0 until beachItem.images.size)
        {
            imageList.add(SlideModel(beachItem.images[i].urlImage))
        }

        binding.imageSlider.setImageList(imageList)
    }
}