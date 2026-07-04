package com.calleserpis.coffeetracker.ui.screens.account

import android.content.Context
import com.calleserpis.coffeetracker.R
import com.google.android.gms.common.api.CommonStatusCodes

fun googleSignInErrorMessage(context: Context, statusCode: Int): String {
    return when (statusCode) {
        CommonStatusCodes.DEVELOPER_ERROR -> context.getString(R.string.account_error_developer)
        CommonStatusCodes.NETWORK_ERROR -> context.getString(R.string.account_error_network)
        CommonStatusCodes.INTERNAL_ERROR -> context.getString(R.string.account_error_internal)
        CommonStatusCodes.CANCELED -> context.getString(R.string.account_error_canceled)
        else -> context.getString(R.string.account_error_generic, statusCode)
    }
}
