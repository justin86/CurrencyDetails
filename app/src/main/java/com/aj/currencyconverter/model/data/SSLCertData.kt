package com.aj.currencyconverter.model.data

import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

data class SSLCertData(
    val sslSocketFactory: SSLSocketFactory,
    val trustManager: X509TrustManager
)
