package org.mhacks.bestkorea.api

import org.mhacks.bestkorea.model.Event
import retrofit.http.Body
import retrofit.http.GET
import retrofit.http.POST

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 19/05/15
 * (C) 2015 Damian Wieczorek
 */
trait ApiService {
  POST("/events") fun createEvent(Body event: Event): Event
}