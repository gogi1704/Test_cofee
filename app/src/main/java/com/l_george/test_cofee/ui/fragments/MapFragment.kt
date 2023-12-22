package com.l_george.test_cofee.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.l_george.test_cofee.R
import com.l_george.test_cofee.databinding.FragmentMapBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var activity: MainActivity
    private lateinit var mapView: MapView
    private lateinit var imageProvider: ImageProvider

    override fun onStart() {
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
        activity = requireActivity() as MainActivity
        imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.icon_point)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        mapView = binding.mapView
        activity.topBarSettings(true, getString(R.string.map_title))
        activity.findViewById<ImageButton>(R.id.button_back).apply {
            setOnClickListener {
                activity.navigateUp()
            }
        }
        val placeMark = mapView.map.mapObjects.addPlacemark().apply {
            geometry = Point(44.1, 22.2)
            setIcon(imageProvider , IconStyle())
        }

        return binding.root
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


}