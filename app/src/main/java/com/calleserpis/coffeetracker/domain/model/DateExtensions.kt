package com.calleserpis.coffeetracker.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ============================================
// EXTENSIONES para formatear fechas
// ============================================

/**
 * Formatea el timestamp a "dd/MM/yyyy 'a las' HH:mm"
 */
fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}

/**
 * Solo la fecha: "27/10/2025"
 */
fun Long.toDateOnly(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}

/**
 * Solo la hora: "14:13"
 */
fun Long.toTimeOnly(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}