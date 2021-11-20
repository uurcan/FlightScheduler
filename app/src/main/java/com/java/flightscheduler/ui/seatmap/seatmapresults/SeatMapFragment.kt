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
class SeatMapFragment : BaseFragment<SeatMapViewModel, FragmentSeatMapBinding>(R.layout.fragment_seat_map){
    override val viewModel: SeatMapViewModel? by viewModels()
    private val args by navArgs<SeatMapFragmentArgs>()
    private var seatMapAdapter: SeatMapAdapter? = null

    override fun onBind() {
        val seatMapIndex = args.seatMapSearch.legs - 1
        viewModel?.getSeatMapFromFlightOffer(args.seatMapSearch)?.observeOnce { seatMap ->
            setLayoutManager(seatMap[seatMapIndex])
            viewModel?.seatMapHeader(seatMap[seatMapIndex])
            seatMapAdapter = seatMap[seatMapIndex].decks?.get(0)?.let {
                SeatMapAdapter(it) { seat ->
                    showDetails(seat)
                }
            }
            binding?.rvSeatMap?.adapter = seatMapAdapter
            binding?.seatMapViewModel = viewModel
        }
    }

    private fun showDetails(it: Seat) {
        MessageHelper.displayInfoMessage(view,it.number + " " + it.travelerPricing?.get(0)?.price?.totalPrice)
    }

    private fun setLayoutManager(seatMap : SeatMap) {
        val layoutManager = GridLayoutManager(context, seatMap.decks?.get(0)?.deckConfiguration?.width ?: DECK_SMALL)
        binding?.rvSeatMap?.layoutManager = layoutManager
    }
}