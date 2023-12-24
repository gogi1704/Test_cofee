package com.l_george.test_cofee.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
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
    private lateinit var imageProvider: ImageProvider
    private lateinit var collections: MapObjectCollection
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

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
        imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.icon_point)
        locationViewModel =
            ViewModelProvider(this, locationViewModelFactory)[LocationViewModel::class.java]
//        fusedLocation = LocationServices.getFusedLocationProviderClient(requireActivity())
//        requestPermissionLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted ->
//            if (isGranted) {
//               ///TODO
//            } else {
//                Toast.makeText(
//                    requireContext(),
//                    "Sorry, but i need a grant!",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//
//        }

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
                    setIcon(imageProvider)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        checkPermissions()
//        requestPermissionLauncher = getPermissionLauncher()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

//    private fun checkPermissions() {
//        when (PackageManager.PERMISSION_GRANTED
//        ) {
//            ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) -> {
//               // todo
//            }
//
//            else -> {
//                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//            }
//        }
//
//    }

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


}