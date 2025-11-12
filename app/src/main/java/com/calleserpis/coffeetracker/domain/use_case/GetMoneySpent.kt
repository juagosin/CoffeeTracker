package com.calleserpis.coffeetracker.domain.use_case


import com.calleserpis.coffeetracker.domain.repository.CoffeeRepository

class GetMoneySpent( private val repository: CoffeeRepository) {
    suspend operator fun invoke(): Double{
        return repository.getSpentMoney()
    }
}