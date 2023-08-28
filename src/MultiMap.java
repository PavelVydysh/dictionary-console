import java.util.*;

class MultiMap<K, V>
{
    private Map<K, Collection<V>> map = new HashMap<>();
    /**
     * Добавление в Map
     * */
    public void put(K key, V value)
    {
        if (map.get(key) == null) {
            map.put(key, new ArrayList<V>());
        }

        map.get(key).add(value);
    }
    /**
     * Получение из Map
     * */
    public Collection<V> get(Object key) {
        return map.get(key);
    }
    /**
     * Получение всех ключей из Map
     * */
    public Set<K> keySet() {
        return map.keySet();
    }
    /**
     * Удаление всех значений у заданного ключа
     * */
    public Collection<V> remove(Object key) {
        return map.remove(key);
    }
    /**
     * Удаление конкретного значения у заданного ключа
     * */
    public boolean remove(K key, V value)
    {
        if (map.get(key) != null)
            return map.get(key).remove(value);

        return false;
    }
    /**
     * Проверка на пустой Map
     * */
    public boolean isEmpty() {
        return map.isEmpty();
    }

}