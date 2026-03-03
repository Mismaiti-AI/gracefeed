package com.gracefeed.core.domain.usecase

import com.gracefeed.core.data.payment.PaymentConfigService
import com.gracefeed.core.data.payment.PaymentService

class CheckFeatureAccessUseCase(
    private val configService: PaymentConfigService,
    private val paymentService: PaymentService
) {
    suspend operator fun invoke(featureId: String): Boolean {
        val entitlements = paymentService.observeCustomerInfo().value?.activeEntitlements ?: emptySet()
        return configService.isFeatureEnabled(featureId, entitlements)
    }
}
