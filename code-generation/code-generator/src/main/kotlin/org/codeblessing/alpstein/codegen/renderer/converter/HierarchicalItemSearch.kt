package org.codeblessing.alpstein.codegen.renderer.converter

import org.codeblessing.alpstein.model.schema.Item
import org.codeblessing.alpstein.model.schema.ItemId

object HierarchicalItemSearch {

    fun findAllItemNames(rootItem: Item, allItems: List<Item>): Set<ItemId> {
        val result = mutableSetOf<ItemId>()
        val visited = mutableSetOf<ItemId>()
        val stack = ArrayDeque<ItemId>()

        // Create lookup map for O(1) item access
        val itemsByName = allItems.associateBy { it.itemId }

        // Start with root item
        stack.addLast(rootItem.itemId)

        while (stack.isNotEmpty()) {
            val currentItemName = stack.removeLast()

            // Skip if already visited (cycle detection)
            if (currentItemName in visited) {
                continue
            }

            // Mark as visited and add to result
            visited.add(currentItemName)
            result.add(currentItemName)

            // Find the item in the schema
            val currentItem = itemsByName[currentItemName] ?: continue

            // Find all nested items: attributes with an ItemId type that are NOT a reference.
            // A reference also carries an ItemId, but it points at a foreign item that is only
            // stored by its primary key, so it must not be pulled in as a nested item.
            currentItem.attributes
                .filter { !it.isReference }
                .map { it.type }
                .filterIsInstance<ItemId>()
                .filter { it !in visited }
                .forEach { stack.addLast(it) }
        }

        return result
    }

}
