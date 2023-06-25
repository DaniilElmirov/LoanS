package com.example.a2023_q2_elmirov.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.a2023_q2_elmirov.domain.entity.Loan

class LoanListAdapter(
    private val onClick: (loanId: Long) -> Unit,
) : ListAdapter<Loan, LoanViewHolder>(LoanDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder =
        LoanViewHolder(parent, onClick)

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val loan = getItem(position)
        holder.bind(loan)
    }
}