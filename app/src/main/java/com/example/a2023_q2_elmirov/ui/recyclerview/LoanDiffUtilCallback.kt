package com.example.a2023_q2_elmirov.ui.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.a2023_q2_elmirov.domain.entity.Loan

class LoanDiffUtilCallback : DiffUtil.ItemCallback<Loan>() {

    override fun areItemsTheSame(oldItem: Loan, newItem: Loan): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Loan, newItem: Loan): Boolean =
        oldItem == newItem
}