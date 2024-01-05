package com.aj.currencyconverter.model.repo
import android.content.Context
import com.aj.currencyconverter.R
import com.aj.currencyconverter.model.data.SSLCertData
import com.aj.currencyconverter.utils.BaseApp
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object RetrofitClient {
    private const val BASE_URL = "https://api.apilayer.com/"
    val intercepter = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY

    }

    val sslCertData : SSLCertData by lazy {
        sslCertificate()
    }



    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("apikey", "xWy8VY8KRx6QRD26AhCUe5CqSvMwc3Me")
                return@Interceptor chain.proceed(builder.build())
            }
        )
        addInterceptor(intercepter)
        sslSocketFactory(sslCertData.sslSocketFactory, sslCertData.trustManager)
    }.build()


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun providApiService() : ApiService{
        return  retrofit.create(ApiService::class.java)
    }


    fun sslCertificate(): SSLCertData {
        val bundledSelfSignedCert: Certificate = CertificateFactory.getInstance("X.509")
            .generateCertificate(BaseApp.appContext.resources.openRawResource(R.raw.ssl_cert))
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", bundledSelfSignedCert)
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)

        val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
        val trustManager = trustManagers[0] as X509TrustManager
        /* check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
             "Unexpected default trust managers:" + Arrays.toString(
                 trustManagers
             )
         }*/
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagerFactory.trustManagers, null)
        return SSLCertData(sslContext.socketFactory, trustManager)
    }
}