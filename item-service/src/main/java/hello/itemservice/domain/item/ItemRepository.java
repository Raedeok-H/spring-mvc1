package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    private static final Map<Long, Item> store = new HashMap<Long, Item>();; //static 사용했다는 것에 주의, 실제로는 HashMap 쓰면 안 됨. -> 동시에 여러스레드가 접근하면 동시성문제가 있다. -> 다른 hashMap 사용
    private static long sequence = 0L; //static 사용에 주의

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<Item>(store.values());
    }

    public void update(long itemid, Item updateParam) {
        Item findItem = findById(itemid);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }


}
