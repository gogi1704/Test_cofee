package com.l_george.test_cofee.ui.fragments

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.l_george.test_cofee.R
import com.l_george.test_cofee.app.CoffeeApp
import com.l_george.test_cofee.databinding.FragmentMapBinding
import com.l_george.test_cofee.ui.viewModels.locationsViewModel.LocationViewModel
import com.l_george.test_cofee.ui.viewModels.locationsViewModel.LocationViewModelFactory
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import javax.inject.Inject


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var activity: MainActivity
    private lateinit var mapView: MapView
    private lateinit var collections: MapObjectCollection

    @Inject
    lateinit var locationViewModelFactory: LocationViewModelFactory
    private lateinit var locationViewModel: LocationViewModel


    override fun onStart() {
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
        activity = requireActivity() as MainActivity
        (requireActivity().applicationContext as CoffeeApp).component.inject(this)
        locationViewModel =
            ViewModelProvider(this, locationViewModelFactory)[LocationViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        mapView = binding.mapView
        collections = mapView.mapWindow.map.mapObjects.addCollection()

        activity.topBarSettings(true, getString(R.string.map_title))
        activity.findViewById<ImageButton>(R.id.button_back).apply {
            setOnClickListener {
                activity.navigateUp()
            }
        }


        locationViewModel.listCoffeeShopLiveData.observe(viewLifecycleOwner) { coffeeList ->
            for (coffeeShop in coffeeList) {
                collections.addPlacemark().apply {
                    geometry = coffeeShop.point
                    setIcon(ImageProvider.fromBitmap(getPlaceMarkIcon(coffeeShop.name)))
                    addTapListener(object : MapObjectTapListener {
                        override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean {
                            createDialog(coffeeShop.name, coffeeShop.id)
                            return true
                        }
                    })
                }
            }
            if (coffeeList.isNotEmpty()) {
                mapView.mapWindow.map.move(
                    CameraPosition(
                        coffeeList[0].point,
                        8f,
                        1f,
                        1f,
                    ),
                    Animation(Animation.Type.SMOOTH, 2f),
                    null
                )
            }
        }

        return binding.root
    }


    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


    private fun createDialog(title: String, coffeeShopId: Int) {
        AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.icon_point)
            .setTitle(title)
            .setMessage(getString(R.string.coffee_menu_title))
            .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                findNavController()//navigate with id todo
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.dismiss)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun getPlaceMarkIcon(title:String):Bitmap{
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.icon_point)
        val bitmap = Bitmap.createBitmap(150, 200, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 30f
            textAlign = Paint.Align.CENTER
            typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_black)
        }

        drawable?.setBounds(0, 0, 150, 150)
        drawable?.draw(canvas)
        canvas.drawText(title, (canvas.width / 2).toFloat(), canvas.height.toFloat()-20, paint)

        return bitmap
    }


}