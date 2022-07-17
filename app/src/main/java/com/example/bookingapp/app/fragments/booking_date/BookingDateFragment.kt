package com.example.bookingapp.app.fragments.booking_date

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentBookingDateBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.util.*

@AndroidEntryPoint
class BookingDateFragment : Fragment(R.layout.fragment_booking_date) {

    private val viewModel: BookingDateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookingDateBinding.bind(view)

        val dateAdapter = DateAdapter()

        //placeId - затычка
        dateAdapter.addList(viewModel.getDays(1))

        //dayId - затычка
        val periods = viewModel.getPeriods(1)

        with(binding) {
            for (time in periods) {
                val chip = layoutInflater.inflate(R.layout.chip_layout, groupDay, false) as Chip
                chip.text = time.timeStart.toStringDate("HH:mm") +
                        " - " + time.timeEnd.toStringDate("HH:mm")

                val timeStart = DateTime(time.timeStart)
                    .withZone(DateTimeZone.forID("Asia/Yekaterinburg"))
                    .hourOfDay()
                    .get()

                Log.e("time11", timeStart.toString())
                if (timeStart < 12)//если меньше 12 -> утреннее время
                    groupMorning.addView(chip)
                else if (timeStart < 17)//если меньше 17 -> дневное время
                    groupDay.addView(chip)
                else//-> вечернее время
                    groupEvening.addView(chip)
            }

            btnSelect.setOnClickListener {
                //переход дальше
            }

            recFilter.apply {
                adapter = dateAdapter
                layoutManager = LinearLayoutManager(
                    this@BookingDateFragment.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
        }
    }

    private fun Long.toStringDate(pattern: String): String {
        return DateTimeFormat
            .forPattern(pattern)
            .withZone(DateTimeZone.forID("Asia/Yekaterinburg"))
            .print(this)
    }
}