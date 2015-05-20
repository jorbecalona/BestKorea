package org.mhacks.bestkorea.property

import org.mhacks.bestkorea.model.DBusModel

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 20/05/15
 * (C) 2015 Damian Wieczorek
 */
open class ReadOnlyDbusProperty<T>(val name: String) {
  fun get(thisRef: DBusModel<*>, desc: PropertyMetadata): T = thisRef.get(name)
}