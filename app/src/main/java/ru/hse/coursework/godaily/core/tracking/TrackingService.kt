package ru.hse.coursework.godaily.core.tracking

import io.appmetrica.analytics.AppMetrica
import java.util.UUID

class TrackingService {
    fun trackRouteDetailsOpen(routeId: UUID?, routeName: String?) {
        AppMetrica.reportEvent(
            "route_card_open",
            mapOf("route_id" to routeId, "route_name" to routeName)
        )
    }
}