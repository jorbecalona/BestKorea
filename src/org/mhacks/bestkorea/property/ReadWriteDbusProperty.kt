package org.mhacks.bestkorea.property

import org.mhacks.bestkorea.model.nfc.DBusModel

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 20/05/15
 * (C) 2015 Damian Wieczorek
 */
class ReadWriteDbusProperty<T>(name: String) : ReadOnlyDbusProperty<T>(name) {
  fun set(thisRef: DBusModel<*>, desc: PropertyMetadata, value: T) = thisRef.set(name, value)
}