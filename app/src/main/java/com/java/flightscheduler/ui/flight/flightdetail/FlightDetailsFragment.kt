package com.java.flightscheduler.ui.flight.flightdetail

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.FlightDetailsPricingBinding
import com.java.flightscheduler.databinding.FragmentFlightDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FlightDetailsFragment : Fragment(),View.OnClickListener {
    private val args by navArgs<FlightDetailsFragmentArgs>()
    private val flightDetailsViewModel : FlightDetailsViewModel by viewModels()
    private lateinit var pricingBinding : FlightDetailsPricingBinding
    private lateinit var binding : FragmentFlightDetailBinding
    private lateinit var flightDetailsAdapter : FlightDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flight_detail,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val flightOffer : FlightOffer = args.offer
        initializeFlightResults(flightOffer)
        initializeViews(flightOffer)
    }

    private fun initializeFlightResults(flightOffer: FlightOffer) {
        val layoutManager = LinearLayoutManager(context)
        binding.rvFlightDetail.layoutManager = layoutManager
        binding.rvFlightDetail.setHasFixedSize(true)
        flightDetailsViewModel.getSegments(flightOffer).observe(viewLifecycleOwner,{
            flightDetailsAdapter = context?.let { FlightDetailsAdapter(flightOffer, it) }!!
            binding.rvFlightDetail.adapter = flightDetailsAdapter
        })
    }

    private fun initializeViews(flightOffer: FlightOffer) {
        binding.txtFlightDetailSource.text = flightOffer.source
        binding.txtFlightDetailSeatCount.text = flightOffer.numberOfBookableSeats.toString()
        binding.txtFlightDetailPrice.text = flightOffer.price?.total.toString()
        binding.txtFlightDetailTicketingDate.text = flightOffer.lastTicketingDate.toString()
        binding.layoutFlightDetailInfo.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            binding.layoutFlightDetailInfo.id -> initializePriceInfo(args.offer)
        }
    }

    private fun initializePriceInfo(flightOffer: FlightOffer) {
        val dialog = Dialog(requireContext())

        pricingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(dialog.context),
            R.layout.flight_details_pricing_info_layout,
            null,
            false
        )

        pricingBinding.txtFlightDetailsPricingBase.text = flightOffer.price?.base.toString()
        pricingBinding.txtFlightDetailsPricingCurrency.text = flightOffer.price?.currency.toString()
        pricingBinding.txtFlightDetailsPricingGrandTotal.text = flightOffer.price?.grandTotal.toString()
        pricingBinding.txtFlightDetailsPricingSupplier.text = flightOffer.price?.fees?.get(0)?.amount.toString()
        pricingBinding.txtFlightDetailsPricingTicketing.text = flightOffer.price?.fees?.get(1)?.amount.toString()
        pricingBinding.txtFlightDetailsPricingTotal.text = flightOffer.price?.total.toString()

        val btnOK = pricingBinding.btnFlightDetailsPricingOk
        btnOK.setOnClickListener { dialog.dismiss() }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setTitle(getString(R.string.text_pricing_info))
        dialog.setContentView(pricingBinding.root)
        dialog.show()
    }
}