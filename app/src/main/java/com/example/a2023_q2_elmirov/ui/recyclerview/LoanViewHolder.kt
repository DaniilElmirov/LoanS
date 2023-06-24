package com.example.a2023_q2_elmirov.ui.recyclerview

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a2023_q2_elmirov.R
import com.example.a2023_q2_elmirov.databinding.ItemLoanBinding
import com.example.a2023_q2_elmirov.domain.entity.Loan
import com.example.a2023_q2_elmirov.domain.entity.LoanStatus.APPROVED
import com.example.a2023_q2_elmirov.domain.entity.LoanStatus.REGISTERED
import com.example.a2023_q2_elmirov.domain.entity.LoanStatus.REJECTED

class LoanViewHolder(
    parent: ViewGroup,
    private val onClick: (Loan) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_loan, parent, false)
) {

    private val binding = ItemLoanBinding.bind(itemView)

    fun bind(loan: Loan) {
        itemView.setOnClickListener {
            onClick(loan)
        }

        with(binding) {
            tvFirstName.text = loan.firstName
            tvLastName.text = loan.lastName

            tvStatus.text = loan.status.toString()
            when (loan.status) {
                APPROVED -> tvStatus.setTextColor(ColorStateList.valueOf(Color.GREEN))

                REJECTED -> tvStatus.setTextColor(ColorStateList.valueOf(Color.RED))

                REGISTERED -> tvStatus.setTextColor(ColorStateList.valueOf(Color.BLACK))
            }

            tvAmount.text = loan.amount.toString()
            tvPercent.text = loan.percent.toString()
        }
    }
}