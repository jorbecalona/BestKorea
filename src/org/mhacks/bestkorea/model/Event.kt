package org.mhacks.bestkorea.model

import com.google.gson.annotations.Expose
import java.util.*

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 24/05/15
 * (C) 2015 Damian Wieczorek
 */
data class Event(Expose val mac: String,
                 Expose val records: List<NfcRecord>,
                 Expose val date: Date = Date())