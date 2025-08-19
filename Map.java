package HashTable;

import java.util.List;

/**
 * This interface represents a map of keys to values. It cannot contain
 * duplicate keys, and each key can map to at most one value.
 * 
 * @author Ian Kramer, Jason Khong
 * @version July 4, 2024
 *
 * @param <K> - placeholder for key type
 * @param <V> - placeholder for value type
 */
public interface Map<K, V> {

	/**
	 * Removes all mappings from this map.
	 * 
	 */
	public void clear();

	/**
	 * Determines whether this map contains the specified key.
	 * 
	 * O(1)
	 * 
	 * @param key - the key being searched for
	 * @return true if this map contains the key, false otherwise
	 */
	public boolean containsKey(K key);

	/**
	 * Determines whether this map contains the specified value.
	 * 
	 * O(table length)
	 * 
	 * @param value - the value being searched for
	 * @return true if this map contains one or more keys to the specified value,
	 *         false otherwise
	 */
	public boolean containsValue(V value);

	/**
	 * Returns a list view of the mappings contained in this map, where the ordering of 
	 * mappings in the list is insignificant.
	 * 
	 * O(table length)
	 * 
	 * @return a list containing all mappings (i.e., entries) in this map
	 */
	public List<MapEntry<K, V>> entries();

	/**
	 * Gets the value to which the specified key is mapped.
	 * 
	 * O(1)
	 * 
	 * @param key - the key for which to get the mapped value
	 * @return the value to which the specified key is mapped, or null if this map
	 *         contains no mapping for the key
	 */
	public V get(K key);

	/**
	 * Determines whether this map contains any mappings.
	 * 
	 * O(1)
	 * 
	 * @return true if this map contains no mappings, false otherwise
	 */
	public boolean isEmpty();


	/**
	 * Associates the specified value with the specified key in this map.
	 * (I.e., if the key already exists in this map, resets the value; 
	 * otherwise adds the specified key-value pair.)
	 * 
	 * O(1)
	 * 
	 * @param key - the key for which to update the value (if exists)
	 *              or to be added to the table
	 * @param value - the value to be mapped to the key
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key
	 */
	public V put(K key, V value);

	/**
	 * Removes the mapping for a key from this map if it is present.
	 * 
	 * O(1)
	 *
	 * @param key - the key to be removed
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key
	 */
	public V remove(K key);

	/**
	 * Determines the number of mappings in this map.
	 * 
	 * O(1)
	 * 
	 * @return the number of mappings in this map
	 */
	public int size();
}