package org.mhacks.bestkorea.model.remote

import org.mhacks.bestkorea.model.nfc.NfcTag
import java.util.*

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 24/05/15
 * (C) 2015 Damian Wieczorek
 */
data class Event(val mac: String,
                 val tag: NfcTag,
                 val date: Date = Date())