package org.mhacks.bestkorea.common

/**
 * @author Damian Wieczorek {@literal <damian@farmlogs.com>}
 * @since 19/05/15
 * (C) 2015 Damian Wieczorek
 */
fun <T: Any?> List<T>.mapToStrings() = mapNotNull { it.toString() }
