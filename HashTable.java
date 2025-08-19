package HashTable;

import java.util.ArrayList;
import java.util.List;

/**
 * An Implementation of a HashTable that use Double Hashing when Collision Occurs
 * @param <K> - Key
 * @param <V> - Value
 *
 * @author - Jason Khong and Ian Kramer
 * @version - July 11, 2024
 */
public class HashTable<K, V> implements Map<K, V>{
    private ArrayList<MapEntry<K, V>> table;
    private int[] primeNumberList;
    private int size, capacity;
    private boolean[] deleted;

    /**
     * Constructor for our HashTable.
     */
    public HashTable() {
        this.table = new ArrayList<MapEntry<K, V>>();
        this.primeNumberList = new int[] {11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853, 25717, 51437, 102877, 205759,
                411527, 823117, 1646237, 3292489, 6584983, 13169977, 26339969, 52679969, 105359939, 210719881,
                421439783, 842879579};
        this.size = 0;
        this.capacity = 11;


        for(int i = 0; i < capacity; i++){
            table.add(null);
        }


        deleted = new boolean[capacity]; // Change to getting a new prime number later
        for(int i = 0; i < capacity; i ++){
            deleted[i] = false;
        }

    }

    /**
     * Removes all mappings from this map.
     * <p>
     * O(table length)
     */
    @Override
    public void clear() {
        for(int i = 0; i < table.size(); i++){
            table.set(i, null);
        }
        this.size = 0;
    }

    /**
     * Determines whether this map contains the specified key.
     * <p>
     * O(1)
     *
     * @param key - the key being searched for
     * @return true if this map contains the key, false otherwise
     */
    @Override
    public boolean containsKey(K key){
        int hash = Math.abs(key.hashCode());
        int index = hash % table.size();

        while(table.get(index) != null || deleted[index]){
            if(!deleted[index] && table.get(index).getKey().equals(key)){
                return true;
            }

            index = Math.abs((index + hashCode2(hash)) % capacity);
        }
        return false;
    }

    /**
     * Determines whether this map contains the specified value.
     * <p>
     * O(table length)
     *
     * @param value - the value being searched for
     * @return true if this map contains one or more keys to the specified value,
     * false otherwise
     */
    @Override
    public boolean containsValue(V value) {
        for(int i = 0; i < table.size(); i++){
            MapEntry<K, V> current = table.get(i);
            if(current != null && current.getValue().equals(value) && !deleted[i]){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list view of the mappings contained in this map, where the ordering of
     * mappings in the list is insignificant.
     * <p>
     * O(table length)
     *
     * @return a list containing all mappings (i.e., entries) in this map
     */
    @Override
    public List<MapEntry<K, V>> entries() {
        List<MapEntry<K,V>> entries = new ArrayList<>();
        for(int i = 0; i < table.size(); i++){
            if(table.get(i) != null){
                entries.add(table.get(i));
            }
        }
        return entries;
    }

    /**
     * Gets the value to which the specified key is mapped.
     * <p>
     * O(1)
     *
     * @param key - the key for which to get the mapped value
     * @return the value to which the specified key is mapped, or null if this map
     * contains no mapping for the key
     */
    @Override
    public V get(K key) {
        int hash = Math.abs(key.hashCode());
        int index = hash % table.size();

        while(table.get(index) != null || deleted[index]) {
            if (!deleted[index] && table.get(index).getKey().equals(key)) {
                return table.get(index).getValue();
            }
            index = (index + hashCode2(hash)) % capacity;
        }
        return table.get(index).getValue();

    }

    /**
     * Determines whether this map contains any mappings.
     * <p>
     * O(1)
     *
     * @return true if this map contains no mappings, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * (I.e., if the key already exists in this map, resets the value;
     * otherwise adds the specified key-value pair.)
     * <p>
     * O(1)
     *
     * @param key   - the key for which to update the value (if exists)
     *              or to be added to the table
     * @param value - the value to be mapped to the key
     * @return the previous value associated with key, or null if there was no
     * mapping for key
     */
    @Override
    public V put(K key, V value){
        if((double) size / capacity >= 0.75){
            rehash();
        }

        int hash = Math.abs(key.hashCode());
        int index = hash % table.size();

        while(table.get(index) != null || deleted[index]){
            if(!deleted[index] && table.get(index).getKey().equals(key)){
                V oldValue = table.get(index).getValue();
                table.set(index, new MapEntry<>(key, value));
                return oldValue;
            }
            index = Math.abs((index + hashCode2(hash)) % capacity);
        }

        table.set(index, new MapEntry<>(key, value));
        deleted[index] = false;
        size ++;
        return null;

    }

    /**
     * Removes the mapping for a key from this map if it is present.
     * <p>
     * O(1)
     *
     * @param key - the key to be removed
     * @return the previous value associated with key, or null if there was no
     * mapping for key
     */
    @Override
    public V remove(K key) {
        int hash = Math.abs(key.hashCode());
        int index = hash % table.size();

        while(table.get(index) != null || deleted[index]) {
            if (!deleted[index] && table.get(index).getKey().equals(key)) {
                V oldValue = table.get(index).getValue();
                table.set(index, null);
                deleted[index] = true;
                size--;
                return oldValue;
            }
            index = Math.abs((index + hashCode2(hash)) % capacity);
        }

        if(table.get(index) == null){
            return null;
        }
        else {
            size--;
            deleted[index] = true;
            return table.get(index).getValue();
        }

    }

    /**
     * Determines the number of mappings in this map.
     * <p>
     * O(1)
     *
     * @return the number of mappings in this map
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Helper Method for double hashing
     * @return - number of how many indecies we need to go.
     */
    private int hashCode2(int index){
        return 1 + (index % (capacity - 2));
    }

    /**
     * A Private helper Method to Rehash the Hash Table when we get past the capacity.
     */
    private void rehash() {
        ArrayList<MapEntry<K, V>> oldTable = table;
        int index = 0;
        for (int num : primeNumberList) {
            if (capacity == primeNumberList[index]) {
                capacity = primeNumberList[index + 1];
                break;
            }
            index++;
        }
        table = new ArrayList<>(capacity);
        deleted = new boolean[capacity];
        for (int i = 0; i < capacity; i++) {
            table.add(null);
            deleted[i] = false;
        }
        size = 0;
        for (MapEntry<K, V> entry : oldTable) {
            if (entry != null) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }
}
