package com.l_george.test_cofee.ui.fragments

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.l_george.test_cofee.R
import com.l_george.test_cofee.app.CoffeeApp
import com.l_george.test_cofee.databinding.FragmentMapBinding
import com.l_george.test_cofee.ui.viewModels.locationsViewModel.LocationViewModel
import com.l_george.test_cofee.ui.viewModels.locationsViewModel.LocationViewModelFactory
import com.l_george.test_cofee.utils.ANIMATION_MAP_DURATION
import com.l_george.test_cofee.utils.AuthError
import com.l_george.test_cofee.utils.BUNDLE_LOCATION_ID
import com.l_george.test_cofee.utils.makeToast
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
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
                    addTapListener { _, _ ->
                        findNavController().navigate(
                            R.id.action_mapFragment_to_menuFragment,
                            Bundle().apply {
                                putInt(BUNDLE_LOCATION_ID, coffeeShop.id)
                            })
                        true
                    }
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
                    Animation(Animation.Type.SMOOTH, ANIMATION_MAP_DURATION),
                    null
                )
            }
        }

        locationViewModel.errorLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it is AuthError) {
                    findNavController().navigate(R.id.authFragment)
                }
                requireContext().makeToast(it.message)
            }
        }

        return binding.root
    }


    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


    private fun getPlaceMarkIcon(title: String): Bitmap {
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
        canvas.drawText(title, (canvas.width / 2).toFloat(), canvas.height.toFloat() - 20, paint)

        return bitmap
    }


}