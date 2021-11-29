package com.java.flightscheduler.ui.seatmap.seatmapresults

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants.DECK_SMALL
import com.java.flightscheduler.data.model.seatmap.base.SeatMap
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat
import com.java.flightscheduler.databinding.FragmentSeatMapBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.utils.extension.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatMapFragment : BaseFragment<SeatMapViewModel, FragmentSeatMapBinding>(R.layout.fragment_seat_map) {
    override val viewModel: SeatMapViewModel? by viewModels()
    private val args by navArgs<SeatMapFragmentArgs>()
    private var seatMapAdapter: SeatMapAdapter? = null
    private val isComingFromSearchOffer: Boolean by lazy {
        args.seatMapSearch != null
    }

    override fun onBind() {
        if (isComingFromSearchOffer) {
            viewModel?.getSeatMapFromFlightOffer(args.seatMapSearch!!)?.observeOnce { seatMap ->
                val seatMapIndex: Int = args.seatMapSearch?.legs?.minus(1) ?: 0
                setViewForSeatMap(seatMap, seatMapIndex)
            }
        } else {
            viewModel?.getSeatMapFromJson(args.seatMapRequest!!)?.observeOnce { seatMap ->
                val seatMapIndex = 0
                setViewForSeatMap(seatMap, seatMapIndex)
            }
        }
    }

    private fun setViewForSeatMap(seatMap: List<SeatMap>, position: Int) {
        setLayoutManager(seatMap[position])
        viewModel?.seatMapHeader(seatMap[position])
        seatMapAdapter = seatMap[position].decks?.get(0)?.let {
            SeatMapAdapter(it) { seat ->
                showDetails(seat)
            }
        }
        binding?.rvSeatMap?.adapter = seatMapAdapter
        binding?.seatMapViewModel = viewModel
    }

    private fun showDetails(it: Seat) {
        MessageHelper.displayInfoMessage(view, it.number + " " + it.travelerPricing?.get(0)?.price?.totalPrice)
    }

    private fun setLayoutManager(seatMap: SeatMap) {
        val layoutManager = GridLayoutManager(context, seatMap.decks?.get(0)?.deckConfiguration?.width ?: DECK_SMALL)
        binding?.rvSeatMap?.layoutManager = layoutManager
    }
}